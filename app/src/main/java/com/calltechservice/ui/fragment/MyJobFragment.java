package com.calltechservice.ui.fragment;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.calltechservice.databinding.FragmentMyJobBinding;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.ui.activity.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class MyJobFragment extends BaseFragment /*implements ViewPager.OnPageChangeListener*/{
    private FragmentMyJobBinding binding;

    private Context context;
    private NavigationView navigationView;

    ViewPagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_job, container, false);
        View view = binding.getRoot();
        setupViewPager();
        setHeder();
        binding.tabs.setupWithViewPager(binding.viewpager);
        setupTabIcons();
        return view;
    }

    private void setHeder() {
        navigationView =  requireActivity().findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.invite_cart);
        ((HomeActivity) requireActivity()).changeIcon(true);
        requireActivity().setTitle("Jobs");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    private void setupTabIcons() {
        /*binding.tabs.getTabAt(0).setIcon(R.drawable.ic_verified_user_white_24dp);
        binding.tabs.getTabAt(1).setIcon(R.drawable.ic_directions_run_black_24dp);
        binding.tabs.getTabAt(2).setIcon(R.drawable.ic_history_black_24dp);*/

        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((HomeActivity) requireActivity()).changeIcon(true);
                requireActivity().setTitle("Jobs");
               /* switch (tab.getPosition())
                {
                    case 0:
                        binding.tabs.getTabAt( tab.getPosition()).setIcon(R.drawable.ic_verified_user_white_24dp);
                        break;
                    case 1:
                        binding.tabs.getTabAt( tab.getPosition()).setIcon(R.drawable.ic_directions_run_white_24dp);
                        break;
                    case 2:
                        binding.tabs.getTabAt( tab.getPosition()).setIcon(R.drawable.ic_history_white_24dp);
                        break;
                }

                for (int i=0;i<3;i++)
                {
                    if(tab.getPosition()!=i)
                    {
                        if(i==0)
                        {
                            binding.tabs.getTabAt( i).setIcon(R.drawable.ic_verified_user_white_24dp);
                        }
                        else  if(i==1)
                        {
                            binding.tabs.getTabAt(i).setIcon(R.drawable.ic_directions_run_black_24dp);
                        }
                        else
                        {
                            binding.tabs.getTabAt( i).setIcon(R.drawable.ic_history_black_24dp);

                        }


                    }
                }*/


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new InviteCartFragment(), "Requested");
        adapter.addFragment(new OnGoingFragment(), "In progress");
        adapter.addFragment(new JobHistoryFragment(), "Completed");
        binding.viewpager.setAdapter(adapter);

        //binding.viewpager.addOnPageChangeListener(this);
    }

  /*  @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        *//*Fragment fragment = adapter.getFragment(position);
        if (fragment != null) {
            fragment.onResume();
        }*//*
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        public Fragment getFragment(int position) {
            Fragment fragment = null;
            if (mFragmentList != null) {
                fragment = mFragmentList.get(position);
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
