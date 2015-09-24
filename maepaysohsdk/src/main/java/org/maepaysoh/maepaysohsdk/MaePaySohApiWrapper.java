package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import org.maepaysoh.maepaysohsdk.models.TokenReturnObject;
import retrofit.Callback;

import static org.maepaysoh.maepaysohsdk.utils.Logger.LOGD;
import static org.maepaysoh.maepaysohsdk.utils.Logger.makeLogTag;

/**
 * Created by yemyatthu on 8/12/15.
 */
public class MaePaySohApiWrapper {
  protected Context mContext;
  protected String mToken;

  private static final String TAG = makeLogTag(MaePaySohApiWrapper.class);

  public MaePaySohApiWrapper(Context context) {
    mContext = context;
  }

  public String getTokenKey() {
    return new TokenAPIHelper(mContext).getTokenKey(Constants.API_KEY);
  }

  public void setTokenKey(String token) {
    LOGD(TAG, "token " + token);
    mToken = token;
  }

  public void getTokenKeyAsync(Callback<TokenReturnObject> returnObjectCallback) {
    new TokenAPIHelper(mContext).getTokenKeyAsync(Constants.API_KEY, returnObjectCallback);
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

  public OMIAPIHelper getOMIApiHelper(){
    return new OMIAPIHelper(mToken,mContext);
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
