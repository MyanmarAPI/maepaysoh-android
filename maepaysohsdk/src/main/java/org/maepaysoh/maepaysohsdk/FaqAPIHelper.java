package org.maepaysoh.maepaysohsdk;

import java.util.HashMap;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.FaqService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.models.FAQ;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class FaqAPIHelper {
  private RestAdapter  mFaqRestAdapter;
  private FaqService mFaqService;
  public FaqAPIHelper(String token){
    mFaqRestAdapter = RetrofitHelper.getResAdapter(token);
    mFaqService = mFaqRestAdapter.create(FaqService.class);
  }

  public void getFaqs(Callback<FAQ> callback){
    getFaqs(true,1,15,callback);
  }


  public void getFaqs(int page,Callback<FAQ> callback){
    getFaqs(true,page,15,callback);
  }

  public void getFaqs(boolean unicode,int page,Callback<FAQ> callback){
    getFaqs(unicode,page,callback);
  }

  public void getFaqs(boolean unicode, int page,int per_page,Callback<FAQ> callback){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(PARAM_FIELD.page,String.valueOf(page));
    optionParams.put(PARAM_FIELD.per_page,String.valueOf(per_page));
    mFaqService.listFaqs(optionParams,callback);
  }

  public enum PARAM_FIELD {
    font, per_page, page, q
  }
}
