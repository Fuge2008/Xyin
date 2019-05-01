package com.fuge.xyin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

public class HsScrollViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment;
    private List<String> mTitle;

    public HsScrollViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitle) {
        super(fm);
        this.mFragment = fragments;
        this.mTitle = mTitle;
        Log.e("打印数据：",mTitle.toString()+":"+mFragment.toString());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitle != null || mTitle.size() > 0)
            try{
                return mTitle.get(position);

            }catch (Exception e){
                e.printStackTrace();
            }

        return super.getPageTitle(position);

    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment != null ? mFragment.size() : 0;
    }


}
