package com.calltechservice.ui.adapter;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.calltechservice.model.response.CompleteJobResponse;
import com.calltechservice.model.response.JobInvitationResponse;
import com.calltechservice.ui.fragment.InviteCartFragment;
import com.calltechservice.ui.fragment.JobHistoryFragment;

import java.util.ArrayList;


public class DashboardPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = new String[]{"Recent Invitation", "Completed Jobs"};
    private ArrayList<JobInvitationResponse> recentInvitationModels;
    private ArrayList<CompleteJobResponse> inprogressJobModels;
    private Fragment fragment = null;

    public DashboardPagerAdapter(FragmentManager fm, ArrayList<JobInvitationResponse> recentInvitationModels, ArrayList<CompleteJobResponse> inprogressJobModels) {
        super(fm);
        this.recentInvitationModels = recentInvitationModels;
        this.inprogressJobModels = inprogressJobModels;
    }

    public DashboardPagerAdapter(FragmentManager fm) {










        super(fm);

    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            fragment = new InviteCartFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("recentList", recentInvitationModels);
            fragment.setArguments(bundle);

        } else {
            fragment = new JobHistoryFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("inprogressList", inprogressJobModels);
            fragment.setArguments(bundle);
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
