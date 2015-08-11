package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import java.sql.SQLException;
import java.util.List;
import org.maepaysoh.maepaysohsdk.api.PartyService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.PartyDao;
import org.maepaysoh.maepaysohsdk.models.Party;
import org.maepaysoh.maepaysohsdk.models.PartyData;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by maepaesoh on 8/11/15.
 */
public class PartyAPIHelper {
  private RestAdapter mPartyRestAdapter;
  private PartyService mPartyService;
  private PartyDao mPartyDao;
  public PartyAPIHelper(String token){
    mPartyRestAdapter = RetrofitHelper.getResAdapter(token);
    mPartyService = mPartyRestAdapter.create(PartyService.class);
  }

  /**
   * 
   * @param party
   */
  public void getPartiesAsync(Callback<Party> party){
    mPartyService.listPartiesAsync(party);
  }

  public Party getParties(Context context,boolean cache){
    mPartyDao = new PartyDao(context);
    Party party = mPartyService.listParties();
    if(cache){
    for (PartyData data : party.getData()) {
      try {
        mPartyDao.createParty(data);
      } catch (java.sql.SQLException e) {
        e.printStackTrace();
      }
    }
    }
    return party;
  }

  public List<PartyData> getPartiesFromCache(Context context){
    mPartyDao = new PartyDao(context);
    try {
      return mPartyDao.getAllPartyData();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
