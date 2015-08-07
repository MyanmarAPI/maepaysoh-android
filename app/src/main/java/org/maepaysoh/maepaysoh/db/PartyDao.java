package org.maepaysoh.maepaysoh.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysoh.models.PartyData;
import org.maepaysoh.maepaysoh.utils.JsonUtils;

/**
 * Created by yemyatthu on 8/7/15.
 */
public class PartyDao {
  private SQLiteDatabase mMaepaysohDb;
  private MaepaysohDbHelper mMaepaysohDbHelper;

  public PartyDao(Context context) {
    mMaepaysohDbHelper = new MaepaysohDbHelper(context);
  }

  public void open() throws SQLException {
    mMaepaysohDb = mMaepaysohDbHelper.getWritableDatabase();
  }

  public void close() throws SQLException {
    mMaepaysohDbHelper.close();
  }

  public boolean createParty(PartyData partyData) throws SQLException {
    open();
    ContentValues partyContentValues = new ContentValues();
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_ID,partyData.getPartyId());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_NAME, partyData.getPartyName());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_NAME_ENGLISH,
        partyData.getPartyNameEnglish());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_APPROVED_PARTY_NUMBER,
        partyData.getApprovedPartyNumber());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_DATE,
        partyData.getEstablishmentApprovalDate());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_APPROVAL_DATE,
        partyData.getEstablishmentApprovalDate());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_MEMBER_COUNT, partyData.getMemberCount());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_POLICY, partyData.getPolicy());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_REGION, partyData.getRegion());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_PARTY_FLAG, partyData.getPartyFlag());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_PARTY_SEAL, partyData.getPartySeal());
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_CONTACT, JsonUtils.convertToJson(
        partyData.getContact()));
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_CHAIRMAN,JsonUtils.convertToJson(partyData.getChairman()));
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_LEADERSHIP,JsonUtils.convertToJson(partyData.getLeadership()));
    partyContentValues.put(MaepaysohDbHelper.COLUMN_PARTY_HEADQUARTER,partyData.getHeadquarters());
    mMaepaysohDb.beginTransaction();
    long insertId =
        mMaepaysohDb.insert(MaepaysohDbHelper.TABLE_NAME_PARTY, null, partyContentValues);
    mMaepaysohDb.endTransaction();
    if (insertId > 0) {
      mMaepaysohDb.setTransactionSuccessful();
      close();
      return true;
    } else {
      close();
      return false;
    }
  }

  public List<PartyData> getAllPartyData() throws SQLException {
    open();
    List<PartyData> partyDatas = new ArrayList<>();
    Cursor cursor =
        mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_PARTY, null, null, null, null, null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      PartyData partyData = cursorToParty(cursor);
      partyDatas.add(partyData);
      cursor.moveToNext();
    }
    cursor.close();
    close();
    return partyDatas;
  }

  public PartyData getPartyById(String id) throws SQLException {
    open();
    Cursor cursor = mMaepaysohDb.query(MaepaysohDbHelper.TABLE_NAME_PARTY,null,MaepaysohDbHelper.COLUMN_PARTY_ID+" = "+id,null,null,null,null);
    PartyData partyData = cursorToParty(cursor);
    cursor.close();
    close();
    return partyData;
  }

  private PartyData cursorToParty(Cursor cursor) {
    Type type = new TypeToken<List<String>>() {
    }.getType();
    PartyData partyData = new PartyData();
    partyData.setPartyId(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_ID)));
    partyData.setPartyName(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_NAME)));
    partyData.setPartyNameEnglish(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_NAME_ENGLISH)));
    partyData.setEstablishmentApprovalDate(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_APPROVAL_DATE)));
    partyData.setApprovedPartyNumber(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_APPROVED_PARTY_NUMBER)));
    partyData.setEstablishmentDate(cursor.getString(
        cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_ESTABLISHMENT_DATE)));
    partyData.setMemberCount(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_MEMBER_COUNT)));
    partyData.setHeadquarters(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_HEADQUARTER)));
    partyData.setChairman(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_CHAIRMAN)),
        type));
    partyData.setLeadership(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_LEADERSHIP)),
        type));
    partyData.setContact(JsonUtils.convertToJava(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_CONTACT)),
        type));
    partyData.setPartyFlag(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_PARTY_FLAG)));
    partyData.setPartySeal(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_PARTY_SEAL)));
    partyData.setPolicy(
        cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_POLICY)));
    partyData.setRegion(cursor.getString(cursor.getColumnIndexOrThrow(MaepaysohDbHelper.COLUMN_PARTY_REGION)));
    return partyData;
  }
}
