package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

  public FAQAPIHelper getFaqApiHelper(){
    return new FAQAPIHelper(mToken,mContext);
  }

  public CandidateAPIHelper getCandidateApiHelper(){
    return new CandidateAPIHelper(mToken,mContext);
  }

  public void setFont(FONT font){
    SharedPreferences sdkPreferences = mContext.getSharedPreferences(Constants.SDK_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sdkPreferences.edit();
    Log.d("font",font.name());
    editor.putString(Constants.FONT_STORAGE,font.name()).apply();
  }

  public enum FONT{
    unicode,zawgyi
  }
}
