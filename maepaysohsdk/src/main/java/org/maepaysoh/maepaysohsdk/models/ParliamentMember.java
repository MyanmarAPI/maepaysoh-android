package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yemyatthu on 9/24/15.
 */
public class ParliamentMember {
  @SerializedName("id")
  @Expose
  private String id;
  @SerializedName("name")
  @Expose
  private Name name;
  @SerializedName("house")
  @Expose
  private House house;
  @SerializedName("constituency")
  @Expose
  private Constituency constituency;
  @SerializedName("party")
  @Expose
  private String party;
  @SerializedName("gender")
  @Expose
  private String gender;

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
   * The name
   */
  public Name getName() {
    return name;
  }

  /**
   *
   * @param name
   * The name
   */
  public void setName(Name name) {
    this.name = name;
  }

  /**
   *
   * @return
   * The house
   */
  public House getHouse() {
    return house;
  }

  /**
   *
   * @param house
   * The house
   */
  public void setHouse(House house) {
    this.house = house;
  }

  /**
   *
   * @return
   * The constituency
   */
  public Constituency getConstituency() {
    return constituency;
  }

  /**
   *
   * @param constituency
   * The constituency
   */
  public void setConstituency(Constituency constituency) {
    this.constituency = constituency;
  }

  /**
   *
   * @return
   * The party
   */
  public String getParty() {
    return party;
  }

  /**
   *
   * @param party
   * The party
   */
  public void setParty(String party) {
    this.party = party;
  }

  /**
   *
   * @return
   * The gender
   */
  public String getGender() {
    return gender;
  }

  /**
   *
   * @param gender
   * The gender
   */
  public void setGender(String gender) {
    this.gender = gender;
  }

}
