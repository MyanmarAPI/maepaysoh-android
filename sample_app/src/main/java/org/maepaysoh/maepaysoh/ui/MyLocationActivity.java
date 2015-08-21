package org.maepaysoh.maepaysoh.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import org.maepaysoh.maepaysoh.R;

/**
 * Created by yemyatthu on 8/21/15.
 */
public class MyLocationActivity extends BaseActivity {
  private GoogleApiClient mGoogleApiClient;
  private Location mLastLocation;
  private LocationManager locationManager;
  private double longitude;
  private double latitude;
  private  ProgressDialog progressDialog;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_detail);
    /** PROCESS for Get Longitude and Latitude **/
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

    // getting GPS status
    boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    Log.d("msg", "GPS:" + isGPSEnabled);

    // check if GPS enabled
    if (isGPSEnabled) {
      progressDialog = ProgressDialog.show(this, null, "Searching for location", true, false);
    /*
    longitude = 70.80079728674089;
    latitude =  22.29090332494221;
     */
      String provider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER).getName();
      Location location = locationManager.getLastKnownLocation(provider);

      //new LoadPlaces().execute();

      if (location != null) {
        progressDialog.cancel();
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Log.d("msg", "first lat long : " + latitude + " " + longitude);
        //new LoadPlaces().execute();
      } else {

        progressDialog.show();
        locationManager.requestLocationUpdates(provider, 0, 0, new LocationListener() {

              @Override public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

              }

              @Override public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

              }

              @Override public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

              }

              @Override public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                progressDialog.cancel();
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Log.d("msg", "changed lat long : " + latitude + " " + longitude);
              }
            });
      }
    } else {
      showSettingsAlert();
    }
  }

  public void showSettingsAlert() {
    AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

    //Setting Dialog Title
    alertDialog.setTitle(R.string.GPSAlertDialogTitle);

    //Setting Dialog Message
    alertDialog.setMessage(R.string.GPSAlertDialogMessage);

    //On Pressing Setting button
    alertDialog.setPositiveButton(R.string.action_settings, new DialogInterface.OnClickListener() {

      @Override public void onClick(DialogInterface dialog, int which) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
      }
    });

    //On pressing cancel button
    alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

      @Override public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    alertDialog.show();
  }
}
