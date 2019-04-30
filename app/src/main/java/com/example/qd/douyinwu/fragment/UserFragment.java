package com.example.qd.douyinwu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.qd.douyinwu.R;
import com.example.qd.douyinwu.view.PullScrollView;
import com.example.qd.douyinwu.view.WaveLineCenterView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment implements PullScrollView.OnTurnListener{
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    private static final float MAX_FLOAT = 40.F;
    private LinearLayout userCenterContainer;
    private WaveLineCenterView waveLineCenterView;

    public UserFragment() {
        // Required empty public constructor
        initView();
    }
    protected void initView() {
        mScrollView = (PullScrollView) getActivity().findViewById(R.id.scroll_view);
        mHeadImg = (ImageView) getActivity().findViewById(R.id.background_img);
        userCenterContainer = (LinearLayout) getActivity().findViewById(R.id.ll_user_container);
        waveLineCenterView = (WaveLineCenterView) getActivity().findViewById(R.id.wl_center);

        waveLineCenterView.setWaveHeightListener(new WaveLineCenterView.WaveHeightListener() {
            @Override
            public void currentWaveHeightScal(float currentWavePercent) {
                changeUserIconParm(MAX_FLOAT * (1 - currentWavePercent / 360F));
            }
        });

        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
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
}
