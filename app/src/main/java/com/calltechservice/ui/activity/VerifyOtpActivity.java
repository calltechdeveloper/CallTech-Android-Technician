package com.calltechservice.ui.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityMobileBinding;

import java.net.ConnectException;
import java.util.Random;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class VerifyOtpActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ActivityMobileBinding binding;
    private ViewPager flContainer;
    private CountDownTimer countDownTimer;
    private View view;
    private String mobileNumber = "";
    private String ccp;
    private String otp;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();

        if (bundle != null) {
            mobileNumber = bundle.getString("mobile");
            ccp = bundle.getString("ccp");
            otp = bundle.getString("otp");


        }

        clickListener();

        setToolbar();

        binding.ccp.setVisibility(View.GONE);
        binding.mobilell.setVisibility(View.GONE);
        binding.tvOtp.setVisibility(View.VISIBLE);
        binding.llOTP.setVisibility(View.VISIBLE);


        binding.edtMobile.setHint("Enter OTP");
        binding.edtMobile.setCursorVisible(false);
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        setTitle("");


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void clickListener() {
        binding.btnNext.setOnClickListener(this);
        binding.tvOtp.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
if(validation()) {
   if(otp.equals(binding.edtOtp.getText().toString())){

                        Intent intent = new Intent(this, RegistrationNewActivity.class);
                        intent.putExtra("mobile", mobileNumber);
                        startActivity(intent);
   }
   else{
       binding.edtOtp.setError(getString(R.string.enter_valid_otp_no));
   }
}else {

    binding.edtOtp.setError("Please enter correct OTP");
}

                break;

            case R.id.tvOtp:
                Random random = new Random();
                otp = String.format("%04d", random.nextInt(10000));
                callResendApi(otp);

                break;

            default:
                break;


        }
    }

    private void startTimer() {
       /* countDownTimer=new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.tvTimer.setText(getString(R.string.__00)+millisUntilFinished / 1000);
                binding.tvResend.setEnabled(false);
                //binding.tvResend.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_bg));
            }

            public void onFinish() {
                binding.tvTimer.setText(R.string._00_);
               // binding.tvResend.setEnabled(true);binding.tvResend.setTextColor(ContextCompat.getColor(requireActivity(),R.color.colorPrimary));

            }
        }.start();*/
    }

    private boolean validation() {
        if(TextUtils.isEmpty(binding.edtOtp.getText().toString().trim()))
        {
            binding.edtOtp.setError(getString(R.string.enter_otp_no));
            return false;
        }
        else if(binding.edtOtp.getText().toString().trim().length()<3)
        {
            binding.edtOtp.setError(getString(R.string.enter_valid_otp_no));
            return false;
        }
        else
        {
            return true;
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //binding.etOtp.setError(null);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

 /*   @Override
    public void onDetach() {
        super.onDetach();
        assert countDownTimer!=null;
        countDownTimer.cancel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }*/




//    private void callResendApi(String number) {
//
//
//
//        apiService.callphoneverified(ccp+number)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(this::showProgressDialog)
//                .doOnCompleted(this::hideProgressDialog)
//                .subscribe(commonResponse -> {
//                    if (commonResponse.getStatus() == 1) {
//                        Log.d("ccode",ccp);
////                        Intent intent = new Intent(this, VerifyOtpActivity.class);
////                        intent.putExtra("mobile",binding.edtMobile.getText().toString());
////                        intent.putExtra("ccp",ccp);
////                        startActivity(intent);
//
//
//
//
//
//                    } else{
//                        // binding.lytTop.setVisibility(View.GONE);
//                        utils.simpleAlert(this,getString(R.string.error),commonResponse.getMessage());
//
//                        Log.d("ccode",ccp);
//                        hideProgressDialog();
//                    }
//                }, throwable -> {
//                    hideProgressDialog();
//                    if(throwable instanceof ConnectException)
//                    {
//                        utils.simpleAlert(this,getString(R.string.error),getString(R.string.check_network_connection));
//                    }
//                    else
//                    {
//                        utils.simpleAlert(this,getString(R.string.error),throwable.getMessage());
//                    }
//                });
//
//    }



    private void callResendApi(String otp) {


        apiService.callphoneotp(mobileNumber,otp,ccp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&!commonResponse.getData().equals("")) {

//                        Intent intent = new Intent(this, RegistrationNewActivity.class);
//                        intent.putExtra("mobile", mobileNumber);
//                        startActivity(intent);





                    } else{
                        // binding.lytTop.setVisibility(View.GONE);
                        utils.simpleAlert(this,getString(R.string.error),commonResponse.getMessage());
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