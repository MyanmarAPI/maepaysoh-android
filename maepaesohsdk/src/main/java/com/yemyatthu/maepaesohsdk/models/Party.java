package com.yemyatthu.maepaesohsdk.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class Party {
  @SerializedName("_meta") private PartyMetaData meta;

  private List<PartyData> data;

  public List<PartyData> getData() {
    return data;
  }

  public void setData(List<PartyData> data) {
    this.data = data;
  }

  public PartyMetaData getMeta() {
    return meta;
  }

  public void setMeta(PartyMetaData meta) {
    this.meta = meta;
  }
}
