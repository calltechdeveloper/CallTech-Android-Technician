package com.calltechservice.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.db.SharedPref;
import com.calltechservice.utils.AppConstant;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.Subscribe;

public class SplashActivity extends BaseActivity {
    private String TAG=SplashActivity.class.getName();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=SplashActivity.this;
        String deviceToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG,getString(R.string.device_token)+deviceToken);
        SharedPref.savePreferencesDevice(getApplicationContext(), AppConstant.DEVICE_ID,deviceToken);
        checkStatus();
    }

    private void checkStatus()
    {


        new Handler().postDelayed(() -> {
            if(userPref.isLogin())
            {
                startActivity(new Intent(this, HomeActivity.class));
            }
            else
            {
                startActivity(new Intent(this, DemoActivity.class));
            }
            finish();

        },2000);



    }


    @Subscribe
    public void onEventMainThread(String Token) {
        SharedPref.savePreferencesDevice(getApplicationContext(), AppConstant.DEVICE_ID,Token);
        Intent intent = new Intent(mContext, DemoActivity.class);
        startActivity(intent);
        finish();
    }

}
