package com.maepaesoh.maepaesohsdk;

import com.maepaesoh.maepaesohsdk.api.PartyService;
import com.maepaesoh.maepaesohsdk.api.RetrofitHelper;
import com.maepaesoh.maepaesohsdk.models.Party;
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

  public void getParties(Callback<Party> party){
    mPartyService.listParties(party);
  }
}
