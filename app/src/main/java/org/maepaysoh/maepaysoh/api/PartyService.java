package org.maepaysoh.maepaysoh.api;

import java.util.List;
import org.maepaysoh.maepaysoh.models.Party;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface PartyService {
  @GET("/party")
  void listParties(
      @Query("token") String token,
      Callback<List<Party>> partyCallback
  );
}
