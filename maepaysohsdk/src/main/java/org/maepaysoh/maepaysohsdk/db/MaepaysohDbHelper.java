package org.maepaysoh.maepaysohsdk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ye Lin Aung on 15/08/07.
 */

// TODO: 8/7/15 CREATE SEPERATE TABLE FOR ALL NESTED OBJECTS
public class MaepaysohDbHelper extends SQLiteOpenHelper {
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "maepaesoh.db";
  // PartyListReturnObject
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
  //FAQs
  public static final String TABLE_NAME_FAQ = "faqs";
  public static final String COLUMN_FAQ_ID = "_id";
  public static final String COLUMN_FAQ_question = "question";
  public static final String COLUMN_FAQ_answer = "answer";
  public static final String COLUMN_FAQ_type = "type";
  public static final String COLUMN_FAQ_basis = "basis";
  public static final String COLUMN_FAQ_sections = "sections";
  public static final String COLUMN_FAQ_url = "url";
  public static final String TABLE_NAME_CANDIDATE = "candidates";
  public static final String COLUMN_CANDIDATE_ID = "_id";
  public static final String COLUMN_CANDIDATE_NAME = "name";
  public static final String COLUMN_CANDIDATE_LEGISLATURE = "legislature";
  public static final String COLUMN_CANDIDATE_NATIONAL_ID = "national_id";
  public static final String COLUMN_CANDIDATE_BIRTHDATE = "birthdate";
  public static final String COLUMN_CANDIDATE_EDUCATION = "education";
  public static final String COLUMN_CANDIDATE_OCCUPATION = "occupation";
  public static final String COLUMN_CANDIDATE_RESIDENCY = "residence";
  public static final String COLUMN_CANDIDATE_CONSTITUENCY = "constituency";
  public static final String COLUMN_CANDIDATE_NATIONALITY_RELIGION = "nationality_religion";
  public static final String COLUMN_CANDIDATE_PARTY_ID = "party_id";
  public static final String COLUMN_CANDIDATE_FATHER = "father";
  public static final String COLUMN_CANDIDATE_MOTHER = "mother";
  public static final String COLUMN_CANDIDATE_PHOTO_URL = "photo_url";
  private static final String COMMA_SEP = ",";
  private static final String TEXT_TYPE = " TEXT";
  private static final String NUMERIC_TYPE = " LONG";
  private static final String PRIMARY_KEY_NOT_NULL = " PRIMARY KEY NOT NULL";
  private static final String SQL_CREATE_PARTY_TABLE =
      "CREATE TABLE " + MaepaysohDbHelper.TABLE_NAME_PARTY + " (" +
          MaepaysohDbHelper.COLUMN_PARTY_ID + TEXT_TYPE + PRIMARY_KEY_NOT_NULL + COMMA_SEP +
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
  private static final String SQL_CREATE_FAQ_TABLE =
      "CREATE TABLE " + MaepaysohDbHelper.TABLE_NAME_FAQ + " (" +
          MaepaysohDbHelper.COLUMN_FAQ_ID + TEXT_TYPE + PRIMARY_KEY_NOT_NULL + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_question + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_answer + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_basis + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_type + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_sections + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_FAQ_url + TEXT_TYPE +
          " )";
  private static final String SQL_DELETE_FAQS =
      "DROP TABLE IF EXISTS " + MaepaysohDbHelper.TABLE_NAME_FAQ;
  private static final String SQL_CREATE_CANDIDATE_TABLE =
      "CREATE TABLE " + MaepaysohDbHelper.TABLE_NAME_CANDIDATE + " (" +
          MaepaysohDbHelper.COLUMN_CANDIDATE_ID + TEXT_TYPE + PRIMARY_KEY_NOT_NULL + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_NAME + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_LEGISLATURE + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONAL_ID + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_BIRTHDATE + NUMERIC_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_EDUCATION + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_OCCUPATION + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_RESIDENCY + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_CONSTITUENCY + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONALITY_RELIGION + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_PARTY_ID + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_FATHER + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_PHOTO_URL + TEXT_TYPE + COMMA_SEP +
          MaepaysohDbHelper.COLUMN_CANDIDATE_MOTHER + TEXT_TYPE +
          " )";
  private static final String SQL_DELETE_CANDIDATES =
      "DROP TABLE IF EXISTS " + MaepaysohDbHelper.TABLE_NAME_CANDIDATE;

  public MaepaysohDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_PARTY_TABLE);
    db.execSQL(SQL_CREATE_FAQ_TABLE);
    db.execSQL(SQL_CREATE_CANDIDATE_TABLE);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(SQL_DELETE_PARTIES);
    db.execSQL(SQL_DELETE_FAQS);
    db.execSQL(SQL_DELETE_CANDIDATES);
    onCreate(db);
  }
}
