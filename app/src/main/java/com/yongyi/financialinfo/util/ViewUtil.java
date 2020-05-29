package com.yongyi.financialinfo.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewUtil {
    public static void setTextSize(TextView tv, int size){
        tv.setTextSize(size);
    }
    public static void setTextClick(TextView tv1, int size,int color, ImageView iv,boolean bl){
        tv1.setTextSize(size);
        tv1.setTextColor(color);
        if(bl==true)
            iv.setVisibility(View.VISIBLE);
        else
            iv.setVisibility(View.INVISIBLE);
    }
}
