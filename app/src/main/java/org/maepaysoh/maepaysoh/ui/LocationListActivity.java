package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import org.maepaysoh.maepaysoh.R;

public class LocationListActivity extends BaseActivity {

  private Button ygnWestBtn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_geolocation);
    ygnWestBtn = (Button) findViewById(R.id.yangon_west_btn);
    ygnWestBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent goToDetail = new Intent(LocationListActivity.this, LocationDetailActivity.class);
        startActivity(goToDetail);
      }
    });
  }
}
