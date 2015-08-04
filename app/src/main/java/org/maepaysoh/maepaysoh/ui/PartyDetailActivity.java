package org.maepaysoh.maepaysoh.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.models.Party;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class PartyDetailActivity extends AppCompatActivity {
  public static final String PARTY_CONSTANT = "org.maepaysoh.maepaysoh.ui.PartyDetailActivity.PARTY_CONSTANT";
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
  private Party mParty;
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
    setSupportActionBar(mToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    mParty = (Party) getIntent().getSerializableExtra(PARTY_CONSTANT);
    if(mParty!=null) {
      Glide.with(this).load(mParty.getPartyFlag()).into(mPartyFlag);

      mPartyNameEnglish.setText(mParty.getPartyNameEnglish());
      mPartyNameMyanmar.setText(mParty.getPartyName());
      mPartyLeader.setText(mParty.getLeadership());
      mPartyChairman.setText(mParty.getChairman());
      mPartyMemberCount.setText(mParty.getMemberCount());
      mPartyEstbDate.setText(mParty.getEstablishmentDate());
      mPartyEstbApprovalDate.setText(mParty.getEstablishmentApprovalDate());
      mPartyRegApplicationDate.setText(mParty.getRegistrationApplicationDate());
      mPartyRegApprovalDate.setText(mParty.getRegistrationApprovalDate());
      mPartyApprovedNo.setText(mParty.getApprovedPartyNumber());
      mPartyRegion.setText(mParty.getRegion());
      mPartyHeadquarters.setText(mParty.getHeadquarters());
      mPartyContact.setText(mParty.getContact());
      mPartyPolicy.setText(mParty.getPolicy());
      Linkify.addLinks(mPartyPolicy,Linkify.WEB_URLS);
    }
  }


  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()){
      case android.R.id.home:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
