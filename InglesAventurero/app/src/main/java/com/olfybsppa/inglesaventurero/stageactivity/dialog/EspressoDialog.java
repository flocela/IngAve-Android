package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.stageactivity.StageActivity;

public class EspressoDialog extends DialogFragment {

	private Button okButton;
  private String times;
  private int pageId;
  private int position;
  private int shadow;
  private String tag;
  public static String TIMES = "TIMES";
  public static String PAGE_ID = "PAGE_ID";
  public static String POSITION = "POSITION";
  public static String SHADOW = "SHADOW";
  public static String TAG = "TAG";

  // Shows times for testing.
  public static EspressoDialog newInstance (Bundle bundle) {
    EspressoDialog bD = new EspressoDialog();
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    Bundle args = (savedInstanceState == null)? getArguments():savedInstanceState;
    if (args != null) {
      if (args.containsKey(TIMES))
        times = args.getString(TIMES);
      if (args.containsKey(PAGE_ID))
        pageId = args.getInt(PAGE_ID);
      if (args.containsKey(POSITION))
        position = args.getInt(POSITION);
      if (args.containsKey(SHADOW))
        shadow = args.getInt(SHADOW);
      if (args.containsKey(TAG))
        tag = args.getString(TAG);
    }
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_espresso_player, container, false);
    TextView textView = (TextView)v.findViewById(R.id.play_times);
    textView.setText(times);
		okButton = (Button)v.findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if ((EspressoDialog.this.getActivity()) instanceof StageActivity) {
          StageActivity stageActivity = (StageActivity)EspressoDialog.this.getActivity();
          stageActivity.notifyProgress(tag,
                                           false,
                                           100,
                                           "" + pageId + "," + position + "," + shadow);
        }
        EspressoDialog.this.dismiss();
			}
		});
		return v;
	}
	
	public void onSaveInstanceState(Bundle outState) {
	  super.onSaveInstanceState(outState);
	  outState.putString(TIMES, times);
    outState.putInt(PAGE_ID, pageId);
    outState.putInt(POSITION, position);
    outState.putInt(SHADOW, shadow);
    outState.putString(TAG, tag);
	}
}
