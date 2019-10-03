package com.olfybsppa.inglesaventurero.utilsutest;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.olfybsppa.inglesaventurero.utils.SpanPainter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;

//import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({SpannableString.class, ForegroundColorSpan.class})
public class SpanPainterUTest {

  @Mock SpannableString mockSpanString;
  @Mock ForegroundColorSpan mockForegroundColor;


  @Test
  public void testAppliesColorPerRange () {

    SpanPainter spanPainter = new SpanPainter(mockForegroundColor);
    int start = 0;
    int end = 10;

    spanPainter.applyColor(mockSpanString, start, end);

    //verify(mockSpanString).setSpan(mockForegroundColor, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
  }


}
