package com.maepaesoh.maepaesohsdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FAQ {
  private List<com.maepaesoh.maepaesohsdk.models.FaqDatum> data = new ArrayList<>();
  @SerializedName("_meta") private com.maepaesoh.maepaesohsdk.models.FaqMeta meta;

  /**
   * @return The data
   */
  public List<com.maepaesoh.maepaesohsdk.models.FaqDatum> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<com.maepaesoh.maepaesohsdk.models.FaqDatum> data) {
    this.data = data;
  }

  /**
   * @return The meta
   */
  public com.maepaesoh.maepaesohsdk.models.FaqMeta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(com.maepaesoh.maepaesohsdk.models.FaqMeta meta) {
    this.meta = meta;
  }
}
