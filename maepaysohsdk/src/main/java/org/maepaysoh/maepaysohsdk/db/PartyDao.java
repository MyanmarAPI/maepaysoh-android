package org.maepaysoh.maepaysohsdk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysohsdk.models.Party;
import org.maepaysoh.maepaysohsdk.utils.JsonUtils;

import static org.maepaysoh.maepaysohsdk.utils.Logger.LOGE;
import static org.maepaysoh.maepaysohsdk.utils.Logger.makeLogTag;

/**
 * Created by yemyatthu on 8/7/15.
 */
// TODO: 8/7/15 USE SQL RELATIONSHIP IN PLACE OF JSON SERIALIZATION
public class PartyDao {
  private SQLiteDatabase mMaepaysohDb;
  private MaepaysohDbHelper mMaepaysohDbHelper;

  private static final String TAG = makeLogTag(PartyDao.class);

  public PartyDao(Context context) {
    mMaepaysohDbHelper = new MaepaysohDbHelper(context);
  }

  public void open() throws SQLException {
    mMaepaysohDb = mMaepaysohDbHelper.getWritableDatabase();
  }

  public void close() throws SQLException {
    mMaepaysohDbHelper.close();
  }

  public boolean createParty(Party party) throws SQLException {
    open();
    ContentValues partyContentValues = new ContentValues();
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_ID, party.getPartyId());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_NAME, party.getPartyName());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_NAME_ENGLISH,
        party.getPartyNameEnglish());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_APPROVED_PARTY_NUMBER,
        party.getApprovedPartyNumber());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_DATE,
        party.getEstablishmentApprovalDate());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_APPROVAL_DATE,
        party.getEstablishmentApprovalDate());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_REGISTRATION_APPLICATION_DATE,
        party.getRegistrationApplicationDate());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_REGISTRATION_APPROVAL_DATE,
        party.getRegistrationApprovalDate());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_MEMBER_COUNT, party.getMemberCount());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_POLICY, party.getPolicy());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_REGION, party.getRegion());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_PARTY_FLAG, party.getPartyFlag());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_PARTY_SEAL, party.getPartySeal());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_CONTACT,
        JsonUtils.convertToJson(party.getContact()));
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_CHAIRMAN,
        JsonUtils.convertToJson(party.getChairman()));
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_LEADERSHIP,
        JsonUtils.convertToJson(party.getLeadership()));
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_HEADQUARTER, party.getHeadquarters());
    mMaepaysohDb.beginTransaction();
    try {
      long insertId = mMaepaysohDb.insertWithOnConflict(MaepaysohDbHelper.TABLE_NAME_PARTY, null,
          partyContentValues, SQLiteDatabase.CONFLICT_REPLACE);
      mMaepaysohDb.setTransactionSuccessful();
    } catch (SQLiteException e) {
      LOGE(TAG, e.getLocalizedMessage());
    } finally {
      mMaepaysohDb.endTransaction();
    }
    return true;
  }

  public List<Party> getAllPartyData() throws SQLException {
    open();
    List<Party> parties = new ArrayList<>();
    Cursor cursor =
        mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_PARTY, null, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      Party party = cursorToParty(cursor);
      parties.add(party);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return parties;
  }

  public Party getPartyById(String id) throws SQLException {
    open();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_PARTY, null,
        MaepaysohDbHelper.COLUMN_PARTY_ID + " = " + id, null, null, null, null);
    Party party = cursorToParty(cursor);
    cursor.close();
    close();
    return party;
  }

  public List<Party> searchPartiesFromDb(String keyword) throws SQLException {
    open();
    List<Party> parties = new ArrayList<>();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_PARTY, null,
        MaepaysohDbHelper.COLUMN_PARTY_NAME + " LIKE " + "'%" + keyword + "%'" + " OR "
            + MaepaysohDbHelper.COLUMN_PARTY_NAME_ENGLISH + " LIKE " + "'%" + keyword + "%'", null,
        null, null, null);
    if (cursor.moveToFirst()) {
      while (!cursor.isAfterLast()) {
        Party party = cursorToParty(cursor);
        parties.add(party);
        cursor.moveToNext();
      }
    }
    cursor.close();
    close();
    return parties;
  }

  private Party cursorToParty(Cursor cursor) {
    Type type = new TypeToken<List<String>>() {
    }.getType();
    Party party = new Party();
    party.setPartyId(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_ID)));
    party.setPartyName(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_NAME)));
    party.setPartyNameEnglish(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_NAME_ENGLISH)));
    party.setEstablishmentApprovalDate(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_APPROVAL_DATE)));
    party.setRegistrationApprovalDate(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_REGISTRATION_APPROVAL_DATE)));
    party.setRegistrationApplicationDate(cursor.getString(cursor.getColumnIndexOrThrow(
        MaepaysohDbHelper.COLUMN_PARTY_REGISTRATION_APPLICATION_DATE)));
    party.setApprovedPartyNumber(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_APPROVED_PARTY_NUMBER)));
    party.setEstablishmentDate(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_DATE)));
    party.setMemberCount(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_MEMBER_COUNT)));
    party.setHeadquarters(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_HEADQUARTER)));
    party.setChairman(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_CHAIRMAN)),
        type));
    party.setLeadership(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_LEADERSHIP)),
        type));
    party.setContact(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_CONTACT)),
        type));
    party.setPartyFlag(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_PARTY_FLAG)));
    party.setPartySeal(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_PARTY_SEAL)));
    party.setPolicy(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_POLICY)));
    party.setRegion(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_REGION)));
    return party;
  }
}
