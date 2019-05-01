package com.fuge.xyin.fragment;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fuge.xyin.R;
import com.fuge.xyin.base.BaseFragment;
import com.fuge.xyin.utils.ToastUtils;
import com.fuge.xyin.view.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class DyAttentionFragment extends BaseFragment {


    @BindView(R.id.mHeadCiv)
    CircleImageView mHeadCiv;
    @BindView(R.id.mAddFriendToWechatIv)
    ImageView mAddFriendToWechatIv;
    @BindView(R.id.mAddFriendToWechatLL)
    LinearLayout mAddFriendToWechatLL;
    @BindView(R.id.mDuoShanRl)
    RelativeLayout mDuoShanRl;
    @BindView(R.id.mDuoShanIv)
    CircleImageView mDuoShanIv;
    @BindView(R.id.mAttentionBtn)
    Button mAttentionBtn;

    private ScaleAnimation anim;

    public static DyAttentionFragment getInstance(boolean big) {
        DyAttentionFragment frag = new DyAttentionFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("big", big);
        frag.setArguments(bundle);
        return frag;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_attention;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        anim = (ScaleAnimation) AnimationUtils.loadAnimation(mActivity, R.anim.bottom_icon);

    }



    @OnClick({R.id.mHeadCiv, R.id.mAddFriendToWechatIv, R.id.mAddFriendToWechatLL, R.id.mDuoShanIv, R.id.mAttentionBtn,R.id.mDuoShanRl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mHeadCiv:
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
            case R.id.mAddFriendToWechatIv:
                break;
            case R.id.mAddFriendToWechatLL:
                try {
                    PackageManager packageManager = mActivity.getPackageManager();
                    //Intent intent = new Intent();
                    Intent intent = packageManager.getLaunchIntentForPackage("com.ss.android.ugc.aweme");
                    mActivity.startActivity(intent);
                    com.fly.video.downloader.core.app.Process.background((Activity) mActivity);
                    ToastUtils.showShortToast(mActivity,"前往抖音");
                } catch (ActivityNotFoundException e) {
                    // TODO: handle exception
                    ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                }
                break;
            case R.id.mDuoShanIv:
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
            case R.id.mAttentionBtn:
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
                case R.id.mDuoShanRl:
                mDuoShanRl.startAnimation(anim);
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
        }
    }
}
