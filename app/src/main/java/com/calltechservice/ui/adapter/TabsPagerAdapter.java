package com.calltechservice.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            /*case 0:
                return new PhoneNoFragment();
            case 1:
                return new VerifyOtpFragment();
            case 2:
                return new RegistrationFragment();*/

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}