package org.maepaysoh.maepaysoh;

import android.app.Application;
import android.content.Context;
import org.maepaysoh.maepaysohsdk.MaePaySohApiWrapper;

/**
 * Created by yemyatthu on 8/21/15.
 */
public class MaePaySoh extends Application {
  private static Context sContext;
  private static MaePaySohApiWrapper mMaePaySohApiWrapper;

  public static MaePaySohApiWrapper getMaePaySohWrapper() {
    if (mMaePaySohApiWrapper == null) {
      mMaePaySohApiWrapper = new MaePaySohApiWrapper(sContext);
    }
    return mMaePaySohApiWrapper;
  }

  @Override public void onCreate() {
    super.onCreate();
    MaePaySoh.sContext = getApplicationContext();
  }
}
