package org.maepaysoh.maepaysoh.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.PartyAdapter;
import org.maepaysoh.maepaysoh.api.PartyService;
import org.maepaysoh.maepaysoh.api.RetrofitHelper;
import org.maepaysoh.maepaysoh.models.Party;
import org.maepaysoh.maepaysoh.models.PartyReturnObject;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PartyListActivity extends BaseActivity {

  // Ui components
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private RecyclerView mPartyListRecyclerView;
  private ProgressBar mProgressView;

  private RestAdapter mPartyRestAdapter;
  private PartyService mPartyService;
  private List<Party> mParties;
  private ViewUtils viewUtils;
  private LinearLayoutManager mLayoutManager;
  private PartyAdapter mPartyAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_list);

    mToolbar = (Toolbar) findViewById(R.id.party_list_toolbar);
    mToolbarShadow = findViewById(R.id.party_list_toolbar_shadow);
    mPartyListRecyclerView = (RecyclerView) findViewById(R.id.party_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.party_list_progress_bar);

    mProgressView.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mToolbar.setTitle(getString(R.string.PartyList));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    viewUtils = new ViewUtils(this);

    // Show Progress on start
    viewUtils.showProgress(mPartyListRecyclerView, mProgressView, true);
    mLayoutManager = new LinearLayoutManager(this);
    mPartyListRecyclerView.setLayoutManager(mLayoutManager);
    mPartyAdapter = new PartyAdapter();
    mPartyListRecyclerView.setAdapter(mPartyAdapter);
    mPartyRestAdapter = RetrofitHelper.getResAdapter();
    mPartyService = mPartyRestAdapter.create(PartyService.class);
    mPartyService.listParties(new Callback<PartyReturnObject>() {
      @Override public void success(PartyReturnObject returnObject, Response response) {

        // Hide Progress on success
        viewUtils.showProgress(mPartyListRecyclerView, mProgressView, false);
        switch (response.getStatus()) {
          case 200:
            mParties = returnObject.getData();
            mPartyAdapter.setParties(mParties);
            break;
        }
      }

      @Override public void failure(RetrofitError error) {
        // Hide Progress on failure too
        viewUtils.showProgress(mPartyListRecyclerView, mProgressView, false);
      }
    });
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
