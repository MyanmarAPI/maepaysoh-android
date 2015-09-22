package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysohsdk.models.Candidate;

/**
 * Created by yemyatthu on 8/5/15.
 */
public class CandidateDetailActivity extends BaseActivity {
  public static final String CANDIDATE_CONSTANT =
      "org.maepaesoh.maepaesoh.ui.CandidateDetailActivity";

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
  private TextView mGender;
  private ImageView mCandidateImage;
  private Candidate mCandidate;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_candidate_detail);
    mToolbar = (Toolbar) findViewById(R.id.candidate_detail_toolbar);
    mToolbarShadow = findViewById(R.id.candidate_detail_toolbar_shadow);
    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

    mCandidateName = (TextView) findViewById(R.id.candidate_name);
    mCandidateImage = (ImageView) findViewById(R.id.candidate_image);
    mLegislature = (TextView) findViewById(R.id.candidate_legislature);
    mNationalId = (TextView) findViewById(R.id.candidate_national_id);
    mBirthDate = (TextView) findViewById(R.id.candidate_birth_date);
    mEducation = (TextView) findViewById(R.id.candidate_education);
    mOccupation = (TextView) findViewById(R.id.candidate_occupation);
    mReligion = (TextView) findViewById(R.id.candidate_religion);
    mResidency = (TextView) findViewById(R.id.candidate_residency);
    mConstituency = (TextView) findViewById(R.id.candidate_constituency);
    //mParty = (TextView) findViewById(R.id.candidate_party);
    mMotherName = (TextView) findViewById(R.id.candidate_mother);
    mFatherName = (TextView) findViewById(R.id.candidate_father);
    mGender = (TextView) findViewById(R.id.candidate_gender);

    setSupportActionBar(mToolbar);
    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    mCandidate = (Candidate) getIntent().getSerializableExtra(CANDIDATE_CONSTANT);
    if (mCandidate != null) {
      mCandidateName.setText(mCandidate.getName());
      mActionBar.setTitle(mCandidate.getName());
      Glide.with(this)
          .load(mCandidate.getPhotoUrl())
          .diskCacheStrategy(DiskCacheStrategy.ALL)
          .into(mCandidateImage);
      mLegislature.setText(mCandidate.getLegislature());
      //mNationalId.setText(mCandidate.getNationalId());
      mBirthDate.setText(String.valueOf(mCandidate.getBirthdate()));
      //mReligion.setText(mCandidate.getNationalityReligion());
      mConstituency.setText(mCandidate.getConstituency().getName());
    //  mResidency.setText(mCandidate.getResidency().getName());
    //  mMotherName.setText(mCandidate.getMother().getName());
    //  mFatherName.setText(mCandidate.getFather().getName());
    //  mGender.setText(mCandidate.getGender());
    //  List<String> occupations = mCandidate.getOccupation();
    //  List<String> educations = mCandidate.getEducation();
    //  for (String occupation : occupations) {
    //    if (occupations.indexOf(occupation) == occupations.size() - 1) {
    //      mOccupation.append(occupation);
    //    } else {
    //      mOccupation.append(occupation + "၊ ");
    //    }
    //  }
    //
    //  for (String education : educations) {
    //    if (occupations.indexOf(education) == occupations.size() - 1) {
    //      mEducation.append(education);
    //    } else {
    //      mEducation.append(education + "၊ ");
    //    }
    //  }
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
        share(mCandidate.getName(), mCandidate.getId());
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
