package com.olfybsppa.inglesaventurero.openingactivity;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.start.LinesCP;

public class SceneTitleAdapter extends CursorAdapter {

  private boolean clickable = true;

  public SceneTitleAdapter(Context context, Cursor cursor, int flags) {
    super(context, cursor, flags);
  }

  public void setAsUnclickable () {
    this.clickable = false;
  }

  @Override
  public void bindView(View v, Context context, Cursor c) {
    String storyName = c.getString(c.getColumnIndexOrThrow(LinesCP.scene_name));
    String englishTitle = c.getString(c.getColumnIndexOrThrow(LinesCP.english_title));
    String spanishTitle = c.getString(c.getColumnIndexOrThrow(LinesCP.spanish_title));
    String difficulty = c.getString(c.getColumnIndexOrThrow(LinesCP.type_1));
    String engDesc = c.getString(c.getColumnIndex(LinesCP.english_description));
    String spnDesc = c.getString(c.getColumnIndex(LinesCP.spanish_description));
    int    isFinished = c.getInt(c.getColumnIndex(LinesCP.finished));
    SceneTitleView titleView = (SceneTitleView)v;
    titleView.setSceneName(storyName);
    titleView.setEnglishTitle(englishTitle);
    titleView.setSpanishTitle(spanishTitle);
    titleView.setDifficulty(difficulty);
    titleView.setSpnDescription(spnDesc);
    titleView.setEngDescription(engDesc);
    titleView.setCompleted(isFinished == 1);
  }

  @Override
  public SceneTitleView newView(Context context, Cursor cursor, ViewGroup parent) {
    SceneTitleView view = new SceneTitleView(context);
    if (!clickable) {
      view.setOnClickListener(null);
      View vv = view.findViewById(R.id.scene_title_rr);
      vv.setBackgroundResource(0);
    }
    return view;
  }
}
