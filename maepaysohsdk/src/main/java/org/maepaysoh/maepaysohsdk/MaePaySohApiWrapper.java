package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import org.maepaysoh.maepaysohsdk.models.TokenReturnObject;
import retrofit.Callback;

/**
 * Created by yemyatthu on 8/12/15.
 */
public class MaePaySohApiWrapper {
  protected Context mContext;
  protected String mToken;

  public MaePaySohApiWrapper(Context context) {
    mContext = context;
  }

  public void setApiKey(String token) {
    mToken = token;
  }

  public String getTokenKey(String apiKey) {
    return new TokenAPIHelper(mContext).getTokenKey(apiKey);
  }

  public void getTokenKeyAsync(String apiKey, Callback<TokenReturnObject> returnObjectCallback){
    new TokenAPIHelper(mContext).getTokenKeyAsync(apiKey,returnObjectCallback);
  }
  
  public PartyAPIHelper getPartyApiHelper() {
    return new PartyAPIHelper(mToken, mContext);
  }

  public FAQAPIHelper getFaqApiHelper() {
    return new FAQAPIHelper(mToken, mContext);
  }

  public CandidateAPIHelper getCandidateApiHelper() {
    return new CandidateAPIHelper(mToken, mContext);
  }

  public GeoAPIHelper getGeoApiHelper() {
    return new GeoAPIHelper(mToken, mContext);
  }

  public void setFont(FONT font) {
    Utils.saveFontPref(font, mContext);
  }

  public boolean isUsingUnicode() {
    return Utils.isUniCode(mContext);
  }

  public enum FONT {
    unicode, zawgyi
  }
}
