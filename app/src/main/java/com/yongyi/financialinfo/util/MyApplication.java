package com.yongyi.financialinfo.util;


import android.app.Application;
import android.content.Context;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.init(this);
        mContext=getApplicationContext();
    }
}
