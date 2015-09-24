package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.models.GeoDetailReturnObject;
import org.maepaysoh.maepaysohsdk.models.GeoReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/18/15.
 */
public interface GeoService {
  @GET("/geo/district") void getLocationAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<GeoReturnObject> callback);

  @GET("/geo/district") GeoReturnObject getLocation(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/geo/district/find") void getLocationByLatLongAsync(@Query("lat") String lat,
      @Query("long") String lon, @QueryMap Map<PARAM_FIELD, String> options, Callback<GeoReturnObject> callback);

  @GET("/geo/district/find") GeoReturnObject getLocationByLatLong(@Query("lat") String lat,
      @Query("long") String lon, @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/get/district/{mongo_id}") GeoDetailReturnObject getSingleDistrictLocationByMongoId(@Path("mongo_id") String mongoId);

  @GET("/get/district/{mongo_id}") void getSingleDistrictLocationByMongoIdAsync(@Path("mongo_id") String mongoId,
      Callback<GeoDetailReturnObject> geoDetailReturnObjectCallback);

  @GET("/geo/upperhouse") GeoReturnObject getUpperHouseLocation(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/geo/upperhouse") void getUpperHouseLocationAsync(@QueryMap Map<PARAM_FIELD, String> options,Callback<GeoReturnObject> geoReturnObjectCallback);

  @GET("/get/upperhouse/find") GeoReturnObject getUpperHouseLocationByLatLong(@Query("lat") String lat,
      @Query("long") String lon, @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/get/upperhouse/find") void getUpperHouseLocationByLatLongAsync(@Query("lat") String lat,
      @Query("long") String lon, @QueryMap Map<PARAM_FIELD, String> options, Callback<GeoReturnObject> callback);

  @GET("/get/upperhouse/{mongo_id}") GeoDetailReturnObject getSingleUpperHouseLocationByMongoId(@Path("mongo_id") String mongoId);

  @GET("/get/upperhouse/{mongo_id}") void getSingleUpperHouseLocationByMongoIdAsync(@Path("mongo_id") String mongoId,
      Callback<GeoDetailReturnObject> geoDetailReturnObjectCallback);


  @GET("/geo/lowerhouse") GeoReturnObject getLowerHouseLocation(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/geo/lowerhouse") void getLowerHouseLocationAsync(@QueryMap Map<PARAM_FIELD, String> options,Callback<GeoReturnObject> geoReturnObjectCallback);

  @GET("/get/lowerhouse/find") GeoReturnObject getLowerHouseLocationByLatLong(@Query("lat") String lat,
      @Query("long") String lon, @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/get/lowerhouse/find") void getLowerHouseLocationByLatLongAsync(@Query("lat") String lat,
      @Query("long") String lon, @QueryMap Map<PARAM_FIELD, String> options, Callback<GeoReturnObject> callback);

  @GET("/get/lowerhouse/{mongo_id}") GeoDetailReturnObject getSingleLowerHouseHouseLocationByMongoId(@Path("mongo_id") String mongoId);

  @GET("/get/lowerhouse/{mongo_id}") void getSingleLowerHouseLocationByMongoIdAsync(@Path("mongo_id") String mongoId,
      Callback<GeoDetailReturnObject> geoDetailReturnObjectCallback);


  @GET("/get/upperhouse/ampcode") GeoDetailReturnObject getSingleUpperHouseHouseLocationByAMPCODE(@Path("am_code") String ampPcode);

  @GET("/get/lowerhouse/ampcode") GeoDetailReturnObject getSingleLowerHouseHouseLocationByAMPCODE(@Path("am_code") String ampPcode);

  @GET("/get/upperhouse/ampcode") void getSingleUpperHouseLocationByAMPCODEAsync(@Path("am_pcode") String amPcode,
      Callback<GeoDetailReturnObject> geoDetailReturnObjectCallback);

  @GET("/get/lowerhouse/ampcode") void getSingleLowerHouseLocationByAMPCODEAsync(@Path("am_pcode") String amPcode,
      Callback<GeoDetailReturnObject> geoDetailReturnObjectCallback);

  enum PARAM_FIELD {
    st_name, dt_name, dt_pcode,st_pcode,object_id, per_page, page, no_geo
  }
}
