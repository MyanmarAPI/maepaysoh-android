package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import org.maepaysoh.maepaysoh.adapters.CandidateAdapter;
import org.maepaysoh.maepaysoh.adapters.EndlessRecyclerViewAdapter;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.CandidateAPIHelper;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;
import org.maepaysoh.maepaysohsdk.models.Candidate;

import static org.maepaysoh.maepaysoh.utils.Logger.makeLogTag;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class CandidateListActivity extends BaseActivity implements CandidateAdapter.ClickInterface {

  private static String TAG = makeLogTag(CandidateListActivity.class);

  // Ui components
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private RecyclerView mCandidateListRecyclerView;
  private ProgressBar mProgressView;
  private View mErrorView;
  private Button mRetryBtn;
  private CandidateAPIHelper mCandidateAPIHelper;

  private ViewUtils viewUtils;
  private List<Candidate> mCandidates;
  private LinearLayoutManager mLayoutManager;
  private CandidateAdapter mCandidateAdapter;
  private EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;
  private int mCurrentPage = 1;
  private MaePaySohApiWrapper mMaePaySohApiWrapper;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_list);

    mToolbar = (Toolbar) findViewById(R.id.candidate_list_toolbar);
    mToolbarShadow = findViewById(R.id.candidate_list_toolbar_shadow);
    mCandidateListRecyclerView = (RecyclerView) findViewById(R.id.candidate_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.candidate_list_progress_bar);
    mErrorView = findViewById(R.id.candidate_list_error_view);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);
    mMaePaySohApiWrapper = getMaePaySohWrapper();
    mCandidateAPIHelper = mMaePaySohApiWrapper.getCandidateApiHelper();
    mProgressView.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mToolbar.setTitle(getString(R.string.CandidateList));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    viewUtils = new ViewUtils(this);
    viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, true);

    mLayoutManager = new LinearLayoutManager(this);
    mCandidateListRecyclerView.setLayoutManager(mLayoutManager);
    mCandidateAdapter = new CandidateAdapter();
    mCandidateAdapter.setOnItemClickListener(this);
    mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, mCandidateAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          @Override public void onLoadMoreRequested() {
            downloadCandidateList();
          }
        });
    mCandidateListRecyclerView.setAdapter(mEndlessRecyclerViewAdapter);
    if (InternetUtils.isNetworkAvailable(this)) {
      downloadCandidateList();
    } else {
      loadFromCache();
    }

    mRetryBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        downloadCandidateList();
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

  private void downloadCandidateList() {
    new DownloadCandidateListAsync().execute(mCurrentPage);
  }

  @Override public void onItemClick(View view, int position) {
    Intent goToCandiDetailIntent = new Intent();
    goToCandiDetailIntent.setClass(CandidateListActivity.this, CandidateDetailActivity.class);
    goToCandiDetailIntent.putExtra(CandidateDetailActivity.CANDIDATE_CONSTANT,
        mCandidates.get(position));
    startActivity(goToCandiDetailIntent);
  }

  private void loadFromCache() {
    //Disable pagination in cache
    mEndlessRecyclerViewAdapter.onDataReady(false);
    try {
      mCandidates = mCandidateAPIHelper.getCandidatesFromCache();
      if (mCandidates != null && mCandidates.size() > 0) {
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        mCandidateAdapter.setCandidates(mCandidates);
        mCandidateAdapter.setOnItemClickListener(CandidateListActivity.this);
      } else {
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        mErrorView.setVisibility(View.VISIBLE);
      }
    } catch (SQLException e) {
      viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
      mErrorView.setVisibility(View.VISIBLE);
      e.printStackTrace();
    }
  }

  class DownloadCandidateListAsync extends AsyncTask<Integer, Void, List<Candidate>> {

    @Override protected List<Candidate> doInBackground(Integer... integers) {
      mCurrentPage = integers[0];
      return mCandidateAPIHelper.getCandidates(integers[0], true);
    }

    @Override protected void onPostExecute(List<Candidate> candidates) {
      super.onPostExecute(candidates);
      viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
      if (candidates.size() > 0) {
        if (mCurrentPage == 1) {
          mCandidates = candidates;
        } else {
          mCandidates.addAll(candidates);
        }
        mCandidateAdapter.setCandidates(mCandidates);
        mEndlessRecyclerViewAdapter.onDataReady(true);
        mCurrentPage++;
      } else {
        if (mCurrentPage == 1) {
          loadFromCache();
        } else {
          mEndlessRecyclerViewAdapter.onDataReady(false);
        }
      }
    }
  }
}
