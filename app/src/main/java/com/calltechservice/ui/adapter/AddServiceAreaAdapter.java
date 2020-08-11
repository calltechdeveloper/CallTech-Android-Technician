package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.AreaServiceListBinding;
import com.calltechservice.model.request.ServiceAreaRequest;

public class AddServiceAreaAdapter extends RecyclerView.Adapter<AddServiceAreaAdapter.MyViewHolder> {

    private Context context;
    private ServiceAreaRequest locatioList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private AreaServiceListBinding binding;

        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);

        }
    }

    public AddServiceAreaAdapter(Context context, ServiceAreaRequest locatioList) {
        this.context=context;
        this.locatioList=locatioList;
    }
 
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_service_list, parent, false);
        return new MyViewHolder(itemView);
    }
 
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.binding.ivServiceName.setText(locatioList.getAreaList().get(holder.getAdapterPosition()).getAreaName());
        holder.binding.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locatioList.getAreaList().remove(holder.getAdapterPosition());
                notifyDataSetChanged();


                for (int i=0; i<locatioList.getAreaList().size();i++){

                    Log.w("data", "<< Location >> " + locatioList.getAreaList().get(i).getAreaName() );

                }
            }
        });
    }
 
    @Override
    public int getItemCount() {
        return locatioList.getAreaList().size();
    }
}