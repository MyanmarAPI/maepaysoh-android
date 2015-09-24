package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import java.util.List;
import org.maepaysoh.maepaysohsdk.api.OMIService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.models.Motion;
import org.maepaysoh.maepaysohsdk.models.MotionReturnObject;
import org.maepaysoh.maepaysohsdk.models.ParliamentMember;
import org.maepaysoh.maepaysohsdk.models.Question;
import org.maepaysoh.maepaysohsdk.models.QuestionReturnObject;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 9/24/15.
 */
public class OMIAPIHelper {

  private RestAdapter mOMIRestAdapter;
  private OMIService mOMIService;
  private Context mContext;

  protected OMIAPIHelper(String token, Context context) {
    mOMIRestAdapter = RetrofitHelper.getResAdapter(token);
    mOMIService = mOMIRestAdapter.create(OMIService.class);
    mContext = context;
  }

  public List<Question> getQuestionsFromMember(String name){
    return mOMIService.getQuestionsFromMember(name).getQuestions();
  }

  public void getQuestionsFromMemberAsync(String name,Callback<QuestionReturnObject> callback){
    mOMIService.getQuestionsFromMemberAsync(name,callback);
  }

  public Question getSingleQuestion(String questionId){
    return mOMIService.getSingleQuestion(questionId);
  }

  public void getSingleQuestionAsync(String questionId,Callback<Question> questionCallback){
    mOMIService.getSingleQuestionAsync(questionId, questionCallback);
  }

  public List<Motion> getMotionsFromMember(String name){
    return mOMIService.getMotionsFromMember(name).getMotions();
  }

  public void getMotionsFromMemberAsync(String name,Callback<MotionReturnObject> callback){
    mOMIService.getMotionsFromMemberAsync(name, callback);
  }

  public Motion getSingleMotion(String motionId){
    return mOMIService.getSingleMotion(motionId);
  }

  public void getSingleMotionAsync(String motionId,Callback<Motion> motionCallback){
    mOMIService.getSingleMotionAsync(motionId, motionCallback);
  }

  public List<ParliamentMember> getAllParliamentMemberDetail(){
    return mOMIService.getAllParliamentMemeberDetail();
  }

  public void getAllParliamentMemberDetail(Callback<List<ParliamentMember>> callback){
    mOMIService.getAllParliamentMemeberDetailAsync(callback);
  }

  public ParliamentMember getSingleParliamentMemberDetail(String name){
    return mOMIService.getSingleParliamentMemberDetail(name);
  }

  public void getSingleParliamentDetailAsync(String name,Callback<ParliamentMember> parliamentMemberCallback){
    mOMIService.getSingleParliamentMemberDetailAsync(name,parliamentMemberCallback);
  }
}
