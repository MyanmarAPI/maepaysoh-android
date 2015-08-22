package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.PartyService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.PartyDao;
import org.maepaysoh.maepaysohsdk.models.Party;
import org.maepaysoh.maepaysohsdk.models.PartyListReturnObject;
import org.maepaysoh.maepaysohsdk.utils.PartyAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.PartyAPIPropertiesMap;
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

  protected PartyAPIHelper(String token, Context context) {
    mPartyRestAdapter = RetrofitHelper.getResAdapter(token);
    mPartyService = mPartyRestAdapter.create(PartyService.class);
    mContext = context;
  }

  /**
   *
   * @param party
   */

  public void getPartiesAsync(Callback<PartyListReturnObject> party) {
    boolean unicode = Utils.isUniCode(mContext);
    getPartiesAsync(new PartyAPIPropertiesMap(), party);
  }

  public void getPartiesAsync(PartyAPIPropertiesMap partyAPIPropertiesMap,
      Callback<PartyListReturnObject> party) {
    boolean unicode =
        partyAPIPropertiesMap.getBoolean(PartyAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    Map<PartyService.PARAM_FIELD, String> optionParams = new HashMap<>();
    mPartyDao = new PartyDao(mContext);
    if (unicode) {
      optionParams.put(PartyService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(PartyService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    mPartyService.listPartiesAsync(optionParams, party);
  }

  public List<Party> getParties() {
    boolean unicode = Utils.isUniCode(mContext);
    return getParties(new PartyAPIPropertiesMap());
  }

  public List<Party> getParties(PartyAPIPropertiesMap partyAPIPropertiesMap) {
    boolean unicode =
        partyAPIPropertiesMap.getBoolean(PartyAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    boolean cache = partyAPIPropertiesMap.getBoolean(PartyAPIProperties.CACHE, true);
    Map<PartyService.PARAM_FIELD, String> optionParams = new HashMap<>();
    mPartyDao = new PartyDao(mContext);
    if (unicode) {
      optionParams.put(PartyService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(PartyService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    PartyListReturnObject partyListReturnObject = mPartyService.listParties(optionParams);
    if (cache) {
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
  public List<Party> getPartiesFromCache() {
    mPartyDao = new PartyDao(mContext);
    try {
      return mPartyDao.getAllPartyData();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Party> searchPartiesFromCache(String keyword) {
    mPartyDao = new PartyDao(mContext);
    try {
      return mPartyDao.searchPartiesFromDb(keyword);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
