package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.NotificationItemBinding;
import com.calltechservice.model.response.NotificationResponse;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener listener;
    private List<NotificationResponse> notificationResponses;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private NotificationItemBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);
            itemView.setOnClickListener(this);
            binding.cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick(getAdapterPosition(), v);
        }
    }

    public NotificationAdapter(Context context, List<NotificationResponse> notificationResponses) {
        this.context=context;
        this.notificationResponses=notificationResponses;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NotificationResponse notificationResponse = notificationResponses.get(position);

        holder.binding.tvTitle.setText(notificationResponse.getTitle());
        holder.binding.tvDate.setText(notificationResponse.getDate());
        holder.binding.tvNotificationDetails.setText(notificationResponse.getMessage());
    }

    @Override
    public int getItemCount() {
        return notificationResponses.size();
    }





    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, View view);
    }}