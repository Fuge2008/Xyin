package com.tiktokdemo.lky.tiktokdemo;

import android.app.Application;
import android.content.Context;

import com.umeng.analytics.MobclickAgent;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by lky on 2018/12/11
 */
public class BaseApplication extends Application {

    public static Context mContext;

    @Override public void onCreate() {
        super.onCreate();
        mContext = this;
        ShareSDK.initSDK(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);//基本数据统计入口
    }
}
