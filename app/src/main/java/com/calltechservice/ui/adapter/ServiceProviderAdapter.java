package com.calltechservice.ui.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.databinding.ServiceProviderListItemBinding;

import com.calltechservice.R;
import com.calltechservice.model.response.ServiceProdersModel;
import com.calltechservice.model.response.employee.EmployeeData;
import com.calltechservice.utils.CommonUtils;

import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.MyViewHolder> {

    private Context context;
    private List<EmployeeData> employeeDatas;
    /*  private MyListener mListener;*/
    private String subCatId;
    private OnItemClickListener listener;
    private List<ServiceProdersModel> prodersModelsList;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ServiceProviderListItemBinding binding;


        public MyViewHolder(View view) {
            super(view);
            binding= DataBindingUtil.bind(view);
            binding.llMain.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listener != null)
                listener.onItemClick(getAdapterPosition(), v);
        }
    }

    public ServiceProviderAdapter(Context context, List<EmployeeData> employeeDatas, String subCatId) {
        this.context=context;
        this.employeeDatas=employeeDatas;
        this.subCatId=subCatId;

    }

    public ServiceProviderAdapter(Context context, List<ServiceProdersModel> prodersModelsList) {
        this.context=context;
        this.prodersModelsList=prodersModelsList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_provider_list_item, parent, false);
        CommonUtils.setAnimation(itemView,context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ServiceProdersModel model = prodersModelsList.get(holder.getAdapterPosition());

        holder.binding.tvUserName.setText(model.getName());
        holder.binding.tvAddress.setText(model.getLocation());
        holder.binding.ratingBar.setRating(Float.parseFloat(model.getRating()));
        //binding.ratingBar.setRating(Float.parseFloat(model.getRating()));

        if (model.getProfile_pic() != null && !model.getProfile_pic().equalsIgnoreCase("")) {

            Glide.with(context)
                    .load(Uri.parse(model.getProfile_pic())).apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .apply(RequestOptions.errorOf(R.drawable.ic_user))
                    .into(holder.binding.ivProfile);
        }else {
            holder.binding.ivProfile.setImageResource(R.drawable.ic_user);
        }




        holder.binding.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodersModelsList.get(holder.getAdapterPosition()).setSelected(holder.binding.cbSelect.isChecked());
                notifyDataSetChanged();
              /*  if(mListener != null){
                    mListener.onNotify(holder.getAdapterPosition(),holder.binding.cbSelect.isChecked());
                }*/
            }
        });

       /* holder.binding.cbSelect.setSelected(employeeDatas.get(holder.getAdapterPosition()).isSelected());
        holder.binding.tvUserName.setText(employeeDatas.get(holder.getAdapterPosition()).getmName()!=null?employeeDatas.get(holder.getAdapterPosition()).getmName():"");
        holder.binding.tvNoOfJobDone.setText(employeeDatas.get(holder.getAdapterPosition()).getmJobDone()!=null?employeeDatas.get(holder.getAdapterPosition()).getmJobDone():"");
        if(employeeDatas.get(holder.getAdapterPosition()).getmRating()!=null&&!employeeDatas.get(holder.getAdapterPosition()).getmRating().equalsIgnoreCase(""))
        {
            holder.binding.ratingBar.setRating(Float.valueOf(employeeDatas.get(holder.getAdapterPosition()).getmRating()));
        }
        else
        {
            holder.binding.ratingBar.setRating(Float.valueOf(3.0f));
        }

        if(employeeDatas.get(holder.getAdapterPosition()).getmProfilePic()!=null&&!employeeDatas.get(holder.getAdapterPosition()).getmProfilePic().equalsIgnoreCase(""))
        {
            Glide.with(context).load(employeeDatas.get(holder.getAdapterPosition()).getmProfilePic()).apply(new RequestOptions().placeholder(R.drawable.user)).into(holder.binding.ivProfile);
        }
        else
        {
            holder.binding.ivProfile.setImageResource(R.drawable.user);
        }

        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new ProviderDetailsFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("details",employeeDatas.get(holder.getAdapterPosition()));
                bundle.putString("sub_cat",subCatId);
                fragment.setArguments(bundle);
                CommonUtils.setFragment(fragment,false, (FragmentActivity) context, R.id.flContainerHome);
            }
        });
        holder.binding.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeDatas.get(holder.getAdapterPosition()).setSelected(holder.binding.cbSelect.isChecked());
                notifyDataSetChanged();
              *//*  if(mListener != null){
                    mListener.onNotify(holder.getAdapterPosition(),holder.binding.cbSelect.isChecked());
                }*//*
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return prodersModelsList.size();
    }

   /* public interface MyListener{
        void onNotify(int pos,boolean status);
    }

    public void setListener(MyListener listener){
        mListener = listener;
    }*/


    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        public void onItemClick(int position, View view);
    }
}