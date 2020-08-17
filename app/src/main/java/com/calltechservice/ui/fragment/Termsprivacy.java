package com.calltechservice.ui.fragment;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.TermprivacyBinding;
import com.calltechservice.model.response.AboutUsResponse;
import com.calltechservice.ui.activity.HomeActivity;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Termsprivacy extends BaseFragment {


    private TermprivacyBinding binding;
    AboutUsResponse model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        callaboutusapi();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.termprivacy, container, false);
        ((HomeActivity) requireActivity()).changeIcon(false);
        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );


    }



    private void callaboutusapi() {


        apiService.calltermapi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&!commonResponse.getData().equals("")) {

                        model= (AboutUsResponse) commonResponse.getData();

                        binding.tvdescription.setText(utils.fromHtml(model.getDescription().toString()));



                    } else{
                        // binding.lytTop.setVisibility(View.GONE);
                        utils.simpleAlert(getContext(),getString(R.string.error),commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(getContext(),getString(R.string.error),getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(getContext(),getString(R.string.error),throwable.getMessage());
                    }
                });

    }



}
