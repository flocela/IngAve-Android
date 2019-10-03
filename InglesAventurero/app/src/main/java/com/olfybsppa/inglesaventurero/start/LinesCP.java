package com.olfybsppa.inglesaventurero.start;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;

import com.olfybsppa.inglesaventurero.utils.Ez;
import com.olfybsppa.inglesaventurero.utils.UriDeterminer;

import java.util.HashSet;

public class LinesCP extends ContentProvider {

  private static final String database_name = "lines.db";

  public static final String AUTHORITY          = "com.olfybsppa.inglesaventurero.start.LinesCP";
  public static final String CP_URI             = "content://" + AUTHORITY + "/";

  public static final String scene_table        = "scene_table";
  public static final Uri    sceneTableUri      = Uri.parse(CP_URI + scene_table);

  public static final String page_table         = "page_table";
  public static final Uri    pageTableUri       = Uri.parse(CP_URI + page_table);

  public static final String hint_table         = "hint_table";
  public static final Uri    hintTableUri       = Uri.parse(CP_URI + hint_table);

  public static final String reply_table        = "reply_table";
  public static final Uri    replyTableUri      = Uri.parse(CP_URI + reply_table);

  public static final String backgrounds_table = "backgrounds_table";
  public static final Uri    backgroundTableUri = Uri.parse(CP_URI + backgrounds_table);

  public static final String img_credits_table = "img_credits_table";
  public static final Uri    imgCreditUri      = Uri.parse(CP_URI + img_credits_table);

  public static final String attributions_table = "attributions_table";
  public static final Uri    attributionTableUri  = Uri.parse(CP_URI + attributions_table);

  public static final String scene_name           = "scene_name";
  public static final String english_title        = "english_title";
  public static final String spanish_title        = "spanish_title";
  public static final String english_description  = "english_description";
  public static final String spanish_description  = "spanish_description";
  public static final String finished             = "finished";
  public static final String active               = "active";
  public static final String voice_attr_eng       = "voice_attr_eng";
  public static final String voice_attr_spn       = "voice_attr_spn";
  public static final String type_1               = "type_1";
  public static final String type_2               = "type_2";

  public static final String page_name      = "page_name";
  public static final String scene_id       = "scene_id";
  public static final String background_id  = "background_id";//_id integer from background table.

  public static final String page_id           = "page_id";
  public static final String pos_id            = "pos_id";
  public static final String reply_group_id    = "reply_group_id";
  public static final String hint_group_id     = "hint_group_id";
  public static final String next_page_name    = "next_page_name";
  public static final String is_first          = "is_first";
  public static final String normal_start_time = "normal_start_time";
  public static final String normal_end_time   = "normal_end_time";
  public static final String slow_start_time   = "slow_start_time";
  public static final String slow_end_time     = "slow_end_time";
  public static final String english_phrase    = "english_phrase";
  public static final String spanish_phrase    = "spanish_phrase";
  public static final String wfw_english       = "wfw_english";
  public static final String wfw_spanish       = "wfw_spanish";
  public static final String eng_explanation   = "eng_explanation";
  public static final String spn_explanation   = "spn_explanation";
  public static final String regex             = "regex";

  public static final String background_name   = "background_name";
  public static final String filename          = "filename";
  public static final String num_of_pages      = "num_of_pages";// num of pages referencing this background.

  public static final String img_credit_id     = "image_credit_id";
  public static final String image_info_name   = "image_info_name";// I came up with this.
  public static final String artist            = "artist";
  public static final String link_to_license   = "link_to_license";
  public static final String name_of_license   = "name_of_license";
  public static final String changes_english   = "changes_english";
  public static final String changes_spanish   = "changes_spanish";
  public static final String artist_image_name = "artist_image_name";
  public static final String image_url         = "image_url";
  public static final String image_url_name    = "image_url_name";
  public static final String artist_filename   = "artist_filename";
  public static final String num_of_pict_attr  = "num_of_pict_attr";// num of picture attributions referencing this imag_credit.

  private LinesDatabaseHelper dbHelper;

  protected static final class LinesDatabaseHelper extends SQLiteOpenHelper {
    LinesDatabaseHelper(Context context) {
      super(context, database_name, null, 1);
    }

    //TODO check to make sure on conflict replace actually works.
    @Override
    public void onCreate (SQLiteDatabase db) {
      db.execSQL("create table " +
          scene_table + " " +
          "( "+
          "_id integer primary key autoincrement, " +
          scene_name          + " text unique on conflict abort, " +
          english_title       + " text unique on conflict abort, " +
          spanish_title       + " text unique on conflict abort, " +
          english_description + " text, "                          +
          spanish_description + " text, "                          +
          voice_attr_eng      + " text, "                          +
          voice_attr_spn      + " text, "                          +
          finished            + " integer, "                       +
          active              + " integer default 1, "             +
          type_1              + " text, "                          +
          type_2              + " text);");

      db.execSQL("create table " +
          page_table + " " +
          "( "+
          "_id integer primary key autoincrement, " +
          scene_id      + " integer, " + //scene table's _id row
          page_name     + " integer, " +  // I determine this
          background_id + " integer, " +
          is_first      + " integer, " + //is first page in scene, 1 true, 0 false.
          "unique (" + page_name + ", " + scene_id + ") on conflict abort);");

      db.execSQL("create table " +
        backgrounds_table + " " +
        "( " +
        "_id integer primary key autoincrement, " +
          background_name + " text unique on conflict abort, " +
          num_of_pages    + " integer default 0, " +
          filename        + " text unique on conflict abort);");

      db.execSQL("create table " +
        attributions_table + " " +
          "( " +
          "_id integer primary key autoincrement, " +
          img_credit_id   + " integer not null, " +
          changes_english + " text not null, " +
          background_id   + " integer not null, " +
          changes_spanish + " text not null); ");

      db.execSQL("create table " +
        img_credits_table + " " +
        "( " +
        "_id integer primary key autoincrement, " +
        image_info_name   + " text unique on conflict ignore, " + // name I make up.
        num_of_pict_attr  + " integer default 0, " + //delete image credit when num_of_pict_attr is zero
        artist            + " text, " +
        link_to_license   + " text, " +
        name_of_license   + " text, " +
        artist_image_name + " text, " +
        image_url         + " text, " + // http where I downloaded the picture from
        image_url_name    + " text, " + // name of where I downloaded the picture from
        artist_filename   + " text); ");

      db.execSQL("create table " +
        hint_table + " " +
        "( " +
        "_id integer primary key autoincrement, " +
        scene_id          + " integer not null, " +
        page_id           + " integer not null, " +
        pos_id            + " integer not null, " +
        hint_group_id     + " integer not null, " +
        next_page_name    + " integer, " + // I determine this
        normal_start_time + " integer, " +
        normal_end_time   + " integer, " +
        slow_start_time   + " integer, " +
        slow_end_time     + " integer, " +
        wfw_english       + " text, " +
        wfw_spanish       + " text, " +
        spanish_phrase    + " text, " +
        english_phrase    + " text, " +
        eng_explanation   + " text, " +
        spn_explanation   + " text, " +
        "unique (" + page_id + ", " + pos_id + ") on conflict abort);");

      db.execSQL("create table " +
        reply_table + " " +
        "( " +
        "_id integer primary key autoincrement, " +
        scene_id          + " integer not null, " +
        page_id           + " integer not null, " +
        pos_id            + " integer not null, " +
        reply_group_id    + " integer not null, " +
        next_page_name    + " integer, " + // I determine this
        normal_start_time + " integer, " +
        normal_end_time   + " integer, " +
        slow_start_time   + " integer, " +
        slow_end_time     + " integer, " +
        wfw_english       + " text, " +
        wfw_spanish       + " text, " +
        spanish_phrase    + " text, " +
        english_phrase    + " text, " +
        regex             + " text, " +
        eng_explanation   + " text, " +
        spn_explanation   + " text, " +
        "unique (" + page_id + ", " + pos_id + ") on conflict abort); ");
    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {}
  } //end of database_helper

  @Override
  public Uri insert (Uri uri, ContentValues values) { //TODO lines
    // affected keeps increasing.
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    long rowIdAffected = -1;

    String tableName    = UriDeterminer.getTableName(uri);
    Uri    uriTableName = Uri.parse(CP_URI + tableName);
    db.beginTransaction();
    try {
      rowIdAffected = db.insertOrThrow(tableName, null, values);
      if (rowIdAffected != -1) {
        transactionSuccessful(uriTableName, db);
        if (uri.equals(pageTableUri)) {
          int backgroundId = values.getAsInteger(background_id);
          db.execSQL("UPDATE " + backgrounds_table + " SET " + num_of_pages + "=" +
            num_of_pages + "+1" + " WHERE " + BaseColumns._ID + "=" + backgroundId);
        }
        else if (uri.equals(attributionTableUri)) {
          int imgCreditID = values.getAsInteger(img_credit_id);
          db.execSQL("UPDATE " + img_credits_table + " SET " + num_of_pict_attr + "=" +
            num_of_pict_attr + "+1" + " WHERE " + BaseColumns._ID + "=" + imgCreditID);
        }
      }

    }finally {
       db.endTransaction();
    }
    Uri.Builder uriBuilder = uriTableName.buildUpon();
    uriBuilder.appendPath(""+rowIdAffected);
    return uriBuilder.build();
  }

  private void transactionSuccessful(Uri uriTableName, SQLiteDatabase db) {
    // should only call if scene was added.
    db.setTransactionSuccessful();
    getContext().getContentResolver().notifyChange(uriTableName, null);
  }

  @Override
  public int bulkInsert (Uri uri, ContentValues[] values) {
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    String tableName    = UriDeterminer.getTableName(uri);
    db.beginTransaction();
    long rowId = -1l;
    try {
      for (int ii = 0; ii < values.length; ii++) {
        rowId = -1l;
        rowId = db.insertOrThrow(tableName, null, values[ii]);
        if (rowId == -1) break;
      }
      if (rowId != -1)
        transactionSuccessful(uri, db);
    } finally {
      db.endTransaction();
    }
    if (rowId != -1) {
      return values.length;
    }
    else {
      return 0;
    }
  }


  @Override
  public int delete (Uri uri, String where, String[] selectionArgs) {
    SQLiteDatabase dB = dbHelper.getWritableDatabase();
    dB.beginTransaction();
    int totalDeleted = 0;
    String tableName = UriDeterminer.getTableName(uri);
    try {
      if (uri.equals(pageTableUri)) {
        Cursor pcu = query(pageTableUri, new String[]{background_id}, where, null, null);
        HashSet<Integer> bgIds = new HashSet<>();
        if (pcu != null) {
          pcu.moveToFirst();
          while (!pcu.isAfterLast()) {
            bgIds.add(pcu.getInt(pcu.getColumnIndex(LinesCP.background_id)));
            pcu.moveToNext();
          }
          pcu.close();
        }
        String whereClause = Ez.orWhere(BaseColumns._ID, bgIds);
        dB.execSQL("UPDATE " + backgrounds_table + " SET " + num_of_pages + "=" +
          num_of_pages + "-1" + " WHERE " + whereClause);
      }
      else if (uri.equals(attributionTableUri)) {
        Cursor acu = query(attributionTableUri, new String[]{img_credit_id}, where, null, null);
        HashSet<Integer> imCreditIDs = new HashSet<>();
        if (acu != null) {
          acu.moveToFirst();
          while (!acu.isAfterLast()) {
            imCreditIDs.add(acu.getInt(acu.getColumnIndex(LinesCP.img_credit_id)));
            acu.moveToNext();
          }
          acu.close();
        }
        String whereClause = Ez.orWhere(BaseColumns._ID, imCreditIDs);
        dB.execSQL("UPDATE " + img_credits_table + " SET " + num_of_pict_attr + "=" +
          num_of_pict_attr + "-1" + " WHERE " + whereClause);
      }
      totalDeleted = dB.delete(tableName, where, null);
      if (totalDeleted > 0)
        transactionSuccessful(uri, dB);
    } finally {
      dB.endTransaction();
    }
    return totalDeleted;
  }

  @Override
  public String getType(Uri uri) {
    return null;
  }

  @Override
  public Cursor query (Uri uri,
                       String[] columns,
                       String selection,
                       String[] selectionArgs,
                       String sortOrder) {
    String tableName = UriDeterminer.getTableName(uri);
    Cursor returnCursor = null;
    SQLiteDatabase dB = dbHelper.getWritableDatabase();
    returnCursor = dB.query(tableName,     columns, selection,
                            selectionArgs, null,    null,
                            sortOrder,     null);
    returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
    return returnCursor;
  }

  @Override
  public int update (Uri uri, ContentValues values,
                     String selection,
                     String[] selectionArgs) {
    SQLiteDatabase dB = dbHelper.getWritableDatabase();
    dB.beginTransaction();
    int totalAffected = 0;

    String tableName = UriDeterminer.getTableName(uri);
    try {
      totalAffected = dB.update(tableName, values, selection, null);
      if (0 < totalAffected) {
        Uri uriTableName = Uri.parse(CP_URI + tableName);
        transactionSuccessful(uriTableName, dB);
      }
    } finally {
      dB.endTransaction();
    }
    return totalAffected;
  }

  @Override
  public boolean onCreate () {
    dbHelper = new LinesDatabaseHelper(getContext());
    return true;
  }

  public SQLiteOpenHelper getOpenHelperForTest() {
    return dbHelper;
  }

}
