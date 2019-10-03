package com.olfybsppa.inglesaventurero.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

public class SpanPainter {
  ForegroundColorSpan color;

  public SpanPainter (ForegroundColorSpan color) {
    this.color = color;
  }

  public SpannableString applyColor(SpannableString span, int start, int end) {
    span.setSpan(color, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    return span;
  }

}
