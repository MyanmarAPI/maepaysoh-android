package org.maepaysoh.maepaysoh.models;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class PartyMetaData {
  private String status;
  private int count;
  private int api_verison;
  private boolean unicode;
  private String format;

  public boolean isUnicode() {
    return unicode;
  }

  public void setUnicode(boolean unicode) {
    this.unicode = unicode;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getApi_verison() {
    return api_verison;
  }

  public void setApi_verison(int api_verison) {
    this.api_verison = api_verison;
  }
}

