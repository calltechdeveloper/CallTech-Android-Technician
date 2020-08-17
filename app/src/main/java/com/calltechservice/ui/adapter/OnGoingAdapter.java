package com.calltechservice.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.calltechservice.R;
import com.calltechservice.databinding.OnGoingItemBinding;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.utils.CommonUtils;

import java.util.List;

public class OnGoingAdapter extends RecyclerView.Adapter<OnGoingAdapter.MyViewHolder> {

    private Context context;
    private List<OnGoingJobResponse> onGoingJobResponseList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private OnGoingItemBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding = DataBindingUtil.bind(view);

        }
    }

    public OnGoingAdapter(Context context, List<OnGoingJobResponse> onGoingJobResponseList) {
        this.context = context;
        this.onGoingJobResponseList = onGoingJobResponseList;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.on_going_item, parent, false);
        CommonUtils.setAnimation(itemView, context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        OnGoingJobResponse model = onGoingJobResponseList.get(holder.getAdapterPosition());
        holder.binding.tvTitle.setText(model.getJobDetails().getTitle() != null ? model.getJobDetails().getTitle() : "");
        holder.binding.tvDescription.setText(model.getJobDetails().getDescription() != null ? model.getJobDetails().getDescription().trim() : "");
        holder.binding.tvName.setText(model.getProviderDetails().getName());
       /*if(onGoingJobResponseList.get(model.getJobDetails().getGetIsImmediate()!=null&& model.getJobDetails().getGetIsImmediate().equalsIgnoreCase("1"))
       {
           holder.binding.tvScheduledJob.setText(R.string.imediate_requirment);
           holder.binding.tvScheduledJob.setTextColor(Color.RED);
       }
       else {
           if (onGoingJobResponseList.get(holder.getAdapterPosition()).getJobDetails().getScheduleDate() != null && !onGoingJobResponseList.get(holder.getAdapterPosition()).getJobDetails().getScheduleDate().equalsIgnoreCase("")) {
               holder.binding.tvScheduledJob.setText(onGoingJobResponseList.get(holder.getAdapterPosition()).getJobDetails().getScheduleDate());
           } else {
               holder.binding.tvScheduledJob.setText("");
           }
       }*/

        if (model.getJobDetails().getGetIsImmediate().equalsIgnoreCase("1")) {
            holder.binding.tvDate.setText(R.string.imediate_requirment);
            holder.binding.tvDate.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            if (model.getJobDetails().getScheduleDate() != null && !model.getJobDetails().getScheduleDate().equalsIgnoreCase("")) {
                holder.binding.tvDate.setText(model.getJobDetails().getScheduleDate());
            } else {
                holder.binding.tvDate.setText("");
            }
        }
    }

    @Override
    public int getItemCount() {
        return onGoingJobResponseList.size();
    }
}