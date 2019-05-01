package com.fuge.xyin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fuge.xyin.R;
import com.fuge.xyin.adapter.HsScrollViewPagerAdapter;
import com.fuge.xyin.base.BaseFragment;
import com.fuge.xyin.model.TabEntity;
import com.fuge.xyin.utils.ToastUtils;
import com.fuge.xyin.view.CircleImageView;
import com.fuge.xyin.view.PullScrollView;
import com.fuge.xyin.view.WaveLineCenterView;
import com.fuge.xyin.view.tab.SlidingTabLayout;
import com.fuge.xyin.view.tab.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DyUserFragment extends BaseFragment implements PullScrollView.OnTurnListener, ViewPager.OnPageChangeListener {
    @BindView(R.id.background_img)
    ImageView mHeadImg;
    @BindView(R.id.mUserHeadCiv)
    CircleImageView mUserHeadCiv;
    @BindView(R.id.mEditInfoTv)
    TextView mEditInfoTv;
    @BindView(R.id.mAddFriendLl)
    RelativeLayout mAddFriendLl;
    @BindView(R.id.mUserNameTv)
    TextView mUserNameTv;
    @BindView(R.id.scroll_view_head)
    RelativeLayout scrollViewHead;
    @BindView(R.id.mSlidingTab)
    SlidingTabLayout mSlidingTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.scroll_view)
    PullScrollView mScrollView;
    @BindView(R.id.wl_center)
    WaveLineCenterView waveLineCenterView;
    @BindView(R.id.ll_user_container)
    LinearLayout userCenterContainer;

    private static final float MAX_FLOAT = 40.F;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<TabEntity> mTitles = new ArrayList<>();
    private ScaleAnimation anim;

    public static DyUserFragment getInstance(boolean big) {
        DyUserFragment frag = new DyUserFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("big", big);
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_user;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    }


    @Override
    protected void initData() {
        anim = (ScaleAnimation) AnimationUtils.loadAnimation(mActivity, R.anim.bottom_icon);
        initTab();
        setLisener();
        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
        waveLineCenterView.setWaveHeightListener(new WaveLineCenterView.WaveHeightListener() {
            @Override
            public void currentWaveHeightScal(float currentWavePercent) {
                changeUserIconParm(MAX_FLOAT * (1 - currentWavePercent / 360F));
            }
        });
    }

    @Override
    public void onTurn() {
    }

    private void changeUserIconParm(float v) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin += v;
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userCenterContainer.setLayoutParams(layoutParams);
    }

    private void setLisener() {

        mSlidingTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mSlidingTab.getTitleView(position).startAnimation(anim);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    }

    private void initTab() {
        List<String> strs = new ArrayList<>();
        //添加热门
        TabEntity item1 = new TabEntity();
        item1.setId("1");
        item1.setName("作品 0");
        mTitles.add(item1);
        //关注
        TabEntity item2 = new TabEntity();
        item2.setId("2");
        item2.setName("动态0");
        mTitles.add(item2);
        //关注
        TabEntity item3 = new TabEntity();
        item3.setId("3");
        item3.setName("喜欢0");
        mTitles.add(item3);
        //mTitles.addAll(HnHomeBrandBiz.mCateData);
        for (int i = 0; i < mTitles.size(); i++) {
            strs.add(mTitles.get(i).getName());
            switch (mTitles.get(i).getId()) {
                case "1"://我的
                    mFragments.add(DyFriendFragment.getInstance(true));
                    break;
                case "2":
                    mFragments.add(DyAttentionFragment.getInstance(true));
                    break;
                default:
                    mFragments.add(DyMessageFragment.getInstance(true));
                    break;
            }
        }

        HsScrollViewPagerAdapter adapter = new HsScrollViewPagerAdapter(getChildFragmentManager(), mFragments, strs);
        if (mFragments.size() < 1) return;
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(adapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @OnClick({R.id.background_img, R.id.mUserHeadCiv, R.id.mEditInfoTv, R.id.mAddFriendLl,R.id.mUserNameTv, R.id.mDouyinNumTv, R.id.mSignLl, R.id.mDataInTotalLl, R.id.mAddTakePhotoRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.background_img:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mUserHeadCiv:
                mUserHeadCiv.setAnimation(anim);
                break;
            case R.id.mEditInfoTv:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mAddFriendLl:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mUserNameTv:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mDouyinNumTv:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mSignLl:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mDataInTotalLl:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
            case R.id.mAddTakePhotoRl:
                ToastUtils.showShortToast(mActivity, "该功能待小主开发");
                break;
        }
    }

}
