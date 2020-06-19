package com.yongyi.financialinfo.util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yongyi.financialinfo.R;


public class MyDialog1 extends android.app.Dialog implements View.OnClickListener{
    private static final String TAG = "MyDialog";
    private TextView content;
    private TextView title;
    private Button ok;
    private Button cancel;

    private Context mContext;
    private String okstr;
    private String canclestr;
    private OnCloseListener listener;
   /* private String okStr;
    private String cancleStr;*/


    public MyDialog1(Context context) {
        super(context);
        this.mContext = context;
    }
    public MyDialog1(Context context, String okstr, String canclestr) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.canclestr = canclestr;
        this.okstr = okstr;
    }

    public MyDialog1(Context context, int themeResId, String okstr, String canclestr) {
        super(context, themeResId);
        this.mContext = context;
        this.canclestr = canclestr;
        this.okstr = okstr;
    }

    public MyDialog1(Context context, int themeResId, String okstr, String canclestr, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.canclestr = canclestr;
        this.okstr = okstr;
        this.listener = listener;
    }

    protected MyDialog1(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
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

        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        title.setText(okstr);
        content.setText(canclestr);
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
        }
    }

    public interface OnCloseListener{
        void onClick(android.app.Dialog dialog, boolean confirm);
    }
}
