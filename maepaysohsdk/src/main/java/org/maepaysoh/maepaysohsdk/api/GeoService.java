package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.models.GeoReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/18/15.
 */
public interface GeoService {
  @GET("/geo/district") void getLocationByRegionAsync(@QueryMap Map<PARAM_FIELD,String> options,
      Callback<GeoReturnObject> callback);

  @GET("/geo")

  enum PARAM_FIELD{
    st_name,dt_name,per_page
  }
}
