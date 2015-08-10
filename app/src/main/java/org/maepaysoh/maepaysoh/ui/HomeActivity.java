package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import org.maepaysoh.maepaysoh.R;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class HomeActivity extends BaseActivity {
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private Button mPartyListBtn;
  private Button mCandidateListBtn;
  private Button mFaqListBtn;
  private Button mLocationListBtn;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
    mToolbarShadow = findViewById(R.id.home_toolbar_shadow);
    mPartyListBtn = (Button) findViewById(R.id.home_party_list_btn);
    mCandidateListBtn = (Button) findViewById(R.id.home_candidate_list_btn);
    mFaqListBtn = (Button) findViewById(R.id.home_faq_list_btn);
    mLocationListBtn = (Button) findViewById(R.id.home_location_list_btn);

    mToolbar.setTitle(getString(R.string.app_name));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

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
  }
}
