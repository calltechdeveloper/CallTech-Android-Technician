package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.R;
import com.calltechservice.databinding.InnerSubCategoryBinding;

import com.calltechservice.model.response.SubCategory;
import com.calltechservice.ui.fragment.ServiceProviderFragment;
import com.calltechservice.utils.CommonUtils;

import java.util.List;

public class ServiceInnerSubAdapter extends RecyclerView.Adapter<ServiceInnerSubAdapter.MyViewHolder> {

    private Context context;
    private List<SubCategory> subCategories;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private InnerSubCategoryBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);

        }
    }

    public ServiceInnerSubAdapter(Context context, List<SubCategory> subCategories) {
        this.context=context;
        this.subCategories=subCategories;

    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_sub_category, parent, false);
        CommonUtils.setAnimation(itemView,context);
         return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.binding.tvSubCategoryName.setText(subCategories.get(holder.getAdapterPosition()).getSubCatName()!=null?subCategories.get(holder.getAdapterPosition()).getSubCatName():"");

        if(subCategories.get(holder.getAdapterPosition()).getSubCatImage()!=null&&!subCategories.get(holder.getAdapterPosition()).getSubCatImage().equalsIgnoreCase(""))
        {
            Glide.with(context).load(subCategories.get(holder.getAdapterPosition()).getSubCatImage()).apply(new RequestOptions().placeholder(R.drawable.gal)).into(holder.binding.ivSubCatImage);
        }
        else
        {
            holder.binding.ivSubCatImage.setImageResource(R.drawable.gal);
        }

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new ServiceProviderFragment();
                Bundle bundle=new Bundle();
                bundle.putString("sub_cat",subCategories.get(holder.getAdapterPosition()).getSubCatId());
                bundle.putString("sub_cat_name",subCategories.get(holder.getAdapterPosition()).getSubCatName());
                fragment.setArguments(bundle);
                CommonUtils.setFragment(fragment,false, (FragmentActivity) context, R.id.flContainerHome);
            }
        });

    }
 
    @Override
    public int getItemCount() {
        return subCategories.size();
    }
}