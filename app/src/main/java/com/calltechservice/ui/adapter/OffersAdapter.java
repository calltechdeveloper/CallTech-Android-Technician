package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.OffersListItemBinding;


public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private Context context;
   // private List<OfferModel> offerModels;

    /*public OffersAdapter(Context context, List<OfferModel> offerModels) {
        this.context = context;
        this.offerModels = offerModels;

    }*/
    public OffersAdapter(Context context) {
        this.context = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offers_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        /*OfferModel model = offerModels.get(holder.getAdapterPosition());

        holder.binding.tvTitle.setText(model.getCoupon_title()!= null ?model.getCoupon_title():"" );
        holder.binding.tvSubject.setText(model.getCoupon_description()!= null ?model.getCoupon_description():"" );
        holder.binding.tvCoupon.setText(model.getCoupon_code()!= null ?model.getCoupon_code():"" );*/



    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        OffersListItemBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,DoctorProfileActivity.class));
                }
            });*/
        }
    }


}