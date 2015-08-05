package org.maepaysoh.maepaysoh.api;

import org.maepaysoh.maepaysoh.models.Candidate;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface CandidateService {
  @GET("/candidate/list") void listCandidates(@Query("_with") String with,@Query("font") String font,@Query("per_page") String perpage,
      Callback<Candidate> candidateCallback);
}
