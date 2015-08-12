package org.maepaysoh.maepaysohsdk.api;

import java.util.Map;
import org.maepaysoh.maepaysohsdk.FaqAPIHelper;
import org.maepaysoh.maepaysohsdk.models.FAQ;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/6/15.
 */
public interface FaqService {
  @GET("/faq/list") void listFaqsAsync(@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options,
      Callback<FAQ> faqCallback);

  @GET("/faq/search") void searchFaqsAsync(@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options,
      Callback<FAQ> faqCallback);

  @GET("/faq/{faq_id}") void searchFaqByIdAsync(@Path("faq_id") String faqId,
      @QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options, Callback<FAQ> faqCallback);

  @GET("/faq/list") FAQ listFaqs(@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options);

  @GET("/faq/search") FAQ searchFaq(@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options);

  @GET("/faq/{faq_id}") FAQ searchFaqById(@Path("faq_id") String faqId,@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options);
}
