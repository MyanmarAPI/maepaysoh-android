package org.maepaysoh.maepaysoh.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;
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
 * Created by yemyatthu on 8/21/15.
 */
public class MyLocationActivity extends BaseActivity {
  private GoogleApiClient mGoogleApiClient;
  private Location mLastLocation;
  private LocationManager locationManager;
  private double longitude;
  private double latitude;
  private  ProgressDialog progressDialog;
  private ProgressBar mProgressView;
  private GoogleMap mMap;
  private ViewUtils mViewUtils;
  private GeoAPIHelper mGeoAPIHelper;
  private RecyclerView mCandidateListRecyclerView;
  private View mErrorView;
  private Button mRetryBtn;
  private MaePaySohApiWrapper mMaePaySohApiWrapper;
  private CandidateAPIHelper mCandidateAPIHelper;
  private CandidateAPIPropertiesMap mCandidateAPIPropertiesMap;
  private LinearLayoutManager mLayoutManager;
  private CandidateAdapter mCandidateAdapter;
  private TextView mValidCandidates;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_detail);
    /** PROCESS for Get Longitude and Latitude **/
    locationManager
        = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    // getting GPS status
    boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    Log.d("msg", "GPS:" + isGPSEnabled);

    // check if GPS enabled
    if (isGPSEnabled) {
      progressDialog = ProgressDialog.show(this, null, "Searching for location", true, false);
      progressDialog.show();
    /*
    longitude = 70.80079728674089;
    latitude =  22.29090332494221;
     */
      String provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER).getName();
      Location location = locationManager.getLastKnownLocation(provider);

      //new LoadPlaces().execute();

      if (location != null) {
        progressDialog.cancel();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Log.d("msg", "first lat long : " + latitude + " " + longitude);
        //new LoadPlaces().execute();
        doGeoThing();
      } else {

        progressDialog.show();
        locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {

              @Override public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

              }

              @Override public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

              }

              @Override public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

              }

              @Override public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                progressDialog.cancel();
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Log.d("msg", "changed lat long : " + latitude + " " + longitude);
                doGeoThing();
              }
            });
      }
    } else {
      showSettingsAlert();
    }
  }

  public void showSettingsAlert() {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

    //Setting Dialog Title
    alertDialog.setTitle(R.string.GPSAlertDialogTitle);

    //Setting Dialog Message
    alertDialog.setMessage(R.string.GPSAlertDialogMessage);

    //On Pressing Setting button
    alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

      @Override public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
      }
    });

    //On pressing cancel button
    alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

      @Override public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    alertDialog.show();
  }
  private void doGeoThing(){
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
    new GetGeoByLocation().execute(pCode);
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

  class GetGeoByLocation extends AsyncTask<String,Void,List<Geo>>{

    @Override protected List<Geo> doInBackground(String... strings) {
      return mGeoAPIHelper.getLocationByLatLong(strings[0],strings[1]);
    }

    @Override protected void onPostExecute(List<Geo> geos) {
      super.onPostExecute(geos);
      Geo geo = geos.get(0);
      new GetCandidateBYDTCODE().execute(geo.getProperties().getSTPCODE(),geo.getProperties().getDTPCODE());
      //setUpMap(LocationDetailActivity.this,geos.get(0));
    }
  }

  class GetCandidateBYDTCODE extends AsyncTask<String,Void,List<Candidate>> {

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
