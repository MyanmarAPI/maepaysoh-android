package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.graphics.PorterDuff;
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
import android.widget.Toast;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.CandidateAdapter;
import org.maepaysoh.maepaysoh.adapters.EndlessRecyclerViewAdapter;
import org.maepaysoh.maepaysoh.api.CandidateService;
import org.maepaysoh.maepaysoh.api.RetrofitHelper;
import org.maepaysoh.maepaysoh.models.Candidate;
import org.maepaysoh.maepaysoh.models.CandidateData;
import org.maepaysoh.maepaysoh.models.Error;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.maepaysoh.maepaysoh.api.CandidateService.PARAM_TYPE._with;
import static org.maepaysoh.maepaysoh.api.CandidateService.PARAM_TYPE.page;
import static org.maepaysoh.maepaysoh.utils.Logger.LOGI;
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

  private RestAdapter mCandidateRestAdapter;
  private CandidateService mCandidateListService;

  private ViewUtils viewUtils;
  private List<CandidateData> mCandidateDatas;
  private LinearLayoutManager mLayoutManager;
  private CandidateAdapter mCandidateAdapter;
  private EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;
  private int mCurrentPage = 1;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_list);

    mToolbar = (Toolbar) findViewById(R.id.candidate_list_toolbar);
    mToolbarShadow = findViewById(R.id.candidate_list_toolbar_shadow);
    mCandidateListRecyclerView = (RecyclerView) findViewById(R.id.candidate_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.candidate_list_progress_bar);
    mErrorView = findViewById(R.id.candidate_list_error_view);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);

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

    mCandidateRestAdapter = RetrofitHelper.getResAdapter();
    mCandidateListService = mCandidateRestAdapter.create(CandidateService.class);
    downloadCandidateList();

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
    Map<CandidateService.PARAM_TYPE, String> options = new HashMap<>();
    options.put(_with, "party");
    options.put(page, String.valueOf(mCurrentPage));
    mCandidateListService.listCandidates(options, new Callback<Candidate>() {
      @Override public void success(Candidate returnObject, Response response) {

        // Hide Progress on success
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        switch (response.getStatus()) {
          case 200:
            if (returnObject.getData() != null && returnObject.getData().size() > 0) {
              if (mCurrentPage == 1) {
                mCandidateDatas = returnObject.getData();
              } else {
                mCandidateDatas.addAll(returnObject.getData());
              }
              mCandidateAdapter.setCandidates(mCandidateDatas);
              mEndlessRecyclerViewAdapter.onDataReady(true);
              mCurrentPage++;
            } else {
              mEndlessRecyclerViewAdapter.onDataReady(false);
            }
            LOGI(TAG, "total candidate : " + returnObject.getData().size());
            break;
        }
      }

      @Override public void failure(RetrofitError error) {
        switch (error.getKind()) {
          case HTTP:
            org.maepaysoh.maepaysoh.models.Error mError =
                (Error) error.getBodyAs(org.maepaysoh.maepaysoh.models.Error.class);
            Toast.makeText(CandidateListActivity.this, mError.getError().getMessage(),
                Toast.LENGTH_SHORT).show();
            break;
          case NETWORK:
            Toast.makeText(CandidateListActivity.this, getString(R.string.PleaseCheckNetwork),
                Toast.LENGTH_SHORT).show();
            break;
        }

        // Hide Progress on failure too
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        mEndlessRecyclerViewAdapter.onDataReady(false);
        mErrorView.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override public void onItemClick(View view, int position) {
    Intent goToCandiDetailIntent = new Intent();
    goToCandiDetailIntent.setClass(CandidateListActivity.this, CandidateDetailActivity.class);
    goToCandiDetailIntent.putExtra(CandidateDetailActivity.CANDIDATE_CONSTANT,
        mCandidateDatas.get(position));
    startActivity(goToCandiDetailIntent);
  }
}
