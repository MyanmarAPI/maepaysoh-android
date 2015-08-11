package org.maepaesoh.maepaesohsdk.api;

import org.maepaesoh.maepaesohsdk.models.Candidate;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface CandidateService {
  @GET("/candidate/list") void listCandidates(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<Candidate> candidateCallback);

  @GET("/candidate/{candidate_id}") void getCandidateById(@Path("candidate_id") String candidateId,
      @QueryMap Map<PARAM_FIELD, String> options, Callback<Candidate> candidateCallback);

  enum PARAM_FIELD {
    _with, font, per_page, page
  }
}
