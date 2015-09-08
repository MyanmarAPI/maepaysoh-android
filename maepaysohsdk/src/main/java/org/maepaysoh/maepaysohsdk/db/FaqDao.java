package org.maepaysoh.maepaysohsdk.db;

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
import org.maepaysoh.maepaysohsdk.models.FAQ;
import org.maepaysoh.maepaysohsdk.utils.JsonUtils;

import static org.maepaysoh.maepaysohsdk.utils.Logger.LOGE;
import static org.maepaysoh.maepaysohsdk.utils.Logger.makeLogTag;

/**
 * Created by yemyatthu on 8/7/15.
 */

// TODO: 8/7/15 USE SQL RELATIONSHIP IN PLACE OF JSON SERIALIZATION
public class FaqDao {
  private SQLiteDatabase mMaepaysohDb;
  private MaepaysohDbHelper mMaepaysohDbHelper;

  private static final String TAG = makeLogTag(FaqDao.class);

  public FaqDao(Context context) {
    mMaepaysohDbHelper = new MaepaysohDbHelper(context);
  }

  public void open() throws SQLException {
    mMaepaysohDb = mMaepaysohDbHelper.getWritableDatabase();
  }

  public void close() throws SQLException {
    mMaepaysohDbHelper.close();
  }

  public boolean createFaq(FAQ FAQ) throws SQLException {
    open();
    ContentValues faqContentValues = new ContentValues();
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_ID, FAQ.getId());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_question, FAQ.getQuestion());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_answer, FAQ.getAnswer());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_basis, FAQ.getBasis());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_type, FAQ.getType());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_url, FAQ.getUrl());
    faqContentValues.put(MaepaysohDbHelper.COLUMN_FAQ_sections,
        JsonUtils.convertToJson(FAQ.getSections()));
    mMaepaysohDb.beginTransaction();
    try {
      long insertId = mMaepaysohDb.insertWithOnConflict(MaepaysohDbHelper.TABLE_NAME_FAQ, null,
          faqContentValues, SQLiteDatabase.CONFLICT_REPLACE);
      mMaepaysohDb.setTransactionSuccessful();
    } catch (SQLiteException e) {
      LOGE(TAG, e.getLocalizedMessage());
    } finally {
      mMaepaysohDb.endTransaction();
    }
    return true;
  }

  public List<FAQ> getAllFaqData() throws SQLException {
    open();
    List<FAQ> FAQs = new ArrayList<>();
    Cursor cursor =
        mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_FAQ, null, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      FAQ FAQ = cursorToFaq(cursor);
      FAQs.add(FAQ);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return FAQs;
  }

  public List<FAQ> searchFAQsFromDb(String keyword) {
    open();
    List<FAQ> FAQs = new ArrayList<>();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_FAQ, null,
        MaepaysohDbHelper.COLUMN_FAQ_question + " LIKE " + "'%" + keyword + "%'", null, null, null,
        null);

    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        FAQ FAQ = cursorToFaq(cursor);
        FAQs.add(FAQ);
        cursor.moveToNext();
      }
    }
    cursor.close();
    close();
    return FAQs;
  }

  public FAQ getFaqById(String id) throws SQLException {
    open();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_FAQ, null,
        MaepaysohDbHelper.COLUMN_FAQ_ID + " = " + id, null, null, null, null);
    FAQ FAQ = cursorToFaq(cursor);
    cursor.close();
    close();
    return FAQ;
  }

  private FAQ cursorToFaq(Cursor cursor) {
    Type type = new TypeToken<List<String>>() {
    }.getType();
    FAQ FAQ = new FAQ();
    FAQ.setId(cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_ID)));
    FAQ.setQuestion(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_question)));
    FAQ.setAnswer(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_answer)));
    FAQ.setBasis(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_basis)));
    FAQ.setSections(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_sections)),
        type));
    FAQ.setType(cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_FAQ_type)));

    return FAQ;
  }
}
