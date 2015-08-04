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
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.CandidateAdapter;
import org.maepaysoh.maepaysoh.api.CandidateService;
import org.maepaysoh.maepaysoh.api.RetrofitHelper;
import org.maepaysoh.maepaysoh.models.Candidate;
import org.maepaysoh.maepaysoh.models.CandidateData;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
    mCandidateListRecyclerView.setLayoutManager(mLayoutManager);
    mCandidateAdapter = new CandidateAdapter();
    mCandidateListRecyclerView.setAdapter(mCandidateAdapter);

    mCandidateRestAdapter = RetrofitHelper.getResAdapter();
    mCandidateListService = mCandidateRestAdapter.create(CandidateService.class);
    mCandidateListService.listCandidates(new Callback<Candidate>() {
      @Override public void success(Candidate returnObject, Response response) {

        // Hide Progress on success
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        switch (response.getStatus()) {
          case 200:
            mCandidateDatas =returnObject.getData();
            mCandidateAdapter.setCandidates(mCandidateDatas);
            Log.i("candidate", "total candidate : " + returnObject.getData().size());
            break;
        }
      }

      @Override public void failure(RetrofitError error) {
        // Hide Progress on failure too
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
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
