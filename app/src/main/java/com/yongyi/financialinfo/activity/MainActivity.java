package com.yongyi.financialinfo.activity;

import androidx.annotation.NonNull;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;



import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.util.MyLog;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;


import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private Timer timer ;
    private TimerTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //要执行的操作
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
        }
        };
        initPermission();

    }

    private void initPermission() {
        //检测权限
        if(EasyPermission.build().hasPermission(this,Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE)){
            timer.schedule(task,2000);
        }else{
            EasyPermission.build()
                    .mRequestCode(1)
                    .mContext(this)
                    .mPerms(Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE)
                    .mResult(new EasyPermissionResult() {
                        @Override
                        public void onPermissionsAccess(int requestCode) {
                            super.onPermissionsAccess(requestCode);
                            //做你想做的
                            MyLog.e(TAG,"申请成功");
                            timer.schedule(task,2000);
                        }

                        @Override
                        public void onPermissionsDismiss(int requestCode, @NonNull List<String> permissions) {
                            super.onPermissionsDismiss(requestCode, permissions);
                            //你的权限被用户拒绝了你怎么办
                            MyLog.e(TAG,"申请失败");
                            finish();
                        }
                    }).requestPermission();

        }
    }


}




