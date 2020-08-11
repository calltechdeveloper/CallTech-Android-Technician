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
import com.calltechservice.databinding.InviteCartListItemBinding;

import com.calltechservice.model.response.InvitationsServiceProviderModel;

import java.util.List;

public class InvitedEmpolyeeListAdapter extends RecyclerView.Adapter<InvitedEmpolyeeListAdapter.MyViewHolder> {

    private Context context;
    //private List<EmployeeData> employeeDatas;
    private List<InvitationsServiceProviderModel> employeeDatas;
    private String providerId;
    private String jobTitle;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private InviteCartListItemBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);
        }
    }

   /* public InvitedEmpolyeeListAdapter(Context context, List<EmployeeData> employeeDatas, String providerId, String jobTitle) {
        this.context=context;
        this.employeeDatas=employeeDatas;
        this.providerId=providerId;
        this.jobTitle=jobTitle;

    }*/

    public InvitedEmpolyeeListAdapter(Context context,List<InvitationsServiceProviderModel> employeeDatas) {
        this.context=context;
        this.employeeDatas=employeeDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_cart_list_item, parent, false);
        //CommonUtils.setAnimation(itemView,context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.binding.tvName.setText(employeeDatas.get(holder.getAdapterPosition()).getName()!=null?employeeDatas.get(holder.getAdapterPosition()).getName():"");
        if(employeeDatas.get(holder.getAdapterPosition()).getInvoice_send()!=null&&employeeDatas.get(holder.getAdapterPosition()).getInvoice_send().equalsIgnoreCase("1"))
        {
            holder.binding.btPayNow.setVisibility(View.VISIBLE);
            holder.binding.tvPayNow.setText("Pay "+" R"+employeeDatas.get(holder.getAdapterPosition()).getAwarded_amount());
        }
        else
        {
            holder.binding.btPayNow.setVisibility(View.GONE);
        }

        if(employeeDatas.get(holder.getAdapterPosition()).getStatus()!=null&&employeeDatas.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase("0"))
        {
            holder.binding.ivAccept.setImageResource(R.drawable.ic_pending);
            //holder.binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pending, 0,0, 0);
            holder.binding.tvStatus.setText("Pending");
            holder.binding.lytChat.setVisibility(View.GONE);
            holder.binding.tvPayment.setVisibility(View.GONE);
        }
        else if(employeeDatas.get(holder.getAdapterPosition()).getStatus()!=null&&employeeDatas.get(holder.getAdapterPosition()).getStatus().equalsIgnoreCase("1"))
        {
            holder.binding.ivAccept.setImageResource(R.drawable.ic_accept);
            //holder.binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_accept, 0,0, 0);
            holder.binding.tvStatus.setText("Accepted");
            holder.binding.lytChat.setVisibility(View.VISIBLE);
            holder.binding.tvPayment.setVisibility(View.VISIBLE);

        }
        else
        {
            holder.binding.ivAccept.setImageResource(R.drawable.ic_reject);
            //holder.binding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_reject, 0,0, 0);
            holder.binding.tvStatus.setText("Canceled");
            holder.binding.lytChat.setVisibility(View.GONE);
            holder.binding.tvPayment.setVisibility(View.GONE);
        }

        if(employeeDatas.get(holder.getAdapterPosition()).getProfile_pic()!=null&&!employeeDatas.get(holder.getAdapterPosition()).getProfile_pic().equalsIgnoreCase(""))
        {
            Glide.with(context).load(employeeDatas.get(holder.getAdapterPosition()).getProfile_pic()).apply(new RequestOptions().placeholder(R.drawable.user)).into(holder.binding.ivProfile);
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