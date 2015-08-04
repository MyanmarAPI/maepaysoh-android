package org.maepaysoh.maepaysoh.api;

import com.squareup.okhttp.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class RetrofitHelper {
  private static String token = "3db8827d-2521-57be-987a-e9e366874d4b";

  public static RestAdapter getResAdapter() {
    return new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient()))
        .setEndpoint("http://api.maepaysoh.org")
        .setLogLevel(RestAdapter.LogLevel.BASIC)
        .setRequestInterceptor(new RequestInterceptor() {
          @Override public void intercept(RequestFacade request) {
            request.addQueryParam("token", token);
          }
        })
        .build();
  }
}
