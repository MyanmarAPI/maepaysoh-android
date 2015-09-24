package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

/**
 * Created by yemyatthu on 9/23/15.
 */
public class GeoDetailReturnObject implements Serializable {
  @Expose private Geo data;

  public Geo getData() {
    return data;
  }

  public void setData(Geo data) {
    this.data = data;
  }
}
