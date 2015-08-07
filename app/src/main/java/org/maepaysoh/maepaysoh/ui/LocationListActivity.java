package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonGeometry;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import java.io.IOException;
import org.json.JSONException;
import org.maepaysoh.maepaysoh.R;

public class LocationListActivity extends FragmentActivity {

  private GoogleMap mMap; // Might be null if Google Play services APK is not available.

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_geolocation);
    setUpMapIfNeeded();
  }

  @Override protected void onResume() {
    super.onResume();
    setUpMapIfNeeded();
  }

  /**
   * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
   * installed) and the map has not already been instantiated.. This will ensure that we only ever
   * call {@link #setUpMap()} once when {@link #mMap} is not null.
   * <p>
   * If it isn't installed {@link SupportMapFragment} (and
   * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
   * install/update the Google Play services APK on their device.
   * <p>
   * A user can return to this FragmentActivity after following the prompt and correctly
   * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
   * have been completely destroyed during this process (it is likely that it would only be
   * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
   * method in {@link #onResume()} to guarantee that it will be called.
   */
  private void setUpMapIfNeeded() {
    // Do a null check to confirm that we have not already instantiated the map.
    if (mMap == null) {
      // Try to obtain the map from the SupportMapFragment.
      mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
      if (mMap != null) {
        setUpMap();
      }
    }
  }

  /**
   * This is where we can add markers or lines, add listeners or move the camera. In this case, we
   * just add a marker near Africa.
   * <p>
   * This should only be called once and when we are sure that {@link #mMap} is not null.
   */
  private void setUpMap() {
    try {
      GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.yangon_west, getApplicationContext());

      for (GeoJsonFeature feature : layer.getFeatures()) {
        GeoJsonGeometry geoJsonGeometry = feature.getGeometry();
        GeoJsonPolygonStyle geoJsonPolygonStyle = feature.getPolygonStyle();
        GeoJsonFeature geoJsonFeature = new GeoJsonFeature(geoJsonGeometry, null, null, null);
        geoJsonPolygonStyle.setFillColor(getResources().getColor(R.color.transparent));
        geoJsonFeature.setPolygonStyle(geoJsonPolygonStyle);
        layer.addFeature(geoJsonFeature);
      }

      layer.addLayerToMap();
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    LatLng ygnLatLng = new LatLng(16.8000, 96.1500);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ygnLatLng, 12));
  }
}
