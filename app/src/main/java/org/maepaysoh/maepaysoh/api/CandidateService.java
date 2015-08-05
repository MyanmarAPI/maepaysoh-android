package org.maepaysoh.maepaysoh.api;

import java.util.Map;
import org.maepaysoh.maepaysoh.models.Candidate;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public interface CandidateService {
  enum PARAM_TYPE{
    _with, font, per_page,page
  }
  @GET("/candidate/list") void listCandidates(@QueryMap Map<PARAM_TYPE,String> options,
      Callback<Candidate> candidateCallback);
}
