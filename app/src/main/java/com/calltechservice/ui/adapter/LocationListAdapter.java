package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.LocationListItemBinding;

import java.util.List;


public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private List<String> locationList;

    public LocationListAdapter(Context context, List<String> locationList) {
        this.context = context;
        this.locationList = locationList;
    }


    @Override
    public LocationListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        //CommonUtils.setAnimation(itemView,context);
        return new LocationListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final LocationListAdapter.MyViewHolder holder, int position) {



        holder.binding.tvLocation.setText(locationList.get(position));


    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        LocationListItemBinding binding;
        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
            //binding.cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick(getAdapterPosition(), v);
        }
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, View view);
    }
}