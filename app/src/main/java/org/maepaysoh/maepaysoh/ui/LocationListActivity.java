package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.LocationAdapter;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.GeoAPIHelper;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;
import org.maepaysoh.maepaysohsdk.models.Geo;

public class LocationListActivity extends BaseActivity implements LocationAdapter.ClickInterface {

  private Button ygnWestBtn;
  private ViewUtils viewUtils;
  private LinearLayoutManager mLayoutManager;
  private RecyclerView mLocationListRecyclerView;
  private LocationAdapter mLocationAdapter;
  private MaePaySohApiWrapper mMaePaySohApiWrapper;
  private GeoAPIHelper mGeoAPIHelper;
  private View mRetryBtn;
  private List<Geo> mGeos;
  private ProgressBar mProgressView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.location_list_toolbar);
    View mToolbarShadow = findViewById(R.id.location_list_toolbar_shadow);
    mProgressView = (ProgressBar) findViewById(R.id.location_list_progress_bar);
    mProgressView.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mToolbar.setTitle(getString(R.string.LocationList));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }
    mLocationListRecyclerView = (RecyclerView) findViewById(R.id.location_list_recycler_view);
    mRetryBtn = findViewById(R.id.location_list_error_view);
    viewUtils = new ViewUtils(this);
    mLayoutManager = new LinearLayoutManager(this);
    mLocationListRecyclerView.setLayoutManager(mLayoutManager);
    mLocationAdapter = new LocationAdapter();
    mLocationAdapter.setOnItemClickListener(this);
    mMaePaySohApiWrapper = getMaePaySohWrapper();
    mGeoAPIHelper = mMaePaySohApiWrapper.getGeoApiHelper();
    //mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(FaqListActivity.this, mFaqAdapter,
    //    new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
    //      @Override public void onLoadMoreRequested() {
    //        loadFaqData(null);
    //      }
    //    });
    mLocationListRecyclerView.setAdapter(mLocationAdapter);
    if (InternetUtils.isNetworkAvailable(this)) {
      loadLocationData();
    } else {
    }
    mRetryBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        loadLocationData();
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadLocationData(){
    new LocationDownloadAsync().execute();
  }

  @Override public void onItemClick(View view, int position) {
    Intent locationDetailIntent = new Intent(LocationListActivity.this,LocationDetailActivity.class);
    locationDetailIntent.putExtra("GEO_OBJECT_ID",mGeos.get(position).getProperties().getDTPCODE());
    startActivity(locationDetailIntent);
  }

  class LocationDownloadAsync extends AsyncTask<Void,Void,List<Geo>>{
    @Override protected void onPreExecute() {
      super.onPreExecute();
      viewUtils.showProgress(mLocationListRecyclerView, mProgressView, true);
    }

    @Override protected List<Geo> doInBackground(Void... voids) {

      List<Geo> geos = mGeoAPIHelper.getLocationList();
      return geos;
    }

    @Override protected void onPostExecute(List<Geo> geos) {
      super.onPostExecute(geos);
      viewUtils.showProgress(mLocationListRecyclerView,mProgressView,false);
      mGeos = geos;
      mLocationAdapter.setGeos(geos);
    }
  }
}
