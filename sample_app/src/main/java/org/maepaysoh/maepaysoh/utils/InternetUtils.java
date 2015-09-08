package org.maepaysoh.maepaysoh.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by yemyatthu on 8/7/15.
 */
public class InternetUtils {
  public static boolean isNetworkAvailable(final Context context) {
    final ConnectivityManager connectivityManager =
        ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
    return connectivityManager.getActiveNetworkInfo() != null
        && connectivityManager.getActiveNetworkInfo().isConnected();
  }
}
