package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.database.SQLException;
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
import com.yemyatthu.maepaesohsdk.CandidateAPIHelper;
import com.yemyatthu.maepaesohsdk.models.Candidate;
import com.yemyatthu.maepaesohsdk.models.CandidateData;
import com.yemyatthu.maepaesohsdk.models.Error;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.CandidateAdapter;
import org.maepaysoh.maepaysoh.adapters.EndlessRecyclerViewAdapter;
import org.maepaysoh.maepaysoh.api.CandidateService;
import org.maepaysoh.maepaysoh.api.RetrofitHelper;
import org.maepaysoh.maepaysoh.db.CandidateDao;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
  private CandidateAPIHelper mCandidateAPIHelper;

  private RestAdapter mCandidateRestAdapter;
  private CandidateService mCandidateListService;

  private ViewUtils viewUtils;
  private List<CandidateData> mCandidateDatas;
  private LinearLayoutManager mLayoutManager;
  private CandidateAdapter mCandidateAdapter;
  private EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;
  private int mCurrentPage = 1;
  private CandidateDao mCandidateDao;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_list);

    mToolbar = (Toolbar) findViewById(R.id.candidate_list_toolbar);
    mToolbarShadow = findViewById(R.id.candidate_list_toolbar_shadow);
    mCandidateListRecyclerView = (RecyclerView) findViewById(R.id.candidate_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.candidate_list_progress_bar);
    mErrorView = findViewById(R.id.candidate_list_error_view);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);
    mCandidateAPIHelper = new CandidateAPIHelper();
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
    mCandidateDao = new CandidateDao(this);
    if(InternetUtils.isNetworkAvailable(this)){
      downloadCandidateList();
    }else{
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
    mCandidateAPIHelper.getCandidates(new Callback<Candidate>() {
      @Override public void success(Candidate returnObject, Response response) {
        // Hide Progress on success
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        switch (response.getStatus()) {
          case 200:
            if (returnObject.getData() != null && returnObject.getData().size() > 0) {
              for (CandidateData candidateData : returnObject.getData()) {
                mCandidateDao.createCandidate(candidateData);
              }
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
            com.yemyatthu.maepaesohsdk.models.Error mError = (Error) error.getBodyAs(Error.class);
            Toast.makeText(CandidateListActivity.this, mError.getError().getMessage(),
                Toast.LENGTH_SHORT).show();
            break;
          case NETWORK:
            Toast.makeText(CandidateListActivity.this, getString(R.string.PleaseCheckNetwork),
                Toast.LENGTH_SHORT).show();
            break;
        }

        // Hide Progress on failure too
        if (mCurrentPage == 1) {
          loadFromCache();
        } else {
          viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
          mEndlessRecyclerViewAdapter.onDataReady(false);
          mErrorView.setVisibility(View.VISIBLE);
        }
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

  private void loadFromCache() {
    //Disable pagination in cache
    mEndlessRecyclerViewAdapter.onDataReady(false);
    try {
      mCandidateDatas = mCandidateDao.getAllCandidateData();
      if (mCandidateDatas != null && mCandidateDatas.size() > 0) {
        viewUtils.showProgress(mCandidateListRecyclerView, mProgressView, false);
        mCandidateAdapter.setCandidates(mCandidateDatas);
        mCandidateAdapter.setOnItemClickListener(CandidateListActivity.this);
      }else{
        viewUtils.showProgress(mCandidateListRecyclerView,mProgressView,false);
        mErrorView.setVisibility(View.VISIBLE);
      }
    } catch (SQLException e) {
      viewUtils.showProgress(mCandidateListRecyclerView,mProgressView,false);
      mErrorView.setVisibility(View.VISIBLE);
      e.printStackTrace();
    }
  }
}
