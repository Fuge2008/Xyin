package com.example.qd.douyinwu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.example.qd.douyinwu.adapter.HsScrollViewPagerAdapter;
import com.example.qd.douyinwu.base.BaseActivity;
import com.example.qd.douyinwu.view.tab.SlidingTabLayout;
import com.example.qd.douyinwu.view.tab.listener.OnTabSelectListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends Activity implements ViewPager.OnPageChangeListener{
    @BindView(R.id.mSlidingTab)
    SlidingTabLayout mSlidingTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>();
    private ScaleAnimation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        anim = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.bottom_icon);
        setLisener();
//        initTab();

    }

    private void setLisener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSlidingTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                mSlidingTab.getTitleView(position).startAnimation(anim);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mViewPager.addOnPageChangeListener(this);
    }

//    private void initTab() {
//        List<String> strs = new ArrayList<>();
//        //添加热门
////        HnBrandTitleModel.DBean.ItemsBean item = new HnBrandTitleModel.DBean.ItemsBean();
////        item.setId("1");
////        item.setName("媒体介绍");
////        mTitles.add(item);
////        //关注
////        HnBrandTitleModel.DBean.ItemsBean item2 = new HnBrandTitleModel.DBean.ItemsBean();
////        item2.setId("2");
////        item2.setName("品牌介绍");
////        mTitles.add(item2);
////        //附近
////        HnBrandTitleModel.DBean.ItemsBean item3 = new HnBrandTitleModel.DBean.ItemsBean();
////        item3.setId("3");
////        item3.setName("品牌历程");
////        mTitles.add(item3);
////        HnBrandTitleModel.DBean.ItemsBean item4 = new HnBrandTitleModel.DBean.ItemsBean();
////        item4.setId("4");
////        item4.setName("老邱说宝");
////        mTitles.add(item4);
//        //mTitles.addAll(HnHomeBrandBiz.mCateData);
//        mTitles.add("首页");
//        mTitles.add("关注");
//        mTitles.add("照");
//        mTitles.add("消息");
//        mTitles.add("我");
//        for (int i = 0; i < mTitles.size(); i++) {
//            strs.add(mTitles.get(i).getName());
//            switch (mTitles.get(i).getId()) {
//                case "1": //  mFragments.add(new HomeHotFrag(big));
//                    mFragments.add(HnMediaLaoqiuFragment.getInstance(""));
//                    break;
//                case "2":
//                    mFragments.add(HnMediaIntroFragment.getInstance(""));
//                    break;
//                case "3":
//                    mFragments.add(BrandExperienceFragment.getInstance(mTitles.get(i).getSort(), mTitles.get(i).getArticle_url()));
//                    break;
//                case "4":
//                    mFragments.add(BrandExperienceFragment.getInstance(mTitles.get(i).getSort(), mTitles.get(i).getArticle_url()));
//                    break;
//                default:
//                    if (StringUtils.equals("N",mTitles.get(i).getIs_article())){
//
//                        mFragments.add(HnMediaIntroFragment.getInstance(""));
//                    }else {
//                        mFragments.add(BrandExperienceFragment.getInstance(mTitles.get(i).getSort(), mTitles.get(i).getArticle_url()));
//                    }
//
//                    break;
//            }
//        }
//
//        HsScrollViewPagerAdapter adapter = new HsScrollViewPagerAdapter(getChildFragmentManager(), mFragments, strs);
//        if (mFragments.size()<1) return;
//        //mViewPager.setOffscreenPageLimit(mFragments.size());
//        mViewPager.setAdapter(adapter);
//        mSlidingTab.setViewPager(mViewPager);
////        mScrollLayout.getHelper().setCurrentScrollableContainer(mFragments.get(0));
//        mViewPager.setCurrentItem(0);
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            @Override
//            public void onPageSelected(int position) {
//                mSlidingTab.getTitleView(position).startAnimation(anim);
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }







    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
