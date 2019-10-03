package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.start.LinesCP;

public class PictureInfoView extends LinearLayout {

  private String imageNameByArtist;
  private String artistName;
  private String licenseName;
  private String linkToLicense;
  private String changesMadeEnglish;
  private String changesMadeSpanish;
  private String imageFilename;
  private String urlName;
  private String urlLink;
  private String imageInfoname;

  private boolean inEnglish = false;

  public PictureInfoView(Context context, Bundle bundle, boolean inEnglish) {
    super(context);
    LayoutInflater.from(context).inflate(R.layout.picture_info, this, true);
    imageNameByArtist =  bundle.getString(LinesCP.artist_image_name);
    artistName =         bundle.getString(LinesCP.artist);
    licenseName =        bundle.getString(LinesCP.name_of_license);
    linkToLicense =      bundle.getString(LinesCP.link_to_license);
    changesMadeEnglish = bundle.getString(LinesCP.changes_english);
    changesMadeSpanish = bundle.getString(LinesCP.changes_spanish);
    imageFilename =      bundle.getString(LinesCP.artist_filename);
    urlName =            bundle.getString(LinesCP.image_url_name);
    urlLink =            bundle.getString(LinesCP.image_url);
    imageInfoname =      bundle.getString(LinesCP.image_info_name);
    setLanguage(inEnglish);
    showViews();
  }

  public void setLanguage(boolean inEnglish) {
    this.inEnglish = inEnglish;
  }

  public void showViews () {
    if (inEnglish) {
      setViewsInEnglish();
    }
    else {
      setViewsInSpanish();
    }
  }

  private void setHtmlInfoText (int viewId, String label, String info, String link) {
    TextView textView = (TextView)this.findViewById(viewId);
    StringBuilder builder = new StringBuilder(label);
    builder.append("<a href=\"")
      .append(link)
      .append("\">")
      .append(info)
      .append("</a>");
    textView.setText(Html.fromHtml(builder.toString()));
    textView.setLinkTextColor(getResources().getColor(R.color.dark_dark_blue));
    textView.setMovementMethod(LinkMovementMethod.getInstance());
    textView.setLinksClickable(true);
  }

  private void setInfoText (int viewId, String label, String info) {
    TextView textView = (TextView)this.findViewById(viewId);
    String text = label + info;
    textView.setText(text);
  }

  private void setViewsInEnglish () {
    setInfoText(R.id.title_from_artist, "Artist Title: ", imageNameByArtist);
    setInfoText(R.id.artist, "Artist: ", artistName);
    setInfoText(R.id.modifications, "Modifications: ", changesMadeEnglish);
    setInfoText(R.id.artist_image_filename, "Filename: ", imageFilename);
    setInfoText(R.id.ia_info_name, "ID: ", imageInfoname);
    setHtmlInfoText(R.id.link_to_image, "Link to Image: ", urlName, urlLink);
    setHtmlInfoText(R.id.license, "Licensed Under: ", licenseName, linkToLicense);
  }

  private void setViewsInSpanish () {
    setInfoText(R.id.title_from_artist, "Título por Artista: ", imageNameByArtist);
    setInfoText(R.id.artist, "Artista: ", artistName);
    setInfoText(R.id.modifications, "Modificaciones: ", changesMadeSpanish);
    setInfoText(R.id.artist_image_filename, "Archivo: ", imageFilename);
    setInfoText(R.id.ia_info_name, "ID: ", imageInfoname);
    setHtmlInfoText(R.id.link_to_image, "Enlace a Imagen: ", urlName, urlLink);
    setHtmlInfoText(R.id.license, "Licenciado Usando: ", licenseName, linkToLicense);
  }

}