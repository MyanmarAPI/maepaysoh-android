package org.maepaysoh.maepaysoh.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;
import org.maepaysoh.maepaysoh.Constants;
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
      mMaePaySohApiWrapper.setTokenKey(org.maepaysoh.maepaysoh.Constants.API_KEY);
    }
    return mMaePaySohApiWrapper;
  }

  protected void showFontChooserDialog(boolean cancellable){
    mMaePaySohApiWrapper = getMaePaySohWrapper();
    View view = getLayoutInflater().inflate(R.layout.font_dialog,null);
    final RadioGroup fontRbg = (RadioGroup) view.findViewById(R.id.font_rbg);
    boolean isUsingUnicode = mMaePaySohApiWrapper.isUsingUnicode();
    if (isUsingUnicode){
      fontRbg.check(R.id.unicode_rb);
    }else{
      fontRbg.check(R.id.zawgyi_rb);
    }
    int padding = (int) getResources().getDimension(R.dimen.spacing_major);
    AlertDialog.Builder builder = new AlertDialog.Builder(this).setView(view, padding, padding,
        padding, padding)
        .setTitle("Please Choose Font")
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            switch (fontRbg.getCheckedRadioButtonId()) {
              case R.id.unicode_rb:
                mMaePaySohApiWrapper.setFont(MaePaySohApiWrapper.FONT.unicode);
                break;
              case R.id.zawgyi_rb:
                mMaePaySohApiWrapper.setFont(MaePaySohApiWrapper.FONT.zawgyi);
                break;
              default:
                mMaePaySohApiWrapper.setFont(MaePaySohApiWrapper.FONT.unicode);
                break;
            }
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .edit()
                .putBoolean(Constants.FIRST_TIME, false).apply();
            dialogInterface.dismiss();
          }
        });
    if(!cancellable){
      builder.setCancelable(false);
    }else{
      builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialogInterface, int i) {
          dialogInterface.dismiss();
        }
      });
    }
    builder.show();
  }

  protected String convertISO8601toString(String iso8601){
    return iso8601.replaceAll("T.*?Z", "");
  }
}
