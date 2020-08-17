package com.calltechservice.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;


import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.NotificationdetaisBinding;
import com.calltechservice.ui.activity.HomeActivity;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NotificationDetailsFragment extends BaseFragment {
    private NotificationdetaisBinding binding;
  Context context;
  String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("notificationid");
            callnotificationapi( id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.notificationdetais, container, false);
        View view = binding.getRoot();
        requireActivity().setTitle("Notification Detail");
        ((HomeActivity) requireActivity()).changeIcon(false);

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private void callnotificationapi(String id) {
        apiService.callnotificationdetailsApi(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(response -> {
                    if (response.getStatus() == 1&&response.getData()!=null) {
                        binding.tvDate.setText(response.getData().getDate()!=null?response.getData().getDate():"");
                        binding.tvTitle.setText(response.getData().getTitle()!=null?response.getData().getTitle():"");
                        binding.tvNotificationDetails.setText(response.getData().getMessage()!=null?response.getData().getMessage():"");
                    } else {


                        utils.simpleAlert(requireActivity(), "Error", response.getMessage());
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),requireActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),throwable.getMessage());
                    }                       });
    }



}
