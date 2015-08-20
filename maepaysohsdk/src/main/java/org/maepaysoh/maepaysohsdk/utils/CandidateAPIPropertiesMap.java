package org.maepaysoh.maepaysohsdk.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yemyatthu on 8/20/15.
 */
public class CandidateAPIPropertiesMap {
  private final Map<CandidateAPIProperties<?>, Object> properties = new HashMap<CandidateAPIProperties<?>, Object>();

  public <T> void put(CandidateAPIProperties<T> property, T value) {
    properties.put(property, value);
  }
  public <T> T get(CandidateAPIProperties<T> property) {
    return property.propertyClass.cast(properties.get(property));
  }

  public <T> CandidateAPIPropertiesMap with(CandidateAPIProperties<T> property, T value) {
    put(property, value);
    return this;
  }
}
