package com.yemyatthu.maepaesohsdk;

import com.yemyatthu.maepaesohsdk.api.CandidateService;
import com.yemyatthu.maepaesohsdk.api.RetrofitHelper;
import com.yemyatthu.maepaesohsdk.models.Candidate;
import java.util.HashMap;
import java.util.Map;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class CandidateAPIHelper{
  private RestAdapter mCandidateRestAdapter;
  private CandidateService mCandidateService;
  public CandidateAPIHelper(String token){
    mCandidateRestAdapter = RetrofitHelper.getResAdapter(token);
    mCandidateService = mCandidateRestAdapter.create(CandidateService.class);
  }

  /**
   *
   * @param callback
   */
  public void getCandidates(Callback<Candidate> callback){
    getCandidates(false,true,1,15,callback);
  }

  /**
   *
   * @param withParty
   * @param callback
   */
  public void getCandidates(Boolean withParty,Callback<Candidate> callback){
    getCandidates(withParty,true,1,15,callback);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param callback
   */
  public void getCandidates(Boolean withParty,Boolean unicode,Callback<Candidate> callback){
    getCandidates(withParty,unicode,1,15,callback);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param firstPage
   * @param callback
   */
  public void getCandidates(Boolean withParty,Boolean unicode,int firstPage,Callback<Candidate> callback){
    getCandidates(withParty, unicode, firstPage, 15, callback);
  }

  /**
   *
   * @param withParty
   * @param unicode
   * @param firstPage
   * @param perPage
   * @param callback
   */
  public void getCandidates(Boolean withParty,Boolean unicode,int firstPage,int perPage,Callback<Candidate> callback){
    Map<CandidateService.PARAM_FIELD,String> optionParams = new HashMap<>();
    if(withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if(unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(CandidateService.PARAM_FIELD.page,String.valueOf(firstPage));
    optionParams.put(CandidateService.PARAM_FIELD.per_page,String.valueOf(perPage));
    mCandidateService.listCandidates(optionParams,callback);
  }

  public void getCandidateById(String candidateId,Boolean withParty,Boolean unicode,Callback<Candidate> callback){
    Map<CandidateService.PARAM_FIELD,String> optionParams = new HashMap<>();
    if(withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if(unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    mCandidateService.getCandidateById(candidateId,optionParams,callback);
  }

}
