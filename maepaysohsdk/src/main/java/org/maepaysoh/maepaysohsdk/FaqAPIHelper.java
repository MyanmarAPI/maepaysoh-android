package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import android.database.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.FaqService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.FaqDao;
import org.maepaysoh.maepaysohsdk.models.FAQ;
import org.maepaysoh.maepaysohsdk.models.FAQReturnObject;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class FaqAPIHelper {
  private RestAdapter  mFaqRestAdapter;
  private FaqService mFaqService;
  private FaqDao mFaqDao;
  private Context mContext;
  protected FaqAPIHelper(String token,Context context){
    mFaqRestAdapter = RetrofitHelper.getResAdapter(token);
    mFaqService = mFaqRestAdapter.create(FaqService.class);
    mContext = context;
  }

  /**
   *
   * @param callback
   */
  public void getFaqsAsync(Callback<FAQReturnObject> callback){
    getFaqsAsync(true, 1, 15, callback);
  }

  /**
   * @param unicode
   * @param callback
   */
  public void getFaqsAsync(boolean unicode,Callback<FAQReturnObject> callback){
    getFaqsAsync(unicode, 1, 15, callback);
  }

  /**
   *
   * @param page
   * @param callback
   */
  public void getFaqsAsync(int page, Callback<FAQReturnObject> callback){
    getFaqsAsync(true, page, 15, callback);
  }

  /**
   *
   * @param unicode
   * @param page
   * @param callback
   */
  public void getFaqsAsync(boolean unicode, int page, Callback<FAQReturnObject> callback){
    getFaqsAsync(unicode, page, callback);
  }

  /**
   *
   * @param unicode
   * @param page
   * @param per_page
   * @param callback
   */
  public void getFaqsAsync(boolean unicode, int page, int per_page, Callback<FAQReturnObject> callback){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(PARAM_FIELD.page,String.valueOf(page));
    optionParams.put(PARAM_FIELD.per_page,String.valueOf(per_page));
    mFaqService.listFaqsAsync(optionParams, callback);
  }

  /**
   *
   * @param unicode
   * @param page
   * @param per_page
   * @param cache
   * @return
   */
  public List<FAQ> getFaqs(boolean unicode, int page, int per_page,boolean cache){
    mFaqDao = new FaqDao(mContext);
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(PARAM_FIELD.page,String.valueOf(page));
    optionParams.put(PARAM_FIELD.per_page,String.valueOf(per_page));
    FAQReturnObject faqReturnObject = mFaqService.listFaqs(optionParams);
    if(cache) {
      for (FAQ FAQ : faqReturnObject.getData()) {
        try {
          mFaqDao.createFaq(FAQ);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return faqReturnObject.getData();
  }

  /**
   *
   * @param unicode
   * @param cache
   * @return
   */
  public List<FAQ> getFaqs(boolean unicode,boolean cache){
    return getFaqs(unicode, 1, 15, cache);
  }

  /**
   *
   * @param page
   * @param cache
   * @return
   */
  public List<FAQ> getFaqs(int page,boolean cache){
    return getFaqs(true,page,15,cache);
  }

  /**
   *
   * @param page
   * @param page_number
   * @param cache
   * @return
   */
  public List<FAQ> getFaqs(int page,int page_number,boolean cache){
    return getFaqs(true,page,page_number,cache);
  }

  /**
   *
   * @param unicode
   * @param page
   * @param cache
   * @return
   */
  public List<FAQ> getFaqs(boolean unicode,int page,boolean cache){
    return getFaqs(unicode,page,15,cache);
  }

  /**
   *
   * @param keyword
   * @param callback
   */
  public void searchFaqsAsync(String keyword, Callback<FAQReturnObject> callback){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    optionParams.put(PARAM_FIELD.q,keyword);
    mFaqService.searchFaqsAsync(optionParams, callback);
  }

  public List<FAQ> searchFaq(String keyword){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    optionParams.put(PARAM_FIELD.q,keyword);
    return mFaqService.searchFaq(optionParams).getData();
  }

  public enum PARAM_FIELD {
    font, per_page, page, q
  }
}
