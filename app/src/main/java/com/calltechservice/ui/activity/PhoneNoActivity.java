package com.calltechservice.ui.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityMobileBinding;

import java.net.ConnectException;
import java.util.Random;
import java.util.regex.Pattern;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PhoneNoActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    View view;
    private ViewPager flContainer;
    private ActivityMobileBinding binding;
    String ccp = "";
    private String otp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile);
        lickListener();
        setToolbar();

        ccp = binding.ccp.getSelectedCountryCode();

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
    public void lickListener() {
        binding.btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnNext:
                if(validation()){
                    callphoneapi(binding.edtMobile.getText().toString());
                }




                break;
            default:
                break;
        }

    }

    private boolean validation() {
        boolean valid = true;
        if (!Pattern.matches("[a-zA-Z]+", binding.edtMobile.getText().toString())) {

            binding.edtMobile.setError(null);
            if (binding.edtMobile.getText().toString().length() < 6 || binding.edtMobile.getText().toString().length() > 13) {
                valid = false;
                binding.edtMobile.setError("Enter a valid phone number");
            } else {
                binding.edtMobile.setError(null);
            }
        } else {
            binding.edtMobile.setError("Enter a valid phone number");
            valid = false;
        }
        return valid;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void callphoneapi(String number) {
        ccp = binding.ccp.getSelectedCountryCode();
        Random random = new Random();
        otp = String.format("%04d", random.nextInt(10000));
        apiService.callphoneverified(ccp+number)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
               // .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1) {


                        callotpapi(number,otp);

                        Log.d("ccode",ccp);






                    } else{
                        // binding.lytTop.setVisibility(View.GONE);
                        utils.simpleAlert(this,getString(R.string.error),commonResponse.getMessage());

                        Log.d("ccode",ccp);
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



    private void callotpapi(String mobileNumber,String otp) {

        ccp = binding.ccp.getSelectedCountryCode();
        apiService.callphoneotp(mobileNumber,otp,ccp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&!commonResponse.getData().equals("")) {


                        binding.progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(this, VerifyOtpActivity.class);
                        intent.putExtra("mobile",mobileNumber);
                        intent.putExtra("ccp",ccp);
                        intent.putExtra("otp",otp);
                        startActivity(intent);








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