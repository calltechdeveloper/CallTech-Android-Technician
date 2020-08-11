package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.R;
import com.calltechservice.databinding.DashboardListItemBinding;
import com.calltechservice.model.response.ServiceSubCtegoryModel;


import java.util.List;


public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private List<ServiceSubCtegoryModel> serviceSubCtegoryModels;

    public SubCategoryAdapter(Context context, List<ServiceSubCtegoryModel> serviceSubCtegoryModels) {
        this.context = context;
        this.serviceSubCtegoryModels = serviceSubCtegoryModels;
    }


    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item, parent, false);
        //CommonUtils.setAnimation(itemView,context);
        return new SubCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.MyViewHolder holder, int position) {
        ServiceSubCtegoryModel model = serviceSubCtegoryModels.get(holder.getAdapterPosition());

        if(model.isSelected())
        {
            holder.binding.lytTop.setBackgroundColor(ContextCompat.getColor(context,R.color.colorAccent));
            //holder.binding.getRoot().setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
        else
        {
            holder.binding.lytTop.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));
            //holder.binding.getRoot().setBackgroundColor(0);
        }


        holder.binding.tvTitle.setText(model.getService_name());

        if (model.getService_image() != null && !model.getService_image().equalsIgnoreCase("")) {

            Glide.with(context)
                    .load(Uri.parse(model.getService_image()))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .apply(RequestOptions.errorOf(R.drawable.ic_user))
                    .into(holder.binding.ivIcon);
        }else {
            holder.binding.ivIcon.setImageResource(R.drawable.ic_user);
        }

/*
        if (position==0){

            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.kitchen));
            holder.binding.tvTitle.setText("Kitchen Repair");

        }else if (position==1){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.plumbing));
            holder.binding.tvTitle.setText("Bathroom Repair");
        }else if (position==2){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.k_install));
            holder.binding.tvTitle.setText("Kitchen Installation");
        }else if (position==3){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.bathroom));
            holder.binding.tvTitle.setText("Bathroom Installation");
        }else if (position==4){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.major));
            holder.binding.tvTitle.setText("Major/Big Installation");
        }else if (position==5){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.pipes));
            holder.binding.tvTitle.setText("Pipes Repair");
        }else if (position==6){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.k_install));
            holder.binding.tvTitle.setText("Kitchen Repair");
        }else if (position==7){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.bathroom));
            holder.binding.tvTitle.setText("Bathroom Repair");
        }*/


    }

    @Override
    public int getItemCount() {
        return serviceSubCtegoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        DashboardListItemBinding binding;
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