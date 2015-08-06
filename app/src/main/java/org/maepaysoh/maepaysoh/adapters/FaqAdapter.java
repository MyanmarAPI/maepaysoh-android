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
import org.maepaysoh.maepaysoh.models.FaqDatum;

/**
 * Created by yemyatthu on 8/6/15.
 */
public class FaqAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private List<FaqDatum> mFaqDatums;
  private Context mContext;
  private ClickInterface mClickInterface;

  public FaqAdapter() {
    mFaqDatums = new ArrayList<>();
  }

  public void setCandidates(List<FaqDatum> faqDatums) {
    mFaqDatums = faqDatums;
    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.faq_item_view, parent, false);
    return new CandidateViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    FaqDatum faqDatum = mFaqDatums.get(position);
    ((CandidateViewHolder) holder).mFaqQuetion.setText(faqDatum.getQuestion());
    ((CandidateViewHolder) holder).mFaqAnswer.setText(faqDatum.getAnswer());
  }

  @Override public int getItemCount() {
    return mFaqDatums != null ? mFaqDatums.size() : 0;
  }

  public void setOnItemClickListener(ClickInterface clickInterface) {
    mClickInterface = clickInterface;
  }

  public interface ClickInterface {
    void onItemClick(View view, int position);
  }

  class CandidateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mFaqQuetion;
    private TextView mFaqAnswer;

    public CandidateViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mFaqQuetion = (TextView) itemView.findViewById(R.id.faq_question);
      mFaqAnswer = (TextView) itemView.findViewById(R.id.faq_answer);
    }

    @Override public void onClick(View view) {
      if (mClickInterface != null) {
        mClickInterface.onItemClick(view, getAdapterPosition());
      }
    }
  }
}
