package org.maepaysoh.maepaysohsdk.utils;

/**
 * Created by yemyatthu on 8/21/15.
 */
public class FaqAPIProperties<T> {
  public static final FaqAPIProperties<Boolean> IS_UNICODE =
      new FaqAPIProperties<Boolean>(Boolean.class) {
      };
  public static final FaqAPIProperties<Integer> FIRST_PAGE =
      new FaqAPIProperties<Integer>(Integer.class) {
      };
  public static final FaqAPIProperties<Integer> PER_PAGE =
      new FaqAPIProperties<Integer>(Integer.class) {
      };
  public static final FaqAPIProperties<Boolean> CACHE =
      new FaqAPIProperties<Boolean>(Boolean.class) {
      };
  public final Class<T> propertyClass;

  private FaqAPIProperties(Class<T> propertyClass) {
    this.propertyClass = propertyClass;
  }
}
