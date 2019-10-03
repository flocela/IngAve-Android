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
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.stageactivity.dialog.LineExplanationDialog;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;
import com.olfybsppa.inglesaventurero.utils.TextFormatter;

import java.util.ArrayList;
import java.util.Map;

public class ReplyView extends LinearLayout implements LineView {

  private TextView  englTextView;
  private TextView  spanPhraseTextView;
  private TextView  wfwEnglTextView;
  private TextView  wfwSpanTextView;
  private ImageView matchedMarkerView;
  private View      rootView;

  private int       groupID;
  private int       positionInPage;
  private int       englishTextColor;
  private int       spanishTextColor;
  private int       backgroundColor;
  private String    englishExplanation;
  private String    spanishExplanation;
  private int       linesShown = 3;
  private FlasherI  flasher;

  public ReplyView(Context context, AttributeSet attrs, final Actor actor) {
    super(context, attrs);
    LayoutInflater inflater =
      (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    rootView = inflater.inflate(R.layout.reply_view, this, true);
    englTextView       = (TextView)rootView.findViewById(R.id.r_engl_phrase);
    spanPhraseTextView = (TextView)rootView.findViewById(R.id.r_span_phrase);
    wfwEnglTextView    = (TextView)rootView.findViewById(R.id.r_wfw_engl);
    wfwSpanTextView    = (TextView)rootView.findViewById(R.id.r_wfw_span);
    matchedMarkerView  = (ImageView)rootView.findViewById(R.id.r_matchedmarker);
    englishTextColor   = ContextCompat.getColor(this.getContext(),R.color.reply_text_eng);
    spanishTextColor   = ContextCompat.getColor(this.getContext(),R.color.reply_text_spn);
    backgroundColor    = ContextCompat.getColor(this.getContext(),R.color.reply_bg);
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    Map map = preferences.getAll();
    linesShown = getResources().getInteger(R.integer.lines_shown_default_r);
    Object value = map.get(SettingsActivity.SPN_SHOWN_R);
    if (!(value instanceof String)){
      linesShown = (Integer)value;
    }

    ImageView scroller = (ImageView)rootView.findViewById(R.id.r_group_scroll);
    scroller.setVisibility(GONE);

    ImageView explanationView = (ImageView)rootView.findViewById(R.id.r_explanation);
    explanationView.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (actor != null && null != spanishExplanation  && !spanishExplanation.isEmpty()) {
            actor.showDialog(LineExplanationDialog.newInstance(englishExplanation,
              spanishExplanation));
          }
        }
      }
    );

    ImageView playAgainView = (ImageView)rootView.findViewById(R.id.r_play_again);
    playAgainView.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (actor != null) {
            actor.playAgain(groupID, positionInPage);
          }
        }
      }
    );

    ImageView playSlowView = (ImageView)rootView.findViewById(R.id.r_play_slow);
    playSlowView.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (actor != null) {
            actor.playSlow(groupID, positionInPage);
          }
        }
      }
    );

    final ImageView showLines = (ImageView)rootView.findViewById(R.id.lines_shown);
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

    // TODO Delete this (only here for manual testing).
    ImageView submitCorrResponses = (ImageView)rootView.findViewById(R.id.r_correct_response);
    //submitCorrResponses.setVisibility(VISIBLE);
    submitCorrResponses.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (actor != null) {
            ArrayList<String> responses = new ArrayList<String>();
            responses.add(englTextView.getText().toString());
            actor.noticeResponses(responses);
          }
        }
      }
    );
    // TODO Delete this (only here for manual testing).
    ImageView wrongResponse = (ImageView)rootView.findViewById(R.id.r_wrong_response);
    //wrongResponse.setVisibility(VISIBLE);
    wrongResponse.setOnClickListener(
      new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (actor != null) {
            ArrayList<String> responses = new ArrayList<String>();
            responses.add(englTextView.getText().toString() + " wrong");
            actor.noticeResponses(responses);
          }
        }
      }
    );
  }

  @Override
  public void addFlasher(FlasherI flasher) {
    this.flasher = flasher;
  }

  @Override
  public void addMarker(){
    matchedMarkerView.setVisibility(View.VISIBLE);
  }

  @Override
  public void clearFlash () {
    flasher.clearFlash(this);
  }

  @Override
  public void clearMarkers() {
    matchedMarkerView.setVisibility(View.INVISIBLE);
  }

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

  public void initialize(Reply reply) {
    englishExplanation = reply.getEngExplanation();
    spanishExplanation = reply.getSpnExplanation();
    englTextView.setText(reply.getEnglishPhrase());
    spanPhraseTextView.setText(reply.getSpanishPhrase());
    Pair<String, String> strings = TextFormatter.lineUpWords(reply.getWFWEnglish(),
      reply.getWFWSpanish());
    Spannable engSpan = TextFormatter.applyColor(strings.first,
                                                 englishTextColor,
                                                 backgroundColor);
    Spannable spnSpan = TextFormatter.applyColor(strings.second,
                                                 spanishTextColor,
                                                 backgroundColor);
    wfwEnglTextView.setText(engSpan);
    wfwSpanTextView.setText(spnSpan);
    this.positionInPage = reply.getPosition();
    this.groupID = reply.getGroupId();
    if (reply.isMatched())
      addMarker();
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
    int newLinesShown = getResources().getInteger(R.integer.lines_shown_default_r);
    Object value = map.get(SettingsActivity.SPN_SHOWN_R);
    if (!(value instanceof String)){
      newLinesShown = (Integer)value;
    }
    if (newLinesShown != linesShown) {
      linesShown = newLinesShown;
      showCurrLines();
    }
  }

  @Override
  public void turnOnWords () {
    flasher.turnOnWords(this);
  }

  private void initializeButtons () {
    if (this.spanishExplanation == null || this.spanishExplanation.isEmpty()) {
      ImageView explanationButton = (ImageView)findViewById(R.id.r_explanation);
      explanationButton.setBackground(ContextCompat.getDrawable(this.getContext(),
                                      R.drawable.ripple_r_button_bg_greyed));
      explanationButton.setImageAlpha(90);
    }
  }

  private void showCurrLines() {
    switch (linesShown) {
      case 0:
        englTextView.setVisibility(INVISIBLE);
        spanPhraseTextView.setVisibility(INVISIBLE);
        wfwEnglTextView.setVisibility(INVISIBLE);
        wfwSpanTextView.setVisibility(INVISIBLE);
        return;
      case 1:
        englTextView.setVisibility(INVISIBLE);
        spanPhraseTextView.setVisibility(VISIBLE);
        wfwEnglTextView.setVisibility(INVISIBLE);
        wfwSpanTextView.setVisibility(INVISIBLE);
        return;
      case 2:
        englTextView.setVisibility(INVISIBLE);
        spanPhraseTextView.setVisibility(VISIBLE);
        wfwEnglTextView.setVisibility(INVISIBLE);
        wfwSpanTextView.setVisibility(VISIBLE);
        return;
      case 3:
        englTextView.setVisibility(VISIBLE);
        spanPhraseTextView.setVisibility(VISIBLE);
        wfwEnglTextView.setVisibility(VISIBLE);
        wfwSpanTextView.setVisibility(VISIBLE);
        return;
      default:
        englTextView.setVisibility(VISIBLE);
        spanPhraseTextView.setVisibility(VISIBLE);
        wfwEnglTextView.setVisibility(VISIBLE);
        wfwSpanTextView.setVisibility(VISIBLE);
        return;
    }
  }
}
