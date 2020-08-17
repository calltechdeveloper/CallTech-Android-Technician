package com.calltechservice.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.calltechservice.R;
import com.calltechservice.databinding.FragmentInviteFriendBinding;
import com.calltechservice.ui.activity.HomeActivity;

public class InviteFriendFragment extends Fragment {

    private FragmentInviteFriendBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite_friend, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) requireContext()).changeIcon(true);
        requireActivity().setTitle("Share");
    }

}