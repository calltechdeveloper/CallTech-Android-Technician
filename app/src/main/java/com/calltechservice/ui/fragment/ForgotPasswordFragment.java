package com.calltechservice.ui.fragment;


import android.app.ProgressDialog;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.calltechservice.R;
import com.calltechservice.api.ApiService;
import com.calltechservice.databinding.FragmentForgotPasswordBinding;
import com.calltechservice.utils.Utils;
import com.google.gson.JsonObject;

import java.net.ConnectException;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class ForgotPasswordFragment extends DialogFragment implements View.OnClickListener, TextWatcher {

    private AwesomeValidation validation;
    private FragmentForgotPasswordBinding binding;

    public ProgressDialog progressDialog;
    @Inject
    Utils utils;
    @Inject
    ApiService apiService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forgot_password, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setCancelable(false);
        validation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        validation.addValidation(binding.etEmail, Patterns.EMAIL_ADDRESS, "Please enter the valid email id");
        binding.btSend.setOnClickListener(this);
        binding.ivClose.setOnClickListener(this);
        binding.etEmail.getEditText().addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivClose:
                getDialog().dismiss();
                break;

            case R.id.btSend:
                if (validation.validate()) {
                    callForgotPasswordAPI();
                }
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //binding.etEmail.setErrorEnabled(false);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    private void callForgotPasswordAPI() {
        JsonObject request = new JsonObject();
        request.addProperty("email", binding.etEmail.getEditText().getText().toString());
        apiService.callForgotPasswordAPI(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1) {
                        utils.simpleAlert(getActivity(), "Success", commonResponse.getMessage());
                    } else utils.simpleAlert(getActivity(), "Error", commonResponse.getMessage());
                    getDialog().dismiss();
                }, throwable -> {
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


    public void showProgressDialog() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
}