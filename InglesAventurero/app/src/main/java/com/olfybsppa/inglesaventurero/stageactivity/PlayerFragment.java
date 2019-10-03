package com.olfybsppa.inglesaventurero.stageactivity;


import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.listeners.WorkProgressListener;
import com.olfybsppa.inglesaventurero.utils.Ez;

import java.io.File;

public class PlayerFragment extends Fragment implements OnSeekCompleteListener {

  public static String DIALOG_FILE_NAME = "DIALOG_FILE_NAME";
  public  static String PLAYER_DIDNT_FINISH = "PLAYER_DIDNT_FINISH";
  private MediaPlayer mediaPlayer;
  private String      dialogsFilename;
  private boolean     mPlayingTimeStarted = false;
  private int         mPlayTime = 0;
  private boolean     mAskedToPause = false;
  private boolean     firstTimeStartThread = true;
  private boolean     playerStartedOnce = false;
  //mPageId, mLPosition, and mShadow are passed in at requestStart and
  //passed back in notifyProgress.
  private Integer mPageId;
  private Integer mLPosition;
  private Integer mShadow;
  private int     mStartTime;
  boolean mReady    = false;
  boolean mQuitting = false;
  boolean mStop     = false;

  public static PlayerFragment newInstance (String mp3Filename) {
    PlayerFragment playerFragment = new PlayerFragment();
    if (!mp3Filename.endsWith(".m4a")) {
      mp3Filename = mp3Filename.concat(".m4a");
    }
    playerFragment.setArguments(Ez.oneStringBundle(DIALOG_FILE_NAME, mp3Filename));
    return playerFragment;
  }

  //thread is always running, until fragment is destroyed.
  final Thread mThread = new Thread() {
    @Override
    public void run() {
      synchronized (this) {
        while (!mReady || dialogsFilename == null) {
          if (mQuitting)
            return;
          try {
            wait();
          }catch(InterruptedException e) {}
        }
      }
      initMediaPlayer();
      while (true) {
        synchronized (this) {
          if (mQuitting) return;
        }
        if (mPageId == null)
          holdPlayerInPause();
        synchronized (this) {
          if (mQuitting) return; //after wait check mQuitting
        }
        synchronized (this) {
          startPlayer();
        }
        keepPlayerPlaying();
        synchronized (this) {
          if (mQuitting) return;//after wait check mQuitting
        }
        synchronized (this) {
          pauseMediaPlayer();
        }
      }
    }

    private void keepPlayerPlaying() {
      try {
        synchronized (mThread) {
          if (mediaPlayer == null)
            return;
          int currPosition = mediaPlayer.getCurrentPosition();
          if (currPosition > mStartTime + mPlayTime + 100 || currPosition < mStartTime - 100) {
            notifyCancelledPlay(0);
            return;
          }
          int finalPosition = mStartTime + mPlayTime;
          while (currPosition + 0 < finalPosition && !mAskedToPause) {
            int waitTime = finalPosition - currPosition;
            try {
              mPlayingTimeStarted = true;
              mThread.wait(waitTime);
              mPlayingTimeStarted = false;
            } catch (InterruptedException e){
            }
            int oldCurrPosition = currPosition;
            currPosition = mediaPlayer.getCurrentPosition();
            if (oldCurrPosition == currPosition)
              break;
          }
          if (!mStop)
            notifyActivity(mediaPlayer.getCurrentPosition(), finalPosition);
        }
      } catch (Exception e) {} //Could be InterruptedException as well as IllegalStateException
    }

    private void holdPlayerInPause() {
      synchronized (mThread) {
        try {
          mThread.wait();
        } catch (InterruptedException e) {}
      }
    }

    private void pauseMediaPlayer () {
      if (mediaPlayer ==  null)
        return;
      try {
        if (playerStartedOnce)
          mediaPlayer.pause();
      }catch (Exception e) {} //Just in case of an IllegalStateException, but shouldn't be.
    }

    private void startPlayer() {
      if (mediaPlayer == null)
        return;
      try {
        if (mPageId != null) {
          mediaPlayer.start();
          playerStartedOnce = true;
        }
      }
      catch (Exception e) {} //Just in case of an IllegalStateException, but shouldn't be.
    }

  };

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setRetainInstance(true);
    mThread.start();
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    Bundle args = getArguments();
    if (args.containsKey(DIALOG_FILE_NAME)) {
      this.dialogsFilename = args.getString(DIALOG_FILE_NAME);
    }
    synchronized (mThread) {
      mReady = true;
      if (firstTimeStartThread) {
        firstTimeStartThread = false;
        mThread.notify();
      }
      ((StageActivity)getActivity()).notifyProgress(getTag(), false, 110, "ready");
      mPageId = null;
      mLPosition = null;
      mShadow = null;
    }
  }

  @Override
  public void onStart() {
    super.onStart();
    synchronized (mThread) {
      mStop = false;
    }
  }

  @Override
  public void onStop() {
    super.onStop();
    synchronized (mThread) {
      mStop = true;
      if (mediaPlayer != null) {
        //Log.i("ATAG", "PlayerFragment mediaPlayer.release");
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
      }
    }
    requestPause(); //calls mThread.notify()
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    synchronized (mThread) {
      mReady = false;
      mQuitting = true;
      mThread.notify();
    }
  }

  @Override
  public void onSeekComplete(MediaPlayer arg0) {
    synchronized (this.mThread) {
      if (!mQuitting) {
        //will move mThread from holdPlayerInPause() to keepPlayerPlaying()
        this.mThread.notify();
      }
    }
  }

  public void requestPause() {
    synchronized (mThread) {
      this.mAskedToPause = true;
      mThread.notify();
    }
  }

  // has to be called from the UI thread.
  // mPageId, mShadow,and mLPosition are returned to activity in notifyProgress().
  public int requestStart(int startTime, int playTime, int pageId, int pos, int shadow) {
    if (dialogsFilename == null) {
      return -1;
    }
    if (mediaPlayer == null) {
      initMediaPlayer();
    }
    synchronized(mThread) {
      if (mPlayingTimeStarted == true || mPageId != null) {
        moveFromKeepPlayingToPause(); //calls mThread.notify()
        return -1;
      }
    }
    synchronized (mThread) {
      if (mReady) {
        if (!mediaPlayer.isPlaying()) {
          mStartTime = startTime;
          mPlayTime = playTime;
          mPageId = pageId;
          mLPosition = pos;
          mShadow = shadow;
          mAskedToPause = false;
          mediaPlayer.seekTo(startTime);
        }
      }
    }
    return 0;
  }

  private void initMediaPlayer() {
    //mediaPlayer = getPreparedMediaPlayer();
    //mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.bonnie_liz_01);
    File dir = this.getActivity().getDir(getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
    String fullMP3Name = dir.getAbsolutePath() + "/" + dialogsFilename;
    mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), Uri.parse(fullMP3Name));
    mediaPlayer.setOnSeekCompleteListener(this);
    mediaPlayer.setLooping(false);
  }

  private void moveFromKeepPlayingToPause () {
    mAskedToPause = true;
    mThread.notify();
  }

  private void notifyActivity (int currPosition, int finalPosition) {
    if (Math.abs(finalPosition - currPosition) < 50) {
      synchronized (mThread) {
        if (!mStop) {
          notifyCompletedPlay();
        }
      }
    }
    else {
      synchronized (mThread) {
        if (!mStop) {
          notifyCancelledPlay(50);
        }
      }
    }
  }

  private void notifyCompletedPlay () {
    if (null != mPageId && null != mLPosition) {
      WorkProgressListener activity = (WorkProgressListener)getActivity();
      activity.notifyProgress(getTag(), false, 100, ""+ mPageId +","+ mLPosition + ","+ mShadow);
      mPageId = null;
      mLPosition = null;
      mShadow = null;
    }
  }

  private void notifyCancelledPlay (int percent) {
    if (null != this.mPageId && null != this.mLPosition) {
      WorkProgressListener activity = (WorkProgressListener)getActivity();
      activity.notifyProgress(getTag(), true, percent, ""+ mPageId +","+ mLPosition + ","+ mShadow);
      mPageId = null;
      mLPosition = null;
      mShadow = null;
    }
  }

}