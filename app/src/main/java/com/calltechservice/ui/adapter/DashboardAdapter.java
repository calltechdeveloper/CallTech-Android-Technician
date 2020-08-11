package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.DashboardListItemBinding;
import com.calltechservice.model.response.AddRemoveCategoryModel;

import java.util.ArrayList;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private ArrayList<AddRemoveCategoryModel> serviceCtegoryModels;

   /* public DashboardAdapter(Context context, ArrayList<AddRemoveCategoryModel> serviceCtegoryModels) {
        this.context = context;
        this.serviceCtegoryModels = serviceCtegoryModels;
    }*/

    public DashboardAdapter(Context context) {
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list_item, parent, false);
        //CommonUtils.setAnimation(itemView,context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       /* AddRemoveCategoryModel model = serviceCtegoryModels.get(holder.getAdapterPosition());


        holder.binding.tvTitle.setText(model.getCategory_name());

        if (model.getCategory_image() != null && !model.getCategory_image().equalsIgnoreCase("")) {

            Glide.with(context)
                    .load(Uri.parse(model.getCategory_image())).apply(RequestOptions.fitCenterTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .apply(RequestOptions.errorOf(R.drawable.ic_user))
                    .into(holder.binding.ivIcon);
        }else {
            holder.binding.ivIcon.setImageResource(R.drawable.ic_user);
        }*/




    }

    @Override
    public int getItemCount() {
        return 5;
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