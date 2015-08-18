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
import com.google.maps.android.geojson.GeoJsonPointStyle;
import com.google.maps.android.geojson.GeoJsonPolygonStyle;
import java.io.IOException;
import org.json.JSONException;
import org.maepaysoh.maepaysoh.R;

/**
 * Created by Ye Lin Aung on 15/08/10.
 */
public class LocationDetailActivity extends FragmentActivity {

  private GoogleMap mMap; // Might be null if Google Play services APK is not available.
  private GeoJsonPolygonStyle geoJsonPolygonStyle;
  private GeoJsonFeature geoJsonFeature;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_location_detail);
    setUpMapIfNeeded();
  }

  @Override protected void onResume() {
    super.onResume();
    setUpMapIfNeeded();
  }

  private void setUpMapIfNeeded() {
    // Do a null check to confirm that we have not already instantiated the map.
    if (mMap == null) {
      // Try to obtain the map from the SupportMapFragment.
      mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
          R.id.location_detail_map)).getMap();
      if (mMap != null) {
        setUpMap();
      }
    }
  }

  private void setUpMap() {
    try {
      GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.yangon_west, getApplicationContext());
      GeoJsonPointStyle pointStyle = new GeoJsonPointStyle();
      for (GeoJsonFeature feature : layer.getFeatures()) {
        GeoJsonGeometry geoJsonGeometry = feature.getGeometry();
        geoJsonFeature = new GeoJsonFeature(geoJsonGeometry, null, null, null);
        pointStyle = feature.getPointStyle();
      }

      geoJsonPolygonStyle = layer.getDefaultPolygonStyle();
      geoJsonPolygonStyle.setFillColor(getResources().getColor(R.color.geojson_background_color));
      geoJsonPolygonStyle.setStrokeColor(getResources().getColor(R.color.geojson_stroke_color));
      geoJsonPolygonStyle.setStrokeWidth(2);

      pointStyle.setTitle("Yangon West");
      pointStyle.setSnippet("Yangon West");

      geoJsonFeature.setPointStyle(pointStyle);
      geoJsonFeature.setPolygonStyle(geoJsonPolygonStyle);
      layer.addFeature(geoJsonFeature);

      layer.addLayerToMap();
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    }
    LatLng ygnLatLng = new LatLng(16.8000, 96.1500);
    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ygnLatLng, 12));
  }
}
