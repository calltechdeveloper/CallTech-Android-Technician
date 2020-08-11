package com.calltechservice.ui.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityCategoryBinding;
import com.calltechservice.model.response.AddRemoveCategoryModel;
import com.calltechservice.model.response.ServiceSubCtegoryModel;
import com.calltechservice.ui.adapter.SubCategoryAdapter;
import com.calltechservice.utils.CommonUtils;
import com.google.gson.JsonObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SelectSubCategoryActivity extends BaseActivity {

    private ActivityCategoryBinding binding;
    private LinearLayoutManager layoutManager;
    private SubCategoryAdapter adapter;
    private List<ServiceSubCtegoryModel> serviceSubCtegoryModels;
    private Context mContext;
    private ArrayList<AddRemoveCategoryModel> finalCategory;
   private ArrayList<String> selectedCategory;
    ArrayList<String> myList;
    private boolean isRegistration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        mContext = SelectSubCategoryActivity.this;


        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            finalCategory= new ArrayList<>();
            isRegistration = getIntent().getExtras().getBoolean("registration");
            if(isRegistration){

            }

            else
            {
                finalCategory = getIntent().getParcelableArrayListExtra("myselectedlist");
            }

          //  Log.w("selected","<<size143>> "+finalCategory.size());
        }


        serviceSubCtegoryModels = new ArrayList<>();
        selectedCategory = new ArrayList<>();

        setToolBar();



        /*String category_id = getIntent().getExtras().getString("categoryId");

        if (category_id !=null){
            callServiceSubCategoryAPI();
        }*/
        callServiceSubCategoryAPI();

        /*userPref.clearPref();*/
        setRecyclerView();

    }

    private void setToolBar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setTitle("All services");

        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void setRecyclerView() {
        adapter = new SubCategoryAdapter(this,serviceSubCtegoryModels);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        binding.recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SubCategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //AddRemoveCategoryModel model = new AddRemoveCategoryModel();
                if(serviceSubCtegoryModels.get(position).isSelected()) {
                    serviceSubCtegoryModels.get(position).setSelected(false);

                    /*model.setCategory_id(serviceSubCtegoryModels.get(position).getService_id());
                    model.setCategory_image(serviceSubCtegoryModels.get(position).getService_image());
                    model.setCategory_name(serviceSubCtegoryModels.get(position).getService_name());
                    selectedCategory.add(model);*/

                    //model.getCategory_id();
                    selectedCategory.remove(serviceSubCtegoryModels.get(position).getService_id());
                    //adapter.notifyItemRemoved(position);
                }
                else {
                    serviceSubCtegoryModels.get(position).setSelected(true);

                   /* model.setCategory_id(serviceSubCtegoryModels.get(position).getService_id());
                    model.setCategory_image(serviceSubCtegoryModels.get(position).getService_image());
                    model.setCategory_name(serviceSubCtegoryModels.get(position).getService_name());*/
                    selectedCategory.add(serviceSubCtegoryModels.get(position).getService_id());
                }
                Log.w("selected","<<size>> "+selectedCategory.size());
                /*for (int i=0; i<selectedCategory.size(); i++){

                    Log.w("selected","<<Name>> "+selectedCategory.get(i).getCategory_name());
                }*/


                adapter.notifyDataSetChanged();

            }
        });
    }

    private void callServiceSubCategoryAPI() {
        serviceSubCtegoryModels.clear();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("provider_id",userPref.getUser().getUserId());

        apiService.callSubCategoryAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&commonResponse.getData().size()>0) {
                        serviceSubCtegoryModels.addAll(commonResponse.getData());

if(isRegistration){

}
else
{

    for(int i =0; i<finalCategory.size();i++){

        String id1= finalCategory.get(i).getService_id();

        for(int j = 0; j<serviceSubCtegoryModels.size();j++){

            String id2 =serviceSubCtegoryModels.get(j).getService_id();

            if(id1.equals(id2))
            {
                serviceSubCtegoryModels.get(j).setSelected(true);
                selectedCategory.add(serviceSubCtegoryModels.get(j).getService_id());
            }


        }}
}
                        adapter.notifyDataSetChanged();
                    } else{
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

   /* @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String filterChar = charSequence.toString().toLowerCase();
        if (filterChar.length() > 0) {
            categoryModelsFilter.clear();
            for (int j = 0; j < categoryModels.size(); j++) {
                if (categoryModels.get(j).getCategoryName().toLowerCase().contains(filterChar)) {
                    categoryModelsFilter.add(categoryModels.get(j));
                }
            }
            serviceCategoryAdapter = new ServiceCategoryAdapter(mContext, categoryModelsFilter);
            binding.rvServiceCategory.setAdapter(serviceCategoryAdapter);
        } else {
            serviceCategoryAdapter = new ServiceCategoryAdapter(mContext, categoryModels);
            binding.rvServiceCategory.setAdapter(serviceCategoryAdapter);
        }
        setVisibility();
        serviceCategoryAdapter.notifyDataSetChanged();
    }

    private void setVisibility() {
        if (serviceCategoryAdapter.getItemCount() > 0) {
            binding.tvNoData.setVisibility(View.GONE);
            binding.rvServiceCategory.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoData.setText(R.string.cat_not_available);
            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.rvServiceCategory.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }*/

    @Override
    public void onResume() {
        super.onResume();
        /*if (CommonUtils.isOnline(mContext)) {
            callCategoryApi();
        } else {
            CommonUtils.showSnack(binding.getRoot(), getString(R.string.internet_connection));

        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_next:



                    if (selectedCategory.size()==0) {
                        CommonUtils.showSnack(binding.getRoot(), "Please select services");

                    } else{


                        if (isRegistration==true) {

                            StringBuilder stringBuilder = new StringBuilder("");
                            for (int i = 0; i < selectedCategory.size(); i++) {
                                if (!stringBuilder.toString().equalsIgnoreCase("")) {
                                    stringBuilder.append("," + selectedCategory.get(i));
                                } else {
                                    stringBuilder.append(selectedCategory.get(i));
                                }
                            }


                            Intent intent = new Intent(this, FinalSubCategoryActivity.class);
                            intent.putExtra("categoryId", stringBuilder.toString());
                            startActivity(intent);
                        }else {

                            callAddServicesAPI();
                        }

                    }




                return true;
        }

        return onOptionsItemSelected(item);
    }





    //Android Activity Lifecycle Method
// Called when a panel's menu is opened by the user.
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {


        return true;
    }


    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_search:

                if (binding.search.etSearch.getVisibility() == View.VISIBLE) {
                    binding.search.etSearch.setVisibility(View.GONE);
                } else {
                    binding.search.etSearch.setVisibility(View.VISIBLE);
                }
                return true;
            case R.id.action_next:
                callAddServiceListApi();
                return true;
        }
        return onOptionsItemSelected(item);
    }*/



    private void callAddServicesAPI() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (int i = 0; i<selectedCategory.size(); i++){
            if (!stringBuilder.toString().equalsIgnoreCase("")) {
                stringBuilder.append("," + selectedCategory.get(i));
            } else {
                stringBuilder.append(selectedCategory.get(i));
            }
        }

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("service_provider_id",userPref.getUser().getUserId());
        jsonObject.addProperty("services_id",stringBuilder.toString());

        apiService.callAddServicesAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null) {
                        finish();

                    } else{
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



   /* private void callAddServiceListApi() {
        StringBuilder stringBuilder = new StringBuilder("");
        for (Datum datum : categoryModels) {
            for (SubCategory subCategory : datum.getSubCategory()) {
                if (subCategory.isSelected()) {
                    if (!stringBuilder.toString().equalsIgnoreCase("")) {
                        stringBuilder.append("," + subCategory.getSubCatId());
                    } else {
                        stringBuilder.append(subCategory.getSubCatId());
                    }
                }

            }

        }
        if (stringBuilder.toString().equalsIgnoreCase("")) {
            CommonUtils.showSnack(binding.getRoot(), getString(R.string.select_sub_category));
            return;
        }
        shoprogress();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("provider_id", SharedPref.getPreferencesString(mContext, AppConstant.USER_ID));
        jsonObject.addProperty("service_sub_cat_ids", stringBuilder.toString());
        jsonObject.addProperty("rquest","addServiceCategory");

        final Call<CommonResponse> callAddServiceArea = APIExecutor.getApiService(mContext).callAddServiceList(jsonObject);
        callAddServiceArea.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                SharedPref.savePreferenceBoolean(mContext,AppConstant.SERVICE_CAT_ADDED,true);
                Intent intent=new Intent(SelectServiceActivity.this,SelectYourServiceAreaActivity.class);
                startActivity(intent);
                finish();
                setDismis();
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                setDismis();
            }
        });
    }*/

}