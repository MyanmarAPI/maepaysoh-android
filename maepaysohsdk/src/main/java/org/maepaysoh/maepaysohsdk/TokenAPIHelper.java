package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.api.TokenService;
import org.maepaysoh.maepaysohsdk.models.TokenReturnObject;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/20/15.
 */
public class TokenAPIHelper {
  private RestAdapter mGeoRestAdapter;
  private TokenService mTokenService;
  private Context mContext;

  protected TokenAPIHelper(Context context) {
    mGeoRestAdapter = RetrofitHelper.getPublicResAdapter();
    mTokenService = mGeoRestAdapter.create(TokenService.class);
    mContext = context;
  }

  protected String getTokenKey(String apiKey) {
    TokenReturnObject returnObject = mTokenService.generateToken(apiKey);
    return returnObject.getTokenData().getToken();
  }

  protected void getTokenKeyAsync(String apiKey, Callback<TokenReturnObject> callback) {
    mTokenService.generateTokenAsync(apiKey, callback);
  }
}
