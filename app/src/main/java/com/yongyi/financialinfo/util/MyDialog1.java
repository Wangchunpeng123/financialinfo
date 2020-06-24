package com.yongyi.financialinfo.util;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongyi.financialinfo.R;

import androidx.annotation.NonNull;


public class MyDialog1 extends android.app.Dialog implements View.OnClickListener{
    private static final String TAG = "MyDialog";
    private TextView content;
    private TextView title;
    private Button ok;
    private Button cancel;

    private Context mContext;
    private OnCloseListener listener;

    private String titleStr;
    private String contentStr;
    private String okstr;
    private String canclestr;
    private boolean isLl;

    private TextView yinsi;
    private TextView yonghu;
    private LinearLayout dialog_ll;
   /* private String okStr;
    private String cancleStr;*/


    public MyDialog1(Context context) {
        super(context);
        this.mContext = context;
    }
    public MyDialog1(Context context, String titleStr, String contentStr) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.contentStr = contentStr;
        this.titleStr = titleStr;
    }

    public MyDialog1(Context context, int themeResId, String titleStr, String contentStr) {
        super(context, themeResId);
        this.mContext = context;
        this.contentStr = contentStr;
        this.titleStr = titleStr;
    }

    public MyDialog1(Context context, int themeResId, String titleStr, String contentStr, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.contentStr = contentStr;
        this.titleStr = titleStr;
        this.listener = listener;
    }

    public MyDialog1(Context context, int themeResId, String titleStr, String contentStr,String okstr,String canclestr, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.contentStr = contentStr;
        this.titleStr = titleStr;
        this.okstr=okstr;
        this.canclestr=canclestr;
        this.listener = listener;
    }
    public MyDialog1(Context context, int themeResId, String titleStr, String contentStr,String okstr,String canclestr,boolean isLl, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.contentStr = contentStr;
        this.titleStr = titleStr;
        this.okstr=okstr;
        this.canclestr=canclestr;
        this.isLl=isLl;
        this.listener = listener;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_my);
        setCanceledOnTouchOutside(false);

        initView();
    }

    private void initView(){

        title = findViewById(R.id.dialog_title);
        content = findViewById(R.id.dialog_content);
        ok = findViewById(R.id.dialog_ok);
        cancel = findViewById(R.id.dialog_cancel);


        //隐私政策和用户协议
        yinsi = findViewById(R.id.dialog_yinsi);
        yonghu = findViewById(R.id.dialog_yonghu);
        dialog_ll= findViewById(R.id.dialog_ll);
        yinsi.setOnClickListener(this);
        yonghu.setOnClickListener(this);

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);

        title.setText(titleStr);
        content.setText(contentStr);

        ok.setText(okstr);
        cancel.setText(canclestr);

        if(isLl==true){
            dialog_ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_ok:
                if(listener != null){
                    listener.onClick(this, true);
                }
                this.dismiss();
                break;
            case R.id.dialog_cancel:
                if(listener != null){
                    listener.onClick(this,false );
                }
                this.dismiss();
                break;
            case R.id.dialog_yinsi:
                if(listener != null){
                    listener.onClickYinsi(this,"yinsi" );
                }
                break;
            case R.id.dialog_yonghu:
                if(listener != null){
                    listener.onClickYinsi(this,"yonghu" );
                }
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        return false;
    }

    public interface OnCloseListener{
        void onClick(android.app.Dialog dialog, boolean confirm);
        void onClickYinsi(android.app.Dialog dialog, String str);
    }
}
