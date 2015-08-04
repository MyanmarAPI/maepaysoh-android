package org.maepaysoh.maepaysoh.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import org.maepaysoh.maepaysoh.R;
import org.maepaysoh.maepaysoh.models.CandidateData;

/**
 * Created by yemyatthu on 8/4/15.
 */
public class CandidateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
  private List<CandidateData> mCandidateDatas;
  private Context mContext;

  public CandidateAdapter(){
    mCandidateDatas = new ArrayList<>();
  }

  public void setCandidates(List<CandidateData> candidateDatas){
    mCandidateDatas = candidateDatas;
    notifyDataSetChanged();
  }
  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext)
        .inflate(R.layout.candidate_item_view,parent,false);
    return new CandidateViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    CandidateData candidateData = mCandidateDatas.get(position);
    ((CandidateViewHolder)holder).mCandidateName.setText(candidateData.getName());
    List<String> occupations = candidateData.getOccupation();
    for(String occupation:occupations){
      if(occupations.indexOf(occupation) == occupations.size()-1){
        ((CandidateViewHolder) holder).mCandidateOccupation.setText(occupation);
      }else{
        ((CandidateViewHolder) holder).mCandidateOccupation.setText(occupation + "၊ ");
      }
    }
  }

  @Override public int getItemCount() {
    return mCandidateDatas!=null?mCandidateDatas.size():0;
  }

  class CandidateViewHolder extends RecyclerView.ViewHolder{
    private TextView mCandidateName;
    private TextView mCandidateOccupation;
    public CandidateViewHolder(View itemView) {
      super(itemView);
      mCandidateName = (TextView) itemView.findViewById(R.id.candidate_name);
      mCandidateOccupation = (TextView) itemView.findViewById(R.id.candidate_occupation);
    }
  }
}
