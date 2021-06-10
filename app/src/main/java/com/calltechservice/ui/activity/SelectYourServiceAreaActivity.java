package com.calltechservice.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivitySelectYourServiceAreaBinding;
import com.calltechservice.databinding.AskForLocationBinding;
import com.calltechservice.location.LocationTracker;
import com.calltechservice.location.MapDisplayUtils;
import com.calltechservice.model.request.ServiceAreaRequest;
import com.calltechservice.model.response.AddRemoveCategoryModel;
import com.calltechservice.model.response.AreaList;
import com.calltechservice.ui.adapter.AddServiceAreaAdapter;
import com.calltechservice.utils.PermissionUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SelectYourServiceAreaActivity extends BaseActivity {

    private static final int PLACE_PICKER_REQUEST = 1;
    private ActivitySelectYourServiceAreaBinding binding;
    private LocationTracker locationTracker;
    private AddServiceAreaAdapter addServiceAreaAdapter;
    private Context mContext;
    private AskForLocationBinding bindingDialog;
    private ServiceAreaRequest serviceAreaRequests;

    private AddRemoveCategoryModel model;
    private String subCategoryId;
    LatLng currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_your_service_area);
        this.mContext = SelectYourServiceAreaActivity.this;
        setToolBar();
        startLocation();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            model = (AddRemoveCategoryModel) bundle.get("subCategory");
            if (model != null) {
                subCategoryId = model.getService_id();
                serviceAreaRequests = new ServiceAreaRequest();
                serviceAreaRequests.getAreaList().addAll(model.getLocation());
            }
        }

        setListener();
        setRecycler();
    }

    private void setListener() {
        binding.tvSelectLocation.setOnClickListener((v -> askLocationForService()));
        binding.btSubmit.setOnClickListener((v -> {
            if (serviceAreaRequests.getAreaList().size() > 0) {
                callAddLocationAPI();
            } else {
                utils.simpleAlert(this, "", "Please select your service Area");
            }
        }));
    }

    private void setRecycler() {
        binding.rvAddCategory.setLayoutManager(new LinearLayoutManager(mContext));
        addServiceAreaAdapter = new AddServiceAreaAdapter(mContext, serviceAreaRequests);
        binding.rvAddCategory.setAdapter(addServiceAreaAdapter);
    }

    private void setToolBar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setTitle("Select Service Area");
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void askLocationForService() {
        bindingDialog = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.ask_for_location, null, false);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(bindingDialog.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = .7f;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(layoutParams);
        }
        bindingDialog.cbCurrentLocation.setVisibility(View.GONE);

        bindingDialog.btCancel.setOnClickListener(view -> dialog.dismiss());

        bindingDialog.tvSelectLocation.setOnClickListener(view -> {
            if (isLocationEnabled())
                showPlacePicker();
            else
                showAlert();
        });

        bindingDialog.btOk.setOnClickListener(view -> {
            if (!bindingDialog.tvSelectLocation.getText().equals("")) {
                dialog.dismiss();
            } else {
                Toast.makeText(SelectYourServiceAreaActivity.this, "please select the address!!!!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enable Location")
                .setMessage("Locations Settings is set to 'Off'.\nEnable Location to use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.show();
    }

    private void showPlacePicker() {
        PingPlacePicker.IntentBuilder builder = new PingPlacePicker.IntentBuilder();
        builder.setAndroidApiKey("AIzaSyDQ0DpGebHc465i5EMJJOH0tvuL1lUT_dw")
                .setMapsApiKey("AIzaSyDQ0DpGebHc465i5EMJJOH0tvuL1lUT_dw");

        // If you want to set a initial location rather then the current device location.
        // NOTE: enable_nearby_search MUST be true.
        // builder.setLatLng(new LatLng(37.4219999, -122.0862462))

        try {
            Intent placeIntent = builder.build(this);
            startActivityForResult(placeIntent, PLACE_PICKER_REQUEST);
        } catch (Exception ex) {
            // Google Play services is not available...
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == PLACE_PICKER_REQUEST) && (resultCode == RESULT_OK)) {
            com.google.android.libraries.places.api.model.Place place = PingPlacePicker.getPlace(data);
            if (place != null) {
                Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
                AreaList areaList = new AreaList();
                //String location = MapDisplayUtils.getAddressFromLatLng(this, place.getLatLng().latitude,place.getLatLng().longitude);
                //Log.e("hi ", "<<if place>> " +location);


                if (place.getAddress() != null && place.getAddress().length() > 0) {
                    areaList.setAreaName(place.getAddress() + "");
                } else {
                    String location = MapDisplayUtils.getAddressFromLatLng(this, place.getLatLng().latitude, place.getLatLng().longitude);
                    Log.e("hi ", "<<if place>> " + location);
                    areaList.setAreaName(location);
                }
                bindingDialog.tvSelectLocation.setText(place.getAddress());
                areaList.setLat(place.getLatLng().latitude + "");
                areaList.setLng(place.getLatLng().longitude + "");
                serviceAreaRequests.getAreaList().add(areaList);
                addServiceAreaAdapter.notifyDataSetChanged();
                Log.e("Service area", "lat" + place.getLatLng().latitude + "longitude" + place.getLatLng().longitude);
            }
        }
    }


    private void callAddLocationAPI() {
        StringBuilder stringLat = new StringBuilder();
        StringBuilder stringLng = new StringBuilder();
        StringBuilder stringArea = new StringBuilder();
        for (int i = 0; i < serviceAreaRequests.getAreaList().size(); i++) {
            if (!stringLat.toString().equalsIgnoreCase("")) {
                stringLat.append("," + serviceAreaRequests.getAreaList().get(i).getLat());
            } else {
                stringLat.append(serviceAreaRequests.getAreaList().get(i).getLat());
            }

            if (!stringLng.toString().equalsIgnoreCase("")) {
                stringLng.append("," + serviceAreaRequests.getAreaList().get(i).getLng());
            } else {
                stringLng.append(serviceAreaRequests.getAreaList().get(i).getLng());
            }

            if (!stringArea.toString().equalsIgnoreCase("")) {
                stringArea.append("~" + serviceAreaRequests.getAreaList().get(i).getAreaName());
            } else {
                stringArea.append(serviceAreaRequests.getAreaList().get(i).getAreaName());
            }
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("service_provider_id", userPref.getUser().getUserId());
        jsonObject.addProperty("service_id", subCategoryId);
        jsonObject.addProperty("lat", stringLat.toString());
        jsonObject.addProperty("lng", stringLng.toString());
        jsonObject.addProperty("area_id", stringArea.toString());

        apiService.callAddLocationAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null) {
                        finish();
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

    public void startLocation() {
        if (PermissionUtils.checkPermissionLocation(this)) {
            locationTracker = new LocationTracker(this, location -> {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            });
            locationTracker.updateLocation();

        } else {
            PermissionUtils.requestPermissionLocation(this);
        }
    }

}