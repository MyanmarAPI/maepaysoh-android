package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class BaseActivity extends AppCompatActivity {
  private MaePaySohApiWrapper mMaePaySohApiWrapper;
  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
  }

  protected void hideToolBarShadowForLollipop(Toolbar mToolbar, View shadowView) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
      // only for lollipop and newer versions
      shadowView.setVisibility(View.GONE);
      mToolbar.setElevation(getResources().getDimension(R.dimen.toolbar_elevation_height));
    }
  }

  protected void share(String title, String body) {
    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
    startActivity(Intent.createChooser(sharingIntent, "Share via"));
  }

  protected MaePaySohApiWrapper getMaePaySohWrapper(){
    if(mMaePaySohApiWrapper==null){
      mMaePaySohApiWrapper = new MaePaySohApiWrapper(this);
      mMaePaySohApiWrapper.setApiKey(org.maepaysoh.maepaysoh.Constants.API_KEY);
      mMaePaySohApiWrapper.setFont(MaePaySohApiWrapper.FONT.zawgyi);
    }
    return mMaePaySohApiWrapper;
  }
}
