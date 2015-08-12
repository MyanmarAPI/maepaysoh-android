package org.maepaysoh.maepaysohsdk;

import android.content.Context;

/**
 * Created by yemyatthu on 8/12/15.
 */
public class MaePaySohApiWrapper {
  protected Context mContext;
  protected String mToken;
  public MaePaySohApiWrapper(Context context){
    mContext = context;
  }

  public void setApiKey(String token){
    mToken = token;
  }
  public PartyAPIHelper getPartyApiHelper(){
    return new PartyAPIHelper(mToken,mContext);
  }

  public FaqAPIHelper getFaqApiHelper(){
    return new FaqAPIHelper(mToken,mContext);
  }

  public CandidateAPIHelper getCandidateApiHelper(){
    return new CandidateAPIHelper(mToken,mContext);
  }
}
