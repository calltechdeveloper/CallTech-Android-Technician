package com.calltechservice;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.util.Pair;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.calltechservice.api.ApiService;
import com.calltechservice.db.UserPref;
import com.calltechservice.utils.TransitionHelper;
import com.calltechservice.utils.Utils;
import javax.inject.Inject;
import dagger.android.AndroidInjection;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Inject
    public Utils utils;

    @Inject
    public UserPref userPref;

    @Inject
    public ApiService apiService;

    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    protected void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
        fragmentTransaction.replace(R.id.frameContainer, fragment, fragment.getClass().getName()).commit();
    }

    protected void transitionTo(Intent i) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(i, transitionActivityOptions.toBundle());
    }

    protected void showProgressDialog() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_MinWidth);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
