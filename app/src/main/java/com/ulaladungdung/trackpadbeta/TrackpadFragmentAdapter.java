package com.ulaladungdung.trackpadbeta;

/**
 * Created by Nobody on 12/18/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class TrackpadFragmentAdapter extends FragmentPagerAdapter {

    int count = 2;
    FragmentManager fm;

    public TrackpadFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return fm.findFragmentByTag("android:switcher:" + R.id.pager + ":"+"0");
            case 1:
                return fm.findFragmentByTag("android:switcher:" + R.id.pager + ":"+"1");
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    public void setCount(int i){
        count = i;
        notifyDataSetChanged();
    }
}