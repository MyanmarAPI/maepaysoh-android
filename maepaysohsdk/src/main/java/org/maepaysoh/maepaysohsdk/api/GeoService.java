package org.maepaysoh.maepaysohsdk.api;

import com.google.gson.JsonObject;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/18/15.
 */
public interface GeoService {
  @GET("/geo/district") void getLocationByRegionAsync(@QueryMap Map<PARAM_FIELD,String> options,
      Callback<JsonObject> callback);

  @GET("/geo/district") void getAllLocationAsync(@QueryMap Map<PARAM_FIELD,String> options,
      Callback<JsonObject> callback);

  @GET("/geo/district") JsonObject getAllLocation(@QueryMap Map<PARAM_FIELD,String> options);
  enum PARAM_FIELD{
    st_name,dt_name,per_page,page,no_geo
  }
}
