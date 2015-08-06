package org.maepaysoh.maepaysoh.ui;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.utils.ViewUtils;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FaqListActivity extends BaseActivity {

  private RecyclerView mFaqListRecyclerView;
  private ProgressBar mProgressView;
  private View mErrorView;
  private Button mRetryBtn;
  private ViewUtils viewUtils;

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
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}
