package org.maepaysoh.maepaysohsdk.api;

/**
 * Created by yemyatthu on 9/24/15.
 */

import java.util.List;
import org.maepaysoh.maepaysohsdk.models.Motion;
import org.maepaysoh.maepaysohsdk.models.MotionReturnObject;
import org.maepaysoh.maepaysohsdk.models.ParliamentMember;
import org.maepaysoh.maepaysohsdk.models.Question;
import org.maepaysoh.maepaysohsdk.models.QuestionReturnObject;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface OMIService {
  @GET("parliament/members/all.json") List<ParliamentMember> getAllParliamentMemeberDetail();

  @GET("parliament/members/all.json") void getAllParliamentMemeberDetailAsync(Callback<List<ParliamentMember>> callback);

  @GET("parliament/members/{name}.json") ParliamentMember getSingleParliamentMemberDetail(@Path("name") String name);

  @GET("parliament/members/{name}.json") void getSingleParliamentMemberDetailAsync(@Path("name") String name,
      Callback<ParliamentMember> callback);

  @GET("parliament/questions/{name}.json") QuestionReturnObject getQuestionsFromMember(@Path("name") String name);

  @GET("parliament/questions/{name}.json") void getQuestionsFromMemberAsync(@Path("name") String name
      ,Callback<QuestionReturnObject> callback);

  @GET("parliament/questions/{question_id}.json") Question getSingleQuestion(@Path("question_id") String questionId);

  @GET("parliament/questions/{question_id}.json") void getSingleQuestionAsync(@Path("question_id") String questionId,Callback<Question> questionCallback);

  @GET("parliament/motions/{name}.json") MotionReturnObject getMotionsFromMember(@Path("name") String name);

  @GET("parliament/motions/{name}.json") void getMotionsFromMemberAsync(@Path("name") String name,Callback<MotionReturnObject> motionReturnObjectCallback);


  @GET("parliament/motions/{motion_id}.json") Motion getSingleMotion(@Path("motion_id") String motionId);

  @GET("parliament/motions/{motion_id}.json") void getSingleMotionAsync(@Path("motion_id") String motionId,Callback<Motion> motionCallback);


}
