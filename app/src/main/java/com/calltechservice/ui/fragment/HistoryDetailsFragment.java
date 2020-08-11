package com.calltechservice.ui.fragment;

import android.app.Activity;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.databinding.HistoryDetailsBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.InvitedEmpolyeeListAdapter;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.PermissionUtils;

public class HistoryDetailsFragment extends BaseFragment implements View.OnClickListener{
    private HistoryDetailsBinding binding;
    private LinearLayoutManager layoutManager;
    private InvitedEmpolyeeListAdapter inviteCartAdapter;
    private Context context;
    private OnGoingJobResponse onGoingJobResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.history_details, container, false);
        View view = binding.getRoot();
        setRecyslerViw();
        setListener();
        return view;
    }

    private void setListener()
    {
        //binding.tvMobileNo.setOnClickListener(this);

    }
    private void setRecyslerViw()
    {
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) getActivity()).changeIcon(false);
        getActivity().setTitle("Completed");

       /* onGoingJobResponse=getArguments().getParcelable("details");
        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) getActivity()).changeIcon(false);
        getActivity().setTitle("Jobs");
        binding.tvTitle.setText(onGoingJobResponse.getTitle()!=null?onGoingJobResponse.getTitle():"");
        binding.tvComment.setText(onGoingJobResponse.getComment()!=null?onGoingJobResponse.getComment():"");
        binding.tvDescription.setText(onGoingJobResponse.getDescription()!=null?onGoingJobResponse.getDescription():"");
        layoutManager=new LinearLayoutManager(getActivity());
        binding.tvScheduleDate.setText(getActivity().getString(R.string.job_completed)+" "+onGoingJobResponse.getScheduleDate());
        if(onGoingJobResponse.getRating()!=null&&!onGoingJobResponse.getRating().equalsIgnoreCase(""))
        {
            binding.ratingBar.setRating(Float.valueOf(onGoingJobResponse.getRating()));
        }
        else
        {
            binding.ratingBar.setRating(3.5f);
        }

        if(onGoingJobResponse.getEmployee().getAssignedEmp()==null)
        {
            if(onGoingJobResponse.getEmployee().getmProfilePic()!=null&&!onGoingJobResponse.getEmployee().getmProfilePic().equalsIgnoreCase(""))
            {
                Glide.with(context).load(onGoingJobResponse.getEmployee().getmProfilePic()).into(binding.ivEmpProfile);
            }
            else
            {
                binding.ivEmpProfile.setImageResource(R.drawable.user);
            }

            binding.tvName.setText(onGoingJobResponse.getEmployee().getmName()!=null?onGoingJobResponse.getEmployee().getmName():"");
            binding.tvMobileNo.setText(onGoingJobResponse.getEmployee().getmMobile()!=null?onGoingJobResponse.getEmployee().getmMobile():"");
        }
        else
        {
            if(onGoingJobResponse.getEmployee().getAssignedEmp().getProfilePic()!=null&&!onGoingJobResponse.getEmployee().getAssignedEmp().getProfilePic().equalsIgnoreCase(""))
            {
                Glide.with(context).load(onGoingJobResponse.getEmployee().getAssignedEmp().getProfilePic()).into(binding.ivEmpProfile);
            }
            else
            {
                binding.ivEmpProfile.setImageResource(R.drawable.user);
            }
            binding.tvName.setText(onGoingJobResponse.getEmployee().getAssignedEmp().getName()!=null?onGoingJobResponse.getEmployee().getAssignedEmp().getName():"");
            binding.tvMobileNo.setText(onGoingJobResponse.getEmployee().getAssignedEmp().getMobile()!=null?onGoingJobResponse.getEmployee().getAssignedEmp().getMobile():"");

        }*/

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.tvMobileNo:
                if (PermissionUtils.checkPermissionCall(getActivity())) {
                    CommonUtils.call(getActivity(),binding.tvMobileNo.getText().toString().trim());
                }
                else
                {
                    PermissionUtils.requestPermissionCall((Activity) getActivity());
                }
                break;
            default:
                break;
        }
    }
}
