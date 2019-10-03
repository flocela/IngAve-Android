package com.olfybsppa.inglesaventurero.stageactivity;

public class FlasherMaker {
  private FlasherI flasherR;
  private FlasherI flasherH;

  public FlasherMaker (String story) {
    if (story.equals(StageActivity.ESPRESSO_STORY)) {
      flasherR = new FlasherForTests();
      flasherH = new FlasherForTests();
    }
    else {
      flasherR = new FlasherForReply();
      flasherH = new FlasherForHint();
    }
  }

  public FlasherI getFlasher (LineView lineView) {
    if (lineView instanceof ReplyView || lineView instanceof RLineSetView) {
      return flasherR;
    }
    else if (lineView instanceof HintView)
      return flasherH;
    else
      return null;
  }
}
