package com.calltechservice.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentMyServicesBinding;
import com.calltechservice.model.response.AddRemoveCategoryModel;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.activity.SelectSubCategoryActivity;
import com.calltechservice.ui.activity.SelectYourServiceAreaActivity;
import com.calltechservice.ui.adapter.FinalSubCategoryAdapter;
import com.google.gson.JsonObject;

import java.net.ConnectException;
import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyServicesFragment extends BaseFragment {
    private FragmentMyServicesBinding binding;
    private FinalSubCategoryAdapter adapter;
    private ArrayList<AddRemoveCategoryModel> finalCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finalCategory = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_services, container, false);

        setHasOptionsMenu(true);
        ((HomeActivity) requireActivity()).changeIcon(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView();
    }

    private void setRecyclerView() {
        adapter = new FinalSubCategoryAdapter(getContext(), finalCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.recyclerView.setLayoutManager(layoutManager);// set LayoutManager to RecyclerView
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((position, view) -> {
            switch (view.getId()) {
                case R.id.ivLocation:
                    finalCategory.get(position).setSelected(!finalCategory.get(position).isSelected());
                    adapter.notifyDataSetChanged();

                    Intent intent = new Intent(getContext(), SelectYourServiceAreaActivity.class);
                    intent.putExtra("subCategory", finalCategory.get(position));
                    startActivity(intent);
                    break;
                case R.id.delete:
                    logoutAlert(finalCategory.get(position).getService_id());
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onResume() {
        //Log.e("DEBUG", "onResume of HomeFragment");
        super.onResume();
        callMyServicesAPI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.addmore, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getContext(), SelectSubCategoryActivity.class);
            intent.putExtra("registration", false);
            intent.putParcelableArrayListExtra("myselectedlist", finalCategory);
            startActivity(intent);
            return true;
        }
        return onOptionsItemSelected(item);
    }

    private void callMyServicesAPI() {
        finalCategory.clear();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("service_provider_id", userPref.getUser().getUserId());
        apiService.callMyServicesAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {

                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null && commonResponse.getData().size() > 0) {
                        finalCategory.addAll(commonResponse.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if (throwable instanceof ConnectException) {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), requireActivity().getString(R.string.check_network_connection));
                    } else {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), throwable.getMessage());
                    }
                });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void callMyServicesdeleteAPI(String serviceid) {
        finalCategory.clear();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("service_provider_id", userPref.getUser().getUserId());
        jsonObject.addProperty("service_id", serviceid);

        apiService.callMyServicesdeleteAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null && commonResponse.getData().size() > 0) {
                        finalCategory.addAll(commonResponse.getData());
                        adapter.notifyDataSetChanged();
                    } else {
                        utils.simpleAlert(requireActivity(), "", commonResponse.getMessage());
                        adapter.notifyDataSetChanged();
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if (throwable instanceof ConnectException) {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), requireActivity().getString(R.string.check_network_connection));
                    } else {
                        utils.simpleAlert(requireActivity(), requireActivity().getString(R.string.error), throwable.getMessage());
                    }
                });
    }

    private void logoutAlert(String serviceid) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme);
        builder.setTitle("Delete Service");
        builder.setMessage("Do you want to Delete?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            callMyServicesdeleteAPI(serviceid);
        });

        builder.setNegativeButton("No", null);
        builder.create();
        builder.show();
    }
}