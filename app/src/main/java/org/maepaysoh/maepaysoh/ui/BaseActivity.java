package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.utils.ViewUtils;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class BaseActivity extends AppCompatActivity {

  protected ViewUtils viewUtils;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    viewUtils = new ViewUtils(this);
  }

  protected void hideToolBarShadowForLollipop(Toolbar mToolbar, View shadowView) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      // only for lollipop and newer versions
      shadowView.setVisibility(View.GONE);
      mToolbar.setElevation(getResources().getDimension(R.dimen.toolbar_elevation_height));
    }
  }
}
