package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by Ye Lin Aung on 15/08/03.
 */
public class Candidate implements Serializable {

  private String id;
  private String name;
  private String mpid;
  private String gender;
  @SerializedName("photo_url") private String photoUrl;
  private String legislature;
  private Integer birthdate;
  private String education;
  private String occupation;
  private String ethnicity;
  private String religion;
  @SerializedName("ward_village") @Expose private String wardVillage;
  @SerializedName("constituency") @Expose private Constituency constituency;
  @SerializedName("party_id") @Expose private Integer partyId;
  private FatherMother mother;
  private FatherMother father;
  private Party party;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMpid() {
    return mpid;
  }

  public void setMpid(String mpid) {
    this.mpid = mpid;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public String getLegislature() {
    return legislature;
  }

  public void setLegislature(String legislature) {
    this.legislature = legislature;
  }

  public Integer getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Integer birthdate) {
    this.birthdate = birthdate;
  }

  public String getEducation() {
    return education;
  }

  public void setEducation(String education) {
    this.education = education;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getEthnicity() {
    return ethnicity;
  }

  public void setEthnicity(String ethnicity) {
    this.ethnicity = ethnicity;
  }

  public String getReligion() {
    return religion;
  }

  public void setReligion(String religion) {
    this.religion = religion;
  }

  public String getWardVillage() {
    return wardVillage;
  }

  public void setWardVillage(String wardVillage) {
    this.wardVillage = wardVillage;
  }

  public Constituency getConstituency() {
    return constituency;
  }

  public void setConstituency(Constituency constituency) {
    this.constituency = constituency;
  }

  public Integer getPartyId() {
    return partyId;
  }

  public void setPartyId(Integer partyId) {
    this.partyId = partyId;
  }

  public FatherMother getMother() {
    return mother;
  }

  public void setMother(FatherMother mother) {
    this.mother = mother;
  }

  public FatherMother getFather() {
    return father;
  }

  public void setFather(FatherMother father) {
    this.father = father;
  }

  public Party getParty() {
    return party;
  }

  public void setParty(Party party) {
    this.party = party;
  }
}
