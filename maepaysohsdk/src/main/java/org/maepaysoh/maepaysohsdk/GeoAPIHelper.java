package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import com.google.android.gms.maps.GoogleMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.GeoService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.models.Geo;
import org.maepaysoh.maepaysohsdk.models.GeoReturnObject;
import org.maepaysoh.maepaysohsdk.utils.GeoAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.GeoAPIPropertiesMap;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/18/15.
 */
public class GeoAPIHelper {
  private RestAdapter mGeoRestAdapter;
  private GeoService mGeoService;
  private Context mContext;
  private GoogleMap mMap;

  protected GeoAPIHelper(String token, Context context) {
    mGeoRestAdapter = RetrofitHelper.getResAdapter(token);
    mGeoService = mGeoRestAdapter.create(GeoService.class);
    mContext = context;
  }

  public List<Geo> getLocationList() {
    return getLocationList(new GeoAPIPropertiesMap());
  }

  public List<Geo> getLocationList(GeoAPIPropertiesMap propertiesMap) {
    int per_page = propertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = propertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = propertiesMap.getBoolean(GeoAPIProperties.NO_GEO, true);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    GeoReturnObject returnObject = mGeoService.getAllLocation(optionParams);
    return returnObject.getData();
  }

  public void getLocationListAsync(Callback<GeoReturnObject> returnObjectCallback) {
    getLocationListAsync(new GeoAPIPropertiesMap(), returnObjectCallback);
  }

  public void getLocationListAsync(GeoAPIPropertiesMap propertiesMap,
      Callback<GeoReturnObject> returnObjectCallback) {
    int per_page = propertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = propertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = propertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    mGeoService.getAllLocationAsync(optionParams, returnObjectCallback);
  }

  public List<Geo> getLocationByRegion(String dtName, String stName,
      GeoAPIPropertiesMap geoAPIPropertiesMap) {
    int per_page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = geoAPIPropertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    optionParams.put(GeoService.PARAM_FIELD.dt_name, dtName);
    optionParams.put(GeoService.PARAM_FIELD.st_name, stName);
    return mGeoService.getLocationByRegion(optionParams).getData();
  }

  public void getLocationByRegionByAsync(String dt_name, String st_name,
      GeoAPIPropertiesMap geoAPIPropertiesMap, Callback<GeoReturnObject> returnObjectCallback) {
    int per_page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = geoAPIPropertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    optionParams.put(GeoService.PARAM_FIELD.dt_name, dt_name);
    optionParams.put(GeoService.PARAM_FIELD.st_name, st_name);
    mGeoService.getLocationByRegionAsync(optionParams, returnObjectCallback);
  }

  public void getLocationByRegionByAsync(String dt_name, String st_name,
      Callback<GeoReturnObject> returnObjectCallback) {
    getLocationByRegionByAsync(dt_name, st_name, new GeoAPIPropertiesMap(), returnObjectCallback);
  }

  public List<Geo> getLocationByObjectId(String pCode, GeoAPIPropertiesMap geoAPIPropertiesMap) {
    int per_page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = geoAPIPropertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.dt_pcode, pCode);
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    return mGeoService.getLocationByPcode(optionParams).getData();
  }

  public List<Geo> getLocationByObjectId(String pCode) {
    return getLocationByObjectId(pCode, new GeoAPIPropertiesMap());
  }

  public void getLocationByObjectIdAsync(String pCode, GeoAPIPropertiesMap geoAPIPropertiesMap,
      Callback<GeoReturnObject> callback) {
    int per_page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = geoAPIPropertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.dt_pcode, pCode);
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    mGeoService.getLocationByPcodeAsync(optionParams, callback);
  }

  public void getLocationByObjectIdAsync(String pCode, Callback<GeoReturnObject> callback) {
    getLocationByObjectIdAsync(pCode, new GeoAPIPropertiesMap(), callback);
  }

  public void getLocationByLatLongAsync(String lat, String lon,
      GeoAPIPropertiesMap geoAPIPropertiesMap, Callback<GeoReturnObject> callback) {
    int per_page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = geoAPIPropertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.lat, lat);
    optionParams.put(GeoService.PARAM_FIELD.lon, lon);
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    mGeoService.getLocationByLatLongAsync(optionParams, callback);
  }

  public void getLocationByLatLongAsync(String lat, String lon,
      Callback<GeoReturnObject> callback) {
    ;
    getLocationByLatLongAsync(lat, lon, new GeoAPIPropertiesMap(), callback);
  }

  public List<Geo> getLocationByLatLong(String lat, String lon,
      GeoAPIPropertiesMap geoAPIPropertiesMap) {
    int per_page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.PER_PAGE, 15);
    int page = geoAPIPropertiesMap.getInteger(GeoAPIProperties.FIRST_PAGE, 1);
    boolean noGeo = geoAPIPropertiesMap.getBoolean(GeoAPIProperties.NO_GEO, false);
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.lat, lat);
    optionParams.put(GeoService.PARAM_FIELD.lon, lon);
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(noGeo));
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    return mGeoService.getLocationByLatLong(optionParams).getData();
  }

  public List<Geo> getLocationByLatLong(String lat, String lon) {
    return getLocationByLatLong(lat, lon, new GeoAPIPropertiesMap());
  }
}
