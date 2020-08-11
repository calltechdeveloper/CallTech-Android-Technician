package com.calltechservice.ui.fragment;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.calltechservice.BaseFragment;
import com.calltechservice.R;
import com.calltechservice.databinding.FragmentAboutUsBinding;
import com.calltechservice.model.response.AboutUsResponse;
import com.calltechservice.ui.activity.HomeActivity;

import java.net.ConnectException;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class AboutUsFragment extends BaseFragment implements View.OnClickListener {


    private FragmentAboutUsBinding binding;
    AboutUsResponse model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        callaboutusapi();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false);
        ((HomeActivity) getActivity()).changeIcon(false);

        binding.fb.setOnClickListener(this);
        binding.linkedin.setOnClickListener(this);
        binding.twitter.setOnClickListener(this);
        binding.instagram.setOnClickListener(this);
        return binding.getRoot();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );


    }



    private void callaboutusapi() {


        apiService.callaboutusapi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(this::showProgressDialog)
                .doOnCompleted(this::hideProgressDialog)
                .subscribe(commonResponse -> {
                    if (commonResponse.getStatus() == 1&&commonResponse.getData()!=null&&!commonResponse.getData().equals("")) {

                        model= (AboutUsResponse) commonResponse.getData();

                        binding.tvdescription.setText(utils.fromHtml(model.getDescription().toString()));



                    } else{
                        // binding.lytTop.setVisibility(View.GONE);
                        utils.simpleAlert(getContext(),getString(R.string.error),commonResponse.getMessage());
                        hideProgressDialog();
                    }
                }, throwable -> {
                    hideProgressDialog();
                    if(throwable instanceof ConnectException)
                    {
                        utils.simpleAlert(getContext(),getString(R.string.error),getString(R.string.check_network_connection));
                    }
                    else
                    {
                        utils.simpleAlert(getContext(),getString(R.string.error),throwable.getMessage());
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fb:
                movetoWeb("https://www.facebook.com/calltech.technologies/");
                break;

            case R.id.linkedin:
                movetoWeb("https://www.linkedin.com/company/calltech-technologies/");
                break;
            case R.id.instagram:
                movetoWeb("https://www.instagram.com/calltech.technologies/");
                break;
            case R.id.twitter:
                movetoWeb("https://twitter.com/CallTechIT");
                break;
        }
    }

    private void movetoWeb( String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
