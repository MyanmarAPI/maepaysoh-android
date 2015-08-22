package org.maepaysoh.maepaysohsdk.api;

import com.squareup.okhttp.OkHttpClient;
import org.maepaysohsdk.maepaysohsdk.BuildConfig;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class RetrofitHelper {

  public static RestAdapter getResAdapter(final String token) {
    if (BuildConfig.DEBUG) {
      return new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient()))
          .setEndpoint("http://api.maepaysoh.org")
          .setLogLevel(RestAdapter.LogLevel.FULL)
          .setRequestInterceptor(new RequestInterceptor() {
            @Override public void intercept(RequestFacade request) {
              request.addQueryParam("token", token);
            }
          })
          .build();
    } else {
      return new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient()))
          .setEndpoint("http://api.maepaysoh.org")
          .setLogLevel(RestAdapter.LogLevel.NONE)
          .setRequestInterceptor(new RequestInterceptor() {
            @Override public void intercept(RequestFacade request) {
              request.addQueryParam("token", token);
            }
          })
          .build();
    }
  }

  public static RestAdapter getPublicResAdapter() {
    if (BuildConfig.DEBUG) {
      return new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient()))
          .setEndpoint("http://api.maepaysoh.org")
          .setLogLevel(RestAdapter.LogLevel.FULL)
          .build();
    } else {
      return new RestAdapter.Builder().setClient(new OkClient(new OkHttpClient()))
          .setEndpoint("http://api.maepaysoh.org")
          .setLogLevel(RestAdapter.LogLevel.NONE)
          .build();
    }
  }
}
