package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class QuestionReturnObject {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private Name name;
@SerializedName("questions")
@Expose
private List<Question> questions = new ArrayList<Question>();

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
* The questions
*/
public List<Question> getQuestions() {
return questions;
}

/**
* 
* @param questions
* The questions
*/
public void setQuestions(List<Question> questions) {
this.questions = questions;
}

}