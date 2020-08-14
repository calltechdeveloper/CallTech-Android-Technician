package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.R;
import com.calltechservice.databinding.DashboardListItemBinding;
import com.calltechservice.model.response.ServiceCtegoryModel;

import java.util.List;


public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private List<ServiceCtegoryModel> serviceCtegoryModels;

    public ServiceCategoryAdapter(Context context, List<ServiceCtegoryModel> serviceCtegoryModels) {
        this.context = context;
        this.serviceCtegoryModels = serviceCtegoryModels;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item, parent, false);
        //CommonUtils.setAnimation(itemView,context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServiceCtegoryModel model = serviceCtegoryModels.get(holder.getAdapterPosition());

        holder.binding.tvTitle.setText(model.getCategory_name());

        if (model.getCategory_image() != null && !model.getCategory_image().equalsIgnoreCase("")) {

            Glide.with(context)
                    .load(Uri.parse(model.getCategory_image())).apply(RequestOptions.fitCenterTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .apply(RequestOptions.errorOf(R.drawable.ic_user))
                    .into(holder.binding.ivIcon);
        }else {
            holder.binding.ivIcon.setImageResource(R.drawable.ic_user);
        }

       /* if (position==0){

            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.house_things));
            holder.binding.tvTitle.setText("Cleaning Service");

        }else if (position==1){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.plumbing));
            holder.binding.tvTitle.setText("Plumbing Service");
        }else if (position==2){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.carpentry));
            holder.binding.tvTitle.setText("Carpentry Service");
        }else if (position==3){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.painting));
            holder.binding.tvTitle.setText("Painting Service");
        }else if (position==4){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.computer));
            holder.binding.tvTitle.setText("Computer Service");
        }else if (position==5){
            holder.binding.ivIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.bug));
            holder.binding.tvTitle.setText("Plumbing Service");
        }*/


    }

    @Override
    public int getItemCount() {
        return serviceCtegoryModels.size();
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