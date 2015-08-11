package com.maepaesoh.maepaesohsdk.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class Candidate{
  private List<com.maepaesoh.maepaesohsdk.models.CandidateData> data = new ArrayList<com.maepaesoh.maepaesohsdk.models.CandidateData>();
  private com.maepaesoh.maepaesohsdk.models.CandidateMeta meta;

  public Candidate() {
  }

  /**
   * @return The data
   */
  public List<com.maepaesoh.maepaesohsdk.models.CandidateData> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<com.maepaesoh.maepaesohsdk.models.CandidateData> data) {
    this.data = data;
  }

  /**
   * @return The meta
   */
  public com.maepaesoh.maepaesohsdk.models.CandidateMeta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(com.maepaesoh.maepaesohsdk.models.CandidateMeta meta) {
    this.meta = meta;
  }
}
