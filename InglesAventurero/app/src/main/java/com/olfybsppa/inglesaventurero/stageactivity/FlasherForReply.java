package com.olfybsppa.inglesaventurero.stageactivity;


import android.animation.ValueAnimator;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;

public class FlasherForReply implements FlasherI {
  private boolean on;

  @Override
  public void clearFlash(ViewGroup viewGroup) {
    turnOffBackground(viewGroup);
    turnOffEnglish(viewGroup);
  }

  @Override
  public void flash(ViewGroup viewGroup) {
    flashNow(viewGroup);
  }

  @Override
  public boolean isOn(ViewGroup viewGroup) {
    return on;
  }

  @Override
  public void turnOnWords(ViewGroup viewGroup) {
    hilightEnglish(viewGroup);
  }

  private void flashNow(View view) {
    final RelativeLayout rl = (RelativeLayout)(view).findViewById(R.id.scene_title);
    final TextView englishTextView = (TextView)view.findViewById(R.id.r_engl_phrase);
    final ValueAnimator intoFlashAnimator = ValueAnimator.ofInt(0,10).setDuration(1000);

    intoFlashAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      boolean changedForward = false;
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        if (animation.getAnimatedFraction() < 0.2 && !changedForward) {
          rl.setBackgroundResource(R.drawable.bkgd_r_f);
          changedForward = true;
        }
        else if (animation.getAnimatedFraction() > 0.8 && changedForward) {
          rl.setBackgroundResource(R.drawable.bkgd_r);
        }
      }
    });
    intoFlashAnimator.setStartDelay(0);
    intoFlashAnimator.start();
  }

  private void hilightEnglish (View view) {
    TextView englTextView = (TextView)view.findViewById(R.id.r_engl_phrase);
    englTextView.setBackgroundResource(R.drawable.bkgd_r_play_text);
    englTextView.setTextColor(ContextCompat.getColor(view.getContext(),
                                                     R.color.reply_text_on));
  }

  private void turnOffBackground (View view) {
    on = false;
    RelativeLayout rl = (RelativeLayout)view.findViewById(R.id.scene_title);
    rl.setBackgroundResource(R.drawable.bkgd_r);
  }

  private void turnOffEnglish (View view) {
    TextView englTextView = (TextView)view.findViewById(R.id.r_engl_phrase);
    englTextView.setBackgroundResource(R.drawable.bkgd_r);
    englTextView.setTextColor(ContextCompat.getColor(view.getContext(),
                                                     R.color.reply_text_eng));
  }
}
