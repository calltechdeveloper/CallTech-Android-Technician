package com.calltechservice.ui.fragment;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.FragmentOnGoingBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.model.response.CompleteJobResponse;
import com.calltechservice.ui.adapter.CompleteJobAdapter;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.RecyclerItemClickListener;
import com.google.gson.JsonObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class JobHistoryFragment extends BaseFragment {
    private FragmentOnGoingBinding binding;
    private LinearLayoutManager layoutManager;
    private CompleteJobAdapter onGoingAdapter;
    private Context context;
    private List<CompleteJobResponse> onGoingJobResponses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onGoingJobResponses = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_going, container, false);
        View view = binding.getRoot();

        // requireActivity().setTitle("Completed");
        setRecyslerViw();

        callCompletejobListAPI();
        /*if(CommonUtils.isOnline(requireActivity()))
        {
            //callJobHistoryApi();
        }
        else
        {
            CommonUtils.showSnack(requireActivity().findViewById(android.R.id.content),requireActivity().getString(R.string.internet_connection));

        }*/

        return view;
    }

    private void setRecyslerViw() {
        onGoingJobResponses = new ArrayList<>();
        layoutManager = new LinearLayoutManager(requireActivity());
        binding.rvOngoing.setLayoutManager(layoutManager);
        onGoingAdapter = new CompleteJobAdapter(context, onGoingJobResponses);
        onGoingAdapter.setHasStableIds(true);
        binding.rvOngoing.setAdapter(onGoingAdapter);
        binding.rvOngoing.addOnItemTouchListener(new RecyclerItemClickListener(requireActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Fragment fragment = new OnGoingDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("type", 1);
                        bundle.putString("jobId", onGoingJobResponses.get(position).getJobDetails().getJobId());
                        bundle.putString("userId", onGoingJobResponses.get(position).getJobDetails().getUserId());
                        fragment.setArguments(bundle);
                        CommonUtils.setFragment(fragment, false, requireActivity(), R.id.flContainerHome);


                       /* Fragment fragment=new HistoryDetailsFragment();
                        Bundle bundle=new Bundle();
                        bundle.putParcelable("details",onGoingJobResponses.get(position));
                        fragment.setArguments(bundle);
                        CommonUtils.setFragment(fragment,false,  requireActivity(), R.id.flContainerHome);*/
                    }
                })
        );

    }

   /* @Override
    public void onResume() {
        super.onResume();
        //callCompletejobListAPI();
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    private void callCompletejobListAPI() {
        onGoingJobResponses.clear();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("provider_id", userPref.getUser().getUserId());

        apiService.callCompletejobListAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null && commonResponse.getData().size() > 0) {
                        onGoingJobResponses.addAll(commonResponse.getData());
                        onGoingAdapter.notifyDataSetChanged();
                    } else {
                        //utils.simpleAlert(requireActivity(),"",commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if (throwable instanceof ConnectException) {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), requireActivity().getString(R.string.check_network_connection));
                    } else {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), throwable.getMessage());
                    }
                });
    }


    private void callJobHistoryApi() {
       /* showProgressDialog();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id", SharedPref.getPreferencesString(requireActivity(), AppConstant.USER_ID));
        jsonObject.addProperty("rquest","jobHistory");
        Call<CommonResponse<OnGoingJobResponse>> commonResponseCall= APIExecutor.getApiService(requireActivity()).callJobHistory(jsonObject);
        commonResponseCall.enqueue(new Callback<CommonResponse<OnGoingJobResponse>>() {
            @Override
            public void onResponse(Call<CommonResponse<OnGoingJobResponse>> call, Response<CommonResponse<OnGoingJobResponse>> response) {
               onGoingJobResponses.clear();
                hideProgressDialog();
                if(response.body()!=null&&response.body().getmStatus()!=null&&response.body().getmStatus().equalsIgnoreCase("1"))
                {
                    if(response.body().getmData()!=null&&response.body().getmData().size()>0)
                    {
                        onGoingJobResponses.addAll(response.body().getmData());
                        onGoingAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse<OnGoingJobResponse>> call, Throwable t) {
                hideProgressDialog();
            }
        });*/
    }
}
