package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.sql.SQLException;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.PartyAdapter;
import org.maepaysoh.maepaysoh.api.PartyService;
import org.maepaysoh.maepaysoh.api.RetrofitHelper;
import org.maepaysoh.maepaysoh.db.PartyDao;
import org.maepaysoh.maepaysoh.models.Error;
import org.maepaysoh.maepaysoh.models.Party;
import org.maepaysoh.maepaysoh.models.PartyData;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PartyListActivity extends BaseActivity implements PartyAdapter.ClickInterface {

  private RecyclerView mPartyListRecyclerView;
  private ProgressBar mProgressView;
  private View mErrorView;
  private Button mRetryBtn;

  private RestAdapter mPartyRestAdapter;
  private PartyService mPartyService;
  private List<PartyData> mParties;
  private PartyAdapter mPartyAdapter;

  private ViewUtils viewUtils;
  private PartyDao mPartyDao;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_list);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.party_list_toolbar);
    View mToolbarShadow = findViewById(R.id.party_list_toolbar_shadow);
    mErrorView = findViewById(R.id.party_list_error_view);
    mPartyListRecyclerView = (RecyclerView) findViewById(R.id.party_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.party_list_progress_bar);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);

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
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mPartyListRecyclerView.setLayoutManager(mLayoutManager);
    mPartyAdapter = new PartyAdapter();
    mPartyListRecyclerView.setAdapter(mPartyAdapter);
    mPartyDao = new PartyDao(this);
    if (InternetUtils.isNetworkAvailable(this)) {
      downloadPartyList();
    } else {
      loadFromCache();
    }

    mRetryBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        downloadPartyList();
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

  @Override public void onItemClick(View view, int position) {
    Intent goToPartyDetailIntent = new Intent();
    goToPartyDetailIntent.setClass(PartyListActivity.this, PartyDetailActivity.class);
    goToPartyDetailIntent.putExtra(PartyDetailActivity.PARTY_CONSTANT, mParties.get(position));
    startActivity(goToPartyDetailIntent);
  }

  private void downloadPartyList() {
    mPartyRestAdapter = RetrofitHelper.getResAdapter();
    mPartyService = mPartyRestAdapter.create(PartyService.class);
    mPartyService.listParties(new Callback<Party>() {
      @Override public void success(Party returnObject, Response response) {
        // Hide Progress on success
        viewUtils.showProgress(mPartyListRecyclerView, mProgressView, false);
        switch (response.getStatus()) {
          case 200:
            mParties = returnObject.getData();
            mPartyAdapter.setParties(mParties);
            mPartyAdapter.setOnItemClickListener(PartyListActivity.this);
            for (PartyData data : mParties) {
              try {
                mPartyDao.createParty(data);
              } catch (SQLException e) {
                e.printStackTrace();
              }
            }
            break;
        }
      }

      @Override public void failure(RetrofitError error) {
        switch (error.getKind()) {
          case HTTP:
            Error mError = (Error) error.getBodyAs(org.maepaysoh.maepaysoh.models.Error.class);
            Toast.makeText(PartyListActivity.this, mError.getError().getMessage(),
                Toast.LENGTH_SHORT).show();
            break;
          case NETWORK:
            Toast.makeText(PartyListActivity.this, getString(R.string.PleaseCheckNetwork),
                Toast.LENGTH_SHORT).show();
            break;
          case CONVERSION:
            Toast.makeText(PartyListActivity.this, getString(R.string.SomethingWentWrong),
                Toast.LENGTH_SHORT).show();
        }

        // Hide Progress on failure too
        //  viewUtils.showProgress(mPartyListRecyclerView, mProgressView, false);
        //  mErrorView.setVisibility(View.VISIBLE);
        loadFromCache();
      }
    });
  }

  private void loadFromCache() {
    try {
      mParties = mPartyDao.getAllPartyData();
      if (mParties != null && mParties.size() > 0) {
        viewUtils.showProgress(mPartyListRecyclerView, mProgressView, false);
        mPartyAdapter.setParties(mParties);
        mPartyAdapter.setOnItemClickListener(PartyListActivity.this);
      }
    } catch (SQLException e) {
      viewUtils.showProgress(mPartyListRecyclerView,mProgressView,false);
      mErrorView.setVisibility(View.VISIBLE);
      e.printStackTrace();
    }
  }
}
