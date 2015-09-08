package org.maepaysoh.maepaysohsdk.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yemyatthu on 8/7/15.
 */
public class JsonUtils {

  public static String convertToJson(List data) {
    Gson gson = new Gson();
    return gson.toJson(data);
  }

  public static List convertToJava(String jsonString, Type type) {
    List data;
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    data = gson.fromJson(jsonString, type);
    return data;
  }
}
