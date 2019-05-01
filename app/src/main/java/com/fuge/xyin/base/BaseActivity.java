package com.fuge.xyin.base;

import android.app.ActivityManager;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fuge.xyin.R;
import com.fuge.xyin.receiver.ConnectionChangeReceiver;
import com.fuge.xyin.utils.StatusBarManager;
import com.fuge.xyin.utils.ToastUtils;
import com.umeng.analytics.MobclickAgent;


/**
 * 界面基类
 */
public abstract class BaseActivity extends FragmentActivity {

    protected Window window;
    private ConnectionChangeReceiver mReceiver;// 网络连接状态监听广播

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setInit();
//        StatusBarManager.compat(this, 0x151821);//设置状态栏颜色
//        StatusBarManager.compat(this);
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

    private void setInit() {
        //沉浸状态栏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Android 系统5.0一下
        } else {
            //Android 系统5.0一上
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(0xff151821);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
