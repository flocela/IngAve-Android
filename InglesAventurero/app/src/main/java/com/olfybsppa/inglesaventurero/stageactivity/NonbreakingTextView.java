package com.olfybsppa.inglesaventurero.stageactivity;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.util.AttributeSet;

public class NonbreakingTextView extends android.support.v7.widget.AppCompatTextView {
  private static final String TAG = "NonBreakingPeriodTextView";

  public NonbreakingTextView(Context context) {
    super(context);
  }

  public NonbreakingTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    Editable editable = getEditableText();
    if (editable == null) {
      return;
    }
    int width = getWidth() - getPaddingLeft() - getPaddingRight();
    if (width == 0) {
      return;
    }

    Paint p = getPaint();
    float[] widths = new float[editable.length()];
    p.getTextWidths(editable.toString(), widths);
    float curWidth = 0.0f;
    int lastWSPos = -1;
    int strPos = 0;
    final char newLine = '\n';
    final String newLineStr = "\n";
    boolean reset = false;
    int insertCount = 0;

        /*
         * Traverse the string from the start position, adding each character's width to the total
         * until: 1) A whitespace character is found. In this case, mark the whitespace position. If
         * the width goes over the max, this is where the newline will be inserted. 2) A newline
         * character is found. This resets the curWidth counter. curWidth > width. Replace the
         * whitespace with a newline and reset the counter.
         */

    while (strPos < editable.length()) {
      curWidth += widths[strPos];

      char curChar = editable.charAt(strPos);

      if (curChar == newLine) {
        reset = true;
      } else if (Character.isWhitespace(curChar)) {
        lastWSPos = strPos;
      } else if (curWidth > width && lastWSPos >= 0) {
        editable.replace(lastWSPos, lastWSPos + 1, newLineStr);
        insertCount++;
        strPos = lastWSPos;
        lastWSPos = -1;
        reset = true;
      }

      if (reset) {
        curWidth = 0.0f;
        reset = false;
      }

      strPos++;
    }

    if (insertCount != 0) {
      setText(editable);
    }
  }
}
