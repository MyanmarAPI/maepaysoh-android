package org.maepaysoh.maepaysohsdk.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yemyatthu on 8/21/15.
 */
public class GeoAPIPropertiesMap {
  private final Map<GeoAPIProperties<?>, Object> properties =
      new HashMap<GeoAPIProperties<?>, Object>();

  public <T> void put(GeoAPIProperties<T> property, T value) {
    properties.put(property, value);
  }

  public String getString(GeoAPIProperties<String> property, String defaultValue) {
    String result = property.propertyClass.cast(properties.get(property));
    if (result == null) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public boolean getBoolean(GeoAPIProperties<Boolean> property, boolean defaultValue) {
    boolean result;
    try {
      result = property.propertyClass.cast(properties.get(property));
    } catch (Exception e) {
      return defaultValue;
    }
    return result;
  }

  public int getInteger(GeoAPIProperties<Integer> property, int defaultValue) {
    try {
      int result = property.propertyClass.cast(properties.get(property));
      if (result == -1) {
        return defaultValue;
      } else {
        return result;
      }
    } catch (Exception e) {
      return defaultValue;
    }
  }

  public <T> T get(GeoAPIProperties<T> property) {
    return property.propertyClass.cast(properties.get(property));
  }

  public <T> GeoAPIPropertiesMap with(GeoAPIProperties<T> property, T value) {
    put(property, value);
    return this;
  }
}
