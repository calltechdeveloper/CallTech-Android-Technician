package com.calltechservice.ui.activity;

import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.calltechservice.R;
import com.calltechservice.databinding.ActivityNewUserBinding;

public class NewUserActivity extends AppCompatActivity {

    private ActivityNewUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_user);

        binding.btNewUser.setOnClickListener(v -> {
            Intent intent = new Intent(this, PhoneNoActivity.class);
            startActivity(intent);
        });

        binding.btSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}