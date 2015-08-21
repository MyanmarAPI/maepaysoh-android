package org.maepaysoh.maepaysoh.ui;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonGeometry;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.maepaysoh.maepaysoh.MaePaySoh;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.CandidateAdapter;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.CandidateAPIHelper;
import org.maepaysoh.maepaysohsdk.GeoAPIHelper;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;
import org.maepaysoh.maepaysohsdk.models.Candidate;
import org.maepaysoh.maepaysohsdk.models.Geo;
import org.maepaysoh.maepaysohsdk.utils.CandidateAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.CandidateAPIPropertiesMap;

/**
 * Created by Ye Lin Aung on 15/08/10.
 */
public class LocationDetailActivity extends BaseActivity {
  private GeoAPIHelper mGeoAPIHelper;
  private GoogleMap mMap;
  private RecyclerView mCandidateListRecyclerView;
  private ProgressBar mProgressView;
  private View mErrorView;
  private Button mRetryBtn;
  private MaePaySohApiWrapper mMaePaySohApiWrapper;
  private CandidateAPIPropertiesMap mCandidateAPIPropertiesMap;
  private CandidateAPIHelper mCandidateAPIHelper;
  private ViewUtils mViewUtils;
  private CandidateAdapter mCandidateAdapter;
  private LinearLayoutManager mLayoutManager;
  private TextView mValidCandidates;
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_detail);
    mProgressView = (ProgressBar) findViewById(R.id.candidate_list_progress_bar);
    String pCode = getIntent().getStringExtra("GEO_OBJECT_ID");
    System.out.println(pCode);
    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.location_detail_map)).getMap();
    if(mMap!=null) {
      mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(16.8000, 96.1500), 4));
    }else{
      mProgressView.setVisibility(View.GONE);
    }
    mViewUtils = new ViewUtils(this);

    mGeoAPIHelper = MaePaySoh.getMaePaySohWrapper().getGeoApiHelper();
    new GetGeoByID().execute(pCode);
    mCandidateListRecyclerView = (RecyclerView) findViewById(R.id.candidate_list_recycler_view);
    mErrorView = findViewById(R.id.candidate_list_error_view);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);
    mMaePaySohApiWrapper = MaePaySoh.getMaePaySohWrapper();
    mCandidateAPIHelper = mMaePaySohApiWrapper.getCandidateApiHelper();
    mCandidateAPIPropertiesMap = new CandidateAPIPropertiesMap();
    mLayoutManager = new LinearLayoutManager(this);
    mCandidateAdapter = new CandidateAdapter();
    mCandidateListRecyclerView.setLayoutManager(mLayoutManager);
    mCandidateListRecyclerView.setAdapter(mCandidateAdapter);
    mProgressView.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mViewUtils.showProgress(mCandidateListRecyclerView, mProgressView, true);
    mValidCandidates = (TextView) findViewById(R.id.valid_candidates);
  }


  @Override protected void onResume() {
    super.onResume();
  }

  private void setUpMap(AppCompatActivity activity,Geo geo) {
    Gson gson = new GsonBuilder().create();
    String object = gson.toJson(geo);
    try {
      GeoJsonLayer layer = new GeoJsonLayer(mMap,new JSONObject(object));
      GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
      GeoJsonFeature geoJsonFeature = null;
      for (GeoJsonFeature feature : layer.getFeatures()) {
        GeoJsonGeometry geoJsonGeometry = feature.getGeometry();
        geoJsonFeature = new GeoJsonFeature(geoJsonGeometry, null, null, null);
        pointStyle = feature.getPointStyle();
      }
      GeoJsonPolygonStyle geoJsonPolygonStyle = layer.getDefaultPolygonStyle();
      geoJsonPolygonStyle.setFillColor(
          activity.getResources().getColor(R.color.geojson_background_color));
      geoJsonPolygonStyle.setStrokeColor(activity.getResources().getColor(R.color.geojson_stroke_color));
      geoJsonPolygonStyle.setStrokeWidth(2);

      pointStyle.setTitle(geo.getProperties().getDT());
      pointStyle.setSnippet(geo.getProperties().getDT());

      if (geoJsonFeature != null) {
        geoJsonFeature.setPointStyle(pointStyle);
        geoJsonFeature.setPolygonStyle(geoJsonPolygonStyle);
        layer.addFeature(geoJsonFeature);
      }



      layer.addLayerToMap();
    } catch (JSONException e) {
      e.printStackTrace();
    }
    //LatLng ygnLatLng = new LatLng(((geo.getGeometry().getCoordinates().get(0)).get(0)).get(1),
    //    ((geo.getGeometry().getCoordinates().get(0)).get(0)).get(0));
    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ygnLatLng, 8));
  }
  class GetGeoByID extends AsyncTask<String,Void,List<Geo>>{

    @Override protected List<Geo> doInBackground(String... strings) {
      return mGeoAPIHelper.getLocationByObjectId(strings[0]);
    }

    @Override protected void onPostExecute(List<Geo> geos) {
      super.onPostExecute(geos);
      Geo geo = geos.get(0);
      new GetCandidateBYDTCODE().execute(geo.getProperties().getSTPCODE(),geo.getProperties().getDTPCODE());
     //setUpMap(LocationDetailActivity.this,geos.get(0));
    }
  }

  class GetCandidateBYDTCODE extends AsyncTask<String,Void,List<Candidate>>{

    @Override protected List<Candidate> doInBackground(String... strings) {
      mCandidateAPIPropertiesMap.put(CandidateAPIProperties.CACHE,false);
      mCandidateAPIPropertiesMap.put(CandidateAPIProperties.PER_PAGE,20);
      return mCandidateAPIHelper.getCandidatesByConstituency(strings[0],strings[1],mCandidateAPIPropertiesMap);
    }

    @Override protected void onPostExecute(List<Candidate> candidates) {
      super.onPostExecute(candidates);
      mViewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
      if(candidates.size()>0) {
        mValidCandidates.setVisibility(View.VISIBLE);
        mCandidateAdapter.setCandidates(candidates);
      }else{
        mErrorView.setVisibility(View.VISIBLE);
        TextView mErrorText = (TextView) mErrorView.findViewById(R.id.error_view_error_text);
        Button mErrorBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);
        mErrorText.setText(R.string.no_candidate);
        mErrorBtn.setVisibility(View.GONE);
      }


    }
  }
}
