package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import java.sql.SQLException;
import java.util.List;
import org.maepaysoh.maepaysohsdk.api.PartyService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.PartyDao;
import org.maepaysoh.maepaysohsdk.models.PartyReturnObject;
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
    context = mContext;
  }

  /**
   * 
   * @param party
   */
  public void getPartiesAsync(Callback<PartyReturnObject> party){
    mPartyService.listPartiesAsync(party);
  }

  /**
   * @param cache
   * @return
   */
  public PartyReturnObject getParties(boolean cache){
    mPartyDao = new PartyDao(mContext);
    PartyReturnObject partyReturnObject = mPartyService.listParties();
    if(cache){
    for (Party data : partyReturnObject.getData()) {
      try {
        mPartyDao.createParty(data);
      } catch (java.sql.SQLException e) {
        e.printStackTrace();
      }
    }
    }
    return partyReturnObject;
  }

  /**
   *
   * @param context
   * @return
   */
  public List<Party> getPartiesFromCache(Context context){
    mPartyDao = new PartyDao(context);
    try {
      return mPartyDao.getAllPartyData();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
