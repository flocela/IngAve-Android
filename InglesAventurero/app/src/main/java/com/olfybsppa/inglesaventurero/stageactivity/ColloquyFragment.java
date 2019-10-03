package com.olfybsppa.inglesaventurero.stageactivity;


import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Line;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.start.SettingsActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColloquyFragment extends PositionFragment
                              implements Actor,
                                         SharedPreferences.OnSharedPreferenceChangeListener {

  public static String MATCHABLES          = "MATCHABLES";
  public static String HINTS               = "HINTS";
  public static String PAGE_ID             = "PAGE_ID";
  public static String BACKGROUND_FILE     = "BACKGROUND_FILE";
  public static String SCRIPT_POS          = "SCRIPT_POS";
  public static String NEW_PATH            = "NEW_PATH";
  public static String LEAVE_ON_POSITION   = "LEAVE_ON_POSITION";
  public static String FIRST_LAYOUT_CHANGE = "FIRST_LAYOUT_CHANGE";
  // this.page is not the same page as in Director's tracker. It's essentially a clone.
  private Page                        page;
  private int                         positionLeaveOn;
  private LinearLayout                linearLayout;
  private PlayerInterface             player;
  private View.OnLayoutChangeListener scrollViewListener;
  private boolean onFirstLayoutChange = true;
  private HiLiterHandler hiLiterHandler;

  private String GROUP_ID    = "GROUP_ID";
  private String POSITION    = "POSITION";
  private String HILITEWORDS = "HILIGHTWORDS";
  private String FLASH       = "FLASH";
  private String SHOWTYPE    = "SHOWTYPE";

  public static ColloquyFragment newInstance(Page page, int position) {
    Bundle bundle = new Bundle();
    bundle.putParcelableArrayList(MATCHABLES, page.getMatchables());
    bundle.putParcelableArrayList(HINTS, page.getLeads());
    bundle.putInt(PAGE_ID, page.getName());
    bundle.putInt(SCRIPT_POS, position);
    bundle.putString(BACKGROUND_FILE, page.getBackgroundFilename());
    ColloquyFragment fragment = new ColloquyFragment();
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = (savedInstanceState == null) ? getArguments() : savedInstanceState;
    ArrayList<Leader> hints         = bundle.getParcelableArrayList(HINTS);
    ArrayList<Matchable> matchables = bundle.getParcelableArrayList(MATCHABLES);
    int pageId                      = bundle.getInt(PAGE_ID);
    page = new Page(pageId, hints, matchables);
    page.setBackgroundFilename(bundle.getString(BACKGROUND_FILE));
    positionLeaveOn = bundle.getInt(LEAVE_ON_POSITION);
    setCurrPosition(bundle.getInt(SCRIPT_POS));
    if (bundle.containsKey(FIRST_LAYOUT_CHANGE))
      onFirstLayoutChange = bundle.getBoolean(FIRST_LAYOUT_CHANGE);
    scrollViewListener = new View.OnLayoutChangeListener() {
      @Override
      public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (v instanceof NestedScrollView){
          if (onFirstLayoutChange)
            quickScroll();
          onFirstLayoutChange = false;
        }
      }
    };
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           ViewGroup container,
                           Bundle savedInstanceState) {
    LinearLayout rootView = (LinearLayout) inflater.inflate(R.layout.fragment_colloquy,
        container, false);
    Bitmap background = null;
    File dir = this.getActivity().getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    String backgroundFile = dir.getAbsolutePath() + "/" + page.getBackgroundFilename();
    if (null != backgroundFile) {
      File file = new File(backgroundFile);
      if (file.exists()) {
        WindowManager windowManager = getActivity().getWindowManager();
        if (null == windowManager)
          background = getBitmapNormal(page.getBackgroundFilename());
        else {
          Point point = new Point();
          windowManager.getDefaultDisplay().getSize(point);
          background = getBitmapUsingWindowSize(backgroundFile, point.x);
        }
        if (background != null) {
          ImageView imageView = (ImageView) rootView.findViewById(R.id.background);
          imageView.setImageBitmap(getRoundedCornerBitmap(background, 14));
        }
      }
    }
    linearLayout = (LinearLayout)rootView.findViewById(R.id.inside_ll);
    List<LineView> lineViews = page.getLineViews(this.getContext(), this);
    for (LineView lineView : lineViews) {
      linearLayout.addView((View)lineView);
    }
    NestedScrollView scrollView = (NestedScrollView)rootView.findViewById(R.id.colloquy_scroll_v);
    scrollView.setNestedScrollingEnabled(true);
    scrollView.addOnLayoutChangeListener(this.scrollViewListener);
    return rootView;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    String story = ((StageActivity)getActivity()).getStory();
    FlasherMaker flasherMaker = new FlasherMaker(story);
    for (int ii=0; ii<linearLayout.getChildCount(); ii++) {
      View view = linearLayout.getChildAt(ii);
      if (view instanceof LineView)
        ((LineView)view).addFlasher(flasherMaker.getFlasher((LineView)view));
    }
  }

  @Override
  public void onResume() {
    super.onResume();
    hiLiterHandler = new HiLiterHandler();
    //Note StageActivity may very well not have a ready player yet and may return null.
    player = ((StageActivity)getActivity()).getPlayer();
    // Set up a listener whenever a key changes
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
    preferences.registerOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onPause() {
    super.onPause();
    hiLiterHandler.removeCallbacksAndMessages(null);
    hiLiterHandler = null;
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
    preferences.unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    // LINES + PAGE_ID + BackgroundFilename allow me to recreate page.
    outState.putParcelableArrayList(HINTS, page.getLeads());
    outState.putParcelableArrayList(MATCHABLES, page.getMatchables());
    outState.putInt(PAGE_ID, page.getName());
    outState.putInt(SCRIPT_POS, getCurrPosition());
    outState.putBoolean(NEW_PATH, false);
    outState.putInt(LEAVE_ON_POSITION, positionLeaveOn);
    outState.putString(BACKGROUND_FILE, page.getBackgroundFilename());
    outState.putBoolean(FIRST_LAYOUT_CHANGE, onFirstLayoutChange);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(SettingsActivity.SPN_SHOWN_R) ||
        key.equals(SettingsActivity.SPN_SHOWN_H)) {
      refreshViews();
    }
  }

  public void clearMatchesForInUse() {
    page.inUseReset();
    clearHilights();
    int childCount = linearLayout.getChildCount();
    for (int ii=0; ii<childCount; ii++) {
      View child = linearLayout.getChildAt(ii);
      if (child instanceof  LineView)
        ((LineView)linearLayout.getChildAt(ii)).clearMarkers();
    }
  }
  public void clearMatches () {
    page.reset();
    clearHilights();
    int childCount = linearLayout.getChildCount();
    for (int ii=0; ii<childCount; ii++) {
      View child = linearLayout.getChildAt(ii);
      if (child instanceof  LineView)
        ((LineView)linearLayout.getChildAt(ii)).clearMarkers();
    }
  }

  public void clearHilights(int pageName, int positionInPage) {
    View view = getView();
    if (view == null)
      return;
    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.inside_ll);
    if (linearLayout == null)
      return;
    for (int ii=0; ii<linearLayout.getChildCount(); ii++) {
      View child = linearLayout.getChildAt(ii);
      if (child instanceof LineView) {
        LineView childView = (LineView)child;
        if (childView.getPositionInPage() == positionInPage) {
          childView.clearFlash();
          break;
        }
      }
    }
  }

  public void clearHilights() {
    View view = getView();
    if (view == null)
      return;
    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.inside_ll);
    if (linearLayout == null)
      return;
    for (int ii=0; ii<linearLayout.getChildCount(); ii++) {
      View child = linearLayout.getChildAt(ii);
      if (child instanceof LineView) {
        ((LineView) child).clearFlash();
      }
    }
  }

  @Override
  public int getName() {
    if (null == page) {
      Bundle bundle = getArguments();
      if (bundle != null) {
        return bundle.getInt(PAGE_ID);
      }
      return -1;
    }
    return page.getName();
  }

  public void match(ArrayList<String> responses) {
    Answer answer = page.acceptResponses(responses);
    if (answer.isMatched()) {
      page.noticeGroupScroll(answer.getGroupID(), answer.getLinePosition());
      View vv = linearLayout.getChildAt(0);
      int llPosition = answer.getGroupID();
      if (vv instanceof ImageView) {
        llPosition = answer.getGroupID() + 1;
      }
      LineView lineView = (LineView)linearLayout.getChildAt(llPosition);
      if (lineView != null) {
        if (lineView.getPositionInPage() != answer.getLinePosition()) {
          LineView view = page.getLineView(this.getContext(), answer.getGroupID(), this);
          FlasherMaker flasherMaker = new FlasherMaker(((StageActivity)getActivity()).getStory());
          view.addFlasher(flasherMaker.getFlasher(view));
          linearLayout.removeViewAt(llPosition);
          linearLayout.addView((View)view, llPosition);
        }
        lineView.addMarker();
        flashLineAt(answer.getGroupID());
      }
    }
  }

  @Override
  public void noticeGroupHorizontalScroll(int groupID) {
    page.noticeGroupScroll(groupID);
    LineView view = page.getLineView(this.getContext(), groupID, this);
    FlasherMaker flasherMaker = new FlasherMaker(((StageActivity)getActivity()).getStory());
    view.addFlasher(flasherMaker.getFlasher(view));
    if (linearLayout != null) {
      View vv = linearLayout.getChildAt(0);
      int position = 0;
      if (vv instanceof ImageView) {
        position = groupID + 1;
      }
      else {
        position = groupID;
      }
      linearLayout.removeViewAt(position);
      linearLayout.addView((View)view, position);
    }
  }

  // This is just for testing. Allows user to press correct button and send StageActivity
  // responses, simulating that the user had used the mic.
  @Override
  public void noticeResponses(ArrayList<String> responses) {
    Intent intent = new Intent();
    intent.putExtra(RecognizerIntent.EXTRA_RESULTS, responses);
    ((StageActivity)getActivity()).onActivityResult(StageActivity.MIC_REQ_CODE,
                                                    Activity.RESULT_OK,
                                                    intent);
  }

  @Override
  public void playAgain(int groupId, int position) {
    Line line = page.getLine(groupId, position);
    if (null == player) {
      player = ((StageActivity)getActivity()).getPlayer();
    }
    if (player != null) {
      scrollAndHilightWords(line.getGroupId(), position, false);
      player.requestPause();
      player.requestStart(line.getNormalStartTime(),
        line.getNormalEndTime() - line.getNormalStartTime(),
        page.getName(),
        position,
        Director.PLAY_AGAIN_INT,
        0);
    }
  }

  @Override
  public void playSlow(int groupId, int position) {
    Line line = page.getLine(groupId, position);
    if (player == null){
      player = ((StageActivity)getActivity()).getPlayer();
    }
    if (player != null) {
      scrollAndHilightWords(line.getGroupId(), position, false);
      player.requestPause();
      player.requestStart(line.getSlowStartTime(),
        line.getSlowEndTime() - line.getSlowStartTime(),
        page.getName(),
        position,
        Director.PLAY_AGAIN_INT,
        0);
    }
  }

  public void refreshViews () {
    int count = linearLayout.getChildCount();
    for (int ii=0; ii<count; ii++) {
      View view = linearLayout.getChildAt(ii);
      if (view instanceof LineView) {
        ((LineView)view).showLines();
      }
    }
  }

  public void scrollAndFlashNextReplies(int groupId, int scrollTime, boolean doScroll) {
    int calcScrollTime = 0;
    if (doScroll)
      calcScrollTime = scrollToMatchableGroupID(groupId, scrollTime, 1.0f);
    if (calcScrollTime == 0 || doScroll)
      flashNextReplies(groupId);
    else {
      int waitTime = scrollTime + 200;
      if (scrollTime > 500) {
        waitTime = waitTime + 200;
      }
      if (hiLiterHandler != null)
        hiLiterHandler.flashStartAt(groupId, waitTime);
    }
  }

  public void scrollAndHilightWords(int groupId, int position, boolean doScroll) {
    int calcScrollTime = 0;
    if (doScroll)
      calcScrollTime = scrollToMatchableGroupID(groupId, calcScrollTime, 1.0f);
    if (calcScrollTime == 0 || !doScroll)
      turnOnWords(position, groupId);
    else {
      int waitTime = calcScrollTime + 200;
      if (calcScrollTime > 500) {
        waitTime = waitTime + 200;
      }
      if (hiLiterHandler != null)
        hiLiterHandler.hilightWordsStartAt(groupId, position, waitTime);
    }
  }

  public void scrollTo (int groupID, int scrollTime) {
    scrollToMatchableGroupID(groupID, scrollTime, 1.0f);
  }

  @Override
  public void showDialog(DialogFragment dialog) {
    if (player == null){
      player = ((StageActivity)getActivity()).getPlayer();
    }
    if (player != null) {
      player.requestPause();
    }
    ((StageActivity)getActivity()).showDialog(dialog);
  }

  @Override
  public String toString() {
    return "ColloquyFragment: "+this.page.getName();
  }

  private class HiLiterHandler extends Handler {
    @Override
    public void handleMessage (Message inputMessage) {
      Bundle bundle = inputMessage.getData();
      String showType = bundle.getString(SHOWTYPE);
      if (showType.equals(HILITEWORDS)) {
        scrollAndHilightWords(bundle.getInt(GROUP_ID), bundle.getInt(POSITION), false);
      }
      else if (showType.equals(FLASH))
        flashNextReplies(bundle.getInt(GROUP_ID));
    }

    private void hilightWordsStartAt (int groupId, int position, int waitTime) {
      Message message = obtainMessage();
      Bundle bundle = new Bundle();
      bundle.putInt(GROUP_ID, groupId);
      bundle.putString(SHOWTYPE, HILITEWORDS);
      bundle.putInt(POSITION, position);
      message.setData(bundle);
      sendMessageDelayed(message, waitTime);
    }

    private void flashStartAt (int groupId, int waitTime) {
      Message message = obtainMessage();
      Bundle bundle = new Bundle();
      bundle.putInt(GROUP_ID, groupId);
      bundle.putString(SHOWTYPE, FLASH);
      message.setData(bundle);
      sendMessageDelayed(message, waitTime);
    }
  }

  private void flashLineAt(int groupID) {
    if (linearLayout != null) {
      for (int ii=0; ii<linearLayout.getChildCount(); ii++) {
        View child = linearLayout.getChildAt(ii);
        if (child instanceof LineView &&
          ((LineView) child).getGroupId() == groupID) {
          ((LineView)child).flash();
          break;
        }
      }
    }
  }

  private void flashNextReplies (int groupId) {
    flashLineAt(groupId);
  }
  private Bitmap getBitmapNormal (String filename) {
    return BitmapFactory.decodeFile(filename, new BitmapFactory.Options());
  }

  private Bitmap getBitmapUsingWindowSize (String filename, int targetWidth) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(filename, options);
    int srcWidth = options.outWidth;

    BitmapFactory.Options newOptions = new BitmapFactory.Options();
    newOptions.inJustDecodeBounds = false;
    newOptions.inScaled = true;
    newOptions.inSampleSize = 1;
    newOptions.inDensity = srcWidth;
    newOptions.inTargetDensity = targetWidth * newOptions.inSampleSize;
    return BitmapFactory.decodeFile(filename, newOptions);
  }

  public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
      .getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    final RectF rectF = new RectF(rect);
    final float roundPx = pixels;

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);

    return output;
  }

  private void quickScroll () {
    NestedScrollView scrollView =
      (NestedScrollView)this.getView().findViewById(R.id.colloquy_scroll_v);
    if (linearLayout.getChildAt(0) instanceof ImageView) {
      int height = linearLayout.getChildAt(0).getHeight();
      scrollView.scrollTo(0, height);
    }
  }

  private int scrollToMatchableGroupID (int groupId, int scrollTime, float fraction) {
    NestedScrollView scrollV = (NestedScrollView)this.getView().findViewById(R.id.colloquy_scroll_v);
    int bottomOfAppBarLayout = 0;
    View rootView = scrollV.getRootView();
    View stageToolbar = rootView.findViewById(R.id.appBar);
    if (stageToolbar != null && stageToolbar instanceof AppBarLayout) {
      bottomOfAppBarLayout = stageToolbar.getBottom();
    }
    int position = groupId;
    if (linearLayout.getChildAt(0) instanceof ImageView) {
      position = position + 1;
    }
    float toTopOfView = 0f;
    float toFraction  = 0f;
    int ii=0;
    for (ii=0; ii<position; ii++) {
      toTopOfView = toTopOfView + linearLayout.getChildAt(ii).getHeight();
      toFraction = toFraction + linearLayout.getChildAt(ii).getHeight();
    }
    toFraction = toFraction + ((linearLayout.getChildAt(position).getHeight())*fraction);
    int scrollViewHeight = scrollV.getHeight();

    if (toFraction - 150 > scrollViewHeight + scrollV.getScrollY()) {
      int newScrollY = (int)toFraction - scrollViewHeight + bottomOfAppBarLayout;
      if (scrollTime < 250) {
        scrollTime = 250;
      }
      ObjectAnimator.ofInt(scrollV, "scrollY",  newScrollY).setDuration(scrollTime).start();
      return newScrollY;
    }
    else if (toTopOfView < scrollV.getScrollY()) {
      int newYYScroll = (int)toTopOfView;
      if (scrollTime < 250) {
        scrollTime = 250;
      }
      ObjectAnimator.ofInt(scrollV, "scrollY", newYYScroll).setDuration(scrollTime).start();
      return newYYScroll;
    }
    else {
      return 0;
    }
  }

  private void turnOnWords (int position, int groupID) {
    View view = getView();
    LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.inside_ll);
    for (int ii=0; ii<linearLayout.getChildCount(); ii++) {
      View child = linearLayout.getChildAt(ii);
      if (child instanceof LineView &&
        ((LineView) child).getGroupId() == groupID &&
        ((LineView) child).getPositionInPage() == position) {
        ((LineView)child).turnOnWords();
        break;
      }
    }
  }
}