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
import org.maepaysoh.maepaysohsdk.utils.FaqAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.FaqAPIPropertiesMap;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class FAQAPIHelper {
  private RestAdapter mFaqRestAdapter;
  private FaqService mFaqService;
  private FaqDao mFaqDao;
  private Context mContext;

  protected FAQAPIHelper(String token, Context context) {
    mFaqRestAdapter = RetrofitHelper.getResAdapter(token);
    mFaqService = mFaqRestAdapter.create(FaqService.class);
    mContext = context;
  }

  /**
   * @param callback
   */
  public void getFaqsAsync(Callback<FAQListReturnObject> callback) {
    getFaqsAsync(new FaqAPIPropertiesMap(), callback);
  }

  /**
   *
   * @param faqAPIPropertiesMap
   * @param callback
   */
  public void getFaqsAsync(FaqAPIPropertiesMap faqAPIPropertiesMap,
      Callback<FAQListReturnObject> callback) {
    boolean unicode =
        faqAPIPropertiesMap.getBoolean(FaqAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    int page = faqAPIPropertiesMap.getInteger(FaqAPIProperties.FIRST_PAGE, 1);
    int per_page = faqAPIPropertiesMap.getInteger(FaqAPIProperties.PER_PAGE, 15);
    Map<FaqService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (unicode) {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(FaqService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(FaqService.PARAM_FIELD.per_page, String.valueOf(per_page));
    mFaqService.listFaqsAsync(optionParams, callback);
  }

  /**
   *
   * @param faqAPIPropertiesMap
   * @return
   */
  public List<FAQ> getFaqs(FaqAPIPropertiesMap faqAPIPropertiesMap) {
    boolean unicode =
        faqAPIPropertiesMap.getBoolean(FaqAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    int page = faqAPIPropertiesMap.getInteger(FaqAPIProperties.FIRST_PAGE, 1);
    int per_page = faqAPIPropertiesMap.getInteger(FaqAPIProperties.PER_PAGE, 15);
    boolean cache = faqAPIPropertiesMap.getBoolean(FaqAPIProperties.CACHE, true);
    mFaqDao = new FaqDao(mContext);
    Map<FaqService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (unicode) {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(FaqService.PARAM_FIELD.page, String.valueOf(page));
    optionParams.put(FaqService.PARAM_FIELD.per_page, String.valueOf(per_page));
    FAQListReturnObject faqListReturnObject = mFaqService.listFaqs(optionParams);
    if (cache) {
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

  public List<FAQ> getFaqs() {
    return getFaqs(new FaqAPIPropertiesMap());
  }

  /**
   *
   * @param keyword
   * @param callback
   */
  public void searchFaqsAsync(String keyword, Callback<FAQListReturnObject> callback) {
    boolean unicode = Utils.isUniCode(mContext);
    searchFaqsAsync(keyword, new FaqAPIPropertiesMap(), callback);
  }

  /**
   *
   * @param keyword
   * @param callback
   */
  public void searchFaqsAsync(String keyword, FaqAPIPropertiesMap faqAPIPropertiesMap,
      Callback<FAQListReturnObject> callback) {
    boolean unicode =
        faqAPIPropertiesMap.getBoolean(FaqAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    Map<FaqService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (unicode) {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(FaqService.PARAM_FIELD.q, keyword);
    mFaqService.searchFaqsAsync(optionParams, callback);
  }

  public List<FAQ> searchFaq(String keyword) {
    boolean unicode = Utils.isUniCode(mContext);
    return searchFaq(keyword, new FaqAPIPropertiesMap());
  }

  /**
   *
   * @param keyword
   * @return
   */
  public List<FAQ> searchFaq(String keyword, FaqAPIPropertiesMap faqAPIPropertiesMap) {
    boolean unicode =
        faqAPIPropertiesMap.getBoolean(FaqAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    Map<FaqService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (unicode) {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(FaqService.PARAM_FIELD.q, keyword);
    return mFaqService.searchFaq(optionParams).getData();
  }

  public List<FAQ> searchFaqFromCache(String keyword) {
    mFaqDao = new FaqDao(mContext);
    try {
      return mFaqDao.searchFAQsFromDb(keyword);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   *
   * @return
   */
  public List<FAQ> getFaqsFromCache() {
    mFaqDao = new FaqDao(mContext);
    return mFaqDao.getAllFaqData();
  }

  /**
   *
   * @param faqId
   * @param callback
   */
  public void getFaqByIdAsync(String faqId, Callback<FAQDetailReturnObject> callback) {
    boolean unicode = Utils.isUniCode(mContext);
    getFaqByIdAsync(faqId, new FaqAPIPropertiesMap(), callback);
  }

  /**
   *
   * @param faqId
   * @param callback
   */
  public void getFaqByIdAsync(String faqId, FaqAPIPropertiesMap faqAPIPropertiesMap,
      Callback<FAQDetailReturnObject> callback) {
    boolean unicode =
        faqAPIPropertiesMap.getBoolean(FaqAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    Map<FaqService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (unicode) {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    mFaqService.searchFaqByIdAsync(faqId, optionParams, callback);
  }

  /**
   *
   * @param faqId
   */
  public FAQ getFaqById(String faqId) {
    boolean unicode = Utils.isUniCode(mContext);
    return getFaqById(faqId, new FaqAPIPropertiesMap());
  }

  /**
   *
   * @param faqId
   */
  public FAQ getFaqById(String faqId, FaqAPIPropertiesMap faqAPIPropertiesMap) {
    boolean unicode =
        faqAPIPropertiesMap.getBoolean(FaqAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    boolean cache = faqAPIPropertiesMap.getBoolean(FaqAPIProperties.CACHE, true);
    mFaqDao = new FaqDao(mContext);
    Map<FaqService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (unicode) {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(FaqService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    FAQDetailReturnObject returnObject = mFaqService.searchFaqById(faqId, optionParams);
    FAQ faq = returnObject.getFAQ();
    if (cache) {
      mFaqDao.createFaq(faq);
    }
    return faq;
  }
}
