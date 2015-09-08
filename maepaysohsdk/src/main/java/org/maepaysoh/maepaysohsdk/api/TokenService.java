package org.maepaysoh.maepaysohsdk.api;

import org.maepaysoh.maepaysohsdk.models.TokenReturnObject;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by yemyatthu on 8/20/15.
 */
public interface TokenService {
  @FormUrlEncoded @POST("/token/generate") void generateTokenAsync(@Field("api_key") String apiKey,
      Callback<TokenReturnObject> tokenReturnCallback);

  @FormUrlEncoded @POST("/token/generate") TokenReturnObject generateToken(
      @Field("api_key") String apiKey);
}
