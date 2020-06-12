package com.yongyi.financialinfo.app;


import android.os.Bundle;

import android.view.KeyEvent;
import android.view.MotionEvent;

import android.view.inputmethod.InputMethodManager;


import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.util.MyToast;
import com.zyq.easypermission.EasyPermissionHelper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;


public abstract class BaseActivity extends FragmentActivity {
    public String TAG = "";
    ActivityManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        TAG = getLocalClassName();
        activityManager = ActivityManager.getInstance();
        BaseActivity.this.activityManager.pushActivity(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                MyToast.shortToast(this, "再按一次退出程序");
                activityManager.finishAllActivity();
                break;
        }
        //返回false是不吃掉，后面的监听也能得到这个事件，而返回true是吃掉，后面的监听就得不到这个事件了。
        return false;

    }
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
