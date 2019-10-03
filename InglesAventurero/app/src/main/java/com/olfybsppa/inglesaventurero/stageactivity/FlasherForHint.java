package com.olfybsppa.inglesaventurero.stageactivity;


import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class FlasherForHint implements FlasherI {

  private boolean on;

  @Override
  public void clearFlash(ViewGroup viewGroup) {
    turnOffBackground(viewGroup);
    turnOffEnglish(viewGroup);
  }

  @Override
  public void flash(ViewGroup viewGroup) {}

  private void hilightEnglish (View view) {
    TextView englTextView = (TextView)view.findViewById(R.id.h_engl_phrase);
    englTextView.setBackgroundResource(R.drawable.bkgd_h_f_text);
    englTextView.setTextColor(ContextCompat.getColor(view.getContext(),
      R.color.hint_text_on));
  }

  @Override
  public boolean isOn(ViewGroup viewGroup) {
    return on;
  }

  @Override
  public void turnOnWords(ViewGroup viewGroup) {
    turnOnBackground(viewGroup);
    hilightEnglish(viewGroup);
  }

  private void turnOffBackground (View view) {
    on = false;
    LinearLayout rl = (LinearLayout)view.findViewById(R.id.hint);
    rl.setBackgroundResource(R.drawable.bkgd_h);
  }

  private void turnOffEnglish (View view) {
    TextView englTextView = (TextView)view.findViewById(R.id.h_engl_phrase);
    englTextView.setBackgroundColor(ContextCompat.getColor(view.getContext(),
                                                           R.color.hint_bg));
    englTextView.setTextColor(ContextCompat.getColor(view.getContext(),
                                                     R.color.hint_text_eng));
  }

  private void turnOnBackground (View view) {
    on = true;
  }
}