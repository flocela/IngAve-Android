package com.olfybsppa.inglesaventurero.stageactivity.dialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.collectors.Attribution;

import java.util.ArrayList;

public class BackgroundInfoDialog extends DialogFragment {

	public static String ATTRIBUTIONS = "ATTRIBUTIONS";
	private static String IN_ENGLISH = "IN_ENGLISH_BOOLEAN";
	private boolean inEnglish = false;	
	private Button okButton;
  private Button languageButton;

  private TextView titleView;
  private TextView introView;
  private TextView numOfImagesView;
  private LinearLayout linearLayoutInsideScrollView;

  private ArrayList<Attribution> attributions = new ArrayList<Attribution>();

  public static BackgroundInfoDialog newInstance (Bundle attributions) {
    BackgroundInfoDialog bD = new BackgroundInfoDialog();
    Bundle args = attributions;
    bD.setArguments(args);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = (savedInstanceState == null)? getArguments() : savedInstanceState;
    attributions = bundle.getParcelableArrayList(ATTRIBUTIONS);
    inEnglish = bundle.getBoolean(IN_ENGLISH);
    this.setStyle(DialogFragment.STYLE_NO_TITLE,getTheme());
  }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_background_info, container, false);
    this.titleView = (TextView)v.findViewById(R.id.title);
    this.introView = (TextView)v.findViewById(R.id.intro);
    this.numOfImagesView = (TextView)v.findViewById(R.id.num_of_attributions);

    linearLayoutInsideScrollView = (LinearLayout)v.findViewById(R.id.ll_inside_scrollview);
    
		okButton = (Button)v.findViewById(R.id.btn_ok);
		okButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				BackgroundInfoDialog.this.dismiss();
			}
		});
		
		languageButton = (Button)v.findViewById(R.id.btn_language);
		languageButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        inEnglish = !inEnglish;
        setText();
        int count = linearLayoutInsideScrollView.getChildCount();
        for (int ii=0; ii<count; ii++) {
          View child = linearLayoutInsideScrollView.getChildAt(ii);
          if (child instanceof PictureInfoView) {
            ((PictureInfoView)child).setLanguage(inEnglish);
            ((PictureInfoView)child).showViews();
          }
        }
      }
    });

    setText();
    createPictureViews();
		return v;
	}

  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putBoolean(IN_ENGLISH, this.inEnglish);
    outState.putParcelableArrayList(ATTRIBUTIONS, attributions);
  }

  private void createPictureViews () {
    if (inEnglish) {
      for (Attribution attribution : attributions) {
        PictureInfoView pictInfoView =
          new PictureInfoView(this.getActivity(), attribution.getPictureInfoBundle(), true);
        (linearLayoutInsideScrollView).addView(pictInfoView);
      }
    }
    else {
      for (Attribution attribution : attributions) {
        PictureInfoView pictInfoView =
          new PictureInfoView(this.getActivity(), attribution.getPictureInfoBundle(), false);
        (linearLayoutInsideScrollView).addView(pictInfoView);
      }
    }
  }

  private void setEnglishText () {
    this.titleView.setText(getResources().getString(R.string.copyright_eng));
    this.introView.setText(getResources().getString(R.string.picture_copy_right_info_eng));
    if (attributions.size() > 1) {
      String text = "This image is composed of " + attributions.size() + " images.";
      numOfImagesView.setText(text);
      numOfImagesView.setVisibility(View.VISIBLE);
    }
    languageButton.setText(getResources().getString(R.string.enEspanol));
  }

  private void setSpanishText () {
    this.titleView.setText(getResources().getString(R.string.copyright_spn));
    this.introView.setText(getResources().getString(R.string.picture_copy_right_info_spn));
    if (attributions.size() > 1) {
      String text = "Esta imagen se compone de " + attributions.size() + " imágenes.";
      numOfImagesView.setText(text);
      numOfImagesView.setVisibility(View.VISIBLE);
    }
    languageButton.setText(getResources().getString(R.string.inEnglish));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
