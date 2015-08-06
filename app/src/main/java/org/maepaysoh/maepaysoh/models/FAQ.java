package org.maepaysoh.maepaysoh.models;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ye Lin Aung on 15/08/06.
 */
public class FAQ {
  private List<FaqDatum> data = new ArrayList<>();
  @SerializedName("_meta") private FaqMeta meta;

  /**
   * @return The data
   */
  public List<FaqDatum> getData() {
    return data;
  }

  /**
   * @param data The data
   */
  public void setData(List<FaqDatum> data) {
    this.data = data;
  }

  /**
   * @return The meta
   */
  public FaqMeta getMeta() {
    return meta;
  }

  /**
   * @param meta The meta
   */
  public void setMeta(FaqMeta meta) {
    this.meta = meta;
  }
}
