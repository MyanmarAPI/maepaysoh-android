package org.maepaysoh.maepaysohsdk.api;

import org.maepaysoh.maepaysohsdk.models.Party;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface PartyService {
  @GET("/party") void listPartiesAsync(Callback<Party> partyCallback);

  @GET("/party") Party listParties();
}
