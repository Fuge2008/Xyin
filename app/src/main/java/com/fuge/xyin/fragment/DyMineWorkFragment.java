package com.fuge.xyin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fuge.xyin.R;
import com.fuge.xyin.base.BaseFragment;
import com.fuge.xyin.view.WaveLineCenterView;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DyMineWorkFragment extends BaseFragment {
    @BindView(R.id.wl_center)
    WaveLineCenterView waveLineCenterView;
    @BindView(R.id.ll_user_container)
    LinearLayout userCenterContainer;
    private static final float MAX_FLOAT = 40.F;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_dy_mine_work;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        waveLineCenterView.setWaveHeightListener(new WaveLineCenterView.WaveHeightListener() {
            @Override
            public void currentWaveHeightScal(float currentWavePercent) {
                changeUserIconParm(MAX_FLOAT * (1 - currentWavePercent / 360F));
            }
        });
    }

    @Override
    protected void initData() {

    }
    private void changeUserIconParm(float v) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin += v;
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        userCenterContainer.setLayoutParams(layoutParams);
    }
}
