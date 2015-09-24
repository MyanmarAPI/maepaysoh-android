package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Respondent {

@SerializedName("name")
@Expose
private String name;
@SerializedName("position")
@Expose
private String position;

/**
* 
* @return
* The name
*/
public String getName() {
return name;
}

/**
* 
* @param name
* The name
*/
public void setName(String name) {
this.name = name;
}

/**
* 
* @return
* The position
*/
public String getPosition() {
return position;
}

/**
* 
* @param position
* The position
*/
public void setPosition(String position) {
this.position = position;
}

}