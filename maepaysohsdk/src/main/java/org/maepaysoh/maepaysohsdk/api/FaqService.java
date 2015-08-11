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
  @GET("/faq/list") void listFaqs(@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options,
      Callback<FAQ> faqCallback);

  @GET("/faq/search") void searchFaqs(@QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options,
      Callback<FAQ> faqCallback);

  @GET("/faq/{faq_id}") void searchFaqById(@Path("faq_id") String faqId,
      @QueryMap Map<FaqAPIHelper.PARAM_FIELD, String> options, Callback<FAQ> faqCallback);

}
