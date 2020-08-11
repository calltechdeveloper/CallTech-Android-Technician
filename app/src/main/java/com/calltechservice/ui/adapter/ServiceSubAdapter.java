package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.SabCategoryListBinding;

public class ServiceSubAdapter extends RecyclerView.Adapter<ServiceSubAdapter.MyViewHolder> {

    private Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private SabCategoryListBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);

        }
    }

    public ServiceSubAdapter(Context context) {
        this.context=context;

    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sab_category_list, parent, false);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
    }
 
    @Override
    public int getItemCount() {
        return 5;
    }
}