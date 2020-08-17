package com.calltechservice.ui.activity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityNewJobBinding;
import com.calltechservice.model.request.SendInvitationRequest;
import com.calltechservice.ui.fragment.MyJobFragment;
import com.calltechservice.utils.CommonUtils;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewJobActivity extends BaseActivity implements View.OnClickListener {

    private ActivityNewJobBinding binding;
    private SendInvitationRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new_job);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_job);
        request =  request=getIntent().getParcelableExtra("jobRequest");
        setToolbar();
        setListener();
    }


    private void setListener(){
        binding.tvDate.setOnClickListener(this);
        binding.tvTime.setOnClickListener(this);
        binding.btDone.setOnClickListener(this);

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



    private void setToolbar(){
        setSupportActionBar(binding.toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New Job");
        //binding.toolbar.tvTitle("SIGN UP");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private boolean validation() {
        if (!binding.cbImmediate.isChecked() && TextUtils.isEmpty(binding.tvDate.getText().toString().trim())) {
            CommonUtils.showSnack(binding.getRoot(), getString(R.string.req_type));
            return false;
        } else if (TextUtils.isEmpty(binding.etJobDetails.getText().toString().trim())) {
            CommonUtils.showSnack(binding.getRoot(), getString(R.string.enter_job_details));
            return false;
        } else {
            return true;
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tvDate:
                CommonUtils.datePickerDialog(this, binding.tvDate);
                break;
            case R.id.tvTime:
                CommonUtils.setTime(this, binding.tvTime);
                break;

            case R.id.btDone:



                if (validation()) {
                    request.setDescription(binding.etJobDetails.getText().toString().trim());
                    if (binding.cbImmediate.isChecked()) {
                        request.setIsImmediate("1");
                        request.setScheduleDate("");
                    }else {
                        request.setIsImmediate("0");
                        Log.e("scduleDate1", binding.tvDate.getText().toString().trim() + " " + "00:00");
                        if (TextUtils.isEmpty(binding.tvTime.getText().toString().trim())) {
                            request.setScheduleDate(binding.tvDate.getText().toString().trim() + " " + "00:00");
                            // sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+"00:00"));
                            Log.e("TimeStamp1", binding.tvDate.getText().toString().trim() + " " + "00:00");
                        } else {
                            request.setScheduleDate(binding.tvDate.getText().toString().trim() + " " + binding.tvTime.getText().toString().trim());
                            //sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+scheduleBinding.tvTime.getText().toString().trim()));
                            Log.e("TimeStamp2", binding.tvDate.getText().toString().trim() + " " + binding.tvTime.getText().toString().trim() + "");

                        }
                    }


                    callSendInvitationsApi(request);

                }

                /* if (validation()) {
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
                    sendInvitationRequest.setUserId(SharedPref.getPreferencesString(requireActivity(), AppConstant.USER_ID));
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
                }*/


                break;

                default:
                    break;



        }

    }


    private void callSendInvitationsApi(SendInvitationRequest request) {


        apiService.callSendInvitationAPI(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {

                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null) {

                       /* Fragment fragment = new MyJobFragment();
                        CommonUtils.commonAlert(this, commonResponse.getMessage(), fragment);*/

                       //utils.toaster(commonResponse.getMessage());

                        Fragment fragment = new MyJobFragment();
                        CommonUtils.setFragment(fragment,true, (FragmentActivity) this, R.id.flContainerHome);

                        //Fragment fragment = new NewJobFragment();
                        //CommonUtils.setFragment(fragment,false, (FragmentActivity) requireActivity(), R.id.flContainerHome);

                    } else{
                        utils.simpleAlert(this,this.getString(R.string.error),commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(this,getString(R.string.error),getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(this,getString(R.string.error),throwable.getMessage());
                    }
                });
    }
}
