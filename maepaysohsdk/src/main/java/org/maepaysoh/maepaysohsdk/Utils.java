package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yemyatthu on 8/13/15.
 */
public class Utils {
  protected static void saveFontPref(MaePaySohApiWrapper.FONT font, Context context) {
    SharedPreferences sdkPreferences =
        context.getSharedPreferences(Constants.SDK_NAME, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sdkPreferences.edit();
    editor.putString(Constants.FONT_STORAGE, font.name()).apply();
  }

  protected static boolean isUniCode(Context context) {
    SharedPreferences sdkPreferences =
        context.getSharedPreferences(Constants.SDK_NAME, Context.MODE_PRIVATE);
    String font =
        sdkPreferences.getString(Constants.FONT_STORAGE, MaePaySohApiWrapper.FONT.unicode.name());
    return font.equals(MaePaySohApiWrapper.FONT.unicode.name()) || !font.equals(
        MaePaySohApiWrapper.FONT.zawgyi.name());
  }
}
