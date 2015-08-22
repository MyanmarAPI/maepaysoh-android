package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;

public class TokenData {

  @Expose private String token;

  /**
   * @return The token
   */
  public String getToken() {
    return token;
  }

  /**
   * @param token The token
   */
  public void setToken(String token) {
    this.token = token;
  }
}