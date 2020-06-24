package com.yongyi.financialinfo.activity;

import androidx.annotation.NonNull;
import cn.jpush.android.api.JPushInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.google.gson.Gson;
import com.yongyi.financialinfo.BuildConfig;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyApplication;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionResult;


import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseActivity {
    private Timer timer ;
    private TimerTask task;
    private String phone;
    private String password;
    private String jiemiMsg;
    private String startType;//startType=1:从来没有登录成功过，进入游客模式；startType=2:登录过，直接自动登录；startType=3:登陆过从主界面退出来；

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getMsg();
    }

    private void getMsg() {
        RetrofitUtils.init("https://api.qhniua.com/");
        Call<ResponseBody> call=RetrofitUtils.retrofit.create(InterService.class).getSuo(BuildConfig.platform, BuildConfig.name);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.body()!=null){
                    try {
                        //   MyLog.e("MAIN",response.body().string());
                        jiemiMsg= MyUtil.Decrypt(response.body().string(),"njk1!@bas31*@agv");
                        JSONObject jsonObject=new JSONObject(jiemiMsg);
                        int status=jsonObject.getInt("status");
                        if(status==1){
                          //  MyLog.e("MAIN","STATIS=1");
                            String URL=jsonObject.getString("url");
                            startActivity(new Intent(MainActivity.this,DetailActivity.class).putExtra("URL",URL));
                            finish();
                        }else{
                            //MyLog.e("MAIN","STATIS=2");
                            initMsg();
                            initPermission();
                        }
                    } catch (IOException e) {
                        initMsg();
                        initPermission();
                        e.printStackTrace();
                    } catch (Exception e) {
                        initMsg();
                        initPermission();
                        e.printStackTrace();
                    }
                }else {
                    initMsg();
                    initPermission();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                initMsg();
                initPermission();
            }
        });
    }

    private void initMsg() {
        phone= SpSimpleUtils.getSp("phone",this,"LoginActivity");
        password=SpSimpleUtils.getSp("password",this,"LoginActivity");
        // phone="";
        // password="";
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //要执行的操作
                if(phone.equals("")||password.equals(""))
                {
                    SpSimpleUtils.saveSp("startType","1",MainActivity.this,"LoginActivity");
                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    finish();
                } else{
                    login();
                }
            }
        };
    }

    private void initPermission() {
        //检测权限
        if(EasyPermission.build().hasPermission(this,Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)){
            timer.schedule(task,2000);
        }else{
            EasyPermission.build()
                    .mRequestCode(1)
                    .mContext(this)
                    .mPerms(Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
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


    //获取服务器数据
    private void login() {
        RetrofitUtils.init();
        Call<UserBean> result = RetrofitUtils.retrofit.create(InterService.class).login(phone,password,1,"futures");
        result.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                if(response.body()!=null&&response.body().getSuccess()=="true"){
                    MyLog.e(TAG,"##########login:" + response.body().getData().getId()+ "##########");
                    //延时一秒进入主界面
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            //验证成功进入主界面，失败请输入正确的验证码
                            SpSimpleUtils.saveSp("startType","2",MainActivity.this,"LoginActivity");
                            String userStr=new Gson().toJson(response.body());
                            SpSimpleUtils.saveSp("UserBean",userStr ,MainActivity.this,"LoginActivity");
                            SpSimpleUtils.saveSp("userId",response.body().getData().getId()+"" ,MainActivity.this,"LoginActivity");
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            finish();
                        }
                    };
                    new Timer().schedule(task,500);
                }else{
                    new MyDialog(MainActivity.this,"登录失败",response.body().getMsg()){
                        @Override
                        public void onClick() {
                            super.onClick();
                            SpSimpleUtils.saveSp("startType","1",MainActivity.this,"LoginActivity");
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                            finish();
                        }
                    }.show();

                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                new MyDialog(MainActivity.this,"登录失败","请检查账号密码！").show();
            }
        });
    }
}




