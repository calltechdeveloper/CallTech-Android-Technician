package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.databinding.InviteCartListBinding;

import com.calltechservice.R;
import com.calltechservice.model.response.JobInvitationResponse;
import com.calltechservice.utils.CommonUtils;

import java.util.List;

public class InviteCartAdapter extends RecyclerView.Adapter<InviteCartAdapter.MyViewHolder> {

    private Context context;
    private List<JobInvitationResponse> invitationsModelList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private InviteCartListBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);

        }
    }

    public InviteCartAdapter(Context context, List<JobInvitationResponse> invitationsModelList) {
        this.context=context;
        this.invitationsModelList=invitationsModelList;

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_cart_list, parent, false);
        CommonUtils.setAnimation(itemView,context);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        JobInvitationResponse model = invitationsModelList.get(holder.getAdapterPosition());
        holder.binding.tvName.setText(model.getName());
        holder.binding.tvTitle.setText(model.getTitle()!=null?model.getTitle():"");
        holder.binding.tvDescription.setText(model.getDescription()!=null?model.getDescription():"");
        if(model.getIs_immediate().equalsIgnoreCase("1"))
        {
            holder.binding.tvDate.setText(R.string.imediate_requirment);
            holder.binding.tvDate.setTextColor(ContextCompat.getColor(context, R.color.red));
        }
        else {
            if (model.getSchedule_date()!= null && !model.getSchedule_date().equalsIgnoreCase("")) {
                holder.binding.tvDate.setText(model.getSchedule_date());
            } else {
                holder.binding.tvDate.setText("");
            }
        }
    }
 
    @Override
    public int getItemCount() {
        return invitationsModelList.size();
    }
}