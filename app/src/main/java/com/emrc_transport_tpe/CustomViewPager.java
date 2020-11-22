package com.emrc_transport_tpe;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by An on 2018/7/4.
 */

public class CustomViewPager extends ViewPager{
    private boolean isSlidingEnable = true ;
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return  this.isSlidingEnable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isSlidingEnable && super.onInterceptTouchEvent(ev);
    }

    public void setSlidingEnable(boolean slidingEnable) {
        isSlidingEnable = slidingEnable;
    }
}
