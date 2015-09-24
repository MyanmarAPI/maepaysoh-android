package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class MotionReturnObject {

@SerializedName("id")
@Expose
private String id;
@SerializedName("name")
@Expose
private Name name;
@SerializedName("motions")
@Expose
private List<Motion> motions = new ArrayList<Motion>();

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
* The motions
*/
public List<Motion> getMotions() {
return motions;
}

/**
* 
* @param motions
* The motions
*/
public void setMotions(List<Motion> motions) {
this.motions = motions;
}

}