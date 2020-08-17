package com.calltechservice.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.HistoryDetailsBinding;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.InvitedEmpolyeeListAdapter;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.PermissionUtils;
import com.google.android.material.navigation.NavigationView;

public class HistoryDetailsFragment extends BaseFragment implements View.OnClickListener {
    private HistoryDetailsBinding binding;
    private LinearLayoutManager layoutManager;
    private InvitedEmpolyeeListAdapter inviteCartAdapter;
    private OnGoingJobResponse onGoingJobResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.history_details, container, false);
        View view = binding.getRoot();
        setRecyclerView();
        setListener();
        return view;
    }

    private void setListener() {
        //binding.tvMobileNo.setOnClickListener(this);

    }

    private void setRecyclerView() {
        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) requireActivity()).changeIcon(false);
        requireActivity().setTitle("Completed");

       /* onGoingJobResponse=getArguments().getParcelable("details");
        NavigationView navigationView = (NavigationView) requireActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) requireActivity()).changeIcon(false);
        requireActivity().setTitle("Jobs");
        binding.tvTitle.setText(onGoingJobResponse.getTitle()!=null?onGoingJobResponse.getTitle():"");
        binding.tvComment.setText(onGoingJobResponse.getComment()!=null?onGoingJobResponse.getComment():"");
        binding.tvDescription.setText(onGoingJobResponse.getDescription()!=null?onGoingJobResponse.getDescription():"");
        layoutManager=new LinearLayoutManager(requireActivity());
        binding.tvScheduleDate.setText(requireActivity().getString(R.string.job_completed)+" "+onGoingJobResponse.getScheduleDate());
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
    public void onClick(View view) {
        if (view.getId() == R.id.tvMobileNo) {
            if (PermissionUtils.checkPermissionCall(requireActivity())) {
                String phone = binding.tvMobileNo.getText().toString().trim();
                if (phone != null && !phone.startsWith("0")) {
                    phone = "0" + phone;
                }
                CommonUtils.call(requireActivity(), phone);
            } else {
                PermissionUtils.requestPermissionCall(requireActivity());
            }
        }
    }
}