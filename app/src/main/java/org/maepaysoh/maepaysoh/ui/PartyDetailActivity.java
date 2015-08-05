package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.models.PartyData;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class PartyDetailActivity extends AppCompatActivity {
  public static final String PARTY_CONSTANT =
      "org.maepaysoh.maepaysoh.ui.PartyDetailActivity.PARTY_CONSTANT";
  private Toolbar mToolbar;
  private ImageView mPartyFlag;
  private TextView mPartyNameMyanmar;
  private TextView mPartyNameEnglish;
  private TextView mPartyLeader;
  private TextView mPartyChairman;
  private TextView mPartyMemberCount;
  private TextView mPartyEstbDate;
  private TextView mPartyEstbApprovalDate;
  private TextView mPartyRegApplicationDate;
  private TextView mPartyRegApprovalDate;
  private TextView mPartyApprovedNo;
  private TextView mPartyRegion;
  private TextView mPartyHeadquarters;
  private TextView mPartyContact;
  private TextView mPartyPolicy;
  private PartyData mPartyData;
  private ImageView mPartySeal;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_detail);
    mToolbar = (Toolbar) findViewById(R.id.party_detail_toolbar);
    mPartyFlag = (ImageView) findViewById(R.id.party_flag);
    mPartyNameMyanmar = (TextView) findViewById(R.id.party_name_myanmar);
    mPartyNameEnglish = (TextView) findViewById(R.id.party_name_english);
    mPartyLeader = (TextView) findViewById(R.id.party_leader);
    mPartyChairman = (TextView) findViewById(R.id.party_chairman);
    mPartyMemberCount = (TextView) findViewById(R.id.party_member_count);
    mPartyEstbDate = (TextView) findViewById(R.id.party_estb_date);
    mPartyEstbApprovalDate = (TextView) findViewById(R.id.party_estb_approval_date);
    mPartyRegApplicationDate = (TextView) findViewById(R.id.party_reg_application_date);
    mPartyRegApprovalDate = (TextView) findViewById(R.id.party_reg_approval_date);
    mPartyApprovedNo = (TextView) findViewById(R.id.party_approved_no);
    mPartyRegion = (TextView) findViewById(R.id.party_region);
    mPartyHeadquarters = (TextView) findViewById(R.id.party_headquaters);
    mPartyContact = (TextView) findViewById(R.id.party_contact);
    mPartyPolicy = (TextView) findViewById(R.id.party_policy);
    mPartySeal = (ImageView) findViewById(R.id.party_seal);
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mPartyData = (PartyData) getIntent().getSerializableExtra(PARTY_CONSTANT);
    if (mPartyData != null) {
      Glide.with(this).load(mPartyData.getPartyFlag()).into(mPartyFlag);
      Glide.with(this).load(mPartyData.getPartySeal()).into(mPartySeal);
      mPartyNameEnglish.setText(mPartyData.getPartyNameEnglish());
      mPartyNameMyanmar.setText(mPartyData.getPartyName());
      List<String> leaders = mPartyData.getLeadership();
      for (String leader : leaders) {
        if (leaders.indexOf(leader) == leaders.size() - 1) {
          mPartyLeader.setText(leader);
        } else {
          mPartyLeader.setText(leader + "၊ ");
        }
      }
      List<String> chairmans = mPartyData.getChairman();
      for (String chairman : chairmans) {
        if (leaders.indexOf(chairman) == chairmans.size() - 1) {
          mPartyChairman.setText(chairman);
        } else {
          mPartyChairman.setText(chairman + "၊ ");
        }
      }
      mPartyMemberCount.setText(mPartyData.getMemberCount());
      mPartyEstbDate.setText(mPartyData.getEstablishmentDate());
      mPartyEstbApprovalDate.setText(mPartyData.getEstablishmentApprovalDate());
      mPartyRegApplicationDate.setText(mPartyData.getRegistrationApplicationDate());
      mPartyRegApprovalDate.setText(mPartyData.getRegistrationApprovalDate());
      mPartyApprovedNo.setText(mPartyData.getApprovedPartyNumber());
      mPartyRegion.setText(mPartyData.getRegion());
      mPartyHeadquarters.setText(mPartyData.getHeadquarters());
      List<String> contacts = mPartyData.getContact();
      for (String contact : contacts) {
        if (contacts.indexOf(contact) == contacts.size() - 1) {
          mPartyContact.setText(contact);
        } else {
          mPartyContact.setText(contact + "၊ ");
        }
      }
      mPartyPolicy.setText(mPartyData.getPolicy());
      Linkify.addLinks(mPartyPolicy, Linkify.WEB_URLS);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
