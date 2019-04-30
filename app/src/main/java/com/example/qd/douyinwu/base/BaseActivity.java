package com.example.qd.douyinwu.base;

import android.app.ActivityManager;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qd.douyinwu.R;
import com.example.qd.douyinwu.receiver.ConnectionChangeReceiver;
import com.example.qd.douyinwu.utils.StatusBarManager;
import com.example.qd.douyinwu.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;


/**
 * 界面基类
 */
public abstract class BaseActivity extends FragmentActivity {

    protected Window window;
    private ConnectionChangeReceiver mReceiver;// 网络连接状态监听广播

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StatusBarManager.compat(this, 0x002B2B2B);//设置状态栏颜色
        StatusBarManager.compat(this);
        super.onCreate(savedInstanceState);
        registerReceiver();
        initMainView();
        initUi();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);// 注销网络广播监听

    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new ConnectionChangeReceiver();
        this.registerReceiver(mReceiver, filter);
    }


    /**
     * 初始化视图
     *
     */
    public abstract void initMainView();

    /**
     * 初始化界面
     */
    public abstract void initUi();

    /**
     * 初始化数据
     */
    public abstract void loadData();

    public void onResume() {//友盟统计
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
