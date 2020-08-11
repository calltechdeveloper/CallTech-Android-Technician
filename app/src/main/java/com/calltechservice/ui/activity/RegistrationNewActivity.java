package com.calltechservice.ui.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityRegistrationNewBinding;
import com.calltechservice.databinding.AskForLocationBinding;
import com.calltechservice.location.LocationTracker;
import com.calltechservice.model.CountryModel;
import com.calltechservice.model.response.SelectCategoryResponse;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.PermissionUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonObject;
import com.rtchagas.pingplacepicker.PingPlacePicker;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegistrationNewActivity extends BaseActivity implements View.OnClickListener {
    ActivityRegistrationNewBinding binding;
    private boolean isVisible;
    private String mobileNumber = "";
    ArrayAdapter<SelectCategoryResponse> adapterCategory;

    String deviceToken;
    private List<SelectCategoryResponse> serviceCtegoryModels;
    private LocationTracker locationTracker;
    private int PLACE_PICKER_REQUEST = 1;
    private LatLng searchedLocation;
    private LatLng selectedLatLang;
    private AskForLocationBinding bindingDialog;
    String selectedJobLocationAddress;
    private LatLng selectedJobLocationLatLong;
    LatLng currentLocation;
    private ArrayList<CountryModel> list;
    private String country_id = "0", country_id1, country_code = "", country_name = "Select Country";

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration_new);
        setToolbar();
        startLocation();
        serviceCtegoryModels = new ArrayList<>();
        list = new ArrayList<>();
        callServiceCategoryAPI();
        callCountryAPI();
        adapterCategory = new ArrayAdapter<>(this, R.layout.spinner_item, serviceCtegoryModels);
        adapterCategory.setDropDownViewResource(R.layout.spinner_item);
        binding.spCategory.setAdapter(adapterCategory);
        binding.etAddress.setOnClickListener(this);

        binding.btnSignUp.setOnClickListener(v -> {
            if (binding.spCategory.getSelectedItemPosition() != 0) {
                if (validation()) {
                    callRegistration(mobileNumber);
                /*Intent intent = new Intent(RegistrationNewActivity.this, HomeActivity.class);
                startActivity(intent);*/
                }
            } else {
                utils.simpleAlert(this, "category", "please select the category");
            }
        });

        binding.lytVisiblePass.setOnClickListener(v -> {
            if (isVisible) {
                binding.edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.ivVisiblePass.setImageDrawable(getDrawable(R.drawable.not_visible));
                isVisible = false;
            } else {
                binding.edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.ivVisiblePass.setImageDrawable(getDrawable(R.drawable.not_visible_hover));
                isVisible = true;
            }
        });
    }

    private void callCountryAPI() {
        apiService.callCountryAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(response -> {
                    if (response.getStatus() == 1 && response.getData() != null) {
                        CountryModel model = new CountryModel();
                        model.setCurrencyId("0");
                        model.setCountry("Select Country");
                        model.setCode("");
                        model.setSymbol("");
                        model.setCurrency("");
                        list.add(model);
                        list.addAll(response.getData());

                        ArrayAdapter<CountryModel> adapter = new ArrayAdapter<CountryModel>(this, android.R.layout.simple_spinner_dropdown_item, list);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        binding.spCountry1.setAdapter(adapter);
                        binding.spCountry1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                CountryModel c = (CountryModel) parent.getSelectedItem();
                                country_id = c.getCurrencyId();
                                country_name = c.getCountry();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {// todo: goto back activity from here
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.back);
            setTitle("");
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mobileNumber = bundle.getString("mobile");
        }
    }

    public boolean validation() {
        boolean valid = true;
        if (country_id.equalsIgnoreCase("0")) {
            binding.edtFirstName.setError("Please Select Country");
            valid = false;
        }
        if (binding.edtFirstName.getText().toString().isEmpty()) {
            binding.edtFirstName.setError("Please enter first name");
            valid = false;
        } else {
            binding.edtFirstName.setError(null);
        }

        if (binding.edtSurName.getText().toString().isEmpty()) {
            binding.edtSurName.setError("Please enter surname");
            valid = false;
        } else {
            binding.edtSurName.setError(null);
        }

        if (binding.etAddress.getText().toString().isEmpty()) {
            binding.etAddress.setError("Please enter surname");
            valid = false;
        } else {
            binding.etAddress.setError(null);
        }

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

        return valid;
    }

    private void callRegistration(String mobile) {
//        RegistrationRequest registrationRequest=new RegistrationRequest();
//        registrationRequest.setFirst_name(binding.edtFirstName.getText().toString());
//        registrationRequest.setLast_name(binding.edtSurName.getText().toString());
//        registrationRequest.setMobile(mobile);
//        registrationRequest.setEmail(binding.edtEmail.getText().toString());
//        registrationRequest.setPassword(binding.edtPassword.getText().toString());
//        SelectCategoryResponse packageResponse = (SelectCategoryResponse) binding.spCategory.getSelectedItem();
//        registrationRequest.setCategory_id(packageResponse.getCategory_id());
//        registrationRequest.setDevice_id(SharedPref.getPreferencesDevice(this, AppConstant.DEVICE_ID));
//        registrationRequest.setDevice_type("A");

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(instanceIdResult -> {
            deviceToken = instanceIdResult.getToken();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("first_name", binding.edtFirstName.getText().toString());
            jsonObject.addProperty("last_name", binding.edtSurName.getText().toString());
            jsonObject.addProperty("email", binding.edtEmail.getText().toString());
            jsonObject.addProperty("mobile", mobile);
            jsonObject.addProperty("password", binding.edtPassword.getText().toString());
            jsonObject.addProperty("device_id", deviceToken);
            jsonObject.addProperty("device_type", "A");
            jsonObject.addProperty("type", "1");
            jsonObject.addProperty("category_id", binding.spCategory.getSelectedItemId());
            jsonObject.addProperty("address", binding.etAddress.getText().toString());
            jsonObject.addProperty("latitude", String.valueOf(selectedJobLocationLatLong.latitude));
            jsonObject.addProperty("longitude", String.valueOf(selectedJobLocationLatLong.longitude));
            jsonObject.addProperty("country_id", country_id);

            apiService.callRegistrationAPI(jsonObject)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe(this::showProgressDialog)
                    .doOnCompleted(this::hideProgressDialog)
                    .subscribe(response -> {
                        if (response.getStatus() == 1) {
                            //userPref.setLogin(true);
                            userPref.setUser(response.getData());
                            utils.toaster(response.getMessage());
                            startActivity(new Intent(this, SelectSubCategoryActivity.class).putExtra("registration", true));
                            finishAffinity();
                        } else utils.simpleAlert(this, getString(R.string.error), response.getMessage());
                    }, e -> {
                        hideProgressDialog();
                        if (e instanceof ConnectException) {
                            utils.simpleAlert(this, getString(R.string.error), getString(R.string.check_network_connection));
                        } else {
                            utils.simpleAlert(this, getString(R.string.error), e.getMessage());
                        }
                    });
        }); //TODO: add failure listener

    }

    private void callServiceCategoryAPI() {
        serviceCtegoryModels.clear();
        apiService.callServiceCategoryAPI()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1 && commonResponse.getData() != null && commonResponse.getData().size() > 0) {
                        binding.lytTop.setVisibility(View.VISIBLE);
                        SelectCategoryResponse packageResponse = new SelectCategoryResponse();
                        packageResponse.setCategory_name("Select Category");
                        serviceCtegoryModels.add(packageResponse);
                        serviceCtegoryModels.addAll(commonResponse.getData());
                        adapterCategory.notifyDataSetChanged();
                    } else {
                        binding.lytTop.setVisibility(View.GONE);
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

    private void askLocationForService() {
        bindingDialog = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.ask_for_location, null, false);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(bindingDialog.getRoot());
        WindowManager.LayoutParams layoutParams = Objects.requireNonNull(dialog.getWindow()).getAttributes();
        layoutParams.dimAmount = .7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);

        bindingDialog.btCancel.setOnClickListener(view -> dialog.dismiss());

        bindingDialog.tvSelectLocation.setOnClickListener(view -> {
            if (isLocationEnabled()) {
                showPlacePicker();
            } else {
                showLocationPermissionSettingsDialog();
            }
        });

        bindingDialog.btOk.setOnClickListener(view -> {
            if (!bindingDialog.cbCurrentLocation.isChecked() && TextUtils.isEmpty(bindingDialog.tvSelectLocation.getText().toString().trim())) {
                CommonUtils.showSnack(view, "Please give permission to use current location or enter your location");
            } else {
                if (bindingDialog.cbCurrentLocation.isChecked() && currentLocation != null) {
                    dialog.dismiss();
                    selectedLatLang = currentLocation;
                    //selectedjoblocationadd = (String) place.getAddress();
                    selectedJobLocationAddress = getCompleteAddressString(currentLocation.latitude, currentLocation.longitude);
                    selectedJobLocationLatLong = currentLocation;
                    //callgetEmployee();
                } else if (!TextUtils.isEmpty(bindingDialog.tvSelectLocation.getText().toString().trim()) && searchedLocation != null) {
                    dialog.dismiss();
                    selectedLatLang = searchedLocation;
                    //callgetEmployee();
                } else {
                    CommonUtils.showSnack(view, "Unable to find your location");
                }
            }
        });

        dialog.show();
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showLocationPermissionSettingsDialog() {
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
        if ((requestCode == PLACE_PICKER_REQUEST) && (resultCode == RESULT_OK)) {
            Place place = (Place) PingPlacePicker.getPlace(data);
            if (place != null) {
                Toast.makeText(this, "You selected the place: " + place.getName(), Toast.LENGTH_SHORT).show();
                if (place.getAddress() != null && !place.getAddress().equals("")) {
                    bindingDialog.tvSelectLocation.setText(place.getAddress());
                    selectedJobLocationAddress = (String) place.getAddress();
                    binding.etAddress.setText(place.getAddress());
                    selectedJobLocationLatLong = place.getLatLng();
                    searchedLocation = place.getLatLng();
                } else {
                    Toast.makeText(this, "please pick the address name belows selection", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtils.PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                locationTracker.updateLocation();
            } else {
                utils.toaster("Location permission denied. Unable to read device location");
            }
        }
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(RegistrationNewActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                binding.etAddress.setText(strAdd);

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return strAdd;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.etAddress) {
            askLocationForService();
        }
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


        /*Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        locationTracker = new LocationTracker(RegistrationActivity.this, RegistrationActivity.this);
                        locationTracker.updateLocation();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            //openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).check();*/
    }
}