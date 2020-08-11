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
import com.calltechservice.databinding.MessageLayoutBinding;

import com.calltechservice.db.UserPref;
import com.calltechservice.model.response.ChatModel;
import com.calltechservice.utils.CommonUtils;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context context;
    private List<ChatModel> messageList;
    private UserPref userPref;



    public class MyViewHolder extends RecyclerView.ViewHolder {

        private MessageLayoutBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);

        }
    }

    public MessageAdapter(Context context, List<ChatModel> messageList,UserPref userPref) {
        this.context=context;
        this.messageList=messageList;
        this.userPref= userPref;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       /*sending or sended to oponent user*/
        if((messageList.get(position).getSenderId()!=null&&messageList.get(position).getSenderId().equalsIgnoreCase(userPref.getUser().getUserId())))
        {
            holder.binding.llmy.setVisibility(View.VISIBLE);
            holder.binding.llOther.setVisibility(View.GONE);
            //holder.binding.tvName.setText(messageList.get(position).getName()!=null?messageList.get(position).getName():"");
            if(messageList.get(position).getDate()!=null&&!messageList.get(position).getDate().equalsIgnoreCase(""))
            {
                holder.binding.tvTimeDate.setText(CommonUtils.getTimeStampDate(messageList.get(position).getDate()));

            }
            else
            {
                holder.binding.tvTimeDate.setText("");
            }
            if(messageList.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("1")||messageList.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("2"))
            {
                holder.binding.tvMessage.setText(messageList.get(position).getMessage()!=null?messageList.get(position).getMessage():"");
                holder.binding.tvMessage.setVisibility(View.VISIBLE);
                holder.binding.ivImage.setVisibility(View.GONE);
            }
            else
            {
                if(messageList.get(holder.getAdapterPosition()).getMessage()!=null&&!messageList.get(holder.getAdapterPosition()).getMessage().equalsIgnoreCase(""))
                {
                    Glide.with(context).load(messageList.get(holder.getAdapterPosition()).getMessage()).apply(new RequestOptions().placeholder(R.drawable.gal)).into(holder.binding.ivImage);
                }
                else
                {
                    holder.binding.ivImage.setImageResource(R.drawable.gal);
                }

                holder.binding.ivImage.setVisibility(View.VISIBLE);
                holder.binding.tvMessage.setVisibility(View.GONE);
            }

        }
        /*oponent message receiveing from other user*/
        else
        {
            holder.binding.llmy.setVisibility(View.GONE);
            holder.binding.llOther.setVisibility(View.VISIBLE);
            //holder.binding.tvOtherName.setText(messageList.get(position).getName()!=null?messageList.get(position).getName():"");
            if(messageList.get(position).getDate()!=null&&!messageList.get(position).getDate().equalsIgnoreCase(""))
            {
                holder.binding.tvOtherTimeDate.setText(CommonUtils.getTimeStampDate(messageList.get(position).getDate()));

            }
            else
            {
                holder.binding.tvOtherTimeDate.setText("");
            }
            if(messageList.get(holder.getAdapterPosition()).getType().equalsIgnoreCase("1"))
            {
                holder.binding.tvOtherMessage.setText(messageList.get(position).getMessage()!=null?messageList.get(position).getMessage():"");
                holder.binding.tvOtherMessage.setVisibility(View.VISIBLE);
                holder.binding.ivOtherImage.setVisibility(View.GONE);
            }
            else
            {

                if(messageList.get(holder.getAdapterPosition()).getMessage()!=null&&!messageList.get(holder.getAdapterPosition()).getMessage().equalsIgnoreCase(""))
                {
                    Glide.with(context).load(messageList.get(holder.getAdapterPosition()).getMessage()).apply(new RequestOptions().placeholder(R.drawable.gal)).into(holder.binding.ivOtherImage);
                }
                else
                {
                    holder.binding.ivOtherImage.setImageResource(R.drawable.gal);
                }
                holder.binding.ivOtherImage.setVisibility(View.VISIBLE);
                holder.binding.tvOtherMessage.setVisibility(View.GONE);
            }
      }



    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }
}