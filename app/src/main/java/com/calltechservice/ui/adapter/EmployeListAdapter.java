package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.R;
import com.calltechservice.databinding.EmployeListItemBinding;

import com.calltechservice.model.response.employee.EmployeeData;
import com.calltechservice.utils.CommonUtils;

import java.util.List;

public class EmployeListAdapter extends RecyclerView.Adapter<EmployeListAdapter.MyViewHolder> {

    private Context context;
    private List<EmployeeData> employeeDatas;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private EmployeListItemBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);

        }
    }

    public EmployeListAdapter(Context context, List<EmployeeData> employeeDatas) {
        this.context=context;
        this.employeeDatas=employeeDatas;

    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employe_list_item, parent, false);
        CommonUtils.setAnimation(itemView,context);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.binding.tvUserName.setText(employeeDatas.get(holder.getAdapterPosition()).getmName()!=null?employeeDatas.get(holder.getAdapterPosition()).getmName():"");
       // holder.binding.tvServiceType.setText(employeeDatas.get(holder.getAdapterPosition()).se()!=null?employeeDatas.get(holder.getAdapterPosition()).getmName():"");
        holder.binding.tvNoOfJobDone.setText(employeeDatas.get(holder.getAdapterPosition()).getmJobDone()!=null?employeeDatas.get(holder.getAdapterPosition()).getmJobDone():"");

        if(employeeDatas.get(holder.getAdapterPosition()).getmProfilePic()!=null&&!employeeDatas.get(holder.getAdapterPosition()).getmProfilePic().equalsIgnoreCase(""))
        {
            Glide.with(context).load(employeeDatas.get(holder.getAdapterPosition()).getmProfilePic()).apply(new RequestOptions().placeholder(R.drawable.user)).into(holder.binding.ivProfile);
        }
        else
        {
            holder.binding.ivProfile.setImageResource(R.drawable.user);
        }
    }
 
    @Override
    public int getItemCount() {
        return employeeDatas.size();
    }
}