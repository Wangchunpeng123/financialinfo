package com.yongyi.financialinfo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.util.GradientDrawableUtils;


import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by Administrator on 2017/6/8 0008.
 */
public class BaseRecyclerViewHolder<T> extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }


    public void setGone(int viewId) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setVisibility(View.GONE);
    }
    public void setTxt(int viewId, String txt) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setText(txt);
    }
    public void setLLBackground(int viewId, int imageId) {
        LinearLayout layout = (LinearLayout) itemView.findViewById(viewId);
        layout.setBackgroundResource(imageId);
    }

    public void deleteTvBackground(int viewId) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setBackgroundResource(0);
    }
    public void setTxtInt(int viewId, Integer txt) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setText(txt);
    }

    public String getTxt(int viewId) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        return textView.getText().toString();
    }

    public void setTxtSize(int viewId, int textSize) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public void setCheckBoxTxt(int viewId, String txt) {
        CheckBox checkBox = (CheckBox) itemView.findViewById(viewId);
        checkBox.setText(txt);
    }

    public void setBitMap(int viewId, Bitmap bitmap) {
        ImageView imageView = (ImageView) itemView.findViewById(viewId);
        imageView.setImageBitmap(bitmap);
    }


    public void setTxtColor(int viewId, String color) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setTextColor(Color.parseColor(color));
    }

    public void setCheck(int viewId, Boolean bol) {
        CheckBox checkBox = (CheckBox) itemView.findViewById(viewId);
        checkBox.setChecked(bol);
    }

    public Boolean isChecked(int viewId) {
        CheckBox checkBox = (CheckBox) itemView.findViewById(viewId);
        return checkBox.isChecked();
    }


    public void setLinHeight(int viewId, int h) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemView.findViewById(viewId).getLayoutParams();
        params.height = h;
        itemView.findViewById(viewId).setLayoutParams(params);
    }

    public void setRelHeight(int viewId, int h) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemView.findViewById(viewId).getLayoutParams();
        params.height = h;
        itemView.findViewById(viewId).setLayoutParams(params);
    }

    public void setWidth(int viewId, int w) {
        LinearLayout linearLayout = itemView.findViewById(viewId);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);//定义一个LayoutParams
        layoutParams.setMargins(w, 0, 0, 0);//4个参数按顺序分别是左上右下
        linearLayout.setLayoutParams(layoutParams);
    }


    public void setRelWidth(int viewId, int w) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) itemView.findViewById(viewId).getLayoutParams();
        params.width = w;
        itemView.findViewById(viewId).setLayoutParams(params);
    }

    public void setLinWidth(int viewId, int w) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) itemView.findViewById(viewId).getLayoutParams();
        params.width = w;
        itemView.findViewById(viewId).setLayoutParams(params);
    }

    public void setImg(Context context, String imgUrl, int viewId) {
        ImageView imageView = (ImageView) itemView.findViewById(viewId);
        if (!"".equals(imgUrl)) {
            Glide.with(context).load(imgUrl).into(imageView);
        }
    }

    //设置图片为圆角
    public void setImgGray(Context context, String imgUrl, int viewId) {
        ImageView imageView = (ImageView) itemView.findViewById(viewId);
        if (!"".equals(imgUrl)) {
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(15);
            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
            // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
            RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
            Glide.with(context).load(imgUrl).apply(options).into(imageView);
        }
    }
/*
    public void setImgCrop(Context context, String imgUrl, int viewId){
        ImageView imageView= (ImageView) itemView.findViewById(viewId);
        if (!"".equals(imgUrl)){
            Picasso.with(context).load(imgUrl)
                    .resize(200,200)
                    .centerCrop()
                    .placeholder(R.drawable.empty)
                    .error(R.drawable.empty)
                    .into(imageView);
        }
    }*/

    //        设置控件是否可见
    public void setInVisibility(int viewId, int v) {
        itemView.findViewById(viewId).setVisibility(v);
    }

    //        设置recyclerView里面的子recycleView
    public void setRecyclerViewItem(int viewId, RecyclerView.LayoutManager manager, BaseRecyclerAdapter baseRecyclerAdapter) {
        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(viewId);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(baseRecyclerAdapter);
    }


    public void setClick(final int viewId, final T t, final int position, final BaseRecyclerAdapter recyclerAdapter) {
        View view = itemView.findViewById(viewId);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerAdapter.clickEvent(viewId, t, position);
            }
        });
    }

    public void setBgResource(int viewId, int sourceId) {
        View view = itemView.findViewById(viewId);
        view.setBackgroundResource(sourceId);
    }


   /* public void setRichText(int viewId,String txt){
        TextView textView= (TextView) itemView.findViewById(viewId);
        RichText.from(txt).into(textView);
    }*/


    public void setCheckBockListener(int viewId, CheckBox.OnCheckedChangeListener listener) {
        CheckBox checkBox = (CheckBox) itemView.findViewById(viewId);
        checkBox.setOnCheckedChangeListener(listener);
    }

    public void setAllDrawable(int viewId, int size, String color) {
        View view = itemView.findViewById(viewId);
        view.setBackground(GradientDrawableUtils.setGDrawable(size, color));
    }

    public void setImgRes(Context context, int viewId, int res) {
        ImageView imageView = (ImageView) itemView.findViewById(viewId);
        Picasso.with(context).load(res).into(imageView);//Picasso加载图片的一种方式
    }

    //设置tv背景颜色
    public void setTxtBackground(int viewId, String color) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setBackgroundColor(Color.parseColor(color));
    }
    //设置tv背景图片
    public void setTxtBackgroundIv(int viewId,int mipmap) {
        TextView textView = (TextView) itemView.findViewById(viewId);
        textView.setBackgroundResource(mipmap);
    }
}
