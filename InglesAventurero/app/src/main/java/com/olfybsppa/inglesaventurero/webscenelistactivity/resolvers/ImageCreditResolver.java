package com.olfybsppa.inglesaventurero.webscenelistactivity.resolvers;


import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;

public class ImageCreditResolver {
  private ResolverWrapper rW;

  public ImageCreditResolver(ResolverWrapper rW) {
    this.rW = rW;
  }

  //Checked by inspection.
  /**
   * deletes imageCredit if it is not used by any attributions.
   */
  public void clearImageCredit (int imageCreditRow) {
    int numOfAttrs = rW.retrieveNumOfAttributionsUsingImageCredit(imageCreditRow);
    if (numOfAttrs <= 0) {
      rW.deleteImageCredit(imageCreditRow);
    }
  }

}