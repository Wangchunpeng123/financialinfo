package com.yongyi.financialinfo.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.yongyi.financialinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyDialog extends Dialog {
    private static final String TAG = "MyDialog";
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.dialog_content)
    TextView dialogContent;
    @BindView(R.id.dialog_ok)
    Button dialogOk;
    private Button dialog_ok;

    private Context mContext;

    private String title;
    private String content;
    private OnCloseListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_dialog);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        dialogTitle.setText(title);
        dialogContent.setText(content);
    }

    @OnClick(R.id.dialog_ok)
    public void onClick() {
        if(listener != null){
            listener.onClick(this, false);
        }
        this.dismiss();
    }


    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
    public MyDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyDialog(Context context, String title, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.title = title;
        this.content = content;
    }

    public MyDialog(Context context, int themeResId, String title, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
        this.content = content;
    }

    public MyDialog(Context context, int themeResId, String title, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.title = title;
        this.listener = listener;
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }
}

