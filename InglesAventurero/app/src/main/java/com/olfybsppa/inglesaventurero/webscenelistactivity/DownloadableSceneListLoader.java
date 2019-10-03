package com.olfybsppa.inglesaventurero.webscenelistactivity;


import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Xml;

import com.olfybsppa.inglesaventurero.collectors.SceneInfo;
import com.olfybsppa.inglesaventurero.exceptions.TracedException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * loads list of songs from web
 */
public class DownloadableSceneListLoader extends AsyncTaskLoader<List<SceneInfo>> {
	
	final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
  private String urlString;
  private List<SceneInfo> songInfos;
  private TracedException tracedException = null;
  
  public DownloadableSceneListLoader(Context context, String urlString) {
  	super(context); 	
  	this.urlString = urlString;
  }
  
  @Override 
  public List<SceneInfo> loadInBackground() {
		ResolverWrapper rw = new ResolverWrapper(getContext().getContentResolver());
		Collection<String> completedScenes = rw.retrieveCompletedScenes();
    Collection<String> activeScenes    = rw.retrieveActiveScenes();
  	URL url;
  	BufferedInputStream inStream = null;
  	List<SceneInfo> entries = null;
  	HttpURLConnection conn = null;
  	try {
  		url = new URL(urlString);
  		conn = (HttpURLConnection) url.openConnection();  		
  		conn.setReadTimeout(12000 /* milliseconds */);
      conn.setConnectTimeout(17000 /* milliseconds */);
      conn.setDoInput(true);
      conn.setRequestProperty("Accept","text/xml");
  		conn.connect();
  		inStream = new BufferedInputStream(conn.getInputStream()); 
  	  		
  		SetXmlParser xmlParser = new SetXmlParser();
  		entries = xmlParser.parse(inStream);
  		inStream.close();
  	}  	
  	catch (MalformedURLException e) {
  	  tracedException = new TracedException (e, "DownloadableSceneListLoader.loadInBackground();MalformedURLException.");
  	  return entries;
  	}
  	catch (IOException e) {
      tracedException = new TracedException (e, "DownloadableSceneListLoader.loadInBackground(). Hay posibilidad que no está connectado a la red o que " +
				"www.appsbyflo\\ingles_aventurero no está funcionando ahora mismo.");
      return entries;
  	} 
  	catch (XmlPullParserException e) {
      tracedException = new TracedException (e, "DownloadableSceneListLoader.loadInBackground();XmlPullParserException.");
      return entries;
		} 
  	finally {
  		if (null != inStream) {
  			try {
					inStream.close();
				} catch (IOException e) {
          // do nothing, the important exception was before.
				}
  		} 
  	}
    for (SceneInfo sceneInfo : entries) {
			if (completedScenes.contains(sceneInfo.getSceneName())) {
				sceneInfo.setCompleted(true);
			}
			else if (activeScenes.contains(sceneInfo.getSceneName())) {
				sceneInfo.setDownloaded(true);
			}
		}
  	return entries;
  }

  public TracedException getTracedException () {
    return tracedException;
  }

  public boolean exceptionOccurred () {
    return (tracedException == null)? false : true;
  }

  @Override 
  public void deliverResult(List<SceneInfo> preparedSetes) {
  	if (isStarted()) {
  		// If the Loader is currently started, we can immediately
  		// deliver its results.
  		super.deliverResult(preparedSetes);
  	}
  }
  
  protected void onReleaseResources(List<SceneInfo> apps) {}
    
  @Override 
  protected void onStartLoading() { // TODO does readySetzes really need to reload on a config change?
  	if (songInfos != null) {
  		deliverResult(songInfos);
  	}

  	// Has something interesting in the configuration changed since we
  	// last built the app list?
  	boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());

  	if (takeContentChanged() || songInfos == null || configChange) {
  		// If the data has changed since the last time it was loaded
  		// or is not currently available, start a load.
  		forceLoad();
  	}
  }
  
  // TODO this is probably not necessary.
  public static class InterestingConfigChanges {
  	final Configuration mLastConfiguration = new Configuration();
  	int mLastDensity;

  	boolean applyNewConfig(Resources res) {
  		int configChanges = mLastConfiguration.updateFrom(res.getConfiguration());
  		boolean densityChanged = mLastDensity != res.getDisplayMetrics().densityDpi;
  		if (densityChanged || (configChanges&(ActivityInfo.CONFIG_LOCALE
  				|ActivityInfo.CONFIG_UI_MODE|ActivityInfo.CONFIG_SCREEN_LAYOUT)) != 0) {
  			mLastDensity = res.getDisplayMetrics().densityDpi;
  			return true;
  		}
  		return false;
  	}
  }
  
  @Override 
  protected void onStopLoading() {
  	// Attempt to cancel the current load task if possible.
  	cancelLoad();
  }
  
  @Override 
  public void onCanceled(List<SceneInfo> apps) {
  	super.onCanceled(apps);
  	// At this point we can release the resources associated with 'apps'
  	// if needed.
  	onReleaseResources(apps);
  } 

  @Override 
  protected void onReset() {
  	super.onReset();

  	// Ensure the loader is stopped
  	onStopLoading();

  	// At this point we can release the resources associated with 'apps'
  	// if needed.
  	if (songInfos!= null) {
  		onReleaseResources(songInfos);
  		songInfos = null;
  	}
  }
  
  private class SetXmlParser {
		// TODO We don't use namespaces
		public List<SceneInfo> parse(InputStream in) throws XmlPullParserException, IOException {
			try {
				XmlPullParser parser = Xml.newPullParser();
				parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
				parser.setInput(in, null);
				parser.next();
				return readFeed(parser);
			} finally {
				in.close();
			}
		}

		private String getStringFromTag(XmlPullParser parser, String tag) 
		    throws XmlPullParserException, IOException {
      while (parser.getEventType() != XmlPullParser.START_TAG) {
        parser.next();
      }
      if (parser.getName().equals(tag)) {
        parser.next();
        return parser.getText();
      }
      return null;
    }
    
    private Integer getIntegerFromTag(XmlPullParser parser, String tag) 
        throws XmlPullParserException, IOException {
      while (parser.getEventType() != XmlPullParser.START_TAG) {
        parser.next();
      }
      if (parser.getName().equals(tag)) {
        parser.next();
        return Integer.parseInt(parser.getText());
      }
      return null;
    }
		
		private List<SceneInfo> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
			List<SceneInfo> entries = new ArrayList<SceneInfo>();
			
			do {
			  if (parser.getEventType() == XmlPullParser.START_TAG) {
			    Integer webId = null;
			    String filename = null;
          String englishTitle = null;
          String spanishTitle = null;
			    String difficulty = null;
					String sceneName = null;
					String spanishDescription = null;
					String englishDescription = null;

			    if (parser.getName().equals("english-description")) {
						englishDescription = getStringFromTag(parser, "english-description");
            englishTitle = getStringFromTag(parser, "english-title");
						webId = getIntegerFromTag(parser, "id");
						sceneName = getStringFromTag(parser, "scene-name");
						spanishDescription = getStringFromTag(parser, "spanish-description");
						spanishTitle = getStringFromTag(parser, "spanish-title");
						difficulty = getStringFromTag(parser, "type-1");
            SceneInfo sceneInfo = new SceneInfo();
            sceneInfo.setWebId(webId);
						sceneInfo.setSceneName(sceneName);
            sceneInfo.setEnglishTitle(englishTitle);
            sceneInfo.setSpanishTitle(spanishTitle);
            sceneInfo.setFilename(filename);
            sceneInfo.setDifficulty(difficulty);
						sceneInfo.setEnglishDescription(englishDescription);
						sceneInfo.setSpanishDescription(spanishDescription);
			      entries.add(sceneInfo);
			    }			
			  }
				parser.next();				
			}while(parser.getEventType() != XmlPullParser.END_DOCUMENT);
						
			return entries;
		}
			
	}
}