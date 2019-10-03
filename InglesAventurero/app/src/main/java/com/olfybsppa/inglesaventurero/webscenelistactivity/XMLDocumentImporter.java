package com.olfybsppa.inglesaventurero.webscenelistactivity;

import com.olfybsppa.inglesaventurero.exceptions.TracedException;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;
import com.olfybsppa.inglesaventurero.worker.Worker;

import org.w3c.dom.Document;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLDocumentImporter extends Worker {
  private String urlString;
  private Document doc;

  XMLDocumentImporter (String urlString) {
    this.urlString = urlString;
  }

  @Override
  public void work() {
    URL url = null;
    URLConnection conn = null;
    try {
      url = new URL(urlString);
      conn = url.openConnection();
    } catch (IOException e) {
      TracedException tr =
        new TracedException(e, "XMLDocumentImporter url.openConnection;" +
                               "urlString: " + UriDeterminer.getScenId(urlString));
      throw tr;
    }
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder builder = factory.newDocumentBuilder();
      //TESTING DOWN CONNECTION. throw new IOException("Fake IO Exception");
      doc = builder.parse(conn.getInputStream());
      workDone = true;
    } catch (Exception e) {
      TracedException tr =
        new TracedException(e, "XMLDocumentImporter" +
                               " doc.parse " +
                               UriDeterminer.getScenId(urlString));
      throw tr;
    }
  }

  public Document getDocument () {
    return this.doc;
  }
}


