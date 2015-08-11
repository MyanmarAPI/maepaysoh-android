package org.maepaysoh.maepaysoh.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysoh.utils.JsonUtils;
import org.maepaysoh.maepaysohsdk.models.FaqDatum;

/**
 * Created by yemyatthu on 8/7/15.
 */

// TODO: 8/7/15 USE SQL RELATIONSHIP IN PLACE OF JSON SERIALIZATION
public class FaqDao {
  private SQLiteDatabase mMaepaysohDb;
  private MaepaysohDbHelper mMaepaysohDbHelper;

  public FaqDao(Context context) {
    mMaepaysohDbHelper = new MaepaysohDbHelper(context);
  }

  public void open() throws SQLException {
    mMaepaysohDb = mMaepaysohDbHelper.getWritableDatabase();
  }

  public void close() throws SQLException {
    mMaepaysohDbHelper.close();
  }

  public boolean createFaq(FaqDatum faqDatum) throws SQLException {
    open();
    ContentValues faqContentValues = new ContentValues();
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_ID, faqDatum.getId());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_question,faqDatum.getQuestion());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_answer,faqDatum.getAnswer());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_basis,faqDatum.getBasis());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_type,faqDatum.getType());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_url,faqDatum.getUrl());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_sections,JsonUtils.convertToJson(faqDatum.getSections()));
    mMaepaysohDb.beginTransaction();
    try {
      long insertId =
          mMaepaysohDb.insert(MaepaysohDbHelper.TABLE_NAME_FAQ, null, faqContentValues);
      mMaepaysohDb.setTransactionSuccessful();
    } catch (SQLiteException e) {
      Log.e("error: ", e.getLocalizedMessage());
    } finally {
      mMaepaysohDb.endTransaction();
    }
    return true;
  }

  public List<FaqDatum> getAllFaqData() throws SQLException {
    open();
    List<FaqDatum> faqDatums = new ArrayList<>();
    Cursor cursor =
        mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_FAQ, null, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      FaqDatum faqDatum = cursorToFaq(cursor);
      faqDatums.add(faqDatum);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return faqDatums;
  }

  public FaqDatum get(String id) throws SQLException {
    open();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_FAQ, null,
        MaepaysohDbHelper.COLUMN_FAQ_ID + " = " + id, null, null, null, null);
    FaqDatum faqDatum = cursorToFaq(cursor);
    cursor.close();
    close();
    return faqDatum;
  }

  private FaqDatum cursorToFaq(Cursor cursor) {
    Type type = new TypeToken<List<String>>() {
    }.getType();
    FaqDatum faqDatum = new FaqDatum();
    faqDatum.setId(cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_ID)));
    faqDatum.setQuestion(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_question)));
    faqDatum.setAnswer(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_answer)));
    faqDatum.setBasis(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_basis)));
    faqDatum.setSections(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_sections)),
        type));
    faqDatum.setType(cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_type)));

    return faqDatum;
  }
}
