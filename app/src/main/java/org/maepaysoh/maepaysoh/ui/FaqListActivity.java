package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import org.maepaysoh.maepaysoh.R;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FaqListActivity extends BaseActivity {
  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_faq_list);

    Toolbar mToolbar = (Toolbar) findViewById(R.id.faq_list_toolbar);
    View mToolbarShadow = findViewById(R.id.faq_list_toolbar_shadow);
    mToolbar.setTitle(getString(R.string.FaqList));
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    setSupportActionBar(mToolbar);

    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }
}
