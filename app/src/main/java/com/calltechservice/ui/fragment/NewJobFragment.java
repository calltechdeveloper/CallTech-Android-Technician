package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentNewJobBinding;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.utils.CommonUtils;

import java.util.Objects;

public class NewJobFragment extends BaseFragment implements View.OnClickListener {

    private FragmentNewJobBinding binding;

    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_job, container, false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);

        setListener();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.post, menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_post:
                Fragment fragment = new MyJobFragment();
                CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);
                //Snackbar.make(binding.getRoot(),"Working", Snackbar.LENGTH_SHORT).show();
               /* if (validation()) {
                    if (CommonUtils.isOnline(getActivity())) {
                        //callUpdateProfileApi();
                    } else {
                        Snackbar.make(binding.getRoot(), getString(R.string.internet_connection), Snackbar.LENGTH_SHORT).show();
                    }
                }*/

                return true;
        }
        return onOptionsItemSelected(item);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    private void setListener(){
        binding.tvDate.setOnClickListener(this);
        binding.tvTime.setOnClickListener(this);
        binding.btDone.setOnClickListener(this);

        /*NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.payment_cart);*/
        ((HomeActivity) getActivity()).changeIcon(false);
        //getActivity().setTitle("Payment");
        Objects.requireNonNull(getActivity()).setTitle("New job");

        binding.cbImmediate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    binding.llDateTime.setVisibility(View.GONE);//TO HIDE THE BUTTON
                else
                    binding.llDateTime.setVisibility(View.VISIBLE);
            }
        });
    }







    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvDate:
                CommonUtils.datePickerDialog(getContext(), binding.tvDate);
                break;
            case R.id.tvTime:
                CommonUtils.setTime(getContext(), binding.tvTime);
                break;

            /*case R.id.btDone:
                *//* if (validation()) {
                    StringBuilder empId = new StringBuilder("");
                    for (EmployeeData data : employeeDatas) {
                        if (data.isSelected()) {
                            if (empId.toString().length() > 0) {
                                empId.append(", " + data.getmEmpId());
                            } else {
                                empId.append(data.getmEmpId());
                            }
                        }

                   }
                    Log.e("EMpoyeeId",empId.toString());
                    SendInvitationRequest sendInvitationRequest = new SendInvitationRequest();
                    sendInvitationRequest.setUserId(SharedPref.getPreferencesString(getActivity(), AppConstant.USER_ID));
                    sendInvitationRequest.setDescription(scheduleBinding.etJobDetails.getText().toString().trim());
                    sendInvitationRequest.setTitle(subCatName);
                    sendInvitationRequest.setEmpIds(empId);

                    if (scheduleBinding.cbImmediate.isChecked()) {
                        sendInvitationRequest.setIsImmediate("1");
                        sendInvitationRequest.setScheduleDate("");
                        sendInvitationRequest.setRquest("sendInvitations");


                    } else {
                        sendInvitationRequest.setIsImmediate("0");
                        sendInvitationRequest.setRquest("sendInvitations");
                        Log.e("scduleDate1",scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                        if (TextUtils.isEmpty(scheduleBinding.tvTime.getText().toString().trim())) {
                            sendInvitationRequest.setScheduleDate(scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                            // sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+"00:00"));
                            Log.e("TimeStamp1", scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                        } else {
                            sendInvitationRequest.setScheduleDate(scheduleBinding.tvDate.getText().toString().trim() + " " + scheduleBinding.tvTime.getText().toString().trim());
                            //sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+scheduleBinding.tvTime.getText().toString().trim()));
                            Log.e("TimeStamp2", scheduleBinding.tvDate.getText().toString().trim() + " " + scheduleBinding.tvTime.getText().toString().trim() + "");

                        }
                    }

                 callSendInvitationsApi(sendInvitationRequest);
                }*//*

                Fragment fragment = new MyJobFragment();
                CommonUtils.setFragment(fragment,true, (FragmentActivity) getActivity(), R.id.flContainerHome);
                break;*/

                default:
                    break;



        }

    }
}
