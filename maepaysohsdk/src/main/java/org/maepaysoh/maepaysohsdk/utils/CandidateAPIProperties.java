package org.maepaysoh.maepaysohsdk.utils;

/**
 * Created by yemyatthu on 8/20/15.
 */
public abstract class CandidateAPIProperties<T> {
  public static final CandidateAPIProperties<String> GENDER =
      new CandidateAPIProperties<String>(String.class) {
      };
  public static final CandidateAPIProperties<String> RELIGION =
      new CandidateAPIProperties<String>(String.class) {
      };
  public static final CandidateAPIProperties<String> LEGISLATURE =
      new CandidateAPIProperties<String>(String.class) {
      };
  public static final CandidateAPIProperties<Boolean> WITH_PARTY =
      new CandidateAPIProperties<Boolean>(Boolean.class) {
      };
  public static final CandidateAPIProperties<Boolean> IS_UNICODE =
      new CandidateAPIProperties<Boolean>(Boolean.class) {
      };
  public static final CandidateAPIProperties<Integer> FIRST_PAGE =
      new CandidateAPIProperties<Integer>(Integer.class) {
      };
  public static final CandidateAPIProperties<Integer> PER_PAGE =
      new CandidateAPIProperties<Integer>(Integer.class) {
      };
  public static final CandidateAPIProperties<Boolean> CACHE =
      new CandidateAPIProperties<Boolean>(Boolean.class) {
      };

  public final Class<T> propertyClass;

  private CandidateAPIProperties(Class<T> propertyClass) {
    this.propertyClass = propertyClass;
  }
}
