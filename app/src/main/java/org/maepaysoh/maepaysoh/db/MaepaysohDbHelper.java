package org.maepaysoh.maepaysoh.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ye Lin Aung on 15/08/07.
 */
public class MaepaysohDbHelper extends SQLiteOpenHelper {
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "maepaysoh.db";
  private static final String COMMA_SEP = ",";
  private static final String TEXT_TYPE = " TEXT";

  // Party
  public static final String TABLE_NAME_PARTY = "parties";
  public static final String COLUMN_PARTY_ID = "_id";
  public static final String COLUMN_PARTY_NAME = "name";
  public static final String COLUMN_PARTY_NAME_ENGLISH = "name_english";
  public static final String COLUMN_PARTY_ESTABLISHMENT_DATE = "establishment_date";
  public static final String COLUMN_PARTY_MEMBER_COUNT = "member_count";
  public static final String COLUMN_PARTY_ESTABLISHMENT_APPROVAL_DATE =
      "establishment_approval_date";
  public static final String COLUMN_PARTY_REGISTRATION_APPLICATION_DATE =
      "registration_application_date";
  public static final String COLUMN_PARTY_REGISTRATION_APPROVAL_DATE = "registration_approval_date";
  public static final String COLUMN_PARTY_APPROVED_PARTY_NUMBER = "approved_party_number";
  public static final String COLUMN_PARTY_PARTY_FLAG = "party_flag";
  public static final String COLUMN_PARTY_PARTY_SEAL = "party_seal";
  public static final String COLUMN_PARTY_CHAIRMAN = "chairman";
  public static final String COLUMN_PARTY_REGION = "region";
  public static final String COLUMN_PARTY_CONTACT = "contact";
  public static final String COLUMN_PARTY_POLICY = "policy";
  public static final String COLUMN_PARTY_HEADQUARTER = "party_headquarter";
  public static final String COLUMN_PARTY_LEADERSHIP = "leadership";

  private static final String SQL_CREATE_PARTY_TABLE =
      "CREATE TABLE " + MaepaysohDbHelper.TABLE_NAME_PARTY + " (" +
          MaepaysohDbHelper.COLUMN_PARTY_ID + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_NAME + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_NAME_ENGLISH + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_HEADQUARTER + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_LEADERSHIP + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_DATE + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_MEMBER_COUNT + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_APPROVAL_DATE + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_REGISTRATION_APPLICATION_DATE + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_REGISTRATION_APPROVAL_DATE + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_APPROVED_PARTY_NUMBER + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_PARTY_FLAG + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_PARTY_SEAL + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_CHAIRMAN + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_REGION + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_CONTACT + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_PARTY_POLICY + TEXT_TYPE +
          " )";

  private static final String SQL_DELETE_PARTIES =
      "DROP TABLE IF EXISTS " + MaepaysohDbHelper.TABLE_NAME_PARTY;

  //FAQs
  public static final String TABLE_NAME_FAQ = "faqs";
  public static final String COLUMN_FAQ_ID = "_id";
  public static final String COLUMN_FAQ_question = "question";
  public static final String COLUMN_FAQ_answer = "answer";
  public static final String COLUMN_FAQ_type = "type";
  public static final String COLUMN_FAQ_basis = "basis";
  public static final String COLUMN_FAQ_sections = "sections";
  public static final String COLUMN_FAQ_url = "url";

  private static final String SQL_CREATE_FAQ_TABLE =
      "CREATE TABLE " + MaepaysohDbHelper.TABLE_NAME_FAQ + " (" +
          MaepaysohDbHelper.COLUMN_FAQ_ID + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_question + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_answer + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_basis + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_type + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_sections + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_url + TEXT_TYPE + COMMA_SEP +
          " )";

  private static final String SQL_DELETE_FAQS =
      "DROP TABLE IF EXISTS " + MaepaysohDbHelper.TABLE_NAME_FAQ;

  public MaepaysohDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_PARTY_TABLE);
    db.execSQL(SQL_CREATE_FAQ_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_PARTIES);
    db.execSQL(SQL_DELETE_FAQS);
    onCreate(db);
  }
}
