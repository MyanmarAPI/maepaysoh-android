package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import org.maepaysoh.maepaysoh.R;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class HomeActivity extends AppCompatActivity {
  private Toolbar mToolbar;
  private View mToolBarShadow;
  private Button mPartyListBtn;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
    mToolBarShadow = findViewById(R.id.home_toolbar_shadow);
    mPartyListBtn = (Button) findViewById(R.id.home_party_list_btn);

    mToolbar.setTitle(getString(R.string.app_name));

    mPartyListBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToPartyList = new Intent(HomeActivity.this, PartyListActivity.class);
        startActivity(goToPartyList);
      }
    });
  }
}
