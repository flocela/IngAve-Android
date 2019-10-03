package com.olfybsppa.inglesaventurero.deleteingscenes;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.dialogs.DialogDoneListener;
import com.olfybsppa.inglesaventurero.utils.Ez;

public class DeleteConfirmationDialog extends DialogFragment {

	public static String  DELETE        = "DELETE";
  public static String  ENGLISH_SCENE = "ENGLISH_SCENE";
  public static String  SPANISH_SCENE = "SPANISH_SCENE";
  public static String  SCENE_NAME    = "SCENE_NAME";
  private static String IN_ENGLISH    = "IN_ENGLISH";
  private static String REQUEST_CODE  = "REQUEST_CODE";
  public static String ID             = "ID";
	private String   sceneTitleEnglish;
  private String   sceneTitleSpanish;
  private String   sceneName;
	private boolean  inEnglish = false;
  private int      id;
  private TextView titleView;
  private TextView notificationView;
  private Button   languageButton;
  private Button   cancelButton;
  private int      requestCode = -1;

  /**
   * Bundle has to have ENGLISH_SCENE, SPANISH_SCENE, SCENE_NAME, ID
   */
  public static DeleteConfirmationDialog newInstance (Bundle bundle) {
    DeleteConfirmationDialog bD = new DeleteConfirmationDialog();
    bD.setArguments(bundle);
    return bD;
  }

  @Override
  public void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setStyle(DialogFragment.STYLE_NO_TITLE, getTheme());
    Bundle args = (savedInstanceState==null)? getArguments() : savedInstanceState;
    if (args != null) {
      if (args.containsKey(IN_ENGLISH))
        inEnglish = savedInstanceState.getBoolean(IN_ENGLISH);
      sceneTitleEnglish = args.getString(ENGLISH_SCENE);
      sceneTitleSpanish = args.getString(SPANISH_SCENE);
      id = args.getInt(ID);
      sceneName = args.getString(SCENE_NAME);
      if (args.containsKey(REQUEST_CODE))
        requestCode = args.getInt(REQUEST_CODE);
      else
        requestCode = getTargetRequestCode();
    }
  }

  @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog_delete_confirmation, container, false);
    titleView = getTextView(v, R.id.title);
    notificationView = getTextView(v, R.id.notification_message);
    Button okButton = (Button)v.findViewById(R.id.btn_ok);
    okButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Bundle bundle = Ez.oneIntBundle(ID, id);
        bundle.putString(SCENE_NAME, sceneName);
        bundle.putBoolean(DELETE, true);
        ((DialogDoneListener)getActivity()).notifyDialogDone(requestCode, false, bundle);
        DeleteConfirmationDialog.this.dismiss();
      }
    });

    cancelButton = (Button)v.findViewById(R.id.cancel);
    cancelButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        DeleteConfirmationDialog.this.dismiss();
      }
    });
    
    languageButton = (Button)v.findViewById(R.id.btn_language);
    languageButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        inEnglish = !inEnglish;
        setText();
      }
    });

    setText();
		return v;
	}
	
	public void onSaveInstanceState(Bundle outState) {
	  super.onSaveInstanceState(outState);
	  outState.putBoolean(IN_ENGLISH, this.inEnglish);
    outState.putString(ENGLISH_SCENE, this.sceneTitleEnglish);
    outState.putString(SPANISH_SCENE, this.sceneTitleSpanish);
    outState.putInt(ID, id);
    outState.putString(SCENE_NAME, sceneName);
    outState.putInt(REQUEST_CODE, requestCode);
	}

	private TextView getTextView (View v, int RId) {
    return (TextView)v.findViewById(RId);
  }

  private void setEnglishText () {
    titleView.setText(getResources().getString(R.string.deleteSceneTitleEng));
    String text = getResources().getString(R.string.deleteSceneEng) + " \'" +
      sceneTitleEnglish + "\', \'" + sceneTitleSpanish + "\'?";
    notificationView.setText(text);
    languageButton.setText(getResources().getString(R.string.enEspanol));
    cancelButton.setText(getResources().getString(R.string.EngCancel));
  }

  private void setSpanishText() {
    titleView.setText(getResources().getString(R.string.deleteSceneTitleSpn));
    String text = getResources().getString(R.string.deleteSceneSpn) + " \'" +
      sceneTitleEnglish + "\', \'" + sceneTitleSpanish + "\'?";
    notificationView.setText(text);
    languageButton.setText(getResources().getString(R.string.inEnglish));
    cancelButton.setText(getResources().getString(R.string.SpnCancel));
  }

  private void setText() {
    if (inEnglish)
      setEnglishText();
    else
      setSpanishText();
  }
}
