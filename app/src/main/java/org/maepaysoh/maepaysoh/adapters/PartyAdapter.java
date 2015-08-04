package org.maepaysoh.maepaysoh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.models.Party;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {
  private Context mContext;
  private List<Party> mParties;
  private ClickInterface mClickInterface;
  public PartyAdapter(){
    mParties = new ArrayList<>();
  }

  public void setParties(List<Party> parties){
    mParties = parties;
    notifyDataSetChanged();
  }
  @Override public PartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext)
        .inflate(R.layout.party_item_view,parent,false);
    return new PartyViewHolder(view);
  }

  @Override public void onBindViewHolder(PartyViewHolder holder, int position) {
    Party party = mParties.get(position);
    holder.mPartyNameMyanmar.setText(party.getPartyName());
    holder.mPartyNameEnglish.setText(party.getPartyNameEnglish());
    holder.mPartyLeader.setText(party.getLeadership());
    Glide.with(mContext)
        .load(party.getPartyFlag())
        .centerCrop()
        .into(holder.mPartyFlag);
  }
  public void setOnItemClickListener(ClickInterface clickInterface){
    mClickInterface = clickInterface;
  }
  interface ClickInterface{
     void onItemClick(View view,int position);
  }
  @Override public int getItemCount() {
    return mParties != null?mParties.size():0;
  }

  class PartyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mPartyNameEnglish;
    private TextView mPartyNameMyanmar;
    private TextView mPartyLeader;
    private ImageView mPartyFlag;
    public PartyViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mPartyNameEnglish = (TextView) itemView.findViewById(R.id.party_name_english);
      mPartyNameMyanmar = (TextView) itemView.findViewById(R.id.party_name_myanmar);
      mPartyLeader = (TextView) itemView.findViewById(R.id.party_leader);
      mPartyFlag = (ImageView) itemView.findViewById(R.id.party_flag);
    }

    @Override public void onClick(View view) {
      if(mClickInterface!=null){
        mClickInterface.onItemClick(view,getAdapterPosition());
      }
    }
  }
}
