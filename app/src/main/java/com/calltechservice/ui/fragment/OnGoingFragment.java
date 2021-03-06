package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.R;
import com.calltechservice.databinding.FragmentOnGoingBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.model.response.OnGoingJobResponse;
import com.calltechservice.ui.adapter.OnGoingAdapter;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.RecyclerItemClickListener;
import com.google.gson.JsonObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OnGoingFragment extends BaseFragment {
    private FragmentOnGoingBinding binding;
    private LinearLayoutManager layoutManager;
    private OnGoingAdapter onGoingAdapter;
    private Context context;
    private List<OnGoingJobResponse> onGoingJobResponses, filteredList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onGoingJobResponses = new ArrayList<>();
        filteredList = new ArrayList<>();


       /* Bundle bundle = this.getArguments();
        if (bundle != null) {
            onGoingJobResponses = bundle.getParcelableArrayList("inprogressList");
            filteredList = bundle.getParcelableArrayList("inprogressList");
        } else {
            callInProgressListAPI();
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_on_going, container, false);
        View view = binding.getRoot();
       /* if(CommonUtils.isOnline(getActivity()))
        {
            callInProgressListAPI();
        }
        else
        {
            CommonUtils.showSnack(getActivity().findViewById(android.R.id.content),getActivity().getString(R.string.internet_connection));

        }*/
        setRecyslerViw();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            onGoingJobResponses = bundle.getParcelableArrayList("inprogressList");
            filteredList = bundle.getParcelableArrayList("inprogressList");
        } else {
            callInProgressListAPI();
        }

        return view;
    }




    private void setRecyslerViw()
    {
        onGoingJobResponses=new ArrayList<>();
        layoutManager=new LinearLayoutManager(getActivity());
        binding.rvOngoing.setLayoutManager(layoutManager);
        onGoingAdapter =new OnGoingAdapter(context,onGoingJobResponses);
        onGoingAdapter.setHasStableIds(true);
        binding.rvOngoing.setAdapter(onGoingAdapter);
        binding.rvOngoing.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Fragment fragment=new OnGoingDetailsFragment();

                        Log.e("customer id", "<<>>"+onGoingJobResponses.get(position).getJobDetails().getUserId() +"jobId " +onGoingJobResponses.get(position).getJobDetails().getJobId());

                        Bundle bundle=new Bundle();
                        bundle.putInt("type", 0);
                        bundle.putString("jobId", onGoingJobResponses.get(position).getJobDetails().getJobId());
                        bundle.putString("userId", onGoingJobResponses.get(position).getJobDetails().getUserId());
                        fragment.setArguments(bundle);
                       /* Bundle bundle=new Bundle();
                        bundle.putParcelable("details",onGoingJobResponses.get(position));
                        fragment.setArguments(bundle);*/
                        CommonUtils.setFragment(fragment,false,  getActivity(), R.id.flContainerHome);
                    }
                })
        );

    }

   /* @Override
    public void onResume() {
        super.onResume();
        *//*onGoingJobResponses = new ArrayList<>();
        filteredList = new ArrayList<>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            onGoingJobResponses = bundle.getParcelableArrayList("inprogressList");
            filteredList = bundle.getParcelableArrayList("inprogressList");
        } else {
            callInProgressListAPI();
        }*//*
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }


    private void callInProgressListAPI() {
        onGoingJobResponses.clear();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("provider_id",userPref.getUser().getUserId());

        apiService.callInProgressListAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&commonResponse.getData().size()>0) {
                        onGoingJobResponses.addAll(commonResponse.getData());
                        onGoingAdapter.notifyDataSetChanged();
                    } else{
                        //utils.simpleAlert(getActivity(),"",commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),getActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(getActivity(),getActivity().getString(R.string.error),throwable.getMessage());
                    }
                });
    }


}
