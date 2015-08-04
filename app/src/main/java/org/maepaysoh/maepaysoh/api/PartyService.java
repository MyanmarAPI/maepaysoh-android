package org.maepaysoh.maepaysoh.api;

import org.maepaysoh.maepaysoh.models.PartyReturnObject;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface PartyService {
  @GET("/party")
  void listParties(
      Callback<PartyReturnObject> partyCallback
  );
}
