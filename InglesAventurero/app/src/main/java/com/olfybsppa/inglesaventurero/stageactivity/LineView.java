package com.olfybsppa.inglesaventurero.stageactivity;


public interface LineView {

  void    addFlasher(FlasherI flasher);
  void    addMarker();
  void    clearFlash ();
  void    clearMarkers();
  void    flash();
  int     getGroupId();
  int     getPositionInPage();
  boolean isOn();
  void    showLines();
  void    turnOnWords ();
}
