package com.olfybsppa.inglesaventurero.openingactivity;


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

public class SceneTitleView extends LinearLayout implements View.OnLongClickListener {
  private String sceneName;
  private String englishTitle;
  private String spanishTitle;

  public SceneTitleView(Context context){
    super(context);
    LayoutInflater.from(context).inflate(R.layout.scene_title, this);
  }

  public SceneTitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
    LayoutInflater.from(context).inflate(R.layout.scene_title, this);
  }

  public String getEnglishTitle () {
    return this.englishTitle;
  }

  public String getSpanishTitle () {
    return  this.spanishTitle;
  }

  public String getSceneName() {
    return sceneName;
  }

  public void setEngDescription(String engDescription) {
    TextView sv = (TextView)findViewById(R.id.english_description);
    sv.setText(engDescription);
  }

  public void setSceneName(String sceneName) {
    this.sceneName = sceneName;
  }

  public void setSpnDescription(String spnDescription) {
    TextView sv = (TextView)findViewById(R.id.spanish_description);
    sv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    sv.setSingleLine(true);
    sv.setMarqueeRepeatLimit(2);
    sv.setSelected(true);
    sv.setText(spnDescription);
  }

  public void setEnglishTitle (String englishTitle) {
    this.englishTitle = englishTitle;
  }

  public void setSpanishTitle (String spanishTitle) {
    TextView st = (TextView)findViewById(R.id.spanish_title);
    st.setText(spanishTitle);
    this.spanishTitle = spanishTitle;
  }

  public void setDifficulty(String difficulty) {
    TextView dt = (TextView)findViewById(R.id.difficulty);
    dt.setText(difficulty);
  }

  @Override
  public boolean onLongClick(View v) {
    TextView englishDescriptionView = (TextView)v.findViewById(R.id.english_description);
    if (englishDescriptionView.getVisibility() == VISIBLE) {
      englishDescriptionView.setEllipsize(TextUtils.TruncateAt.END);
      englishDescriptionView.setSingleLine(true);
      englishDescriptionView.setMarqueeRepeatLimit(2);
      englishDescriptionView.setSelected(false);
      englishDescriptionView.setVisibility(GONE);
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

  public void setCompleted (boolean completed) {
    ImageView tag = (ImageView)findViewById(R.id.completed);
    if (completed) {
      tag.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_completed_dark));
    }
    else {
      tag.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_tag_dark));
    }
  }
}