package com.fuge.xyin.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment{

    protected String TAG = getClass().getSimpleName();
    public BaseActivity mActivity;
    protected View mRootView;

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("############################", savedInstanceState + " xx");
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = View.inflate(mActivity, getContentViewId(), null);
        ButterKnife.bind(this, mRootView);
        initView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * this method must be implement by child activity, instead of setContentView in child activity
     */
    public abstract int getContentViewId();

    /**
     * 子类实现此抽象方法返回view进行展示
     *
     * @param inflater
     * @return
     */
    public abstract void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initData();

    /**
     * 事件处理
     */
    protected void initEvent() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
