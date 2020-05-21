package com.yongyi.financialinfo.util;

import android.content.res.Resources;

public class GetTitleHight {
    public static int getStatusBarHeight() {
        Resources resources = MyApplication.mContext.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        MyLog.v("dbw", "Status height:" + height);
        return height;
    }
}
