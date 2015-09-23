package org.maepaysoh.maepaysohsdk.models;

import java.io.Serializable;

public class Father implements Serializable {
  private String name;
  private String religion;

  public Father() {
  }

  /**
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return The religion
   */
  public String getReligion() {
    return religion;
  }

  /**
   * @param religion The religion
   */
  public void setReligion(String religion) {
    this.religion = religion;
  }
}
