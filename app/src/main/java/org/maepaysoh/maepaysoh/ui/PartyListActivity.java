package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import org.maepaysoh.maepaysoh.R;

public class PartyListActivity extends AppCompatActivity {

  private Toolbar mToolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_list);

    mToolbar = (Toolbar) findViewById(R.id.party_list_toolbar);
    mToolbar.setTitle(getString(R.string.PartyList));

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  //@Override public boolean onCreateOptionsMenu(Menu menu) {
  //  // Inflate the menu; this adds items to the action bar if it is present.
  //  getMenuInflater().inflate(R.menu.menu_party_list, menu);
  //  return true;
  //}

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == android.R.id.home) {
      onBackPressed();
    }

    return super.onOptionsItemSelected(item);
  }
}
