package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class BaseActivity extends AppCompatActivity {
  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  protected void hideToolBarShadowForLollipop(Toolbar mToolbar, View shadowView) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      // only for lollipop and newer versions
      shadowView.setVisibility(View.GONE);
    }
  }
}
