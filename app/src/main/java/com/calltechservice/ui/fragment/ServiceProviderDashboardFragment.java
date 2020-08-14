package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentServiceProviderDashboardBinding;
import com.calltechservice.model.response.CompleteJobResponse;
import com.calltechservice.model.response.JobInvitationResponse;
import com.calltechservice.model.response.ServiceDashboardModel;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.DashboardPagerAdapter;
import com.calltechservice.utils.CommonUtils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class ServiceProviderDashboardFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private FragmentServiceProviderDashboardBinding binding;
    private FrameLayout redCircle;
    private TextView countTextView;
    private int alertCount = 0;
    String deviceToken;
    private ArrayList<JobInvitationResponse> recentList;
    private ArrayList<CompleteJobResponse> inProgressList;
    private DashboardPagerAdapter pagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //callServiceDashboardAPI();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_service_provider_dashboard, container, false);
        setHasOptionsMenu(true);

        ((HomeActivity) getActivity()).changeIcon(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().setTitle("Home");
        setDashboardLyt();
        deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("firebasedevice",deviceToken);
        callServiceDashboardAPI();

        setPager(recentList,inProgressList);

       // setPager(recentList,inProgressList);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notification, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item);

        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NotificationFragment();
                CommonUtils.setFragment(fragment, false, (FragmentActivity) mContext, R.id.flContainerHome);
            }
        });
        /*binding.totalJob.tvTitle.setText("Total In Progress Jobs");
        binding.directmember1.tvTitle.setText("Total Completed Jobs");
        binding.totalincome1.tvTitle.setText("Total New Requested Jobs");
        binding.totalincome.tvTitle.setText("Total Jobs");*/
    }


    private void updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        if (0 < alertCount && alertCount < 10) {
            countTextView.setText(String.valueOf(alertCount));
        } else {
            countTextView.setText("");
        }

        redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);

    }

    private void setPager(ArrayList<JobInvitationResponse> recentInvitationList, ArrayList<CompleteJobResponse> inprogressList){

        pagerAdapter = new DashboardPagerAdapter(getChildFragmentManager(),recentInvitationList,inprogressList);
        binding.viewPager.setAdapter(pagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
    }

    private void setDashboardLyt(){
        binding.progressJob.tvTitle.setText("Total In Progress Jobs");
        binding.completedJob.tvTitle.setText("Total Completed Jobs");
        binding.requestedJob.tvTitle.setText("Total New Invitations");
        binding.totalJob.tvTitle.setText("Total Jobs");

        binding.progressJob.ivIcon.setImageResource(R.drawable.ic_progress_job);
        binding.completedJob.ivIcon.setImageResource(R.drawable.ic_completed_job);
        binding.requestedJob.ivIcon.setImageResource(R.drawable.ic_requested_job);
        binding.totalJob.ivIcon.setImageResource(R.drawable.ic_total_job);
    }


    private void setDashboardValue(ServiceDashboardModel model){
        binding.progressJob.tvNo.setText(model.getInprogress_job()+"");
        binding.completedJob.tvNo.setText(model.getCommplete_job()+"");
        binding.requestedJob.tvNo.setText(model.getRequested_job()+"");
        binding.totalJob.tvNo.setText(model.getTotal_job()+"");
    }


    private void callServiceDashboardAPI() {
        recentList = new ArrayList<>();
        inProgressList = new ArrayList<>();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("user_id",userPref.getUser().getUserId());
        apiService.callServiceDashboardAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData()!=null) {
                        Log.d("firebasedevice",deviceToken);
                        recentList.addAll(commonResponse.getData().getRequested_joblist());
                        inProgressList.addAll(commonResponse.getData().getComplete_joblist());
                        setDashboardValue(commonResponse.getData());


                        /*setPager(recentList,inProgressList);*/
                        pagerAdapter.notifyDataSetChanged();

                    } else{
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
