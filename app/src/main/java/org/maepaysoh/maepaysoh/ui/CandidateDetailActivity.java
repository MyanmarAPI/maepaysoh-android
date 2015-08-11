package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.yemyatthu.maepaesohsdk.models.CandidateData;
import java.util.List;
import org.maepaysoh.maepaysoh.R;


/**
 * Created by yemyatthu on 8/5/15.
 */
public class CandidateDetailActivity extends BaseActivity {
  public static final String CANDIDATE_CONSTANT =
      "org.maepaysoh.maepaysoh.ui.CandidateDetailActivity";

  // Ui elements
  private Toolbar mToolbar;
  private View mToolbarShadow;
  private TextView mCandidateName;
  private TextView mLegislature;
  private TextView mNationalId;
  private TextView mBirthDate;
  private TextView mEducation;
  private TextView mOccupation;
  private TextView mReligion;
  private TextView mResidency;
  private TextView mConstituency;
  private TextView mParty;
  private TextView mMotherName;
  private TextView mFatherName;

  private CandidateData mCandidateData;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_detail);
    mToolbar = (Toolbar) findViewById(R.id.candidate_detail_toolbar);
    mToolbarShadow = findViewById(R.id.candidate_detail_toolbar_shadow);
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    mCandidateName = (TextView) findViewById(R.id.candidate_name);
    mLegislature = (TextView) findViewById(R.id.candidate_legislature);
    mNationalId = (TextView) findViewById(R.id.candidate_national_id);
    mBirthDate = (TextView) findViewById(R.id.candidate_birth_date);
    mEducation = (TextView) findViewById(R.id.candidate_education);
    mOccupation = (TextView) findViewById(R.id.candidate_occupation);
    mReligion = (TextView) findViewById(R.id.candidate_religion);
    mResidency = (TextView) findViewById(R.id.candidate_residency);
    mConstituency = (TextView) findViewById(R.id.candidate_constituency);
    mParty = (TextView) findViewById(R.id.candidate_party);
    mMotherName = (TextView) findViewById(R.id.candidate_mother);
    mFatherName = (TextView) findViewById(R.id.candidate_father);

    setSupportActionBar(mToolbar);
    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    mCandidateData = (CandidateData) getIntent().getSerializableExtra(CANDIDATE_CONSTANT);
    if (mCandidateData != null) {
      mCandidateName.setText(mCandidateData.getName());
      mLegislature.setText(mCandidateData.getLegislature());
      mNationalId.setText(mCandidateData.getNationalId());
      mBirthDate.setText(String.valueOf(mCandidateData.getBirthdate()));
      mReligion.setText(mCandidateData.getNationalityReligion());
      mConstituency.setText(mCandidateData.getConstituency().getName());
      mResidency.setText(mCandidateData.getResidency().getName());
      mMotherName.setText(mCandidateData.getMother().getName());
      mFatherName.setText(mCandidateData.getFather().getName());
      List<String> occupations = mCandidateData.getOccupation();
      List<String> educations = mCandidateData.getEducation();
      for (String occupation : occupations) {
        if (occupations.indexOf(occupation) == occupations.size() - 1) {
          mOccupation.append(occupation);
        } else {
          mOccupation.append(occupation + "၊ ");
        }
      }

      for (String education : educations) {
        if (occupations.indexOf(education) == occupations.size() - 1) {
          mEducation.append(education);
        } else {
          mEducation.append(education + "၊ ");
        }
      }
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_candidate_detail, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      case R.id.candidate_detail_action_share:
        // TODO What do we want to share
        share(mCandidateData.getName(), mCandidateData.getId());
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
