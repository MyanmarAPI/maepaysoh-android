package org.maepaysoh.maepaysoh.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.LocationAdapter;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.GeoAPIHelper;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;
import org.maepaysoh.maepaysohsdk.models.Geo;

public class LocationListActivity extends BaseActivity {

  private Button ygnWestBtn;
  private ViewUtils viewUtils;
  private LinearLayoutManager mLayoutManager;
  private RecyclerView mLocationListRecyclerView;
  private LocationAdapter mLocationAdapter;
  private MaePaySohApiWrapper mMaePaySohApiWrapper;
  private GeoAPIHelper mGeoAPIHelper;
  private View mRetryBtn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.location_list_toolbar);
    View mToolbarShadow = findViewById(R.id.location_list_toolbar_shadow);

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
    //mLocationAdapter.setOnItemClickListener(this);
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

  class LocationDownloadAsync extends AsyncTask<Void,Void,List<Geo>>{
    @Override protected List<Geo> doInBackground(Void... voids) {
      List<Geo> geos = mGeoAPIHelper.getLocationList(15,1,true);
      return geos;
    }

    @Override protected void onPostExecute(List<Geo> geos) {
      super.onPostExecute(geos);
      mLocationAdapter.setGeos(geos);
    }
  }
}
