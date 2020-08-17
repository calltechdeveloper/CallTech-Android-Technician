package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentDashboardBinding;
import com.calltechservice.ui.activity.HomeActivity;
import com.calltechservice.ui.adapter.DashboardAdapter;
import com.calltechservice.utils.CommonUtils;

import java.util.Objects;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class DashboardFragment extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private FragmentDashboardBinding binding;
    private DashboardAdapter adapter;
    private FrameLayout redCircle;
    private TextView countTextView;
    private int alertCount = 0;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false);
        setHasOptionsMenu(true);
        ((HomeActivity) requireActivity()).changeIcon(true);
        Objects.requireNonNull(requireActivity()).setTitle("Home");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(requireActivity()).setTitle("Home");
        adapter = new DashboardAdapter(getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        binding.recyclerView.setLayoutManager(gridLayoutManager); // set LayoutManager to RecyclerView
        binding.recyclerView.setAdapter(adapter);


        //adapter.setOnItemClickListener((position, view1) -> replaceFragment(new MyServicesFragment()));

        adapter.setOnItemClickListener((position, view1) -> {
           /* Fragment fragment = null;
            Bundle bundle = new Bundle();
            bundle.putParcelable("category",serviceCtegoryModels.get(position));
            fragment = new MyServicesFragment();
            fragment.setArguments(bundle);
            //Fragment fragment=new MyServicesFragment();
            CommonUtils.setFragment(fragment,false, (FragmentActivity) mContext, R.id.flContainerHome);*/
        });



    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = requireActivity();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notification, menu);
        super.onCreateOptionsMenu(menu,inflater);

        final MenuItem alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item);

        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new NotificationFragment();
                CommonUtils.setFragment(fragment,false, (FragmentActivity) mContext, R.id.flContainerHome);
            }
        });
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        *//*super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);*//*
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.notification, menu);
        final MenuItem alertMenuItem = menu.findItem(R.id.activity_main_alerts_menu_item);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        redCircle = rootView.findViewById(R.id.view_alert_red_circle);
        countTextView =  rootView.findViewById(R.id.view_alert_count_textview);
        //return super.onCreateOptionsMenu(menu);


    }*/


   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_item_to_handle_in_fragment:
                // Do onlick on menu action here
                return true;
        }
        return false;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* switch (item.getItemId()) {
            case R.id.activity_main_alerts_menu_item:

                Fragment fragment= new NotificationFragment();
                CommonUtils.setFragment(fragment,false, (FragmentActivity) mContext, R.id.flContainerHome);
                Toast.makeText(getContext(), "count cleared", Toast.LENGTH_SHORT).show();


                return true;
        }*/
        return onOptionsItemSelected(item);




       /* switch (item.getItemId()) {
            case R.id.notification:
                alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
                updateAlertIcon();
                return true;

            case R.id.activity_main_alerts_menu_item:
                // TODO update alert menu icon
                Toast.makeText(this, "count cleared", Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }*/
        //return super.onOptionsItemSelected(item);
    }


    private void updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        if (0 < alertCount && alertCount < 10) {
            countTextView.setText(String.valueOf(alertCount));
        } else {
            countTextView.setText("");
        }

        redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);
    }





}
