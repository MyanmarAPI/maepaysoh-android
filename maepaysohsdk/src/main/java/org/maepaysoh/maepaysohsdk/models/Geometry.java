package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

public class Geometry {

@Expose
private String type;
@Expose
private List<List<List<List<Double>>>> coordinates = new ArrayList<List<List<List<Double>>>>();

/**
* 
* @return
* The type
*/
public String getType() {
return type;
}

/**
* 
* @param type
* The type
*/
public void setType(String type) {
this.type = type;
}

/**
* 
* @return
* The coordinates
*/
public List<List<List<List<Double>>>> getCoordinates() {
return coordinates;
}

/**
* 
* @param coordinates
* The coordinates
*/
public void setCoordinates(List<List<List<List<Double>>>> coordinates) {
this.coordinates = coordinates;
}

}