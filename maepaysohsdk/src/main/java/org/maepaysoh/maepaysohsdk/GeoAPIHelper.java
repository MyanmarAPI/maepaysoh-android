package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.GeoService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.models.Geo;
import org.maepaysoh.maepaysohsdk.models.GeoReturnObject;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/18/15.
 */
public class GeoAPIHelper {
  private RestAdapter mGeoRestAdapter;
  private GeoService mGeoService;
  private Context mContext;
  protected GeoAPIHelper(String token, Context context) {
    mGeoRestAdapter = RetrofitHelper.getResAdapter(token);
    mGeoService = mGeoRestAdapter.create(GeoService.class);
    mContext = context;
  }
  public List<Geo> getLocationList(int per_page,int page,boolean withGeoJson){
    Map<GeoService.PARAM_FIELD, String> optionParams = new HashMap<>();
    optionParams.put(GeoService.PARAM_FIELD.per_page, String.valueOf(per_page));
    optionParams.put(GeoService.PARAM_FIELD.page,String.valueOf(page));
    optionParams.put(GeoService.PARAM_FIELD.no_geo, String.valueOf(withGeoJson));
    JsonObject jsonObject = mGeoService.getAllLocation(optionParams);
    System.out.println(String.valueOf(withGeoJson));
    System.out.println(jsonObject);
    GeoReturnObject object = new Gson().fromJson(jsonObject.toString(),GeoReturnObject.class);
    return object.getData();
  }
}
