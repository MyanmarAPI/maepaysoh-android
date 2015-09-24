package org.maepaysoh.maepaysohsdk.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Question {

  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("house")
  @Expose
  private String house;
  @SerializedName("session")
  @Expose
  private String session;
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("description")
  @Expose
  private Description description;
  @SerializedName("issue")
  @Expose
  private String issue;
  @SerializedName("respondent")
  @Expose
  private Respondent respondent;

  /**
   *
   * @return
   * The id
   */
  public String getId() {
    return id;
  }

  /**
   *
   * @param id
   * The id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   *
   * @return
   * The house
   */
  public String getHouse() {
    return house;
  }

  /**
   *
   * @param house
   * The house
   */
  public void setHouse(String house) {
    this.house = house;
  }

  /**
   *
   * @return
   * The session
   */
  public String getSession() {
    return session;
  }

  /**
   *
   * @param session
   * The session
   */
  public void setSession(String session) {
    this.session = session;
  }

  /**
   *
   * @return
   * The date
   */
  public String getDate() {
    return date;
  }

  /**
   *
   * @param date
   * The date
   */
  public void setDate(String date) {
    this.date = date;
  }

  /**
   *
   * @return
   * The description
   */
  public Description getDescription() {
    return description;
  }

  /**
   *
   * @param description
   * The description
   */
  public void setDescription(Description description) {
    this.description = description;
  }

  /**
   *
   * @return
   * The issue
   */
  public String getIssue() {
    return issue;
  }

  /**
   *
   * @param issue
   * The issue
   */
  public void setIssue(String issue) {
    this.issue = issue;
  }

  /**
   *
   * @return
   * The respondent
   */
  public Respondent getRespondent() {
    return respondent;
  }

  /**
   *
   * @param respondent
   * The respondent
   */
  public void setRespondent(Respondent respondent) {
    this.respondent = respondent;
  }

}