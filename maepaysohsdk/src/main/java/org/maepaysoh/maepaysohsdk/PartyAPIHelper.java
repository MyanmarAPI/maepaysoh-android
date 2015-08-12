package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import java.sql.SQLException;
import java.util.List;
import org.maepaysoh.maepaysohsdk.api.PartyService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.PartyDao;
import org.maepaysoh.maepaysohsdk.models.PartyListReturnObject;
import org.maepaysoh.maepaysohsdk.models.Party;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by maepaesoh on 8/11/15.
 */
public class PartyAPIHelper {
  private RestAdapter mPartyRestAdapter;
  private PartyService mPartyService;
  private PartyDao mPartyDao;
  private Context mContext;
  protected PartyAPIHelper(String token,Context context){
    mPartyRestAdapter = RetrofitHelper.getResAdapter(token);
    mPartyService = mPartyRestAdapter.create(PartyService.class);
    mContext = context;
  }

  /**
   * 
   * @param party
   */
  public void getPartiesAsync(Callback<PartyListReturnObject> party){
    mPartyService.listPartiesAsync(party);
  }

  /**
   * @param cache
   * @return
   */
  public List<Party> getParties(boolean cache){
    mPartyDao = new PartyDao(mContext);
    PartyListReturnObject partyListReturnObject = mPartyService.listParties();
    if(cache){
    for (Party data : partyListReturnObject.getData()) {
      try {
        mPartyDao.createParty(data);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    }
    return partyListReturnObject.getData();
  }

  /**
   * @return
   */
  public List<Party> getPartiesFromCache(){
    mPartyDao = new PartyDao(mContext);
    try {
      return mPartyDao.getAllPartyData();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
