package com.yongyi.financialinfo.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class HorizontalCanScrollViewPager extends ViewPager {

    public HorizontalCanScrollViewPager(Context context) {
        this(context, null);
    }

    public HorizontalCanScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof HorizontalScrollView || v instanceof ViewPager) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }


}