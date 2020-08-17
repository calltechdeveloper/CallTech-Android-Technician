package com.calltechservice.ui.fragment;


import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentComingSoonBinding;
import com.calltechservice.ui.activity.HomeActivity;


public class ComingSoonFragment extends BaseFragment {
    private FragmentComingSoonBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_coming_soon, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_coming_soon, container, false);
        //View view = binding.getRoot();
        ((HomeActivity) requireActivity()).changeIcon(false);
        //requireActivity().setTitle("Coming Soon");

        return binding.getRoot();
    }

}
