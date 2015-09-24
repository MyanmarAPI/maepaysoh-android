package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Motion {

@SerializedName("id")
@Expose
private String id;
@SerializedName("source")
@Expose
private Source source;
@SerializedName("submitted_date")
@Expose
private String submittedDate;
@SerializedName("house")
@Expose
private String house;
@SerializedName("session")
@Expose
private String session;
@SerializedName("description")
@Expose
private Description description;
@SerializedName("issue")
@Expose
private String issue;
@SerializedName("purpose")
@Expose
private String purpose;
@SerializedName("status")
@Expose
private String status;
@SerializedName("response_date")
@Expose
private String responseDate;
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
* The source
*/
public Source getSource() {
return source;
}

/**
* 
* @param source
* The source
*/
public void setSource(Source source) {
this.source = source;
}

/**
* 
* @return
* The submittedDate
*/
public String getSubmittedDate() {
return submittedDate;
}

/**
* 
* @param submittedDate
* The submitted_date
*/
public void setSubmittedDate(String submittedDate) {
this.submittedDate = submittedDate;
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
* The purpose
*/
public String getPurpose() {
return purpose;
}

/**
* 
* @param purpose
* The purpose
*/
public void setPurpose(String purpose) {
this.purpose = purpose;
}

/**
* 
* @return
* The status
*/
public String getStatus() {
return status;
}

/**
* 
* @param status
* The status
*/
public void setStatus(String status) {
this.status = status;
}

/**
* 
* @return
* The responseDate
*/
public String getResponseDate() {
return responseDate;
}

/**
* 
* @param responseDate
* The response_date
*/
public void setResponseDate(String responseDate) {
this.responseDate = responseDate;
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