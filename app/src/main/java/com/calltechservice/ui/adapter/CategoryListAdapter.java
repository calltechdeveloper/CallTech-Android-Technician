package com.calltechservice.ui.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.calltechservice.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SILU_NVS on 02-Apr-18.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {





    public CategoryListAdapter() {


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvCategoryName;
        public MyViewHolder(View view) {
            super(view);
            //binding = DataBindingUtil.bind(view);

            this.ivImage = view.findViewById(R.id.ivImage);
            this.tvCategoryName = view.findViewById(R.id.tvCategoryName);
        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        View view = holder.itemView;

        ImageView imageView = holder.ivImage;
        TextView text_view = holder.tvCategoryName;

        if (position==0){
            //imageView.setImageResource(R.drawable.mob);
            text_view.setText("Beauty Service");
        }
        if (position==1){
            //imageView.setImageResource(R.drawable.tv);
            text_view.setText("Electrician");
        }
        if (position==2){
            //imageView.setImageResource(R.drawable.home);
            text_view.setText("Plumber");
        }
        if (position==3){
            //imageView.setImageResource(R.drawable.fashion);
            text_view.setText("Carpenter");
        }
        if (position==4){
            //imageView.setImageResource(R.drawable.f1);
            text_view.setText("Ac repair");
        }
       if (position==5){
            //imageView.setBackgroundResource(R.drawable.mob);
            text_view.setText("Pest Control");
        }
        /* if (position==6){
            imageView.setBackgroundResource(R.drawable.mob);
            text_view.setText("Mobile");
        }
        if (position==7){
            imageView.setBackgroundResource(R.drawable.mob);
            text_view.setText("Mobile");
        }*/



    }

    @Override
    public int getItemCount() {
        return 6;
    }

    private String getFormattedDate(String incomingDate) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String formatedDate="";
        try {
            date = fmt.parse(incomingDate);
            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yyyy");
            formatedDate=fmtOut.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatedDate;
    }
}