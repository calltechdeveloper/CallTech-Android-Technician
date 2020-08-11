package com.calltechservice.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.calltechservice.BaseActivity;
import com.calltechservice.R;
import com.calltechservice.databinding.ActivityDemoBinding;
import com.calltechservice.ui.adapter.SlidingImageAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DemoActivity extends BaseActivity implements View.OnClickListener {

    ActivityDemoBinding binding;
    private static final Integer[] IMAGES = {R.drawable.imgs1, R.drawable.imgs2, R.drawable.imgs3, R.drawable.imgs4};
    private static final String[] IMAGES1 = {"Microsoft Specialized Services \n For All your Microsoft Tech Support", "On Site & Remote Support \nFor All your Tech Support", "Cyber Security \nSpecialized Scan & Clean", "AI ROBOT \n  Sales & Support"};
    private ArrayList<Integer> imagesArray = new ArrayList<>();
    private ArrayList<String> imagesArray2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        clickListener();
        init();
    }

    public void clickListener() {
        binding.btSkip.setOnClickListener(this);
        binding.btGetStarted.setOnClickListener(this);
        binding.btLogin.setOnClickListener(this);
    }

    private void init() {
        imagesArray.addAll(Arrays.asList(IMAGES));
        Collections.addAll(imagesArray2, IMAGES1);
        binding.pager.setAdapter(new SlidingImageAdapter(DemoActivity.this, imagesArray, imagesArray2));
        binding.indicator.setViewPager(binding.pager);
        //Set circle indicator radius
        binding.indicator.setRadius(5 * getResources().getDisplayMetrics().density);
        // Pager listener over indicator
        binding.indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
                if (pos == IMAGES.length - 1) {
                    binding.btSkip.setVisibility(View.GONE);
                    binding.btLogin.setVisibility(View.GONE);
                    binding.btGetStarted.setVisibility(View.VISIBLE);
                } else {
                    binding.btSkip.setVisibility(View.VISIBLE);
                    binding.btLogin.setVisibility(View.VISIBLE);
                    binding.btGetStarted.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSkip:
            case R.id.btLogin:
            case R.id.btGetStarted:
            default:
                Intent intent1 = new Intent(DemoActivity.this, NewUserActivity.class);
                finishAffinity();
                startActivity(intent1);
                break;
        }
    }
}