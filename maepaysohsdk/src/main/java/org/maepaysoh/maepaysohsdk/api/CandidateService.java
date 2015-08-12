package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.models.CandidateReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface CandidateService {
  @GET("/candidate/list") void listCandidatesAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<CandidateReturnObject> candidateCallback);

  @GET("/candidate/{candidate_id}") void getCandidateByIdAsync(
      @Path("candidate_id") String candidateId, @QueryMap Map<PARAM_FIELD, String> options,
      Callback<CandidateReturnObject> candidateCallback);

  @GET("/candidate/list") CandidateReturnObject listCandidates(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/candidates/{candidate_id}") CandidateReturnObject getCandidateById(   @Path("candidate_id") String candidateId, @QueryMap Map<PARAM_FIELD, String> options);
  enum PARAM_FIELD {
    _with, font, per_page, page
  }
}
