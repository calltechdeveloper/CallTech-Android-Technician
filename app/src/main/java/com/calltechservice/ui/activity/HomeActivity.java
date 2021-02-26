
package com.calltechservice.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import com.calltechservice.ui.fragment.DocumentsFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.calltechservice.BuildConfig;
import com.calltechservice.R;
import com.calltechservice.db.SharedPref;
import com.calltechservice.db.UserPref;
import com.calltechservice.location.LocationResult;
import com.calltechservice.location.LocationTracker;
import com.calltechservice.location.MapDisplayUtils;
import com.calltechservice.ui.fragment.AboutUsFragment;
import com.calltechservice.ui.fragment.MyAccountFragment;
import com.calltechservice.ui.fragment.MyJobFragment;
import com.calltechservice.ui.fragment.MyServicesFragment;
import com.calltechservice.ui.fragment.NotificationFragment;
import com.calltechservice.ui.fragment.PolicyFragment;
import com.calltechservice.ui.fragment.ServiceProviderDashboardFragment;
import com.calltechservice.ui.fragment.Termsprivacy;
import com.calltechservice.utils.AppConstant;
import com.calltechservice.utils.CommonUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationResult {

    private Toolbar toolbar;
    private Fragment fragment;
    private Context mContext;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private LocationTracker locationTracker;
    private TextView tvLocation;
    private TextView tvName;
    private TextView tvEmail;
    private ImageView ivProfilePic;
    private Location currentLocation;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = HomeActivity.this;
        setDrawar();
    }


    private void setDrawar() {
        startLocation();
        /*if (PermissionUtils.checkPermissionLocation(HomeActivity.this)){
            locationTracker = new LocationTracker(HomeActivity.this, HomeActivity.this);
            locationTracker.onUpdateLocation();
        }else{
            PermissionUtils.requestPermissionLocation(HomeActivity.this);

        }*/

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        tvLocation = header.findViewById(R.id.tvLocation);
        tvName = header.findViewById(R.id.tvUserName);
        tvEmail = header.findViewById(R.id.tvEmail);
        ivProfilePic = header.findViewById(R.id.ivUserImage);

        ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setTitle("My Account");
                fragment = new MyAccountFragment();
                CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
                onBackPressed();
            }
        });
        if (getIntent().getBooleanExtra(AppConstant.IS_NOTIFICATION, false)) {
            fragment = new NotificationFragment();
            CommonUtils.setFragment(fragment, false, (FragmentActivity) mContext, R.id.flContainerHome);
            navigationView.setCheckedItem(R.id.notification);
        } else {
            fragment = new ServiceProviderDashboardFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
            navigationView.setCheckedItem(R.id.user_home);
        }
        if (SharedPref.getPreferencesLat(mContext, AppConstant.LAT) != 0 && SharedPref.getPreferencesLong(mContext, AppConstant.LONG) != 0) {
            tvLocation.setText(MapDisplayUtils.getAddressFromLatLng(HomeActivity.this, SharedPref.getPreferencesLat(mContext, AppConstant.LAT), SharedPref.getPreferencesLong(mContext, AppConstant.LONG)));
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserData();
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    onBackPressed();
                } else {
                    changeIcon(true);
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        hideItem();
    }


    public void startLocation() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        locationTracker = new LocationTracker(HomeActivity.this, HomeActivity.this);
                        locationTracker.updateLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setUserData() {
        UserPref userPref = new UserPref(this);

        if (userPref != null) {

            tvName.setText(userPref.getUser().getName());
            tvEmail.setText(userPref.getUser().getEmail());

            Glide.with(HomeActivity.this)
                    .load(Uri.parse(userPref.getUser().getImage()))
                    .apply(RequestOptions.placeholderOf(R.drawable.user))
                    .apply(RequestOptions.errorOf(R.drawable.user))
                    .into(ivProfilePic);

        }



        /*if(SharedPref.getPreferenceBoolean(mContext,AppConstant.IS_GUEST)) {
            tvName.setText(R.string.guest);
        }
        else {
            if(SharedPref.getPreferencesString(mContext,AppConstant.NAME)!=null)
            {
                tvName.setText(SharedPref.getPreferencesString(mContext,AppConstant.NAME));
            }
            if(SharedPref.getPreferencesString(mContext,AppConstant.EMAIL)!=null)
            {
                tvEmail.setText(SharedPref.getPreferencesString(mContext,AppConstant.EMAIL));
            }

            if(SharedPref.getPreferencesString(mContext,AppConstant.PROFILE_PIC)!=null&&!SharedPref.getPreferencesString(mContext,AppConstant.PROFILE_PIC).equalsIgnoreCase(""))
            {
                Glide.with(mContext).load(SharedPref.getPreferencesString(mContext,AppConstant.PROFILE_PIC)).apply(new RequestOptions().placeholder(R.drawable.user).dontAnimate()).into(ivProfilePic);
            }
            else
            {
                ivProfilePic.setImageResource(R.drawable.user);
            }
        }*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            } else if (fragment instanceof ServiceProviderDashboardFragment) {

                CommonUtils.exitDialog(mContext);
            } else {
                fragment = new ServiceProviderDashboardFragment();
                CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
            }
        }
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.home, menu);
          return true;
      }
  */
 /*   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    private void hideItem() {
        Menu navMenu = navigationView.getMenu();
       /* if(SharedPref.getPreferencesString(mContext,AppConstant.USER_ID)==null||SharedPref.getPreferencesString(mContext,AppConstant.USER_ID).equalsIgnoreCase("")) {
            navMenu.findItem(R.id.invite_cart).setVisible(false);
            navMenu.findItem(R.id.account).setVisible(false);
            navMenu.findItem(R.id.action_register).setVisible(true);
           *//* navMenu.findItem(R.id.).setVisible(false);
            navMenu.findItem(R.id.job_history).setVisible(false);*//*
            navMenu.findItem(R.id.notification).setVisible(false);
            navMenu.findItem(R.id.action_logout).setVisible(false);
            ivProfilePic.setVisibility(View.GONE);
        }
        else
        {
            navMenu.findItem(R.id.action_register).setVisible(false);
        }*/


        if (SharedPref.getPreferencesString(mContext, AppConstant.USER_ID) == null || SharedPref.getPreferencesString(mContext, AppConstant.USER_ID).equalsIgnoreCase("")) {
            navMenu.findItem(R.id.invite_cart).setVisible(true);
            navMenu.findItem(R.id.account).setVisible(true);
            navMenu.findItem(R.id.action_register).setVisible(false);
           /* navMenu.findItem(R.id.).setVisible(false);
            navMenu.findItem(R.id.job_history).setVisible(false);*/
            navMenu.findItem(R.id.notification).setVisible(false);
            navMenu.findItem(R.id.action_logout).setVisible(true);
            ivProfilePic.setVisibility(View.VISIBLE);
        } else {
            navMenu.findItem(R.id.action_register).setVisible(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        CommonUtils.hideSoftKeyboard((Activity) mContext);
        Bundle bundle = new Bundle();
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.user_home) {
            toolbar.setTitle("Home");
            fragment = new ServiceProviderDashboardFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        } else if (id == R.id.account) {
            toolbar.setTitle("My Account");
            fragment = new MyAccountFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        } else if (id == R.id.my_services) {
            toolbar.setTitle("My Services");
            // fragment=new InviteCartFragment();
            fragment = new MyServicesFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        } else if (id == R.id.invite_cart) {
            toolbar.setTitle("Jobs");
            fragment = new MyJobFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        } else if (id == R.id.documents) {
            toolbar.setTitle(R.string.documents);
            fragment = new DocumentsFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        }
         /*else if (id == R.id.offers) {
            toolbar.setTitle("Offers");
            *//*fragment=new OffersFragment();
            CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);*//*
        }*/

        else if (id == R.id.action_register) {
            Intent intent = new Intent(mContext, NewUserActivity.class);
            startActivity(intent);

        }
      /*  else if (id == R.id.share) {
        toolbar.setTitle("Share");
        fragment=new InviteFriendFragment();
        CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);
    }*/
        else if (id == R.id.about_us) {
            toolbar.setTitle("About Us");
            bundle.putInt("type", 0);
            fragment = new AboutUsFragment();
            fragment.setArguments(bundle);

            //fragment=new PolicyFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
           /* Intent intent = new Intent(HomeActivity.this,WebDemoActivity.class);
            startActivity(intent);*/
            /*toolbar.setTitle("About Us");
            fragment=new AboutUsFragment();
            CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);*/
        }

      /*  else if (id == R.id.on_going) {
            toolbar.setTitle("On Going Jobs");
            fragment=new OnGoingFragment();
            CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);
        }
        else if (id == R.id.job_history) {
            toolbar.setTitle("Job History");
            fragment=new JobHistoryFragment();
            CommonUtils.setFragment(fragment,true, (FragmentActivity) mContext, R.id.flContainerHome);
        }*/

       /* else if (id == R.id.notification) {
            toolbar.setTitle("Notification");
            fragment=new NotificationFragment();
            CommonUtils.setFragment(fragment,false, (FragmentActivity) mContext, R.id.flContainerHome);
        }*/
        else if (id == R.id.terms) {
            toolbar.setTitle("Terms & Privacy");
            bundle.putInt("type", 1);
            fragment = new Termsprivacy();
            fragment.setArguments(bundle);
            //fragment=new ComingSoonFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        } else if (id == R.id.help) {
            toolbar.setTitle("Help Center");
            bundle.putInt("type", 2);
            fragment = new PolicyFragment();
            fragment.setArguments(bundle);
            //fragment=new ComingSoonFragment();
            CommonUtils.setFragment(fragment, true, (FragmentActivity) mContext, R.id.flContainerHome);
        } else if (id == R.id.action_logout) {
            //CommonUtils.logoutDialog(mContext);
            logoutAlert();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void logoutAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Logout");
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            UserPref userPref = new UserPref(this);
            userPref.clearPref();
            SharedPref.removePreference(this);
            startActivity(new Intent(this, NewUserActivity.class));
            finish();
        });

        builder.setNegativeButton("No", null);
        builder.create();
        builder.show();
    }

    public void changeIcon(boolean show) {
        if (show) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            toggle.setDrawerIndicatorEnabled(true);
            toggle.syncState();
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            toggle.syncState();
        } else {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            toggle.setDrawerIndicatorEnabled(false);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            /*toggle.setHomeAsUpIndicator(R.drawable.ic_arrow_back);*/
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        }
    }

    @Override
    public void gotLocation(Location location) {
        currentLocation = location;
        if (tvLocation != null) {
            SharedPref.savePreferencesLat(mContext, AppConstant.LAT, location);
            SharedPref.savePreferencesLong(mContext, AppConstant.LONG, location);
            tvLocation.setText(MapDisplayUtils.getAddressFromLatLng(HomeActivity.this, currentLocation.getLatitude(), currentLocation.getLongitude()));
        }
    }
}


