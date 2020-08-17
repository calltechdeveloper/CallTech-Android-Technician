package com.calltechservice.ui.fragment;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.FragmentRatingCommentBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.utils.CommonUtils;

public class RatingCommentFragment extends BaseFragment implements View.OnClickListener {

    private FragmentRatingCommentBinding binding;
    private OnGoingJobResponse onGoingJobResponse;

    public RatingCommentFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rating_comment, container, false);
        requireActivity().setTitle("Feedback");
        ((HomeActivity) requireActivity()).changeIcon(false);
        setListener();
        setData();
        return binding.getRoot();
    }

    private void setData() {
       /* onGoingJobResponse=getArguments().getParcelable("details");
        if(onGoingJobResponse.getEmployee()!=null)
        {
            if(onGoingJobResponse.getEmployee().getmProfilePic()!=null&&!onGoingJobResponse.getEmployee().getmProfilePic().equalsIgnoreCase(""))
            {
                Glide.with(requireActivity()).load(onGoingJobResponse.getEmployee().getmProfilePic()).into(binding.ivEmpProfile);
            }
            else
            {
                binding.ivEmpProfile.setImageResource(R.drawable.user);
            }
            binding.tvEmpName.setText(onGoingJobResponse.getEmployee().getmName()!=null?onGoingJobResponse.getEmployee().getmName():"");
        }*/
    }

    private void setListener() {
        binding.btSubmit.setOnClickListener(this);
    }

    private boolean validation() {
        if (binding.ratingBar.getRating() == 0) {
            CommonUtils.showSnack(binding.getRoot(), getString(R.string.rate_employee));
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btSubmit) {
            if (validation()) {
                if (CommonUtils.isOnline(requireActivity())) {
                    callShareFeedBackApi();
                } else {
                    CommonUtils.showSnack(binding.getRoot(), getString(R.string.internet_connection));
                }
            }
        }
    }


    private void callShareFeedBackApi() {
        /*showProgressDialog();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("provider_id",onGoingJobResponse.getEmployee().getmEmpId());
        jsonObject.addProperty("job_id", onGoingJobResponse.getJobId());
        jsonObject.addProperty("rating", binding.ratingBar.getRating()+"");
        jsonObject.addProperty("comment", binding.etComment.getText().toString().trim());
        jsonObject.addProperty("rquest","feedback");
        Call<CommonResponse> commonResponseCall= APIExecutor.getApiService(requireActivity()).callFeedback(jsonObject);

        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                if(response.body()!=null&&response.body().getmStatus()!=null&&response.body().getmStatus().equalsIgnoreCase("1"))
                {
                    Fragment fragment=new OnGoingFragment();
                    CommonUtils.alertMessage(requireActivity(),response.body().getmMessage()!=null?response.body().getmMessage()+"\n"+"You want to rate this again??":"",fragment);
                }
                else
                {
                    CommonUtils.showSnack(binding.getRoot(),requireActivity().getString(R.string.server_not_responding));

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });*/
    }

}