package org.maepaysoh.maepaysoh.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.models.PartyData;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class PartyDetailActivity extends BaseActivity {
  public static final String PARTY_CONSTANT =
      "org.maepaysoh.maepaysoh.ui.PartyDetailActivity.PARTY_CONSTANT";

  // Ui elements
  private Toolbar mToolbar;
  private View mToolbarShadow;

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

  private ShareActionProvider mShareActionProvider;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_party_detail);
    mToolbar = (Toolbar) findViewById(R.id.party_detail_toolbar);
    mToolbarShadow = findViewById(R.id.party_detail_toolbar_shadow);

    hideToolBarShadowForLollipop(mToolbar, mToolbarShadow);

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
    mPartyHeadquarters = (TextView) findViewById(R.id.party_headquarters);
    mPartyContact = (TextView) findViewById(R.id.party_contact);
    mPartyPolicy = (TextView) findViewById(R.id.party_policy);
    mPartySeal = (ImageView) findViewById(R.id.party_seal);

    setSupportActionBar(mToolbar);
    ActionBar mActionBar = getSupportActionBar();
    if (mActionBar != null) {
      // Showing Back Arrow  <-
      mActionBar.setDisplayHomeAsUpEnabled(true);
    }

    mPartyData = (PartyData) getIntent().getSerializableExtra(PARTY_CONSTANT);
    if (mPartyData != null) {
      Glide.with(this).load(mPartyData.getPartyFlag()).into(mPartyFlag);
      Glide.with(this).load(mPartyData.getPartySeal()).into(mPartySeal);
      mPartyNameEnglish.setText(mPartyData.getPartyNameEnglish());
      mPartyNameMyanmar.setText(mPartyData.getPartyName());
      List<String> leaders = mPartyData.getLeadership();
      for (String leader : leaders) {
        if (leaders.indexOf(leader) == leaders.size() - 1) {
          mPartyLeader.append(leader);
        } else {
          mPartyLeader.append(leader + "၊ ");
        }
      }
      List<String> chairmen = mPartyData.getChairman();
      for (String chairman : chairmen) {
        if (leaders.indexOf(chairman) == chairmen.size() - 1) {
          mPartyChairman.append(chairman);
        } else {
          mPartyChairman.append(chairman + "၊ ");
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
          mPartyContact.append(contact);
        } else {
          mPartyContact.append(contact + "၊ ");
        }
      }
      mPartyPolicy.setText(mPartyData.getPolicy());
      Linkify.addLinks(mPartyPolicy, Linkify.WEB_URLS);
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_party_detail, menu);
    //MenuItem item = menu.findItem(R.id.party_detail_action_share);
    //mShareActionProvider = (ShareActionProvider) item.getActionProvider();
    //share(mShareActionProvider.);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;
      case R.id.party_detail_action_share:
        share();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void share() {
    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    String shareBody = "Here is the share content body";
    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
    startActivity(Intent.createChooser(sharingIntent, "Share via"));
  }
}
