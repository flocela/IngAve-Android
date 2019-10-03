package com.olfybsppa.inglesaventurero.webscenelistactivityutest.resolverwrapper.rwscenes;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.start.LinesCP;
import com.olfybsppa.inglesaventurero.webscenelistactivity.ResolverWrapper;
import com.olfybsppa.inglesaventurero.utils.Ez;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Uri.class})
public class RWSceneQueryUTest {

  @Mock Uri mSceneUri;
  @Mock Cursor mCursor;
  @Mock ContentResolver mCR;
  String sceneName = "sceneName";
  @Before
  public void setUp() {
    mockStatic(Uri.class);
    when(Uri.parse(LinesCP.CP_URI + LinesCP.scene_table)).thenReturn(mSceneUri);
  }

  @Test
  public void returnRowIdWhenSceneInContentProvider () throws Exception {
    when(mCR.query(mSceneUri,
                   new String[]{BaseColumns._ID},
                   Ez.where(LinesCP.scene_name, sceneName),
                   null,
                   null)).thenReturn(mCursor);
    when(mCursor.moveToFirst()).thenReturn(true);
    when(mCursor.getColumnIndex(BaseColumns._ID)).thenReturn(11);
    when(mCursor.getInt(11)).thenReturn(13);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    assertEquals(13, rW.retrieveSceneRowId(sceneName));
  }

  @Test
  public void returnNegativeOneWhenSceneNotInContentProvider () throws Exception {
    when(mCR.query(mSceneUri,
      new String[]{BaseColumns._ID},
      Ez.where(LinesCP.scene_name, sceneName),
      null,
      null)).thenReturn(mCursor);
    when(mCursor.moveToFirst()).thenReturn(false);

    ResolverWrapper rW = new ResolverWrapper(mCR);
    assertEquals(-1, rW.retrieveSceneRowId(sceneName));
  }

}
