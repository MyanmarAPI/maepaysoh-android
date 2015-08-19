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

  public List<Geo> getLocationList(){
    return getLocationList(15,1,false);
  }

  public List<Geo> getLocationList(boolean withGeoJson){
    return getLocationList(15,1,withGeoJson);
  }
  public List<Geo> getLocationList(int page,boolean withGeoJson){
    return getLocationList(15,page,withGeoJson);
  }
  public List<Geo> getLocationList(int per_page,int page,boolean withGeoJson){
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(!withGeoJson));
    GeoReturnObject returnObject = mGeoService.getAllLocation(optionParams);
    return returnObject.getData();
  }

  public void getLocationListAsync(Callback<GeoReturnObject> returnObjectCallback){
    getLocationListAsync(15,1,false,returnObjectCallback);
  }
  public void getLocationListAsync(boolean withGeoJson, Callback<GeoReturnObject> returnObjectCallback){
    getLocationListAsync(15,1,withGeoJson,returnObjectCallback);
  }

  public void getLocationListAsync(int page,boolean withGeoJson, Callback<GeoReturnObject> returnObjectCallback){
    getLocationListAsync(15,page,withGeoJson,returnObjectCallback);
  }
  public void getLocationListAsync(int per_page,int page,boolean withGeoJson, Callback<GeoReturnObject> returnObjectCallback){
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(!withGeoJson));
    mGeoService.getAllLocationAsync(optionParams,returnObjectCallback);
  }
}
