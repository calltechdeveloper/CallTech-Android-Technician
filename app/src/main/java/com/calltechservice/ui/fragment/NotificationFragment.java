package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import com.calltechservice.databinding.FragmentNotificationBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.model.response.NotificationResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.NotificationAdapter;
import com.calltechservice.utils.CommonUtils;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NotificationFragment extends BaseFragment {
    private FragmentNotificationBinding binding;
    private LinearLayoutManager layoutManager;
    private NotificationAdapter notificationAdapter;
    private Context context;
    private List<NotificationResponse> notificationResponses;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notificationResponses= new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false);
        View view = binding.getRoot();
        requireActivity().setTitle("Notification");
        ((HomeActivity) getActivity()).changeIcon(false);
        setRecyslerViw();

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Notification");
        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                Bundle bundle = new Bundle();
                Fragment fragment = null;
                bundle.putString("notificationid",notificationResponses.get(position).getId() );
                fragment = new NotificationDetailsFragment();
                fragment.setArguments(bundle);
                CommonUtils.setFragment(fragment,true, (FragmentActivity) getActivity(), R.id.flContainerHome);

            }
        });
    }

    private void setRecyslerViw()
    {
        notificationResponses=new ArrayList<>();
        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.notification);
        ((HomeActivity) getActivity()).changeIcon(false);
        getActivity().setTitle("Notification");
        layoutManager=new LinearLayoutManager(getActivity());
        binding.rvNotification.setLayoutManager(layoutManager);
        notificationAdapter =new NotificationAdapter(context,notificationResponses);
        binding.rvNotification.setAdapter(notificationAdapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private void callnotification() {
        notificationResponses.clear();


        apiService.callnotificationapi(userPref.getUser().getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null && commonResponse.getData().size() > 0) {
                        notificationResponses.addAll(commonResponse.getData());
                        notificationAdapter.notifyDataSetChanged();


                    } else {
                        //utils.simpleAlert(getActivity(),"",commonResponse.getMessage());

                        binding.listtag.setVisibility(View.VISIBLE);
                        hideProgressDialog();
                    }
                }, throwable -> {

                    hideProgressDialog();
                    if (throwable instanceof ConnectException) {
                        utils.simpleAlert(getActivity(), getActivity().getString(R.string.error), getActivity().getString(R.string.check_network_connection));
                    } else {
                        utils.simpleAlert(getActivity(), getActivity().getString(R.string.error), throwable.getMessage());
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();


        if(CommonUtils.isOnline(getActivity()))
        {
            callnotification();
        }
        else
        {
            CommonUtils.showSnack(getActivity().findViewById(android.R.id.content),getActivity().getString(R.string.internet_connection));

        }

    }
}
