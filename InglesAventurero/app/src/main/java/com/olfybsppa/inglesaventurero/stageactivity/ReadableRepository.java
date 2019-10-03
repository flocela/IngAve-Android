package com.olfybsppa.inglesaventurero.stageactivity;


import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

public interface ReadableRepository {
  int     getCount();
  Page    getPage(int position);
  Page    getPageFromName(int name);
  void    addListener(Listener listener);

  interface Listener {
    void notifyDataSetChanged();
  }
}
