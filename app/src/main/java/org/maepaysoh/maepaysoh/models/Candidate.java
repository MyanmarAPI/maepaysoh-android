package org.maepaysoh.maepaysoh.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class Candidate {
  private List<CandidateData> data = new ArrayList<CandidateData>();
  private CandidateMeta meta;

  public Candidate() {
  }

  /**
   * @return The data
   */
  public List<CandidateData> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<CandidateData> data) {
    this.data = data;
  }

  /**
   * @return The meta
   */
  public CandidateMeta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(CandidateMeta meta) {
    this.meta = meta;
  }
}
