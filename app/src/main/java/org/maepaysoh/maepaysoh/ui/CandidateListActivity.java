package org.maepaysoh.maepaysoh.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.maepaysoh.maepaysoh.api.CandidateService.PARAM_TYPE._with;
import static org.maepaysoh.maepaysoh.api.CandidateService.PARAM_TYPE.page;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class CandidateListActivity extends BaseActivity {

  // Ui components
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private RecyclerView mCandidateListRecyclerView;
  private ProgressBar mProgressView;

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
    mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, mCandidateAdapter,
        new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
          @Override public void onLoadMoreRequested() {
            loadData();
          }
        });
    mCandidateListRecyclerView.setAdapter(mEndlessRecyclerViewAdapter);

    mCandidateRestAdapter = RetrofitHelper.getResAdapter();
    mCandidateListService = mCandidateRestAdapter.create(CandidateService.class);
    loadData();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  private void loadData() {
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
            Log.i("candidate", "total candidate : " + returnObject.getData().size());
            break;
        }
      }

      @Override public void failure(RetrofitError error) {
        // Hide Progress on failure too
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        mEndlessRecyclerViewAdapter.onDataReady(false);
      }
    });
  }
}
