package com.calltechservice.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.R;
import com.calltechservice.databinding.FinalListItemBinding;
import com.calltechservice.model.response.AddRemoveCategoryModel;

import java.util.List;

public class FinalSubCategoryAdapter extends RecyclerView.Adapter<FinalSubCategoryAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private List<AddRemoveCategoryModel> serviceSubCtegoryModels;

    public FinalSubCategoryAdapter(Context context, List<AddRemoveCategoryModel> serviceSubCtegoryModels) {
        this.context = context;
        this.serviceSubCtegoryModels = serviceSubCtegoryModels;
    }


    @NonNull
    @Override
    public FinalSubCategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_list_item, parent, false);
        //CommonUtils.setAnimation(itemView,context);
        return new FinalSubCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FinalSubCategoryAdapter.MyViewHolder holder, int position) {
        AddRemoveCategoryModel model = serviceSubCtegoryModels.get(holder.getAdapterPosition());

        if (model.isSelected()) {
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_grey));
        } else {
            holder.binding.card.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_white));
        }

        holder.binding.tvTitle.setText(model.getService_name());

        if (model.getCategory_image() != null && !model.getCategory_image().equalsIgnoreCase("")) {
            Glide.with(context)
                    .load(Uri.parse(model.getCategory_image()))
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .apply(RequestOptions.errorOf(R.drawable.ic_user))
                    .into(holder.binding.ivIcon);
        } else {
            holder.binding.ivIcon.setImageResource(R.drawable.ic_user);
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < model.getLocation().size(); i++) {
            if (!stringBuilder.toString().equalsIgnoreCase("")) {
                stringBuilder.append(",").append(model.getLocation().get(i).getAreaName());
            } else {
                stringBuilder.append(model.getLocation().get(i).getAreaName());
            }
        }

        holder.binding.tvSelectLocation.setText(stringBuilder.toString());

        //AddRemoveCategoryModel

        /*locationData = new ArrayList<>();
        holder.binding.ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locationData.add(String.valueOf(position));

                serviceSubCtegoryModels.get(position).setLocation(locationData);

                adapter = new LocationListAdapter(context,  serviceSubCtegoryModels.get(position).getLocation());
                RecyclerView.LayoutManager locationLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                holder.binding.recyclerLocation.setLayoutManager(locationLayoutManager);
                holder.binding.recyclerLocation.setItemAnimator(new DefaultItemAnimator());
                holder.binding.recyclerLocation.setAdapter(adapter);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return serviceSubCtegoryModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FinalListItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            //itemView.setOnClickListener(this);
            binding.card.setOnClickListener(this);
            binding.ivLocation.setOnClickListener(this);
            binding.delete.setOnClickListener(this);
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
        void onItemClick(int position, View view);
    }
}