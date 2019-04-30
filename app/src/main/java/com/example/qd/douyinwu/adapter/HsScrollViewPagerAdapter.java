package com.example.qd.douyinwu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Copyright (C) 2016,深圳市恒实网络科技股份有限公司 All rights reserved.
 * 类描述：主页面
 * 创建人：刘勇
 * 创建时间：2017/2/22 16:22
 * 修改人：刘勇
 * 修改时间：2017/2/22 16:34
 * 修改备注：
 * Version:  1.0.0
 */

public class HsScrollViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragment;
    private List<String> mTitle;

    public HsScrollViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitle) {
        super(fm);
        this.mFragment = fragments;
        this.mTitle = mTitle;
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