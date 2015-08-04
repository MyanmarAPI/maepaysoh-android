package org.maepaysoh.maepaysoh.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ye Lin Aung on 15/08/04.
 */
public class PartyAdapter extends RecyclerView.Adapter<PartyAdapter.PartyViewHolder> {

  @Override public PartyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(PartyViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return 0;
  }

  class PartyViewHolder extends RecyclerView.ViewHolder {
    public PartyViewHolder(View itemView) {
      super(itemView);
    }
  }
}
