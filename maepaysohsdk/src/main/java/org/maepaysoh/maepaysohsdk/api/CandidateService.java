package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.models.CandidateDetailReturnObject;
import org.maepaysoh.maepaysohsdk.models.CandidateListReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface CandidateService {
  @GET("/candidate/list") void listCandidatesAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<CandidateListReturnObject> candidateCallback);

  @GET("/candidate/{candidate_id}") void getCandidateByIdAsync(
      @Path("candidate_id") String candidateId, @QueryMap Map<PARAM_FIELD, String> options,
      Callback<CandidateDetailReturnObject> candidateCallback);

  @GET("/candidate/list") CandidateListReturnObject listCandidates(
      @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/candidates/{candidate_id}") CandidateDetailReturnObject getCandidateById(
      @Path("candidate_id") String candidateId, @QueryMap Map<PARAM_FIELD, String> options);

  @GET("/candidate/search") void
  searchCandidateByNameAsync(@Query("q") String name,@QueryMap Map<PARAM_FIELD, String> options,
      Callback<CandidateListReturnObject> candidateCallback);

  @GET("/candidate/search") CandidateListReturnObject
  searchCandidateByName(@Query("q") String name,@QueryMap Map<PARAM_FIELD, String> options);


  enum PARAM_FIELD {
    _with,
    font,
    per_page,
    page,
    gender,
    fields,
    religion,
    legislature,
    party,
    constituency_st_pcode,
    constituency_dt_pcode,
    constituency_ts_pcode,
    constituency_am_pcode,
    constituency_name,
    constituency_number
  }
}
