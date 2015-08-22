package org.maepaysoh.maepaysohsdk;

import android.content.Context;
import android.database.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.maepaysoh.maepaysohsdk.api.CandidateService;
import org.maepaysoh.maepaysohsdk.api.RetrofitHelper;
import org.maepaysoh.maepaysohsdk.db.CandidateDao;
import org.maepaysoh.maepaysohsdk.models.Candidate;
import org.maepaysoh.maepaysohsdk.models.CandidateDetailReturnObject;
import org.maepaysoh.maepaysohsdk.models.CandidateListReturnObject;
import org.maepaysoh.maepaysohsdk.utils.CandidateAPIProperties;
import org.maepaysoh.maepaysohsdk.utils.CandidateAPIPropertiesMap;
import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by yemyatthu on 8/11/15.
 */
public class CandidateAPIHelper {
  private RestAdapter mCandidateRestAdapter;
  private CandidateService mCandidateService;
  private CandidateDao mCandidateDao;
  private Context mContext;

  public CandidateAPIHelper(String token, Context context) {
    mCandidateRestAdapter = RetrofitHelper.getResAdapter(token);
    mCandidateService = mCandidateRestAdapter.create(CandidateService.class);
    mContext = context;
  }

  /**
   *
   * @param callback
   */
  public void getCandidatesAsync(Callback<CandidateListReturnObject> callback) {
    getCandidatesAsync(new CandidateAPIPropertiesMap(), callback);
  }

  /**
   *
   * @param propertiesMap
   * @param callback
   */
  public void getCandidatesAsync(CandidateAPIPropertiesMap propertiesMap,
      Callback<CandidateListReturnObject> callback) {
    String gender = propertiesMap.getString(CandidateAPIProperties.GENDER, "");
    String religion = propertiesMap.getString(CandidateAPIProperties.RELIGION, "");
    String legislature = propertiesMap.getString(CandidateAPIProperties.LEGISLATURE, "");
    boolean withParty = propertiesMap.getBoolean(CandidateAPIProperties.WITH_PARTY, false);
    boolean unicode =
        propertiesMap.getBoolean(CandidateAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    int firstPage = propertiesMap.getInteger(CandidateAPIProperties.FIRST_PAGE, 1);
    int perPage = propertiesMap.getInteger(CandidateAPIProperties.PER_PAGE, 15);

    Map<CandidateService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if (unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(CandidateService.PARAM_FIELD.gender, gender);
    optionParams.put(CandidateService.PARAM_FIELD.religion, religion);
    optionParams.put(CandidateService.PARAM_FIELD.page, String.valueOf(firstPage));
    optionParams.put(CandidateService.PARAM_FIELD.per_page, String.valueOf(perPage));
    optionParams.put(CandidateService.PARAM_FIELD.legislature, legislature);
    mCandidateService.listCandidatesAsync(optionParams, callback);
  }

  public List<Candidate> getCandidates() {
    return getCandidates(new CandidateAPIPropertiesMap());
  }

  /**
   *
   * @param propertiesMap
   *
   */
  public List<Candidate> getCandidates(CandidateAPIPropertiesMap propertiesMap) {
    String gender = propertiesMap.getString(CandidateAPIProperties.GENDER, "");
    String religion = propertiesMap.getString(CandidateAPIProperties.RELIGION, "");
    String legislature = propertiesMap.getString(CandidateAPIProperties.LEGISLATURE, "");
    boolean withParty = propertiesMap.getBoolean(CandidateAPIProperties.WITH_PARTY, false);
    boolean unicode =
        propertiesMap.getBoolean(CandidateAPIProperties.IS_UNICODE, Utils.isUniCode(mContext));
    int firstPage = propertiesMap.getInteger(CandidateAPIProperties.FIRST_PAGE, 1);
    int perPage = propertiesMap.getInteger(CandidateAPIProperties.PER_PAGE, 15);
    boolean cache = propertiesMap.getBoolean(CandidateAPIProperties.CACHE, true);

    mCandidateDao = new CandidateDao(mContext);
    Map<CandidateService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if (unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(CandidateService.PARAM_FIELD.page, String.valueOf(firstPage));
    optionParams.put(CandidateService.PARAM_FIELD.per_page, String.valueOf(perPage));
    optionParams.put(CandidateService.PARAM_FIELD.gender, gender);
    optionParams.put(CandidateService.PARAM_FIELD.religion, religion);
    optionParams.put(CandidateService.PARAM_FIELD.legislature, legislature);

    CandidateListReturnObject returnObject = mCandidateService.listCandidates(optionParams);
    if (cache) {
      for (Candidate data : returnObject.getData()) {
        try {
          mCandidateDao.createCandidate(data);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return returnObject.getData();
  }

  public void getCandidateByIdAsync(String candidateId,
      Callback<CandidateDetailReturnObject> returnObjectCallback) {
    getCandidateByIdAsync(candidateId, new CandidateAPIPropertiesMap(), returnObjectCallback);
  }

  /**
   *
   * @param candidateId
   * @param candidateAPIPropertiesMap
   * @param callback
   */
  public void getCandidateByIdAsync(String candidateId,
      CandidateAPIPropertiesMap candidateAPIPropertiesMap,
      Callback<CandidateDetailReturnObject> callback) {
    boolean withParty =
        candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.WITH_PARTY, false);
    boolean unicode = candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.IS_UNICODE,
        Utils.isUniCode(mContext));
    Map<CandidateService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if (unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    mCandidateService.getCandidateByIdAsync(candidateId, optionParams, callback);
  }

  public Candidate getCandidateById(String candidateId) {
    return getCandidateById(candidateId, new CandidateAPIPropertiesMap());
  }

  /**
   *
   * @param candidateId
   * @param candidateAPIPropertiesMap
   */
  public Candidate getCandidateById(String candidateId,
      CandidateAPIPropertiesMap candidateAPIPropertiesMap) {
    boolean withParty =
        candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.WITH_PARTY, false);
    boolean unicode = candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.IS_UNICODE,
        Utils.isUniCode(mContext));
    boolean cache = candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.CACHE, true);
    Map<CandidateService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if (unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    CandidateDetailReturnObject returnObject =
        mCandidateService.getCandidateById(candidateId, optionParams);
    Candidate candidate = returnObject.getCandidate();
    if (cache) {
      mCandidateDao.createCandidate(candidate);
    }
    return candidate;
  }

  public List<Candidate> getCandidatesFromCache() {
    mCandidateDao = new CandidateDao(mContext);
    return mCandidateDao.getAllCandidateData();
  }

  public Candidate getCandidateByIdFromCache(String candidateId) {
    mCandidateDao = new CandidateDao(mContext);
    return mCandidateDao.getCandidateById(candidateId);
  }

  public List<Candidate> searchCandidateFromCache(String keyword) {
    mCandidateDao = new CandidateDao(mContext);
    try {
      return mCandidateDao.searchCandidatesFromDb(keyword);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public List<Candidate> getCandidatesByConstituency(String stPcode, String dtPcode,
      CandidateAPIPropertiesMap candidateAPIPropertiesMap) {
    boolean withParty =
        candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.WITH_PARTY, false);
    boolean unicode = candidateAPIPropertiesMap.getBoolean(CandidateAPIProperties.IS_UNICODE,
        Utils.isUniCode(mContext));
    Map<CandidateService.PARAM_FIELD, String> optionParams = new HashMap<>();
    if (withParty) {
      optionParams.put(CandidateService.PARAM_FIELD._with, Constants.WITH_PARTY);
    }
    if (unicode) {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.UNICODE);
    } else {
      optionParams.put(CandidateService.PARAM_FIELD.font, Constants.ZAWGYI);
    }
    optionParams.put(CandidateService.PARAM_FIELD.constituency_st_pcode, stPcode);
    optionParams.put(CandidateService.PARAM_FIELD.constituency_dt_pcode, dtPcode);
    CandidateListReturnObject returnObject = mCandidateService.listCandidates(optionParams);
    List<Candidate> candidates = returnObject.getData();
    return candidates;
  }
}
