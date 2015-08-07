package org.maepaysoh.maepaysoh.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ye Lin Aung on 15/08/07.
 */
public class MaepaysohDbHelper extends SQLiteOpenHelper {

  // Party
  public static final String TABLE_NAME_PARTY = "parties";
  public static final String COLUMN_PARTY_ID = "_id";
  public static final String COLUMN_PARTY_NAME = "name";
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

  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAME = "maepaysoh.db";

  public MaepaysohDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase db) {

  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
