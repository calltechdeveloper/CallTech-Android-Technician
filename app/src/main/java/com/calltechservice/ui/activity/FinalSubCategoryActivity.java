package com.calltechservice.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityCategoryBinding;
import com.calltechservice.model.response.AddRemoveCategoryModel;
import com.calltechservice.ui.adapter.FinalSubCategoryAdapter;
import com.calltechservice.utils.Utils;
import com.google.gson.JsonObject;

import java.net.ConnectException;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FinalSubCategoryActivity extends BaseActivity {

    private ActivityCategoryBinding binding;
    private FinalSubCategoryAdapter adapter;
    private Context mContext;
    private ArrayList<AddRemoveCategoryModel> finalCategory;
    private String serviceIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        mContext = FinalSubCategoryActivity.this;
        finalCategory = new ArrayList<>();
        serviceIds = getIntent().getExtras().getString("categoryId");
        setToolBar();
        setRecyclerView();
    }

    private void setToolBar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setTitle("Our selected services");
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void setRecyclerView() {
        adapter = new FinalSubCategoryAdapter(this, finalCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position, view) -> {
            int id = view.getId();
            if (id == R.id.ivLocation) {
                finalCategory.get(position).setSelected(!finalCategory.get(position).isSelected());
                adapter.notifyDataSetChanged();
                Intent intent = new Intent(FinalSubCategoryActivity.this, SelectYourServiceAreaActivity.class);
                intent.putExtra("subCategory", finalCategory.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (serviceIds != null) {
            Utils.serviceID = serviceIds;
            callAddServicesAPI(serviceIds);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_next) {
            if (finalCategory.size() > 0) {
                boolean isServiceSelected = false;
                for (int i = 0; i < finalCategory.size(); i++) {
                    if (finalCategory.get(i).getLocation().isEmpty()) {
                        isServiceSelected = false;
                        utils.simpleAlert(this, "", "Please select service Area for every service");
                        break;
                    } else {
                        isServiceSelected = true;
                    }
                }
                if (isServiceSelected) {
                    /*userPref.setLogin(true);
                    startActivity(new Intent(this, HomeActivity.class)); */
                    utils.simpleAlert(this, "NOTE", "Please note that the administrator needs to approve your profile before you can start using the app. The app will now close.", dialog -> startActivity(new Intent(FinalSubCategoryActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
                }
            }
            return true;
        }
        return onOptionsItemSelected(item);
    }

    //Android Activity Lifecycle Method
    // Called when a panel's menu is opened by the user.
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return true;
    }

    private void callAddServicesAPI(String services_id) {
        finalCategory.clear();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("service_provider_id", userPref.getUser().getUserId());
        jsonObject.addProperty("services_id", services_id);

        apiService.callAddServicesAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null && commonResponse.getData().size() > 0) {
                        finalCategory.addAll(commonResponse.getData());
                        adapter.notifyDataSetChanged();

//                        Intent intent = new Intent(this, FinalSubCategoryActivity.class);
//                        intent.putParcelableArrayListExtra("category",finalCategory);
//                        startActivity(intent);
                    } else {
                        utils.simpleAlert(this, getString(R.string.error), commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if (throwable instanceof ConnectException) {
                        utils.simpleAlert(this, getString(R.string.error), getString(R.string.check_network_connection));
                    } else {
                        utils.simpleAlert(this, getString(R.string.error), throwable.getMessage());
                    }
                });
    }

}