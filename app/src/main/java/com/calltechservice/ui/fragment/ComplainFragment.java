package com.calltechservice.ui.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.calltechservice.R;
import com.calltechservice.api.APIExecutor;
import com.calltechservice.databinding.ComplainFragmentBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.model.response.CommonResponse;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplainFragment extends BaseFragment implements View.OnClickListener{

    private ComplainFragmentBinding binding;
    private OnGoingJobResponse onGoingJobResponse;

    public ComplainFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater, R.layout.complain_fragment, container, false);
        getActivity().setTitle("Post your Complain");
        ((HomeActivity) getActivity()).changeIcon(false);
        setListener();
        setData();
        return binding.getRoot();
    }

    private void setData() {
        onGoingJobResponse=getArguments().getParcelable("details");
    }

    private void setListener()
    {
        binding.btSubmit.setOnClickListener(this);

    }


    private boolean validation()
    {
        if(TextUtils.isEmpty(binding.etSubject.getText().toString().trim()))
        {
            CommonUtils.showSnack(binding.getRoot(),getString(R.string.enter_subject));
            return false;
        }
        if(TextUtils.isEmpty(binding.etComment.getText().toString().trim()))
        {
            CommonUtils.showSnack(binding.getRoot(),getString(R.string.enter_comment));
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btSubmit:
                if(validation())
                {
                    if(CommonUtils.isOnline(getActivity()))
                    {
                        callShareFeedBackApi();
                    }
                    else
                    {
                        CommonUtils.showSnack(binding.getRoot(),getString(R.string.internet_connection));

                    }
                }
                break;
            default:
                break;
        }
    }



    private void callShareFeedBackApi()
    {
        showProgressDialog();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("job_id", onGoingJobResponse.getJobDetails().getJobId());
        jsonObject.addProperty("comment_title", binding.etSubject.getText().toString().trim());
        jsonObject.addProperty("comment", binding.etComment.getText().toString().trim());
        jsonObject.addProperty("rquest","raiseComplaint");
        Call<CommonResponse> commonResponseCall= APIExecutor.getApiService(getActivity()).callraiseComplaint(jsonObject);

        commonResponseCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                hideProgressDialog();
                if(response.body()!=null&&response.body().getmStatus()!=null&&response.body().getmStatus().equalsIgnoreCase("1"))
                {
                    CommonUtils.commonAlertBackStack(getActivity(),response.body().getmMessage()!=null?response.body().getmMessage():"");
                }
                else
                {
                    CommonUtils.showSnack(binding.getRoot(),getActivity().getString(R.string.server_not_responding));

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                hideProgressDialog();
            }
        });
    }

}
