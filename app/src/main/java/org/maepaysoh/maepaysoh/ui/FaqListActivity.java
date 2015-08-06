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
import org.maepaysoh.maepaysoh.adapters.EndlessRecyclerViewAdapter;
import org.maepaysoh.maepaysoh.adapters.FaqAdapter;
import org.maepaysoh.maepaysoh.api.FaqService;
import org.maepaysoh.maepaysoh.api.RetrofitHelper;
import org.maepaysoh.maepaysoh.models.FAQ;
import org.maepaysoh.maepaysoh.models.FaqDatum;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static org.maepaysoh.maepaysoh.api.FaqService.PARAM_TYPE;
import static org.maepaysoh.maepaysoh.api.FaqService.PARAM_TYPE.page;
import static org.maepaysoh.maepaysoh.utils.Logger.LOGI;
import static org.maepaysoh.maepaysoh.utils.Logger.makeLogTag;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FaqListActivity extends BaseActivity implements FaqAdapter.ClickInterface{

  private static String TAG = makeLogTag(FaqListActivity.class);
  private RecyclerView mFaqListRecyclerView;
  private ProgressBar mProgressView;
  private View mErrorView;
  private Button mRetryBtn;
  private ViewUtils viewUtils;
  private RestAdapter mFaqRestAdapter;
  private LinearLayoutManager mLayoutManager;
  private FaqAdapter mFaqAdapter;
  private EndlessRecyclerViewAdapter mEndlessRecyclerViewAdapter;
  private FaqService mFaqService;
  private int mCurrentPage = 1;
  private List<FaqDatum> mFaqDatas;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_faq_list);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.faq_list_toolbar);
    View mToolbarShadow = findViewById(R.id.faq_list_toolbar_shadow);

    mErrorView = findViewById(R.id.faq_list_error_view);
    mFaqListRecyclerView = (RecyclerView) findViewById(R.id.faq_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.faq_list_progress_bar);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);

    mProgressView.getIndeterminateDrawable()
        .setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);

    mToolbar.setTitle(getString(R.string.FaqList));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    viewUtils = new ViewUtils(this);
    viewUtils.showProgress(mFaqListRecyclerView, mProgressView, true);
    mLayoutManager = new LinearLayoutManager(this);
    mFaqListRecyclerView.setLayoutManager(mLayoutManager);
    mFaqRestAdapter = RetrofitHelper.getResAdapter();
    mFaqService = mFaqRestAdapter.create(FaqService.class);
    mFaqAdapter = new FaqAdapter();
    mEndlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(FaqListActivity.this,
        mFaqAdapter, new EndlessRecyclerViewAdapter.RequestToLoadMoreListener() {
      @Override public void onLoadMoreRequested() {
        loadFaqDatas();
      }
    });
    mFaqListRecyclerView.setAdapter(mEndlessRecyclerViewAdapter);
    loadFaqDatas();
    mRetryBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        loadFaqDatas();
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

  private void loadFaqDatas(){
    Map<PARAM_TYPE, String> options = new HashMap<>();
    options.put(page, String.valueOf(mCurrentPage));
    mFaqService.listFaqs(options, new Callback<FAQ>() {
      @Override public void success(FAQ returnObject, Response response) {

        // Hide Progress on success
        viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
        switch (response.getStatus()) {
          case 200:
            if (returnObject.getData() != null && returnObject.getData().size() > 0) {
              if (mCurrentPage == 1) {
                 mFaqDatas = returnObject.getData();
              } else {
                mFaqDatas.addAll(returnObject.getData());
              }
              mFaqAdapter.setCandidates(mFaqDatas);
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
                (org.maepaysoh.maepaysoh.models.Error) error.getBodyAs(org.maepaysoh.maepaysoh.models.Error.class);
            Toast.makeText(FaqListActivity.this, mError.getError().getMessage(),
                Toast.LENGTH_SHORT).show();
            break;
          case NETWORK:
            Toast.makeText(FaqListActivity.this, getString(R.string.PleaseCheckNetwork),
                Toast.LENGTH_SHORT).show();
            break;
        }

        // Hide Progress on failure too
        viewUtils.showProgress(mFaqListRecyclerView, mProgressView, false);
        mEndlessRecyclerViewAdapter.onDataReady(false);
        mErrorView.setVisibility(View.VISIBLE);
      }
    });
  }

  @Override public void onItemClick(View view, int position) {
    Intent goToFaqDetailIntent = new Intent();
    goToFaqDetailIntent.setClass(FaqListActivity.this, FaqDetailActivity.class);
    goToFaqDetailIntent.putExtra(FaqDetailActivity.FAQ_CONSTANT,
        mFaqDatas.get(position));
    startActivity(goToFaqDetailIntent);
  }
}
