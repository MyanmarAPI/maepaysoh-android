package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.models.FAQDetailReturnObject;
import org.maepaysoh.maepaysohsdk.models.FAQListReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/6/15.
 */
public interface FaqService {
  @GET("/faq/list") void listFaqsAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<FAQListReturnObject> faqCallback);

  @GET("/faq/search") void searchFaqsAsync(@QueryMap Map<PARAM_FIELD, String> options,
      Callback<FAQListReturnObject> faqCallback);

  @GET("/faq/{faq_id}") void searchFaqByIdAsync(@Path("faq_id") String faqId,
      @QueryMap Map<PARAM_FIELD, String> options, Callback<FAQDetailReturnObject> faqCallback);

  @GET("/faq/list") FAQListReturnObject listFaqs(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/faq/search") FAQListReturnObject searchFaq(@QueryMap Map<PARAM_FIELD, String> options);

  @GET("/faq/{faq_id}") FAQDetailReturnObject searchFaqById(@Path("faq_id") String faqId,
      @QueryMap Map<PARAM_FIELD, String> options);

  enum PARAM_FIELD {
    font, per_page, page, q
  }
}
