package com.calltechservice.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.location.Location;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.calltechservice.model.response.ServiceProdersModel;
import com.calltechservice.model.response.ServiceSubCtegoryModel;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.calltechservice.R;
import com.calltechservice.api.APIExecutor;
import com.calltechservice.databinding.ActivitySubCategoryBinding;
import com.calltechservice.databinding.AskForLocationBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.databinding.ScheduleBinding;
import com.calltechservice.db.SharedPref;
import com.calltechservice.location.LocationResult;
import com.calltechservice.location.LocationTracker;
import com.calltechservice.model.request.SendInvitationRequest;
import com.calltechservice.model.response.employee.EmployeeData;
import com.calltechservice.model.response.employee.EmployeeResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.ServiceProviderAdapter;
import com.calltechservice.utils.AppConstant;
import com.calltechservice.utils.CommonUtils;
import com.calltechservice.utils.PermissionUtils;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

public class ServiceProviderFragment extends BaseFragment implements View.OnClickListener {

    private int PLACE_PICKER_REQUEST = 1;
    private ActivitySubCategoryBinding binding;
    private LinearLayoutManager layoutManager;
    private ServiceProviderAdapter serviceProviderAdapter;
    private LatLng currentLocation;
    private LatLng searchedLocation;
    private LatLng selectedLatLang;
    private LocationTracker locationTracker;
    private AskForLocationBinding bindingDialog;
    private List<EmployeeData> employeeDatas;
    private ScheduleBinding scheduleBinding;
    private Dialog dialogScheduleJob;
    private String subCatId;
    private String subCatName;
    private Context mContext;
    private ServiceSubCtegoryModel serviceSubCtegoryModel;
    private List<ServiceProdersModel> serviceProdersModels;

    public ServiceProviderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            serviceSubCtegoryModel = (ServiceSubCtegoryModel) bundle.get("subCategory");
            subCatName = Objects.requireNonNull(serviceSubCtegoryModel).getService_name();
        }
        serviceProdersModels = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_sub_category, container, false);
        setHasOptionsMenu(true);
        setRecyclerView();
        setListener();
       /* if (getArguments() != null) {
            subCatId = getArguments().getString("sub_cat");
            subCatName = getArguments().getString("sub_cat_name");
        }*/
        if (SharedPref.getPreferencesLat(requireActivity(), AppConstant.LAT) != 0 && SharedPref.getPreferencesLong(requireActivity(), AppConstant.LONG) != 0) {
            selectedLatLang = new LatLng(SharedPref.getPreferencesLat(requireActivity(), AppConstant.LAT), SharedPref.getPreferencesLong(requireActivity(), AppConstant.LONG));

            if (selectedLatLang!=null){
                callServicesProviderAPI(serviceSubCtegoryModel.getService_id(),String.valueOf(selectedLatLang.latitude),String.valueOf(selectedLatLang.longitude) );
            }

        } else {
            askLocationForService();
        }
        return binding.getRoot();
    }

    private void setListener() {
        ((HomeActivity) requireActivity()).changeIcon(false);
        if (PermissionUtils.checkPermissionLocation(requireActivity())) {
            locationTracker = new LocationTracker(getContext(), new LocationResult() {
                @Override
                public void gotLocation(Location location) {
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    selectedLatLang = currentLocation;
                }
            });
            locationTracker.updateLocation();

        } else {
            PermissionUtils.requestPermissionLocation(requireActivity());

        }

    }

    private void setRecyclerView() {
        employeeDatas = new ArrayList<>();
        binding.fabFilerList.setOnClickListener(this);
        requireActivity().setTitle("Service Providers");
        layoutManager = new LinearLayoutManager(requireActivity());
        binding.rvServiceCategory.setLayoutManager(layoutManager);
        serviceProviderAdapter = new ServiceProviderAdapter(requireActivity(), serviceProdersModels);
        //serviceProviderAdapter = new ServiceProviderAdapter(requireActivity(), employeeDatas, subCatId);
        binding.rvServiceCategory.setAdapter(serviceProviderAdapter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spProvider.setAdapter(adapter);
       /* serviceProviderAdapter.setListener(this);*/

        serviceProviderAdapter.setOnItemClickListener((position, view1) -> {
            Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putParcelable("provider",serviceProdersModels.get(position));
            fragment = new ProviderDetailsFragment();
            fragment.setArguments(bundle);
            //Fragment fragment = new ProviderDetailsFragment();
            CommonUtils.setFragment(fragment,false, (FragmentActivity) mContext, R.id.flContainerHome);
        });


        binding.spProvider.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (selectedLatLang != null) {
                    callgetEmployee();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_next:
                boolean isEmployeeSelected = false;
                for (ServiceProdersModel datum : serviceProdersModels) {
                    if (datum.isSelected()) {
                        isEmployeeSelected = true;
                        break;
                    }
                }

                if (isEmployeeSelected){

                    scheduleJob();


                       /* StringBuilder proderId = new StringBuilder("");
                        for (ServiceProdersModel data : serviceProdersModels) {
                            if (data.isSelected()) {
                                if (proderId.toString().length() > 0) {
                                    proderId.append(", " + data.getProvider_id());
                                } else {
                                    proderId.append(data.getProvider_id());
                                }
                            }
                        }


                        SendInvitationRequest sendInvitationRequest = new SendInvitationRequest();
                        sendInvitationRequest.setUserId(userPref.getUser().getUserId());
                        sendInvitationRequest.setProvider_ids(proderId);
                        sendInvitationRequest.setTitle(subCatName);

                    Intent intent = new Intent(getContext(),NewJobActivity.class);
                    intent.putExtra("jobRequest", sendInvitationRequest);
                    startActivity(intent);*/

                }else {
                    CommonUtils.showSnack(binding.getRoot(), "Please select provider");
                }

                return true;
        }
        return onOptionsItemSelected(item);
    }

    private void scheduleJob() {
        scheduleBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.schedule, null, false);
        dialogScheduleJob = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialogScheduleJob.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogScheduleJob.setCancelable(true);
        dialogScheduleJob.setContentView(scheduleBinding.getRoot());
        WindowManager.LayoutParams layoutParams = dialogScheduleJob.getWindow().getAttributes();
        layoutParams.dimAmount = .7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        dialogScheduleJob.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialogScheduleJob.getWindow().setAttributes(layoutParams);
        dialogScheduleJob.show();
        scheduleBinding.tvDate.setOnClickListener(this);
        scheduleBinding.tvTime.setOnClickListener(this);
        scheduleBinding.btDone.setOnClickListener(this);
        scheduleBinding.cbImmediate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    scheduleBinding.llDateTime.setVisibility(View.GONE);//TO HIDE THE BUTTON
                else
                    scheduleBinding.llDateTime.setVisibility(View.VISIBLE);
            }
        });

    }


    private void askLocationForService() {
        bindingDialog = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.ask_for_location, null, false);
        final Dialog dialog = new Dialog(requireActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(bindingDialog.getRoot());
        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
        layoutParams.dimAmount = .7f;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setAttributes(layoutParams);
        if (selectedLatLang != null) {
            bindingDialog.btCancel.setVisibility(View.VISIBLE);
        } else {
            bindingDialog.btCancel.setVisibility(View.GONE);
        }
        bindingDialog.btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        bindingDialog.tvSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!bindingDialog.cbCurrentLocation.isChecked()) {
                      /*  Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(requireActivity());
                        startActivityForResult(intent, PLACE_PICKER_REQUEST);
*/
                        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        startActivityForResult(builder.build(requireActivity()), PLACE_PICKER_REQUEST);
                    } else {
                        CommonUtils.showSnack(view, getString(R.string.use_can_use_one));

                    }

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        if (currentLocation == null) {
            Toast.makeText(requireActivity(), "Please wait", Toast.LENGTH_SHORT).show();
        }
        dialog.show();
        bindingDialog.btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bindingDialog.cbCurrentLocation.isChecked() && TextUtils.isEmpty(bindingDialog.tvSelectLocation.getText().toString().trim())) {
                    CommonUtils.showSnack(view, "Please give permission to use current location or enter your location");
                } else {
                    if (bindingDialog.cbCurrentLocation.isChecked() && currentLocation != null) {
                        dialog.dismiss();
                        selectedLatLang = currentLocation;
                        callServicesProviderAPI(serviceSubCtegoryModel.getService_id(),String.valueOf(selectedLatLang.latitude),String.valueOf(selectedLatLang.longitude) );
                        //callgetEmployee();
                    } else if (!TextUtils.isEmpty(bindingDialog.tvSelectLocation.getText().toString().trim()) && searchedLocation != null) {
                        dialog.dismiss();
                        selectedLatLang = searchedLocation;
                        callServicesProviderAPI(serviceSubCtegoryModel.getService_id(),String.valueOf(selectedLatLang.latitude),String.valueOf(selectedLatLang.longitude) );
                        //callgetEmployee();
                    } else {
                        CommonUtils.showSnack(view, "Unable to find your location");
                    }


                }
            }

        });
    }

    private void callgetEmployee() {
        if (CommonUtils.isOnline(requireActivity())) {
           // callgetEmployeeApi();
        } else {
            CommonUtils.showSnack(binding.getRoot(), getString(R.string.internet_connection));

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                bindingDialog.tvSelectLocation.setText(place.getAddress());
                searchedLocation = place.getLatLng();
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabFilerList:
                askLocationForService();
                break;
            case R.id.tvDate:
                CommonUtils.datePickerDialog(getContext(), scheduleBinding.tvDate);
                break;
            case R.id.tvTime:
                CommonUtils.setTime(getContext(), scheduleBinding.tvTime);
                break;
            case R.id.btDone:


                if (validation()) {
                    StringBuilder proderId = new StringBuilder("");
                    for (ServiceProdersModel data : serviceProdersModels) {
                        if (data.isSelected()) {
                            if (proderId.toString().length() > 0) {
                                proderId.append(", " + data.getProvider_id());
                            } else {
                                proderId.append(data.getProvider_id());
                            }
                        }
                    }


                    SendInvitationRequest sendInvitationRequest = new SendInvitationRequest();
                    sendInvitationRequest.setUserId(userPref.getUser().getUserId());
                    sendInvitationRequest.setDescription(scheduleBinding.etJobDetails.getText().toString().trim());
                    sendInvitationRequest.setTitle(subCatName);
                    sendInvitationRequest.setProvider_ids(proderId);

                    if (scheduleBinding.cbImmediate.isChecked()) {
                        sendInvitationRequest.setIsImmediate("1");
                        sendInvitationRequest.setScheduleDate("");
                    }else {
                        sendInvitationRequest.setIsImmediate("0");
                        Log.e("scduleDate1", scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                        if (TextUtils.isEmpty(scheduleBinding.tvTime.getText().toString().trim())) {
                            sendInvitationRequest.setScheduleDate(scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                            // sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+"00:00"));
                            Log.e("TimeStamp1", scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                        } else {
                            sendInvitationRequest.setScheduleDate(scheduleBinding.tvDate.getText().toString().trim() + " " + scheduleBinding.tvTime.getText().toString().trim());
                            //sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+scheduleBinding.tvTime.getText().toString().trim()));
                            Log.e("TimeStamp2", scheduleBinding.tvDate.getText().toString().trim() + " " + scheduleBinding.tvTime.getText().toString().trim() + "");

                        }

                    }

                    callSendInvitationsApi(sendInvitationRequest);


                }

             /* if (validation()) {
                    StringBuilder empId = new StringBuilder("");
                    for (EmployeeData data : employeeDatas) {
                        if (data.isSelected()) {
                            if (empId.toString().length() > 0) {
                                empId.append(", " + data.getmEmpId());
                            } else {
                                empId.append(data.getmEmpId());
                            }
                        }

                   }
                    Log.e("EMpoyeeId",empId.toString());
                    SendInvitationRequest sendInvitationRequest = new SendInvitationRequest();
                    sendInvitationRequest.setUserId(SharedPref.getPreferencesString(requireActivity(), AppConstant.USER_ID));
                    sendInvitationRequest.setDescription(scheduleBinding.etJobDetails.getText().toString().trim());
                    sendInvitationRequest.setTitle(subCatName);
                    sendInvitationRequest.setEmpIds(empId);

                    if (scheduleBinding.cbImmediate.isChecked()) {
                        sendInvitationRequest.setIsImmediate("1");
                        sendInvitationRequest.setScheduleDate("");
                        sendInvitationRequest.setRquest("sendInvitations");


                    } else {
                        sendInvitationRequest.setIsImmediate("0");
                        sendInvitationRequest.setRquest("sendInvitations");
                        Log.e("scduleDate1",scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                        if (TextUtils.isEmpty(scheduleBinding.tvTime.getText().toString().trim())) {
                            sendInvitationRequest.setScheduleDate(scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                            // sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+"00:00"));
                            Log.e("TimeStamp1", scheduleBinding.tvDate.getText().toString().trim() + " " + "00:00");
                        } else {
                            sendInvitationRequest.setScheduleDate(scheduleBinding.tvDate.getText().toString().trim() + " " + scheduleBinding.tvTime.getText().toString().trim());
                            //sendInvitationRequest.setScheduleDate(CommonUtils.getTimeStamp(scheduleBinding.tvDate.getText().toString().trim()+" "+scheduleBinding.tvTime.getText().toString().trim()));
                            Log.e("TimeStamp2", scheduleBinding.tvDate.getText().toString().trim() + " " + scheduleBinding.tvTime.getText().toString().trim() + "");

                        }
                    }

                 callSendInvitationsApi(sendInvitationRequest);
                }*/

               /* Fragment fragment = new MyJobFragment();
                CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);*/


                break;
            default:
                break;
        }
    }

    private boolean validation() {
        if (!scheduleBinding.cbImmediate.isChecked() && TextUtils.isEmpty(scheduleBinding.tvDate.getText().toString().trim())) {
            CommonUtils.showSnack(scheduleBinding.getRoot(), getString(R.string.req_type));
            return false;
        } else if (TextUtils.isEmpty(scheduleBinding.etJobDetails.getText().toString().trim())) {
            CommonUtils.showSnack(scheduleBinding.getRoot(), getString(R.string.enter_job_details));
            return false;
        } else {
            return true;
        }

    }


    private void callgetEmployeeApi() {
        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("lat", selectedLatLang.latitude + "");
        jsonObject.addProperty("lng", selectedLatLang.longitude + "");
        jsonObject.addProperty("employee_type", binding.spProvider.getSelectedItemPosition());
        jsonObject.addProperty("sub_cat_id", subCatId);
        jsonObject.addProperty("rquest","getServiceProvider");
        Call<EmployeeResponse> employeeResponseCall = APIExecutor.getApiService().callEmployee(jsonObject);
        employeeResponseCall.enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                hideProgressDialog();
                if (response != null && response.body() != null && response.body().getStatus() != null) {
                    employeeDatas.clear();
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        binding.tvNoData.setVisibility(View.GONE);
                        employeeDatas.addAll(response.body().getData());
                    } else {
                        binding.tvNoData.setText(response.body().getMessage() != null ? response.body().getMessage() : "");
                        binding.tvNoData.setVisibility(View.VISIBLE);
                    }
                    serviceProviderAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.e("onFailure", t + "");
                hideProgressDialog();
            }
        });
    }


   /* @Override
    public void onNotify(int pos,boolean status) {
        employeeDatas.get(pos).setSelected(status);
        serviceProviderAdapter.notifyDataSetChanged();
    }*/


    /*private void callSendInvitationsApi(SendInvitationRequest sendInvitationRequest) {
        if (dialogScheduleJob != null) {
            dialogScheduleJob.dismiss();
        }
        showProgressDialog();
        Call<InvitationsResponse> registrationModelCall = APIExecutor.getApiService(requireActivity()).callSendInvitations(sendInvitationRequest);
        registrationModelCall.enqueue(new Callback<InvitationsResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<InvitationsResponse> call, Response<InvitationsResponse> response) {
                hideProgressDialog();
                if (response != null && response.body() != null && response.body().getStatus() != null && response.body().getStatus().equalsIgnoreCase("1")) {

                    Fragment fragment = new MyJobFragment();
                    CommonUtils.commonAlert(requireActivity(), response.body().getMessage(), fragment);
                } else {
                    CommonUtils.showSnack(scheduleBinding.getRoot(), getString(R.string.server_not_responding));
                }
            }

            @Override
            public void onFailure(Call<InvitationsResponse> call, Throwable t) {
                Log.e("onFailure", t + "");
                hideProgressDialog();
            }
        });
    }*/



    private void callSendInvitationsApi(SendInvitationRequest request) {


        apiService.callSendInvitationAPI(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {

                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null) {

                        Fragment fragment = new MyJobFragment();
                        CommonUtils.commonAlert(requireActivity(), commonResponse.getMessage(), fragment);

                        if (dialogScheduleJob != null) {
                            dialogScheduleJob.dismiss();
                        }

                        //Fragment fragment = new NewJobFragment();
                        //CommonUtils.setFragment(fragment,false, (FragmentActivity) requireActivity(), R.id.flContainerHome);

                    } else{
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),requireActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),throwable.getMessage());
                    }
                });
    }



    private void callServicesProviderAPI(String subCategotyId, String lat, String log) {
        serviceProdersModels.clear();
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("subcat_id",subCategotyId);
        jsonObject.addProperty("lat",lat);
        jsonObject.addProperty("long",log);

        apiService.callServicesProviderAPI(jsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {

                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&commonResponse.getData().size()>0) {
                        serviceProdersModels.addAll(commonResponse.getData());
                        serviceProviderAdapter.notifyDataSetChanged();
                    } else{
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),requireActivity().getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(requireActivity(),requireActivity().getString(R.string.error),throwable.getMessage());
                    }
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = requireActivity();
    }

}