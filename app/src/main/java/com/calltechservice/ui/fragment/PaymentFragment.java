package com.calltechservice.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentPaymentBinding;
import com.calltechservice.ui.activity.HomeActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class PaymentFragment extends BaseFragment {
    private FragmentPaymentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_payment, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false);
        setHasOptionsMenu(true);
        lickListener();
        return binding.getRoot();
    }

    public void lickListener() {
        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
        //navigationView.setCheckedItem(R.id.payment_cart);
        ((HomeActivity) requireActivity()).changeIcon(true);
        //requireActivity().setTitle("Payment");
        requireActivity().setTitle("Payment");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            Snackbar.make(binding.getRoot(), "Working", Snackbar.LENGTH_SHORT).show();
               /* if (validation()) {
                    if (CommonUtils.isOnline(requireActivity())) {
                        //callUpdateProfileApi();
                    } else {
                        Snackbar.make(binding.getRoot(), getString(R.string.internet_connection), Snackbar.LENGTH_SHORT).show();
                    }
                }*/

            return true;
        }
        return onOptionsItemSelected(item);
    }

}