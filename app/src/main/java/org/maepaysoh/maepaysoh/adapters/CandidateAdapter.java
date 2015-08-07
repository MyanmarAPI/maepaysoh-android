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
public class CandidateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private List<CandidateData> mCandidateDatas;
  private Context mContext;
  private ClickInterface mClickInterface;

  public CandidateAdapter() {
    mCandidateDatas = new ArrayList<>();
  }

  public void setCandidates(List<CandidateData> candidateDatas) {
    mCandidateDatas = candidateDatas;
    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.candidate_item_view, parent, false);
    return new CandidateViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    CandidateData candidateData = mCandidateDatas.get(position);
    ((CandidateViewHolder) holder).mCandidateName.setText(candidateData.getName());
    ((CandidateViewHolder) holder).mCandidateOccupation.setText(
        "");//Reset the textview unless you want some weird shit to happen
    List<String> occupations = candidateData.getOccupation();
    for (String occupation : occupations) {
      if (occupations.indexOf(occupation) == occupations.size() - 1) {
        ((CandidateViewHolder) holder).mCandidateOccupation.append(occupation);
      } else {
        ((CandidateViewHolder) holder).mCandidateOccupation.append(occupation + "၊ ");
      }
    }
  }

  @Override public int getItemCount() {
    return mCandidateDatas != null ? mCandidateDatas.size() : 0;
  }

  public void setOnItemClickListener(ClickInterface clickInterface) {
    mClickInterface = clickInterface;
  }

  public interface ClickInterface {
    void onItemClick(View view, int position);
  }

  class CandidateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mCandidateName;
    private TextView mCandidateOccupation;

    public CandidateViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mCandidateName = (TextView) itemView.findViewById(R.id.candidate_name);
      mCandidateOccupation = (TextView) itemView.findViewById(R.id.candidate_occupation);
    }

    @Override public void onClick(View view) {
      if (mClickInterface != null) {
        mClickInterface.onItemClick(view, getAdapterPosition());
      }
    }
  }
}
