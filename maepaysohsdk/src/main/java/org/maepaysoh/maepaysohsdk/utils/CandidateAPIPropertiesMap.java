package org.maepaysoh.maepaysohsdk.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yemyatthu on 8/20/15.
 */
public class CandidateAPIPropertiesMap {
  private final Map<CandidateAPIProperties<?>, Object> properties =
      new HashMap<CandidateAPIProperties<?>, Object>();

  public <T> void put(CandidateAPIProperties<T> property, T value) {
    properties.put(property, value);
  }

  public String getString(CandidateAPIProperties<String> property, String defaultValue) {
    String result = property.propertyClass.cast(properties.get(property));
    if (result == null) {
      return defaultValue;
    } else {
      return result;
    }
  }

  public boolean getBoolean(CandidateAPIProperties<Boolean> property, boolean defaultValue) {
    boolean result;
    try {
      result = property.propertyClass.cast(properties.get(property));
    } catch (Exception e) {
      return defaultValue;
    }
    return result;
  }

  public int getInteger(CandidateAPIProperties<Integer> property, int defaultValue) {
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

  public <T> T get(CandidateAPIProperties<T> property) {
    return property.propertyClass.cast(properties.get(property));
  }

  public <T> CandidateAPIPropertiesMap with(CandidateAPIProperties<T> property, T value) {
    put(property, value);
    return this;
  }
}
