package com.olfybsppa.inglesaventurero.webscenelistactivity;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class WebTitleView extends LinearLayout implements View.OnLongClickListener {
  private String sceneName;
  private String englishTitle;
  private String spanishTitle;

  @Override
  public boolean onLongClick(View v) {
    TextView englishDescriptionView = (TextView)v.findViewById(R.id.english_description);
    if (englishDescriptionView.getVisibility() == VISIBLE) {
      englishDescriptionView.setVisibility(GONE);
      englishDescriptionView.setEllipsize(TextUtils.TruncateAt.END);
      englishDescriptionView.setSingleLine(true);
      englishDescriptionView.setSelected(false);
    }
    else {
      englishDescriptionView.setVisibility(VISIBLE);
      englishDescriptionView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      englishDescriptionView.setSingleLine(true);
      englishDescriptionView.setMarqueeRepeatLimit(2);
      englishDescriptionView.setSelected(true);
    }
    return true;
  }

  private int difficulty;
  private int webId;

  public WebTitleView(Context context) {
    super(context);
    LayoutInflater.from(getContext()).inflate(R.layout.web_scene_title, this);
  }

  public WebTitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater.from(getContext()).inflate(R.layout.web_scene_title, this);
  }

  public int getDifficulty () {
    return this.difficulty;
  }


  public String getEnglishTitle () {
    return this.englishTitle;
  }

  public int getIdentifier() {
    return webId;
  }

  public String getSceneName() {
    return sceneName;
  }

  public String getSpanishTitle () {
    return  this.spanishTitle;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
    TextView dt = (TextView)findViewById(R.id.difficulty);
    dt.setText("" + difficulty);
  }

  public void setDownloaded (boolean downloaded) {
    TextView dt  = (TextView)findViewById(R.id.difficulty);
    TextView ed  = (TextView)findViewById(R.id.english_description);
    TextView st  = (TextView)findViewById(R.id.spanish_title);
    TextView sd  = (TextView)findViewById(R.id.spanish_description);
    ImageView iv = (ImageView)findViewById(R.id.aquired);
    if (downloaded) {
      dt.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      ed.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      st.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      sd.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      iv.setVisibility(VISIBLE);
      iv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_tag_light));
    }
    else {
      dt.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      ed.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      st.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      sd.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      iv.setVisibility(GONE);
    }
  }

  public void setEnglishDescription(String englishDescription) {
    TextView ed = (TextView)findViewById(R.id.english_description);
    ed.setText(englishDescription);
  }

  public void setEnglishTitle (String englishTitle) {
    this.englishTitle = englishTitle;
  }

  public void setSpanishDescription(String spanishDescription) {
    TextView sd = (TextView)findViewById(R.id.spanish_description);
    sd.setText(spanishDescription);
    sd.setVisibility(VISIBLE);
    sd.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    sd.setSingleLine(true);
    sd.setMarqueeRepeatLimit(2);
    sd.setSelected(true);
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public void setSpanishTitle (String spanishTitle) {
    TextView st = (TextView)findViewById(R.id.spanish_title);
    st.setText(spanishTitle);
    this.spanishTitle = spanishTitle;
  }

  public void setWebId (int webId) {
    this.webId = webId;
  }

  public void setCompleted (boolean completed) {
    TextView dt  = (TextView)findViewById(R.id.difficulty);
    TextView ed  = (TextView)findViewById(R.id.english_description);
    TextView st  = (TextView)findViewById(R.id.spanish_title);
    TextView sd  = (TextView)findViewById(R.id.spanish_description);
    ImageView iv = (ImageView)findViewById(R.id.aquired);
    if (completed) {
      dt.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      ed.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      st.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      sd.setTextColor(ContextCompat.getColor(getContext(), R.color.webListCompletedText));
      iv.setVisibility(VISIBLE);
      iv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_completed_light));
    }
    else {
      dt.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      ed.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      st.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      sd.setTextColor(ContextCompat.getColor(getContext(), R.color.webListText));
      iv.setVisibility(GONE);
      iv.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight));
    }    
  }
}