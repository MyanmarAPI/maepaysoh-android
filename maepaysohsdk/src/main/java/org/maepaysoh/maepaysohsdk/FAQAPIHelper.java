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
import org.maepaysoh.maepaysohsdk.models.FAQDetailReturnObject;
import org.maepaysoh.maepaysohsdk.models.FAQListReturnObject;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class FAQAPIHelper {
  private RestAdapter  mFaqRestAdapter;
  private FaqService mFaqService;
  private FaqDao mFaqDao;
  private Context mContext;
  protected FAQAPIHelper(String token, Context context){
    mFaqRestAdapter = RetrofitHelper.getResAdapter(token);
    mFaqService = mFaqRestAdapter.create(FaqService.class);
    mContext = context;
  }

  /**
   *
   * @param callback
   */
  public void getFaqsAsync(Callback<FAQListReturnObject> callback){
    boolean unicode = Utils.isUniCode(mContext);
    getFaqsAsync(unicode, 1, 15, callback);
  }

  /**
   * @param unicode
   * @param callback
   */
  public void getFaqsAsync(boolean unicode,Callback<FAQListReturnObject> callback){
    getFaqsAsync(unicode, 1, 15, callback);
  }

  /**
   *
   * @param page
   * @param callback
   */
  public void getFaqsAsync(int page, Callback<FAQListReturnObject> callback){
    boolean unicode = Utils.isUniCode(mContext);
    getFaqsAsync(unicode, page, 15, callback);
  }

  /**
   *
   * @param unicode
   * @param page
   * @param callback
   */
  public void getFaqsAsync(boolean unicode, int page, Callback<FAQListReturnObject> callback){
    getFaqsAsync(unicode, page, callback);
  }

  /**
   *
   * @param unicode
   * @param page
   * @param per_page
   * @param callback
   */
  public void getFaqsAsync(boolean unicode, int page, int per_page, Callback<FAQListReturnObject> callback){
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
    FAQListReturnObject faqListReturnObject = mFaqService.listFaqs(optionParams);
    if(cache) {
      for (FAQ FAQ : faqListReturnObject.getData()) {
        try {
          mFaqDao.createFaq(FAQ);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return faqListReturnObject.getData();
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
    boolean unicode = Utils.isUniCode(mContext);
    return getFaqs(unicode, page, 15, cache);
  }

  /**
   *
   * @param page
   * @param page_number
   * @param cache
   * @return
   */
  public List<FAQ> getFaqs(int page,int page_number,boolean cache){
    boolean unicode = Utils.isUniCode(mContext);
    return getFaqs(unicode,page,page_number,cache);
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
  public void searchFaqsAsync(String keyword, Callback<FAQListReturnObject> callback){
    boolean unicode = Utils.isUniCode(mContext);
    searchFaqsAsync(unicode, keyword, callback);
  }

  /**
   *
   * @param keyword
   * @param callback
   */
  public void searchFaqsAsync(boolean unicode,String keyword, Callback<FAQListReturnObject> callback){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(PARAM_FIELD.q,keyword);
    mFaqService.searchFaqsAsync(optionParams, callback);
  }


  public List<FAQ> searchFaq(String keyword){
    boolean unicode = Utils.isUniCode(mContext);
    return searchFaq(unicode,keyword);
  }

  /**
   *
   * @param keyword
   * @return
   */
  public List<FAQ> searchFaq(boolean unicode,String keyword){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(PARAM_FIELD.q, keyword);
    return mFaqService.searchFaq(optionParams).getData();
  }

  /**
   *
   * @return
   */
  public List<FAQ> getFaqsFromCache(){
    mFaqDao = new FaqDao(mContext);
    return mFaqDao.getAllFaqData();
  }

  /**
   *
   * @param faqId
   * @param callback
   */
  public void getFaqByIdAsync(String faqId, Callback<FAQDetailReturnObject> callback){
    boolean unicode = Utils.isUniCode(mContext);
    getFaqByIdAsync(faqId,unicode,callback);
  }
  /**
   *
   * @param faqId
   * @param unicode
   * @param callback
   */
  public void getFaqByIdAsync(String faqId,boolean unicode,Callback<FAQDetailReturnObject> callback){
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    mFaqService.searchFaqByIdAsync(faqId, optionParams, callback);
  }


  /**
   *
   * @param faqId
   * @param cache
   */
  public FAQ getFaqById(String faqId, boolean cache){
    boolean unicode = Utils.isUniCode(mContext);
    return getFaqById(faqId, unicode, cache);
  }
  /**
   *
   * @param faqId
   * @param unicode
   * @param cache
   */
  public FAQ getFaqById(String faqId,boolean unicode,boolean cache){
    mFaqDao = new FaqDao(mContext);
    Map<PARAM_FIELD,String> optionParams = new HashMap<>();
    if(unicode) {
      optionParams.put(PARAM_FIELD.font, Constants.UNICODE);
    }else{
      optionParams.put(PARAM_FIELD.font, Constants.ZAWGYI);
    }
    FAQDetailReturnObject returnObject = mFaqService.searchFaqById(faqId,optionParams);
    FAQ faq = returnObject.getFAQ();
    if(cache) {
      mFaqDao.createFaq(faq);
    }
    return faq;
  }

  public FAQ getFaqByIdFromCache(String faqId){
    mFaqDao = new FaqDao(mContext);
    return mFaqDao.getFaqById(faqId);
  }


  public enum PARAM_FIELD {
    font, per_page, page, q
  }
}
