package org.maepaysoh.maepaysohsdk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysohsdk.models.Candidate;
import org.maepaysoh.maepaysohsdk.models.Constituency;
import org.maepaysoh.maepaysohsdk.models.Father;
import org.maepaysoh.maepaysohsdk.models.Mother;

import static org.maepaysoh.maepaysohsdk.utils.Logger.LOGE;
import static org.maepaysoh.maepaysohsdk.utils.Logger.makeLogTag;

/**
 * Created by yemyatthu on 8/7/15.
 */
// TODO: 8/7/15 USE SQL RELATIONSHIP IN PLACE OF JSON SERIALIZATION
public class CandidateDao {
  private SQLiteDatabase mMaepaysohDb;
  private MaepaysohDbHelper mMaepaysohDbHelper;

  private static final String TAG = makeLogTag(CandidateDao.class);

  public CandidateDao(Context context) {
    mMaepaysohDbHelper = new MaepaysohDbHelper(context);
  }

  public void open() throws SQLException {
    mMaepaysohDb = mMaepaysohDbHelper.getWritableDatabase();
  }

  public void close() throws SQLException {
    mMaepaysohDbHelper.close();
  }

  public boolean createCandidate(Candidate candidate) throws SQLException {
    Gson gson = new Gson();
    open();
    ContentValues candidateContentValues = new ContentValues();
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_ID, candidate.getId());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_NAME, candidate.getName());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONALITY_RELIGION,
        candidate.getReligion());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_BIRTHDATE,
        (long) candidate.getBirthdate());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_EDUCATION,
       candidate.getEducation());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_OCCUPATION,
       candidate.getOccupation());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_LEGISLATURE,
        candidate.getLegislature());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_RESIDENCY,candidate.getWardVillage());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_CONSTITUENCY,
        gson.toJson(candidate.getConstituency()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_MOTHER,
        gson.toJson(candidate.getMother()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_FATHER,
        gson.toJson(candidate.getFather()));
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_PARTY_ID, candidate.getPartyId());
    candidateContentValues.put(MaepaysohDbHelper.COLUMN_CANDIDATE_PHOTO_URL,candidate.getPhotoUrl());
    mMaepaysohDb.beginTransaction();
    try {
      long insertId =
          mMaepaysohDb.insertWithOnConflict(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null,
              candidateContentValues, SQLiteDatabase.CONFLICT_REPLACE);
      mMaepaysohDb.setTransactionSuccessful();
    } catch (SQLiteException e) {
      LOGE(TAG, e.getLocalizedMessage());
    } finally {
      mMaepaysohDb.endTransaction();
    }
    return true;
  }

  public List<Candidate> getAllCandidateData() throws SQLException {
    open();
    List<Candidate> candidates = new ArrayList<>();
    Cursor cursor =
        mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null, null, null, null, null,
            null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Candidate candidate = cursorToCandidate(cursor);
      candidates.add(candidate);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return candidates;
  }

  public Candidate getCandidateById(String id) throws SQLException {
    open();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null,
        MaepaysohDbHelper.COLUMN_CANDIDATE_ID + " = " + id, null, null, null, null);
    Candidate candidate = cursorToCandidate(cursor);
    cursor.close();
    close();
    return candidate;
  }

  private Candidate cursorToCandidate(Cursor cursor) {
    Gson gson = new Gson();
    Type type = new TypeToken<List<String>>() {
    }.getType();
    Candidate candidate = new Candidate();
    candidate.setId(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_ID)));
    candidate.setName(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_NAME)));
    //candidate.setNationalId(cursor.getString(
    //    cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONAL_ID)));
    candidate.setBirthdate((int) cursor.getLong(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_BIRTHDATE)));
    candidate.setConstituency(gson.fromJson(cursor.getString(
            cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_CONSTITUENCY)),
        Constituency.class));
    candidate.setEducation(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_EDUCATION)));
    candidate.setEducation(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_EDUCATION)));
    candidate.setFather(gson.fromJson(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_FATHER)),
        Father.class));
    candidate.setMother(gson.fromJson(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_MOTHER)),
        Mother.class));
    candidate.setLegislature(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_LEGISLATURE)));
    candidate.setReligion(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_NATIONALITY_RELIGION)));
    candidate.setPartyId(cursor.getInt(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_PARTY_ID)));
    candidate.setWardVillage(cursor.getString(
            cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_CANDIDATE_RESIDENCY)));
    return candidate;
  }

  public List<Candidate> searchCandidatesFromDb(String keyword) {
    open();
    List<Candidate> candidates = new ArrayList<>();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_CANDIDATE, null,
        MaepaysohDbHelper.COLUMN_CANDIDATE_NAME + " LIKE " + "'%" + keyword + "%'", null, null,
        null, null);

    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        Candidate candidate = cursorToCandidate(cursor);
        candidates.add(candidate);
        cursor.moveToNext();
      }
    }
    cursor.close();
    close();
    return candidates;
  }
}
