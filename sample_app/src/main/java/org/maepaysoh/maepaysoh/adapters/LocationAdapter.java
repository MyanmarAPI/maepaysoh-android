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
import org.maepaysoh.maepaysohsdk.models.Geo;

/**
 * Created by yemyatthu on 8/18/15.
 */
public class LocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
  private List<Geo> mGeos;
  private Context mContext;
  private ClickInterface mClickInterface;

  public LocationAdapter() {
    mGeos = new ArrayList<>();
  }

  public void setGeos(List<Geo> geos) {
    mGeos.addAll(geos);
    notifyDataSetChanged();
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.location_item_view, parent, false);
    return new LocationViewHolder(view);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Geo geo = mGeos.get(position);
    ((LocationViewHolder) holder).mLocationDivision.setText(geo.getProperties().getDT());
    ((LocationViewHolder)holder).mLocationTownship.setText(geo.getProperties().getST());
  }

  @Override public int getItemCount() {
    return mGeos != null ? mGeos.size() : 0;
  }

  public void setOnItemClickListener(ClickInterface clickInterface) {
    mClickInterface = clickInterface;
  }

  public interface ClickInterface {
    void onItemClick(View view, int position);
  }

  class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView mLocationDivision;
    private TextView mLocationTownship;

    public LocationViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
      mLocationDivision = (TextView) itemView.findViewById(R.id.location_division);
      mLocationTownship = (TextView) itemView.findViewById(R.id.location_township);
    }

    @Override public void onClick(View view) {
      if (mClickInterface != null) {
        mClickInterface.onItemClick(view, getAdapterPosition());
      }
    }
  }
}
