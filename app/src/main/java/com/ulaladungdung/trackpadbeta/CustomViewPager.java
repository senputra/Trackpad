package com.ulaladungdung.trackpadbeta;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Nobody on 12/18/2015.
 */
public class CustomViewPager extends ViewPager{

    boolean swiping = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return swiping ? super.onTouchEvent(event) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return swiping ? super.onInterceptTouchEvent(event) : false;
    }

    public void setSwipingEnabled(boolean enabled) {
        this.swiping = enabled;
    }

    public boolean isSwipingEnabled() {
        return swiping;
    }
}
