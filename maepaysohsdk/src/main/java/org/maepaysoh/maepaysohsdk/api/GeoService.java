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
  @GET("/geo/district") void getLocationByRegionAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<GeoReturnObject> callback);

  @GET("/geo/district") void getAllLocationAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<GeoReturnObject> callback);

  @GET("/geo/district") GeoReturnObject getAllLocation(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/geo/district") GeoReturnObject getLocationByRegion(
      @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/geo/district") GeoReturnObject getLocationByPcode(
      @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/geo/district") void getLocationByPcodeAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<GeoReturnObject> callback);

  @GET("/geo/district/find") void getLocationByLatLongAsync(
      @QueryMap Map<PARAM_FIELD, String> options, Callback<GeoReturnObject> callback);

  @GET("/geo/district/find") GeoReturnObject getLocationByLatLong(
      @QueryMap Map<PARAM_FIELD, String> options);

  enum PARAM_FIELD {
    st_name, dt_name, dt_pcode, per_page, page, no_geo, lat, lon
  }
}
