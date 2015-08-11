package com.yemyatthu.maepaesohsdk.api;

import com.yemyatthu.maepaesohsdk.models.FAQ;
import java.util.Map;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/6/15.
 */
public interface FaqService {
  @GET("/faq/list") void listFaqs(@QueryMap Map<PARAM_TYPE, String> options,
      Callback<FAQ> faqCallback);

  @GET("/faq/search") void searchFaqs(@QueryMap Map<PARAM_TYPE, String> options,
      Callback<FAQ> faqCallback);

  @GET("/faq/{faq_id}") void searchFaqById(@Path("faq_id") String faqId,
      @QueryMap Map<PARAM_TYPE, String> options, Callback<FAQ> faqCallback);

  enum PARAM_TYPE {
    font, per_page, page, q
  }
}
