package org.maepaysoh.maepaysohsdk.models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Geometry implements Serializable{

@Expose
private String type;
@Expose
private List<List<List<Double>>> coordinates = new ArrayList<List<List<Double>>>();

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
public List<List<List<Double>>> getCoordinates() {
return coordinates;
}

/**
*
 * @param coordinates
 * The coordinates
 */
public void setCoordinates(List<List<List<Double>>> coordinates) {
this.coordinates = coordinates;
}

}