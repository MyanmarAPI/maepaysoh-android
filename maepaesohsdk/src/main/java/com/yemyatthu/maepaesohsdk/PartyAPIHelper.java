package com.yemyatthu.maepaesohsdk;

import com.yemyatthu.maepaesohsdk.api.PartyService;
import com.yemyatthu.maepaesohsdk.api.RetrofitHelper;
import com.yemyatthu.maepaesohsdk.models.Party;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
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
