package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.maepaysoh.maepaysoh.Constants;
import org.maepaysoh.maepaysoh.MaePaySoh;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class HomeActivity extends BaseActivity {
  MaePaySohApiWrapper maePaySohApiWrapper;
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private Button mPartyListBtn;
  private Button mCandidateListBtn;
  private Button mFaqListBtn;
  private Button mLocationListBtn;
  private Button mMyLocationBtn;
  private LinearLayout mMainContent;
  private ProgressBar mProgressBar;
  private ViewUtils mViewUtils;
  private TokenKeyGenerateClass mTokenClass;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
    mToolbarShadow = findViewById(R.id.home_toolbar_shadow);
    mPartyListBtn = (Button) findViewById(R.id.home_party_list_btn);
    mCandidateListBtn = (Button) findViewById(R.id.home_candidate_list_btn);
    mFaqListBtn = (Button) findViewById(R.id.home_faq_list_btn);
    mLocationListBtn = (Button) findViewById(R.id.home_location_list_btn);
    mMyLocationBtn = (Button) findViewById(R.id.my_location);
    mMainContent = (LinearLayout) findViewById(R.id.main_content);
    mProgressBar = (ProgressBar) findViewById(R.id.home_progress_bar);
    mProgressBar.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
    mViewUtils = new ViewUtils(this);
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);
    setSupportActionBar(mToolbar);
    String apiKey = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        .getString(Constants.API_KEY, "");
    Log.d("apikey", apiKey);
    if (apiKey.length() > 0) {
      maePaySohApiWrapper = MaePaySoh.getMaePaySohWrapper();
      maePaySohApiWrapper.setTokenKey(apiKey);
      inflateLayout();
    } else {
      if (!InternetUtils.isNetworkAvailable(this)) {
        Toast.makeText(this, "You need to enable Internet first time", Toast.LENGTH_LONG).show();
        return;
      }
      mTokenClass = new TokenKeyGenerateClass();
      mViewUtils.showProgress(mMainContent, mProgressBar, true);
      mTokenClass.execute();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_home, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.change_font:
        showFontChooserDialog(true);
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void inflateLayout() {
    boolean firstTime = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        .getBoolean(Constants.FIRST_TIME, true);
    if (firstTime) {
      showFontChooserDialog(false);
    }
    mPartyListBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToPartyList = new Intent(HomeActivity.this, PartyListActivity.class);
        startActivity(goToPartyList);
      }
    });

    mCandidateListBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToCandidateList = new Intent(HomeActivity.this, CandidateListActivity.class);
        startActivity(goToCandidateList);
      }
    });

    mFaqListBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToFaqList = new Intent(HomeActivity.this, FaqListActivity.class);
        startActivity(goToFaqList);
      }
    });

    mLocationListBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToLocationList = new Intent(HomeActivity.this, LocationListActivity.class);
        startActivity(goToLocationList);
      }
    });

    mMyLocationBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent goToMyLocation = new Intent(HomeActivity.this, MyLocationActivity.class);
        startActivity(goToMyLocation);
      }
    });
  }

  @Override protected void onPause() {
    super.onPause();
    if (mTokenClass != null && !mTokenClass.isCancelled()) {
      mTokenClass.cancel(true);
    }
  }

  class TokenKeyGenerateClass extends AsyncTask<Void, Void, String> {

    @Override protected String doInBackground(Void... voids) {
      maePaySohApiWrapper = MaePaySoh.getMaePaySohWrapper();
      return maePaySohApiWrapper.getTokenKey();
    }

    @Override protected void onPostExecute(String s) {
      super.onPostExecute(s);
      mViewUtils.showProgress(mMainContent, mProgressBar, false);
      maePaySohApiWrapper.setTokenKey(s);
      PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
          .edit()
          .putString(Constants.API_KEY, s)
          .apply();
      inflateLayout();
    }
  }
}
