package com.calltechservice.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import com.calltechservice.location.LocationResult;
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
import java.util.List;
import java.util.Locale;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SelectYourServiceAreaActivity extends BaseActivity implements LocationResult, View.OnClickListener {

    private static final int PLACE_PICKER_REQUEST = 1;
    private ActivitySelectYourServiceAreaBinding binding;
    private LocationTracker locationTracker;
    private Location location;
    private AddServiceAreaAdapter addServiceAreaAdapter;
    private Context mContext;
    private AskForLocationBinding bindingDialog;
    private List<String> serviceArea;
    private ServiceAreaRequest serviceAreaRequests;
    private Dialog locationDialog;

    private AddRemoveCategoryModel model;
    private String subCategoryId;
    String selectedJobLocationAdd;
    private LatLng selectedJobLocationLatLong;
    LatLng currentLocation;
    private LatLng searchedLocation;
    private LatLng selectedLatLang;

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
        binding.tvSelectLocation.setOnClickListener(this);
        binding.btSubmit.setOnClickListener(this);
    }

    private void setRecycler() {
       /* serviceAreaRequests=new ServiceAreaRequest();

        for (int i = 0; i<model.getLocation().size(); i++){
            AreaList areaList=new AreaList();
            areaList.setAreaName(model.getLocation().get(i).getAreaName()+"");
            areaList.setLat(model.getLocation().get(i).getLat()+"");
            areaList.setLng(model.getLocation().get(i).getLng()+"");
            serviceAreaRequests.getAreaList().add(areaList);

        }*/
        binding.rvAddCategory.setLayoutManager(new LinearLayoutManager(mContext));
        addServiceAreaAdapter = new AddServiceAreaAdapter(mContext, serviceAreaRequests);
        binding.rvAddCategory.setAdapter(addServiceAreaAdapter);
    }

    private void setToolBar() {
        locationTracker = new LocationTracker(mContext, SelectYourServiceAreaActivity.this);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back));
        setTitle("Select Service Area");
        binding.toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void gotLocation(Location location) {
        if (location != null) {
            this.location = location;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSelectLocation:
                askLocationForService();
                break;
            case R.id.btSubmit:
                if (serviceAreaRequests.getAreaList().size() > 0) {
                    callAddLocationAPI();
                } else {
                    utils.simpleAlert(this, "", "Please select your service Area");
                }
                break;
        }
    }

    private void askLocationForService() {
        bindingDialog = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.ask_for_location, null, false);
        locationDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        locationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        locationDialog.setCancelable(true);
        locationDialog.setContentView(bindingDialog.getRoot());
        Window window = locationDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = .7f;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(layoutParams);

            bindingDialog.cbCurrentLocation.setVisibility(View.GONE);
            bindingDialog.btCancel.setOnClickListener(view -> locationDialog.dismiss());

            bindingDialog.tvSelectLocation.setOnClickListener(view -> {
                if (isLocationEnabled())
                    showPlacePicker();
                else
                    showAlert();
            });

            bindingDialog.btOk.setOnClickListener(view -> {
                if (!bindingDialog.tvSelectLocation.getText().equals("")) {
                    locationDialog.dismiss();
                } else {
                    Toast.makeText(SelectYourServiceAreaActivity.this, "please select the address!!!!", Toast.LENGTH_SHORT).show();
                }
            });

            locationDialog.show();
        }
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<android.location.Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                //   binding.etjoblocation.setText( strAdd );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Enable Location")
                .setMessage("Locations Settings is set to 'Off'.\nEnable Location to use this app")
                .setPositiveButton("Location Settings", (dialog, id) -> {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, id) -> {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
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
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            com.google.android.libraries.places.api.model.Place place = PingPlacePicker.getPlace(data);
            if (place != null) {
                Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
                AreaList areaList = new AreaList();
                //String location = MapDisplayUtils.getAddressFromLatLng(this, place.getLatLng().latitude,place.getLatLng().longitude);
                //Log.e("hi ", "<<if place>> " +location);

                LatLng latLng = place.getLatLng();
                if (place.getAddress() != null && place.getAddress().length() > 0) {
                    areaList.setAreaName(place.getAddress() + "");
                } else {
                    String location = MapDisplayUtils.getAddressFromLatLng(this, latLng.latitude, latLng.longitude);
                    Log.e("hi ", "<<if place>> " + location);
                    areaList.setAreaName(location);
                }
                bindingDialog.tvSelectLocation.setText(place.getAddress());
                if (latLng != null) {
                    areaList.setLat(latLng.latitude + "");
                    areaList.setLng(latLng.longitude + "");
                    serviceAreaRequests.getAreaList().add(areaList);
                    addServiceAreaAdapter.notifyDataSetChanged();
                    locationDialog.dismiss();
                    Log.e("Service area", "lat" + latLng.latitude + "longitude" + latLng.longitude);
                }
            }
        }
        locationDialog.dismiss();
    }

    private void callAddLocationAPI() {
        StringBuilder stringLat = new StringBuilder();
        StringBuilder stringLng = new StringBuilder();
        StringBuilder stringArea = new StringBuilder();
        for (int i = 0; i < serviceAreaRequests.getAreaList().size(); i++) {
            if (!stringLat.toString().equalsIgnoreCase("")) {
                stringLat.append(",").append(serviceAreaRequests.getAreaList().get(i).getLat());
            } else {
                stringLat.append(serviceAreaRequests.getAreaList().get(i).getLat());
            }

            if (!stringLng.toString().equalsIgnoreCase("")) {
                stringLng.append(",").append(serviceAreaRequests.getAreaList().get(i).getLng());
            } else {
                stringLng.append(serviceAreaRequests.getAreaList().get(i).getLng());
            }

            if (!stringArea.toString().equalsIgnoreCase("")) {
                stringArea.append("~").append(serviceAreaRequests.getAreaList().get(i).getAreaName());
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
                       /* userPref.setLogin(true);

                        startActivity(new Intent(this, HomeActivity.class));
                        finish();*/
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

    private void callAddServiceListApi() {
        /*shoprogress();
        serviceAreaRequests.setProviderId(SharedPref.getPreferencesString(mContext,AppConstant.USER_ID));
        serviceAreaRequests.setRequest("addServiceArea");
        final Call<CommonResponse> callAddServiceArea = APIExecutor.getApiService(mContext).callAddServiceAreaList(serviceAreaRequests);
        callAddServiceArea.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                if(response!=null)
                {
                    if(response.body().getmStatus()!=null&&response.body().getmStatus().equalsIgnoreCase("1"))
                    {
                        SharedPref.savePreferenceBoolean(mContext,AppConstant.SERVICE_AREA_ADDED,true);
                        Intent intent=new Intent(SelectYourServiceAreaActivity.this,BankInfoActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        CommonUtils.showSnack(binding.getRoot(),response.body().getmMessage()!=null?response.body().getmMessage():"");
                    }
                }

                setDismis();
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                setDismis();
            }
        });*/
    }

    public void startLocation() {
        if (PermissionUtils.checkPermissionLocation(this)) {
            locationTracker = new LocationTracker(this, location -> {
                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                selectedLatLang = currentLocation;
            });
            locationTracker.updateLocation();
        } else {
            PermissionUtils.requestPermissionLocation(this);
        }

//        Dexter.withActivity(this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        locationTracker = new LocationTracker(RegistrationActivity.this, RegistrationActivity.this);
//                        locationTracker.onUpdateLocation();
//
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        if (response.isPermanentlyDenied()) {
//                            // open device settings when the permission is
//                            // denied permanently
//                            openSettings();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//
//
//                }).check();
    }

}