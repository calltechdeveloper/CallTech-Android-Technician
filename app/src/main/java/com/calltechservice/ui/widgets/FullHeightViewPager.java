package com.calltechservice.ui.widgets;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class FullHeightViewPager extends ViewPager {

    public FullHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // super has to be called in the beginning so the child views can be
        // initialized.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getChildCount() <= 0)
            return;

        // Check if the selected layout_height mode is set to wrap_content
        // (represented by the AT_MOST constraint).
        boolean wrapHeight = MeasureSpec.getMode(heightMeasureSpec)
                == MeasureSpec.AT_MOST;

        int width = getMeasuredWidth();

        View firstChild = getChildAt(0);

        // Initially set the height to that of the first child - the
        // PagerTitleStrip (since we always know that it won't be 0).
        int height = firstChild.getMeasuredHeight();

        if (wrapHeight) {

            // Keep the current measured width.
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);

        }

        int fragmentHeight;
        fragmentHeight = measureFragment(((Fragment) getAdapter().instantiateItem(this, getCurrentItem())).getView());

        // Just add the height of the fragment:
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height + fragmentHeight,
                MeasureSpec.EXACTLY);

        // super has to be called again so the new specs are treated as
        // exact measurements.
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int measureFragment(View view) {
        if (view == null)
            return 0;

        view.measure(0, 0);
        return view.getMeasuredHeight();
    }
}