package org.maepaysoh.maepaysoh.api;

import org.maepaysoh.maepaysoh.models.Candidate;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface CandidateService {
  @GET("/candidate/list") void listCandidates(Callback<Candidate> candidateCallback);
}
