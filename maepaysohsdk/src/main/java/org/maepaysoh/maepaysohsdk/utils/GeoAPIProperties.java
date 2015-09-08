package org.maepaysoh.maepaysohsdk.utils;

/**
 * Created by yemyatthu on 8/21/15.
 */
public class GeoAPIProperties<T> {
  public static final GeoAPIProperties<Boolean> NO_GEO =
      new GeoAPIProperties<Boolean>(Boolean.class) {
      };
  public static final GeoAPIProperties<Integer> FIRST_PAGE =
      new GeoAPIProperties<Integer>(Integer.class) {
      };
  public static final GeoAPIProperties<Integer> PER_PAGE =
      new GeoAPIProperties<Integer>(Integer.class) {
      };
  public static final GeoAPIProperties<Boolean> CACHE =
      new GeoAPIProperties<Boolean>(Boolean.class) {
      };
  public final Class<T> propertyClass;

  private GeoAPIProperties(Class<T> propertyClass) {
    this.propertyClass = propertyClass;
  }
}
