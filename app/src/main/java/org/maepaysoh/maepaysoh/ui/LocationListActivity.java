package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import org.maepaysoh.maepaysoh.R;

public class LocationListActivity extends BaseActivity {

  private Button ygnWestBtn;

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

    ygnWestBtn = (Button) findViewById(R.id.yangon_west_btn);
    ygnWestBtn.setText("Yangon West");
    ygnWestBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToDetail = new Intent(LocationListActivity.this, LocationDetailActivity.class);
        startActivity(goToDetail);
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
}
