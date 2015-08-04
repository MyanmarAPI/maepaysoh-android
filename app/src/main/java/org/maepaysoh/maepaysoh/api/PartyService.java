package org.maepaysoh.maepaysoh.api;

import org.maepaysoh.maepaysoh.models.Party;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface PartyService {
  @GET("/party") void listParties(Callback<Party> partyCallback);
}
