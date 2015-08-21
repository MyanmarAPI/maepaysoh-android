package org.maepaysoh.maepaysoh.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.List;
import org.maepaysoh.maepaysoh.MaePaySoh;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.adapters.CandidateAdapter;
import org.maepaysoh.maepaysoh.adapters.EndlessRecyclerViewAdapter;
import org.maepaysoh.maepaysoh.utils.InternetUtils;
import org.maepaysoh.maepaysoh.utils.ViewUtils;
import org.maepaysoh.maepaysohsdk.CandidateAPIHelper;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;
import org.maepaysoh.maepaysohsdk.models.Candidate;
import org.maepaysoh.maepaysohsdk.utils.CandidateAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.CandidateAPIPropertiesMap;

import static org.maepaysoh.maepaysoh.utils.Logger.makeLogTag;
import static org.maepaysoh.maepaysohsdk.utils.Logger.LOGD;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class CandidateListActivity extends BaseActivity implements CandidateAdapter.ClickInterface,
    SearchView.OnQueryTextListener {

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
  private DownloadCandidateListAsync mDownloadCandidateListAsync;
  private MenuItem mSearchMenu;
  private SearchView mSearchView;
  private int genderIDRB = R.id.gender_all_rb;
  private int religionIDRB = R.id.religion_all_rb;
  private int legiIDRB = R.id.lgei_all_rb;
  private CandidateAPIPropertiesMap mCandidateAPIPropertiesMap;

  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_list);

    mToolbar = (Toolbar) findViewById(R.id.candidate_list_toolbar);
    mToolbarShadow = findViewById(R.id.candidate_list_toolbar_shadow);
    mCandidateListRecyclerView = (RecyclerView) findViewById(R.id.candidate_list_recycler_view);
    mProgressView = (ProgressBar) findViewById(R.id.candidate_list_progress_bar);
    mErrorView = findViewById(R.id.candidate_list_error_view);
    mRetryBtn = (Button) mErrorView.findViewById(R.id.error_view_retry_btn);
    mMaePaySohApiWrapper = MaePaySoh.getMaePaySohWrapper();
    mCandidateAPIHelper = mMaePaySohApiWrapper.getCandidateApiHelper();
    mCandidateAPIPropertiesMap = new CandidateAPIPropertiesMap();
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
    switch (id){
      case android.R.id.home:
        onBackPressed();
        return true;
      case R.id.menu_filter:
        showFilterDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void downloadCandidateList() {
    mCandidateAPIPropertiesMap.put(CandidateAPIProperties.FIRST_PAGE, mCurrentPage);
    mDownloadCandidateListAsync = new DownloadCandidateListAsync(mCandidateAPIPropertiesMap);
    mDownloadCandidateListAsync.execute();
  }

  @Override protected void onPause() {
    super.onPause();
    if(mDownloadCandidateListAsync!=null){
      mDownloadCandidateListAsync.cancel(true);
    }
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

  @Override public boolean onQueryTextSubmit(String query) {
    return true;
  }

  @Override public boolean onQueryTextChange(String newText) {
    mCurrentPage = 1;
    searchCandidateFromCache(newText);
    LOGD(TAG, "searching");
    return true;
  }

  class DownloadCandidateListAsync extends AsyncTask<Void, Void, List<Candidate>> {
    CandidateAPIPropertiesMap propertiesMap;
    public DownloadCandidateListAsync(CandidateAPIPropertiesMap propertiesMap){
      this.propertiesMap = propertiesMap;
    }
    @Override protected List<Candidate> doInBackground(Void... voids) {
      return mCandidateAPIHelper.getCandidates(propertiesMap);
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
  private void searchCandidateFromCache(String keyword){
    TextView errorText = (TextView) mErrorView.findViewById(R.id.error_view_error_text);
    errorText.setText(getString(R.string.PleaseCheckNetworkAndTryAgain));
    mRetryBtn.setVisibility(View.VISIBLE);
    if (mErrorView.getVisibility() == View.VISIBLE) {
      mErrorView.setVisibility(View.GONE);
    }

    if(keyword.length()>0) {
      mCandidates = mCandidateAPIHelper.searchCandidateFromCache(keyword);
      if (mCandidates != null && mCandidates.size() > 0) {
        mCandidateListRecyclerView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
        mCandidateAdapter.setCandidates(mCandidates);
        mCandidateAdapter.setOnItemClickListener(CandidateListActivity.this);
      } else {
        mCandidateListRecyclerView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
        mRetryBtn.setVisibility(View.GONE);
        errorText.setText(R.string.search_not_found);
      }
    }else{
      loadFromCache();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_candidate_list, menu);
    mSearchMenu = menu.findItem(R.id.menu_search);
    mSearchView = (android.support.v7.widget.SearchView) mSearchMenu.getActionView();
    mSearchView.setOnQueryTextListener(this);
    return true;
  }

  private void showFilterDialog(){
    mCurrentPage = 1;
    View view = getLayoutInflater().inflate(R.layout.filter_dialog_view,null);
    RadioGroup genderRg = (RadioGroup) view.findViewById(R.id.gender_radio_group);
    RadioGroup religionRg = (RadioGroup) view.findViewById(R.id.religion_radio_group);
    RadioGroup legRg = (RadioGroup) view.findViewById(R.id.legi_radio_group);
    genderRg.check(genderIDRB);
    religionRg.check(religionIDRB);
    legRg.check(legiIDRB);

    genderRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup radioGroup, int i) {
        genderIDRB = i;
        switch (i) {
          case R.id.gender_male_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.GENDER, "male");
            break;
          case R.id.gender_female_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.GENDER, "female");
            break;
          case R.id.gender_all_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.GENDER, "");
            break;
        }
      }
    });

    religionRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup radioGroup, int i) {
        religionIDRB = i;
        switch (i){
          case R.id.religion_all_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.RELIGION,"");
            break;
          case R.id.religion_buddhism_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.RELIGION,"buddhism");
            break;
          case R.id.religion_christian_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.RELIGION,"christian");
            break;
          case R.id.religion_Islam_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.RELIGION,"islam");
            break;
          case R.id.religion_hindu_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.RELIGION,"hindu");
            break;
        }
      }
    });

    legRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i){
          case R.id.lgei_all_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.LEGISLATURE,"");
            break;
          case R.id.legi_amyotha_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.LEGISLATURE,"amyotha_hluttaw");
            break;
          case R.id.legi_pyitthu_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.LEGISLATURE,"pyithu_hluttaw");
            break;
          case R.id.legi_region_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.LEGISLATURE,"region");
            break;
          case R.id.legi_state_rb:
            mCandidateAPIPropertiesMap.put(CandidateAPIProperties.LEGISLATURE,"state");
            break;
        }
      }
    });
    Dialog filterDialog = new AlertDialog.Builder(CandidateListActivity.this)
        .setCustomTitle(null)
        .setView(view)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            viewUtils.showProgress(mCandidateListRecyclerView,mProgressView,true);
            downloadCandidateList();
          }
        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        })
        .create();
    filterDialog.show();
  }

}
