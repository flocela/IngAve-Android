package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwscenes;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class, UriDeterminer.class})
public class RWSceneInsertUTest {
  @Mock Uri mockSceneUri;

  @Mock ContentResolver mockCR;
  @Mock ContentValues   mockContentValues;

  @Mock ContentValues badContentValues;//used to fail tests to make sure test is running at all.
  @Mock Uri badUri;

  @Test
  public void callsInsertSceneGivenOnContentResolver () throws Exception {
    ResolverWrapper rW = new ResolverWrapper(mockCR);
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.scene_table)).thenReturn(mockSceneUri);
    mockStatic(UriDeterminer.class);
    when(UriDeterminer.getLastId(mockSceneUri)).thenReturn(77);
    when(mockCR.insert(LinesCP.sceneTableUri, mockContentValues)).thenReturn(mockSceneUri);

    int rowId = rW.insertScene(mockContentValues);
    assertEquals(77, rowId);
  }


}
