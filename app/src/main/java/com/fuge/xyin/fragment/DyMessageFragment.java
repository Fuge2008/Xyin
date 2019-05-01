package com.fuge.xyin.fragment;


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
import android.widget.TextView;

import com.fuge.xyin.R;
import com.fuge.xyin.base.BaseFragment;
import com.fuge.xyin.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subs.
 */
public class DyMessageFragment extends BaseFragment {


    @BindView(R.id.mContactTv)
    TextView mContactTv;
    @BindView(R.id.mFanLl)
    LinearLayout mFanLl;
    @BindView(R.id.mZanLl)
    LinearLayout mZanLl;
    @BindView(R.id.mCommentLl)
    LinearLayout mCommentLl;
    @BindView(R.id.mTokePhotoLl)
    LinearLayout mTokePhotoLl;
    @BindView(R.id.mDuoShanIv)
    ImageView mDuoShanIv;
    @BindView(R.id.mDownloadBtn)
    Button mDownloadBtn;
    @BindView(R.id.mDuoShanRl)
    RelativeLayout mDuoShanRl;
    private ScaleAnimation anim;
    public static DyMessageFragment getInstance(boolean big) {
        DyMessageFragment frag = new DyMessageFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("big", big);
        frag.setArguments(bundle);
        return frag;
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_dy_message;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        anim = (ScaleAnimation) AnimationUtils.loadAnimation(mActivity, R.anim.bottom_icon);
    }

    @Override
    protected void initData() {

    }




    @OnClick({R.id.mFanLl, R.id.mZanLl, R.id.mCommentLl, R.id.mTokePhotoLl, R.id.mDownloadBtn, R.id.mDuoShanRl,R.id.mContactTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFanLl:
                mFanLl.startAnimation(anim);
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
            case R.id.mZanLl:
                mZanLl.startAnimation(anim);
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
            case R.id.mCommentLl:
                mCommentLl.startAnimation(anim);
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
            case R.id.mTokePhotoLl:
                mTokePhotoLl.startAnimation(anim);
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
            case R.id.mDownloadBtn:
                ToastUtils.showShortToast(mActivity,"暂时无法下载噢");
                break;
            case R.id.mDuoShanRl:
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
                case R.id.mContactTv:
                ToastUtils.showShortToast(mActivity,"该功能待小主开发");
                break;
        }
    }
}
