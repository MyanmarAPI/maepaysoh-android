package org.maepaysoh.maepaysoh.api;

import java.util.Map;
import org.maepaysoh.maepaysoh.models.FAQ;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;

/**
 * Created by yemyatthu on 8/6/15.
 */
public interface FaqService {
  @GET("/faq/list")
  void listFaqs(
      @QueryMap Map<PARAM_TYPE, String> options,
      Callback<FAQ> faqCallback
  );

  enum PARAM_TYPE {
    font, per_page, page
  }
}
