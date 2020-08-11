package com.calltechservice.ui.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.calltechservice.R;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<Integer> images1;
    private ArrayList<String> images2;
    private LayoutInflater inflater;
    private Context context;

    public SlidingImageAdapter(Context context, ArrayList<Integer> images1, ArrayList<String> images2) {
        this.context = context;
        this.images1 = images1;
        this.images2 = images2;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images1.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.splash_image, view, false);
        assert imageLayout != null;
        ImageView imageView = (ImageView) imageLayout.findViewById(R.id.ivImage);
        TextView tx = (TextView) imageLayout.findViewById(R.id.tvDemo);
        imageView.setImageResource(images1.get(position));
        tx.setText(images2.get(position));
        view.addView(imageLayout, 0);
        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}