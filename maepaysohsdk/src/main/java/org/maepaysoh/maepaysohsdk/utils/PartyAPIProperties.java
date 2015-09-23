package org.maepaysoh.maepaysohsdk.utils;

/**
 * Created by yemyatthu on 8/21/15.
 */
public class PartyAPIProperties<T> {
  public static final PartyAPIProperties<Boolean> IS_UNICODE =
      new PartyAPIProperties<Boolean>(Boolean.class) {
      };
  public static final PartyAPIProperties<Boolean> CACHE =
      new PartyAPIProperties<Boolean>(Boolean.class) {
      };

  public static final PartyAPIProperties<Integer> FIRST_PAGE =
      new PartyAPIProperties<Integer>(Integer.class) {
      };
  public static final PartyAPIProperties<Integer> PER_PAGE =
      new PartyAPIProperties<Integer>(Integer.class) {
      };
  public final Class<T> propertyClass;

  public PartyAPIProperties(Class<T> aClass) {
    propertyClass = aClass;
  }
}
