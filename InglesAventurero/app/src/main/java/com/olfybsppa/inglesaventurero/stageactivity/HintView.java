package com.olfybsppa.inglesaventurero.stageactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.LineExplanationDialog;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;
import com.olfybsppa.inglesaventurero.utils.TextFormatter;

import java.util.Map;

public class HintView extends LinearLayout implements LineView {

  private int      groupID;
  private int      positionInPage;
  private int      englishTextColor;
  private int      spanishTextColor;
  private int      backgroundColor;
  private int      linesShown = 3;
  private TextView engTextView;
  private TextView spnTextView;
  private TextView wfwEnglTextView;
  private TextView wfwSpanTextView;
  private View     rootView;
  private String   englishExplanation;
  private String   spanishExplanation;
  private FlasherI flasher;

  public HintView (Context context, AttributeSet attrs, final Actor actor) {
    super(context, attrs);
    LayoutInflater inflater =
      (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    rootView = inflater.inflate(R.layout.hint_view, this, true);
    engTextView = (TextView)rootView.findViewById(R.id.h_engl_phrase);
    spnTextView = (TextView)rootView.findViewById(R.id.h_span_phrase);
    wfwEnglTextView    = (TextView)rootView.findViewById(R.id.h_wfw_engl);
    wfwSpanTextView    = (TextView)rootView.findViewById(R.id.h_wfw_span);
    englishTextColor   = ContextCompat.getColor(this.getContext(),R.color.hint_text_eng);
    spanishTextColor   = ContextCompat.getColor(this.getContext(),R.color.hint_text_spn);
    backgroundColor    = ContextCompat.getColor(this.getContext(),R.color.hint_bg);
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    Map map = preferences.getAll();
    linesShown = getResources().getInteger(R.integer.lines_shown_default_h);
    Object value = map.get(SettingsActivity.SPN_SHOWN_H);
    if (!(value instanceof String)){
      linesShown = (Integer)value;
    }
    ImageView explanationView = (ImageView)rootView.findViewById(R.id.h_explanation);
    explanationView.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (actor != null && spanishExplanation != null && !spanishExplanation.isEmpty())
          actor.showDialog(LineExplanationDialog.newInstance(englishExplanation,
            spanishExplanation));
        }
      }
    );

    ImageView playAgainView = (ImageView)rootView.findViewById(R.id.replay);
    playAgainView.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          actor.playAgain(groupID, positionInPage);
        }
      }
    );

    ImageView playSlow = (ImageView)rootView.findViewById(R.id.slow_play);
    playSlow.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          actor.playSlow(groupID, positionInPage);
        }
      }
    );

    ImageView showLines = (ImageView)rootView.findViewById(R.id.lines_shown);
    showLines.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (linesShown == 3)
            linesShown = 0;
          else
            linesShown++;
          showCurrLines();
        }
      }
    );
  }

  @Override
  public void addFlasher(FlasherI flasher) {
    this.flasher = flasher;
  }

  @Override
  public void addMarker(){}

  @Override
  public void clearFlash () {
    flasher.clearFlash(this);
  }

  @Override
  public void clearMarkers() {}

  @Override
  public void flash(){
    flasher.flash(this);
  }

  @Override
  public int getGroupId() {
    return groupID;
  }
  @Override
  public int getPositionInPage() {
    return positionInPage;
  }

  public void initialize(Hint hint) {
    englishExplanation = hint.getEngExplanation();
    spanishExplanation = hint.getSpnExplanation();
    engTextView.setText(hint.getEnglishPhrase());
    spnTextView.setText(hint.getSpanishPhrase());
    Pair<String, String> strings = TextFormatter.lineUpWords(hint.getWFWEnglish(),
      hint.getWFWSpanish());
    Spannable engSpan = TextFormatter.applyColor(strings.first,
                                                 englishTextColor,
                                                 backgroundColor);
    Spannable spnSpan = TextFormatter.applyColor(strings.second,
                                                 spanishTextColor,
                                                 backgroundColor);
    wfwEnglTextView.setText(engSpan);
    wfwSpanTextView.setText(spnSpan);
    this.positionInPage = hint.getPosition();
    this.groupID = hint.getGroupId();
    showCurrLines();
    initializeButtons();
  }

  @Override
  public boolean isOn() {
    return flasher.isOn(this);
  }

  @Override
  public void showLines() {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
    Map map = preferences.getAll();
    int newLinesShown = getResources().getInteger(R.integer.lines_shown_default_h);
    Object value = map.get(SettingsActivity.SPN_SHOWN_H);
    if (!(value instanceof String)){
      newLinesShown = (Integer)value;
    }
    if (newLinesShown != linesShown) {
      linesShown = newLinesShown;
      showCurrLines();
    }
  }

  @Override
  public String toString() {
    return "Hint: " + engTextView.getText().toString();
  }

  @Override
  public void turnOnWords () {
    flasher.turnOnWords(this);
  }

  private void initializeButtons () {
    if (this.spanishExplanation == null || this.spanishExplanation.isEmpty()) {
      ImageView explanationButton = (ImageView)findViewById(R.id.h_explanation);
      explanationButton.setBackground(ContextCompat.getDrawable(this.getContext(),
                                      R.drawable.ripple_h_button_bg_greyed));
      explanationButton.setImageAlpha(70);
    }
  }

  private void showCurrLines() {
    switch (linesShown) {
      case 0:
        engTextView.setVisibility(INVISIBLE);
        spnTextView.setVisibility(INVISIBLE);
        wfwEnglTextView.setVisibility(INVISIBLE);
        wfwSpanTextView.setVisibility(INVISIBLE);
        break;
      case 1:
        engTextView.setVisibility(VISIBLE);
        spnTextView.setVisibility(INVISIBLE);
        wfwEnglTextView.setVisibility(INVISIBLE);
        wfwSpanTextView.setVisibility(INVISIBLE);
        break;
      case 2:
        engTextView.setVisibility(VISIBLE);
        spnTextView.setVisibility(INVISIBLE);
        wfwEnglTextView.setVisibility(VISIBLE);
        wfwSpanTextView.setVisibility(VISIBLE);

        break;
      case 3:
        engTextView.setVisibility(VISIBLE);
        spnTextView.setVisibility(VISIBLE);
        wfwEnglTextView.setVisibility(VISIBLE);
        wfwSpanTextView.setVisibility(VISIBLE);
        break;
      default:
        engTextView.setVisibility(VISIBLE);
        spnTextView.setVisibility(VISIBLE);
        wfwEnglTextView.setVisibility(VISIBLE);
        wfwSpanTextView.setVisibility(VISIBLE);
        return;
    }
  }
}