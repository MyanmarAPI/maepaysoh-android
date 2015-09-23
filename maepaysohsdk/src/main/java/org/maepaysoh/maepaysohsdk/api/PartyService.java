package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.models.PartyListReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface PartyService {
  @GET("/party") void listPartiesAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<PartyListReturnObject> partyCallback);

  @GET("/party") PartyListReturnObject listParties(@QueryMap Map<PARAM_FIELD, String> options);

  enum PARAM_FIELD {
    font,
    per_page,
    page
  }
}
