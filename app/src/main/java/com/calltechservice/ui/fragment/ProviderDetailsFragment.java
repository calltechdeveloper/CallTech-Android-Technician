package com.calltechservice.ui.fragment;

import android.content.Context;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.model.response.ServiceProdersModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import com.calltechservice.R;
import com.calltechservice.api.APIExecutor;
import com.calltechservice.databinding.ProviderDetailsBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.model.MyItem;
import com.calltechservice.model.response.employee.EmployeeData;
import com.calltechservice.model.response.employee.EmployeeResponse;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.EmployeListAdapter;
import com.calltechservice.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderDetailsFragment extends BaseFragment /*implements OnMapReadyCallback*/ {
    /*implements OnMapReadyCallback*/
    private ProviderDetailsBinding binding;
    private GoogleMap mMap;
    private ClusterManager<MyItem> mClusterManager;
    private LinearLayoutManager linearLayoutManager;
    private EmployeListAdapter employeListAdapter;
    private EmployeeData details;
    private List<EmployeeData> employeeDatas;
    private String subCatId;

    private ServiceProdersModel model;


    public ProviderDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            model = (ServiceProdersModel) bundle.get("provider");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.provider_details, container, false);
        requireActivity().setTitle("Service Provider Details");
        ((HomeActivity) requireActivity()).changeIcon(false);
        employeeDatas = new ArrayList<>();
        // details=getArguments().getParcelable("details");
        //subCatId=getArguments().getString("sub_cat");

        setData();
        if (details != null) {
            setData();
            if (details.getIsIndividual() != null && details.getIsIndividual().equalsIgnoreCase("0")) {
                //callGetEmployeeApi();
            }
        }
        //loadMap();

        return binding.getRoot();
    }

    private void callGetEmployeeApi() {
        showProgressDialog();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("agency_id", details.getmEmpId());
        jsonObject.addProperty("sub_cat_id", subCatId);
        jsonObject.addProperty("rquest", "getAgencyEmp");
        Call<EmployeeResponse> registrationModelCall = APIExecutor.getApiService().callAgencyEmployee(jsonObject);
        registrationModelCall.enqueue(new Callback<EmployeeResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                hideProgressDialog();
                if (response != null && response.body() != null && response.body().getStatus() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("1")) {
                        employeeDatas.addAll(response.body().getData());
                        employeListAdapter.notifyDataSetChanged();
                    } else {
                        CommonUtils.showSnack(binding.getRoot(), response.body().getMessage() != null ? response.body().getMessage() : requireActivity().getString(R.string.server_not_responding));
                    }
                } else {
                    CommonUtils.showSnack(binding.getRoot(), getString(R.string.server_not_responding));
                }
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.e("onFailure", t + "");
                hideProgressDialog();
            }
        });
    }

    private void setData() {
        ((HomeActivity) requireActivity()).changeIcon(false);

        binding.tvUserName.setText(model.getName());
        binding.tvDetails.setText(model.getAbout());
        binding.tvNoOfJobDone.setText(model.getTotal_job());
        binding.tvExperience.setText(model.getTotal_exp());
        binding.tvLocation.setText(model.getService_area_name());

        binding.ratingBar.setRating(Float.parseFloat(model.getRating()));

        if (model.getProfile_pic() != null && !model.getProfile_pic().equalsIgnoreCase("")) {

            Glide.with(requireActivity())
                    .load(Uri.parse(model.getProfile_pic())).apply(RequestOptions.fitCenterTransform())
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_user))
                    .apply(RequestOptions.errorOf(R.drawable.ic_user))
                    .into(binding.ivProfile);
        } else {
            binding.ivProfile.setImageResource(R.drawable.ic_user);
        }
    }

    private void loadMap() {
        /*SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);*/
    }

   /* @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnInfoWindowClickListener(mClusterManager);
        setUpClusterer();
        mClusterManager.cluster();

    }*/


    private void setUpClusterer() {
       /* mClusterManager = new ClusterManager<MyItem>(requireActivity(), mMap);
        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);
        addItems();
        new RenderClusterInfoWindow(getContext(),mMap,mClusterManager);*/
    }

    private void addItems() {
       /* LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i = 0; i < details.getAreaList().size(); i++) {
            MyItem offsetItem = new MyItem(Float.valueOf(details.getAreaList().get(i).getLat()), Float.valueOf(details.getAreaList().get(i).getLng()),details.getAreaList().get(i).getArea_name(),"");
            mClusterManager.addItem(offsetItem);
            builder.include(new LatLng(Float.valueOf(details.getAreaList().get(i).getLat()), Float.valueOf(details.getAreaList().get(i).getLng())));
        }
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mMap.animateCamera(cu);*/
    }


    private class RenderClusterInfoWindow extends DefaultClusterRenderer<MyItem> {

        RenderClusterInfoWindow(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
            super.onClusterRendered(cluster, marker);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
            markerOptions.title(item.getTitle());
            super.onBeforeClusterItemRendered(item, markerOptions);
        }
    }

}