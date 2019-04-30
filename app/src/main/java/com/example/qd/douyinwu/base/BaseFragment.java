package com.example.qd.douyinwu.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected View rootview;
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity=getActivity();
        if (rootview != null) {
            ViewGroup parent = (ViewGroup) rootview.getParent();
            if (parent != null) {
                parent.removeView(rootview);
            }
        } else {
            rootview = inflater.inflate(InitMainView(), container, false);
            initUI();
            loadData();
        }
        return rootview;
    }


    public abstract int InitMainView();

    public abstract void initUI();

    public abstract void loadData();



}
