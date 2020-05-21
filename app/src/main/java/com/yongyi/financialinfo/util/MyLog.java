package com.yongyi.financialinfo.util;

import android.util.Log;

/**
 * 作者Administrator on 2018/5/22 0022 10:02
 */
public class MyLog {
    private static final String TAG = "MyLog";

    private static final boolean DEBUG=true;

    public MyLog() {
    }

    public static void v(String tag,String msg){
        if (DEBUG){
            Log.v(TAG, "[ " + tag + " ]" + msg);
        }
    }

    public static void d(String tag,String msg){
        if (DEBUG) {
             Log.d(TAG, "[ " + tag + " ]" + msg);
        }
    }

    public static void i(String tag,String msg){
        if (DEBUG) {
             Log.i(TAG, "[ " + tag + " ]" + msg);
        }
    }
    public static void w(String tag,String msg){
        if (DEBUG){
            Log.w(TAG, "[ " + tag + " ]" + msg);
        }
    }

    public static void e(String tag,String msg){
        if (DEBUG){
             Log.e(TAG, "[ " + tag + " ]" + msg);
        }
    }

}
