package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import org.maepaysoh.maepaysoh.R;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class HomeActivity extends BaseActivity {
  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    setContentView(R.layout.activity_home);
  }
}
