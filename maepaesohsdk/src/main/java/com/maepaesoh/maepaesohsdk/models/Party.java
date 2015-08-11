package com.maepaesoh.maepaesohsdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class Party {
  @SerializedName("_meta") private com.maepaesoh.maepaesohsdk.models.PartyMetaData meta;

  private List<com.maepaesoh.maepaesohsdk.models.PartyData> data;

  public List<com.maepaesoh.maepaesohsdk.models.PartyData> getData() {
    return data;
  }

  public void setData(List<com.maepaesoh.maepaesohsdk.models.PartyData> data) {
    this.data = data;
  }

  public com.maepaesoh.maepaesohsdk.models.PartyMetaData getMeta() {
    return meta;
  }

  public void setMeta(com.maepaesoh.maepaesohsdk.models.PartyMetaData meta) {
    this.meta = meta;
  }
}
