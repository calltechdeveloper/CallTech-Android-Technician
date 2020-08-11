package com.calltechservice.ui.adapter;


import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.SupportListItemBinding;

import java.util.ArrayList;

public class SupportListAdapter extends RecyclerView.Adapter<SupportListAdapter.MyViewHolder>{


   /* ArrayList<RiderHistoryModel> mList;*/
    ArrayList<String> mList;
    private Context mContext;
    private int mPosition;

    public SupportListAdapter(Context context, ArrayList<String> mList) {
        mContext = context;
        this.mList = mList;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public SupportListItemBinding binding;


        public MyViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);
        }


    }


    @Override
    public SupportListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.support_list_item, parent, false);

        return new SupportListAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
    @Override
    public void onBindViewHolder(SupportListAdapter.MyViewHolder holder, int position) {
        mPosition=position;



        holder.binding.tvSupport.setText(mList.get(position));

       /* mPosition=position;
     holder.binding.tvBookingNo.setText(mList.get(position).getBookingNo());
     holder.binding.tvDate.setText(mList.get(position).getRideDate());
   //  holder.binding.tvVehicleType.setText(mList.get(position).getDriverInfo().get(0).getVehicle_color()+" "+mList.get(position).getDriverInfo().get(0).getVehicle_model());
     if(mList.get(position).getCabType().equalsIgnoreCase("1")){
         holder.binding.ivRideType.setImageResource(R.drawable.black_car1);
     }
     else{
         holder.binding.ivRideType.setImageResource(R.drawable.bike_icon);
        }

     if(mList.get(position).getBookingStatus().equalsIgnoreCase("0")){
         holder.binding.tvStatus.setText("Schedule");
         holder.binding.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.green));;
     }

     else if(mList.get(position).getBookingStatus().equalsIgnoreCase("1")){
         holder.binding.tvStatus.setText("Complete");
         holder.binding.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.gray));
     }
     else if(mList.get(position).getBookingStatus().equalsIgnoreCase("2")|| mList.get(position).getBookingStatus().equalsIgnoreCase("3")  ){
         holder.binding.tvStatus.setText("Cancelled");
         holder.binding.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.color_btn_bg));
     }

     holder.binding.tvPickupLocation.setText(Utils.getAddressFromLatLng(mContext,new LatLng(Double.parseDouble(mList.get(position).getSourceLat()),Double.parseDouble(mList.get(position).getSourceLong()))));
     holder.binding.tvDropLocation.setText(Utils.getAddressFromLatLng(mContext,new LatLng(Double.parseDouble(mList.get(position).getDestiLat()),Double.parseDouble(mList.get(position).getDestiLong().trim()))));*/


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
