package org.maepaysoh.maepaysohsdk.models;

/**
 * Created by yemyatthu on 9/24/15.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ParliamentConstituency {

  @SerializedName("english")
  @Expose
  private String english;
  @SerializedName("myanmar")
  @Expose
  private String myanmar;

  /**
   *
   * @return
   * The english
   */
  public String getEnglish() {
    return english;
  }

  /**
   *
   * @param english
   * The english
   */
  public void setEnglish(String english) {
    this.english = english;
  }

  /**
   *
   * @return
   * The myanmar
   */
  public String getMyanmar() {
    return myanmar;
  }

  /**
   *
   * @param myanmar
   * The myanmar
   */
  public void setMyanmar(String myanmar) {
    this.myanmar = myanmar;
  }

}
