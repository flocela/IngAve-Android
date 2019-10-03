package com.olfybsppa.inglesaventurero.webscenelistactivity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.olfybsppa.inglesaventurero.R;
import com.olfybsppa.inglesaventurero.collectors.SceneInfo;

import java.util.List;

public class WebListAdapter extends ArrayAdapter<SceneInfo> {

  public WebListAdapter(Context context, int textViewResourceId) {
    super(context, R.layout.web_scene_title);
  }

  public WebListAdapter(Context context, int resource, List<SceneInfo> items) {
    super(context, resource, items);
  }

  public void setData(List<SceneInfo> data) {
    clear();
    if (data != null) {
      addAll(data);
    }
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    WebTitleView view = null;
    if (convertView == null) {
      view = new WebTitleView(getContext());
    }
    else {
      view = (WebTitleView)convertView;
    }
    SceneInfo item = getItem(position);
    view.setSceneName(item.getSceneName());
    view.setEnglishTitle(item.getEnglishTitle());
    view.setSpanishTitle(item.getSpanishTitle());
    view.setDifficulty(Integer.parseInt(item.getDifficulty()));
    view.setWebId(item.getWebId());
    view.setSpanishDescription(item.getSpanishDescription());
    view.setEnglishDescription(item.getEnglishDescription());
    if (item.getCompleted()) {
      view.setCompleted(item.getCompleted());
    }
    else if (item.getDownloaded()) {
      view.setDownloaded(item.getDownloaded());
    }
    return view;
  }
}
