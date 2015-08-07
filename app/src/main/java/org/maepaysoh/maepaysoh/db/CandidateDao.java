package org.maepaysoh.maepaysoh.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysoh.models.CandidateData;
import org.maepaysoh.maepaysoh.models.Constituency;
import org.maepaysoh.maepaysoh.models.Father;
import org.maepaysoh.maepaysoh.models.Mother;
import org.maepaysoh.maepaysoh.models.Residency;
import org.maepaysoh.maepaysoh.utils.JsonUtils;

/**
 * Created by yemyatthu on 8/7/15.
 */
// TODO: 8/7/15 USE SQL RELATIONSHIP IN PLACE OF JSON SERIALIZATION
public class CandidateDao {
  private SQLiteDatabase mMaepaysohDb;
  private MaepaysohDbHelper mMaepaysohDbHelper;

  public CandidateDao(Context context) {
    mMaepaysohDbHelper = new MaepaysohDbHelper(context);
  }

  public void open() throws SQLException {
    mMaepaysohDb = mMaepaysohDbHelper.getWritableDatabase();
  }

  public void close() throws SQLException {
    mMaepaysohDbHelper.close();
  }

  public boolean createCandidate(CandidateData candidateData) throws SQLException {
    Gson gson = new Gson();
    open();
    ContentValues candidateContentValues = new ContentValues();
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_ID, candidateData.getId());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_NAME, candidateData.getName());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONAL_ID,
        candidateData.getNationalId());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONALITY_RELIGION,
        candidateData.getNationalityReligion());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_BIRTHDATE,
        (long)candidateData.getBirthdate());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_EDUCATION,
        JsonUtils.convertToJson(candidateData.getEducation()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_OCCUPATION,
        JsonUtils.convertToJson(candidateData.getOccupation()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_LEGISLATURE,
        candidateData.getLegislature());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_RESIDENCY,gson.toJson(
        candidateData.getResidency()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_CONSTITUENCY, gson.toJson(
        candidateData.getConstituency()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_MOTHER, gson.toJson(
        candidateData.getMother()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_FATHER, gson.toJson(
        candidateData.getFather()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_PARTY_ID, candidateData.getPartyId());
    mMaepaysohDb.beginTransaction();
    try {
      long insertId =
          mMaepaysohDb.insert(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null, candidateContentValues);
      mMaepaysohDb.setTransactionSuccessful();
    } catch (SQLiteException e) {
      Log.e("error: ", e.getLocalizedMessage());
    } finally {
      mMaepaysohDb.endTransaction();
    }
    return true;
  }

  public List<CandidateData> getAllCandidateData() throws SQLException {
    open();
    List<CandidateData> candidateDatas = new ArrayList<>();
    Cursor cursor =
        mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      CandidateData candidateData = cursorToCandidate(cursor);
      candidateDatas.add(candidateData);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return candidateDatas;
  }

  public CandidateData getCandidateById(String id) throws SQLException {
    open();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null,
        MaepaysohDbHelper.COLUMN_CANDIDATE_ID + " = " + id, null, null, null, null);
    CandidateData candidateData = cursorToCandidate(cursor);
    cursor.close();
    close();
    return candidateData;
  }

  private CandidateData cursorToCandidate(Cursor cursor) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<String>>() {
    }.getType();
    CandidateData candidateData = new CandidateData();
    candidateData.setId(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_ID)));
    candidateData.setName(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_NAME)));
    candidateData.setNationalId(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONAL_ID)));
    candidateData.setBirthdate(
        (int) cursor.getLong(
            cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_BIRTHDATE)));
    candidateData.setConstituency(gson.fromJson(cursor.getString(
            cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_CONSTITUENCY)),
        Constituency.class));
    candidateData.setEducation(JsonUtils.convertToJava(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_EDUCATION)), type));
    candidateData.setOccupation(JsonUtils.convertToJava(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_OCCUPATION)), type));
    candidateData.setFather(gson.fromJson(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_FATHER)),
        Father.class));
    candidateData.setMother(gson.fromJson(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_MOTHER)),
        Mother.class));
    candidateData.setLegislature(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_LEGISLATURE)));
    candidateData.setNationalityReligion(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONALITY_RELIGION)));
    candidateData.setPartyId(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_PARTY_ID)));
    candidateData.setResidency(gson.fromJson(cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_RESIDENCY)), Residency.class));
    return candidateData;
  }

}
