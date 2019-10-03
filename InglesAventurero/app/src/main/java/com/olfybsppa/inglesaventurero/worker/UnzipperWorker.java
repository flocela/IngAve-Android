package com.olfybsppa.inglesaventurero.worker;

import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.utils.Ez;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class UnzipperWorker extends Worker {

	private String mZipFileName; 
	private String mLocationForFiles;
	private LinkedList<WorkProgressListener> listeners = new LinkedList<>();

	private String UnzipperFailed = "Unzipper error: ";
	/* UnzipperWorker will overwrite any files that may already be in the ingles_
	   aventurero directory*/
	
	public UnzipperWorker(String zipFileFullName) {
		mZipFileName = zipFileFullName;
		mLocationForFiles = Ez.getPath(mZipFileName);
	}
	
	@Override
	public void work() {
		boolean doneReading = false;
		try {
			File file = new File(mZipFileName);
			if (!file.isFile())
				return;
			FileInputStream fin = new FileInputStream(mZipFileName); 
			ZipInputStream zin = new ZipInputStream(fin); 
			ZipEntry ze = null;
			while ((ze = zin.getNextEntry()) != null) {  
				if(ze.isDirectory()) { 
					File tempFile = new File(mLocationForFiles + ze.getName());
					tempFile.mkdirs();
				} 
				else {				  
					String path = mLocationForFiles + ze.getName();
					OutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
					int bufferSize = 16384;
					byte[] buffer = new byte[bufferSize];
					int len = 0;
					while ((len = zin.read(buffer)) != -1) {
						stream.write(buffer, 0, len);
						if (true == this.isCancelRequested()) {
							if(stream != null) {
								stream.close();
							}							
							zin.closeEntry();
							if (zin != null) {
								zin.close();
							}
							this.cancelCompleted = true;
							return;
						}
					}
					if (len == -1)
						doneReading = true;
					if(stream != null) {
						stream.close();
					}
					zin.closeEntry();
				}				
			}
			if (zin != null) {
				zin.close();
			}
		}
		catch(Exception e) {
			TracedException tracedException
				= new TracedException(e, UnzipperFailed +
				                         "location for files: " +
				                         mLocationForFiles +
				                         "phoneZipFile: " +
			                           mZipFileName);
			throw tracedException;
		}
		finally {
			File zipFile = new File(mZipFileName);
			if (zipFile.exists()) {
				zipFile.delete();
			}
			workDone = doneReading;
		}
	}

	public void addListener(WorkProgressListener listener) {
		listeners.add(listener);
	}
  
}
