package com.calltechservice.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityLoginBinding;
import com.calltechservice.ui.fragment.ForgotPasswordFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class LoginActivity extends BaseActivity implements View.OnTouchListener {

    //https://www.tutorialkart.com/kotlin-android/android-show-hide-password-in-edittext/
    private ActivityLoginBinding binding;
    private boolean isVisible;
    private RadioButton radioButton;
    int type;
    String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_logon);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        addListenerOnButton();
        setToolbar();
        binding.btSubmit.setOnClickListener(v -> {
            if (validation()) {
                callLogin();
            }
      /* Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);*/

            //utils.toaster("Working...");
        });

        binding.tvForgotPass.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
            forgotPasswordFragment.show(fm, "Forgot Password?");
        });

        //binding.lytVisiblePass.setOnTouchListener(this);

        binding.lytVisiblePass.setOnClickListener(v -> {
            if (isVisible) {
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //Toast.makeText(this,"hide",Toast.LENGTH_SHORT).show();
                binding.ivVisiblePass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.not_visible));
                isVisible = false;
            } else {
                //Toast.makeText(this,"show",Toast.LENGTH_SHORT).show();
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.ivVisiblePass.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.not_visible_hover));
                isVisible = true;
            }
        });

    }

    public void addListenerOnButton() {
        int selectedId = binding.rgAccount.getCheckedRadioButtonId();

        // find the radiobutton by returned id
        radioButton = (RadioButton) findViewById(selectedId);
    }

    public boolean validation() {
        boolean valid = true;

        if (binding.edtEmail.getText().toString().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText().toString()).matches()) {
            binding.edtEmail.setError("Please enter email id");
            valid = false;
        } else {
            binding.edtEmail.setError(null);
        }

        if (binding.edtPassword.getText().toString().isEmpty()) {
            binding.edtPassword.setError("Please enter password");
            valid = false;
        } else {
            binding.edtPassword.setError(null);
        }

        if (radioButton.getText() == null && radioButton.getText().equals("")) {
            Toast.makeText(this, "please select your type", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            if (radioButton.getText().equals("Company")) {
                String btn = radioButton.getText().toString();
                type = 0;
            } else {
                type = 1;
            }
        }

        return valid;
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
        setTitle("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view.getId() == R.id.lytVisiblePass) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
                    binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Toast.makeText(this, "hide", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        return true;
        //return false;
    }

    private void callLogin() {
//        LoginRequest request = new LoginRequest();
//        request.setEmail(binding.edtEmail.getText().toString());
//        request.setPassword(binding.edtPassword.getText().toString());
//        request.setDevice_id(SharedPref.getPreferencesDevice(this, AppConstant.DEVICE_ID));
//        request.setDevice_type("Android");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceToken = instanceIdResult.getToken();
            }
        });
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", binding.edtEmail.getText().toString());
        jsonObject.addProperty("password", binding.edtPassword.getText().toString());
        jsonObject.addProperty("device_id", deviceToken);
        jsonObject.addProperty("device_type", "A");
        jsonObject.addProperty("type", type);

        apiService.callLoginApi(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(response -> {
                    if (response.getStatus() == 1) {
                        userPref.setLogin(true);
                        userPref.setUser(response.getData());
                        startActivity(new Intent(this, HomeActivity.class));
                        finish();
                    } else
                        utils.simpleAlert(this, getString(R.string.error), response.getMessage());
                }, e -> {
                    hideProgressDialog();
                    if (e instanceof ConnectException) {
                        utils.simpleAlert(this, getString(R.string.error), getString(R.string.check_network_connection));
                    } else {
                        utils.simpleAlert(this, getString(R.string.error), e.getMessage());
                    }
                });
    }

}