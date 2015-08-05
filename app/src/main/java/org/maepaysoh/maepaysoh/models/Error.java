package org.maepaysoh.maepaysoh.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class Error {
  @SerializedName("errors") private Errors error;

  public Errors getError() {
    return error;
  }

  public void setError(Errors error) {
    this.error = error;
  }

  public class Errors {
    private String message;
    private String type;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }
}
