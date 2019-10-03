package com.olfybsppa.inglesaventurero.stageactivity;


import android.os.Bundle;
import android.os.Parcelable;

import com.olfybsppa.inglesaventurero.nonUIFragments.WorkerFragment;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Hint;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.HintInfo;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Leader;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Matchable;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.Reply;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyInfo;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplyLineSet;
import com.olfybsppa.inglesaventurero.stageactivity.collectors.ReplySetInfo;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers.PageResolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class TrackerMakerFragment extends WorkerFragment {
  public static String REPLY_INFOS     = "REPLY_INFOS";
  public static String REPLY_SET_INFOS = "REPLY_SET_INFOS";
  public static String HINT_INFOS      = "HINT_INFOS";
  private boolean workIsDone = false;
  private Tracker tracker;
  public static String TRACKER_MAKER_TAG = "TRACKER_MAKER_TAG";
  private String story;
  public static String STORY = "STORY";
  private ArrayList<Parcelable> hintInfos = new ArrayList<>();
  private ArrayList<Parcelable> replyInfos = new ArrayList<>();
  private ArrayList<Parcelable> replySetInfos = new ArrayList<>();
  /**
   * Uses STORY as key to String story name.
  **/
  public static TrackerMakerFragment newInstance(Bundle bundle) {
    TrackerMakerFragment trackerMakerFragment = new TrackerMakerFragment();
    trackerMakerFragment.setArguments(bundle);
    return trackerMakerFragment;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    Bundle args = (savedInstanceState != null)? savedInstanceState : getArguments();
    if (null != args) {
      story = args.getString(StageActivity.STORY);
      if (args.containsKey(HINT_INFOS))
        hintInfos = args.getParcelableArrayList(HINT_INFOS);
      if (args.containsKey(REPLY_INFOS))
        replyInfos = args.getParcelableArrayList(REPLY_INFOS);
      if (args.containsKey(REPLY_SET_INFOS))
        replySetInfos = args.getParcelableArrayList(REPLY_SET_INFOS);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putString(StageActivity.STORY, story);
    if (tracker != null) {
      ArrayList<HintInfo> hintInfos = tracker.getHintInfos();
      ArrayList<ReplyInfo> replyInfos = tracker.getReplyInfos();
      ArrayList<ReplySetInfo> replyLineSetInfos = tracker.getReplySetInfos();
      outState.putParcelableArrayList(HINT_INFOS, hintInfos);
      outState.putParcelableArrayList(REPLY_INFOS, replyInfos);
      outState.putParcelableArrayList(REPLY_SET_INFOS, replyLineSetInfos);
    }
  }

  // work here is not on the UI thread.
  @Override
  protected boolean work() {
    if (story.equals(StageActivity.TEST_STORY) ||
        story.equals(StageActivity.ESPRESSO_STORY) ||
        story.equals(StageActivity.SILENT_STORY))
      tracker = AlphabeticalTracker.getTracker();
    else {
      synchronized (mThread) {
        if (mReady == true && mQuitting == false) {
          ResolverWrapper rw = new ResolverWrapper(getContext().getContentResolver());
          PageResolver pageResolver = new PageResolver(rw);
          HashMap<Integer, Page> pageHashMap = pageResolver.retrievePagesForScene(story);
          mergeLineInfos(pageHashMap);
          tracker = new Tracker(pageHashMap);
        }
        else if (mReady) {
          notifyWorkProgressListener(TRACKER_MAKER_TAG, true, 0, null);
          return false;
        }
        else
          return false;
      }
    }
    workIsDone = true;
    notifyWorkProgressListener(TRACKER_MAKER_TAG, false, 100, null);
    return workIsDone;
  }

  @Override
  protected boolean workIsDone() {
    return workIsDone;
  }

  @Override
  protected void toDoOnActivityCreated(Bundle savedInstanceState) {
    //NOTHING
  }

  public Tracker getTracker () {
    return tracker;
  }

  private void mergeLineInfos (HashMap<Integer, Page> pageHashMap) {
    Iterator<Page> iterator = pageHashMap.values().iterator();
    while (iterator.hasNext()) {
      Page page = iterator.next();
      ArrayList<Leader> leaders = page.getLeads();
      for (Leader lead : leaders) {
        int iiCopy = -1;
        for (int ii = 0; ii<hintInfos.size(); ii++) {
          HintInfo info = (HintInfo)hintInfos.get(ii);
          if (info.hintGroupID == lead.getGroupId() && info.posId == lead.getPosition()) {
            info.mergeHint((Hint)lead);
            iiCopy = ii;
            break;
          }
        }
        if (iiCopy != -1) {
          hintInfos.remove(iiCopy);
        }
      }
      ArrayList<Matchable> matchables = page.getMatchables();
      for (Matchable matchable : matchables) {
        if (matchable instanceof ReplyLineSet) {
          ReplyLineSet replyLineSet = (ReplyLineSet)matchable;
          int iiCopy = -1;
          for (int ii = 0; ii<replySetInfos.size(); ii++) {
            ReplySetInfo info = (ReplySetInfo)replySetInfos.get(ii);
            if (info.replyGroupID == replyLineSet.getGroupId()) {
              info.mergeReplySet(replyLineSet);
              iiCopy = ii;
              ArrayList<Reply> replies = replyLineSet.getReplies();
              for (Reply reply : replies) {
                int jjCopy = -1;
                for (int jj = 0; jj<replyInfos.size(); jj++) {
                  ReplyInfo replyInfo = (ReplyInfo)replyInfos.get(jj);
                  if (replyInfo.replyGroupID == reply.getGroupId() &&
                      replyInfo.positionID == reply.getPosition()) {
                    replyInfo.mergeReply(reply);
                    jjCopy = jj;
                    break;
                  }
                }
                if (jjCopy != -1) {
                  replyInfos.remove(jjCopy);
                }
              }
              break;
            }
          }
          if (iiCopy != -1) {
            replySetInfos.remove(iiCopy);
          }
        }
        else if (matchable instanceof Reply) {
          Reply reply = (Reply)matchable;
          int iiCopy = -1;
          for (int ii = 0; ii<replyInfos.size(); ii++) {
            ReplyInfo info = (ReplyInfo)replyInfos.get(ii);
            if (info.replyGroupID == reply.getGroupId() &&
                info.positionID == reply.getPosition()) {
              info.mergeReply(reply);
              iiCopy = ii;
              break;
            }
          }
          if (iiCopy != -1) {
            replyInfos.remove(iiCopy);
          }
        }
      }
    }
  }
}