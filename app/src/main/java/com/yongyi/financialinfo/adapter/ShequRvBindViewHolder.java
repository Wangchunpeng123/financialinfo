package com.yongyi.financialinfo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.yongyi.financialinfo.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class ShequRvBindViewHolder {


    public void onBindViewHolder2(RecyclerView.ViewHolder holder, List<Integer> listBeans) {
        final int pos = getRealPosition(holder);
        MyLog.e("onBindViewHolder2",""+pos);
        ((ShequRvViewHolder) holder).title.setText(listBeans.get(pos).toString());
    }


    // 获取条目的真实位置
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (position < 3) {
            return position ;
        }
         else {
            return position-1 ;
        }

    }


}
