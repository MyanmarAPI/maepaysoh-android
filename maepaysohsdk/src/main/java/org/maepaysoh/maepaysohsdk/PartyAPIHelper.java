package org.maepaysoh.maepaysohsdk;
import org.maepaysoh.maepaysohsdk.api.PartyService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.models.Party;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by maepaesoh on 8/11/15.
 */
public class PartyAPIHelper {
  private RestAdapter mPartyRestAdapter;
  private PartyService mPartyService;
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

  public Party getParties(){
    Party party = mPartyService.listParties();
    return party;
  }
}
