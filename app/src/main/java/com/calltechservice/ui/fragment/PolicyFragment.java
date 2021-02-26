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
import com.calltechservice.databinding.FragmentPolicyBinding;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PolicyFragment extends BaseFragment {

    private FragmentPolicyBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int type = bundle.getInt("type");
            if (type == 0) {
                callTermsPolicyAPI("about");
            } else if (type == 1) {
                callTermsPolicyAPI("term");
            } else {
                callTermsPolicyAPI("contact");
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_policy, container, false);
        //String terms=getArguments()==null?"term":"privacy";

        return binding.getRoot();
    }

    private void callTermsPolicyAPI(String terms) {
        apiService.callTermsPolicyAPI(terms)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(response -> {
                    if (response.getStatus() == 1 && response.getData() != null) {
                        binding.tvMessage.setText(utils.fromHtml(response.getData().getDescription()));
                    } else {
                        utils.simpleAlert(requireActivity(), "Error", response.getMessage());
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

}
