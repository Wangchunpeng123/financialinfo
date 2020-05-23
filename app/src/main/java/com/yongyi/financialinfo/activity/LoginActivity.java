package com.yongyi.financialinfo.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyLog;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.login_caijin)
    TextView loginCaijin;
    @BindView(R.id.login_tv)
    TextView loginTv;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.login_line)
    View loginLine;
    @BindView(R.id.login_ok)
    ImageButton loginOk;
    @BindView(R.id.login_weixin)
    ImageView loginWeixin;
    @BindView(R.id.login_qq)
    ImageView loginQq;
    @BindView(R.id.login_xieyi)
    TextView loginXieyi;
    @BindView(R.id.login_phong)
    TextView loginPhong;
    @BindView(R.id.login_86)
    TextView login86;
    @BindView(R.id.login_miao)
    TextView loginMiao;
    @BindView(R.id.login_try)
    TextView loginTry;
    @BindView(R.id.container)
    ConstraintLayout container;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    private String phong="";
    private boolean logintry=false;
    private TimeCount time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyLog.e(TAG, "LoginActivity启动");
        time = new TimeCount(60000, 1000);
        initView();
        //getMsg();
    }

    //获取服务器数据
    private void getMsg() {
        MyLog.e("onResponse","1111111111");
            Retrofit retrofit = new Retrofit.Builder().baseUrl(InterService.baseURL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            MyLog.e("onResponse","222222222");
            Call<ResponseBody> result = retrofit.create(InterService.class).getData();
            result.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        MyLog.e("onResponse", 111+response.body().string()+111);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    MyLog.e("onResponse","3333333333");
                }
            });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        //设置按钮按下特效,有可能和点击事件发生冲突
        loginOk.setOnTouchListener((v, event) -> {
            if(login86.getVisibility()==View.VISIBLE){
        //重新设置按下时的背景图片
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                MyLog.i(TAG, "按下");
            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.denglu_btn_yanzhengma2));

            }else if(event.getAction() == MotionEvent.ACTION_UP){
        //再修改为抬起时的正常图片
                MyLog.i(TAG, "抬起" );
            ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.denglu_btn_yanzhengma));
            }
            }else{
                //重新设置按下时的背景图片
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    MyLog.i(TAG, "按下");
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.denglu_btn_denglu2));

                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    MyLog.i(TAG, "抬起" );
                    ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.denglu_btn_denglu));
                }
            }
            return false;
        });

        //设置底部字体颜色
        SpannableStringBuilder style=new SpannableStringBuilder(loginXieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),9,18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        loginXieyi.setText(style);
    }


    @OnClick({R.id.login_try,R.id.back_iv, R.id.login_ok, R.id.login_weixin, R.id.login_qq})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                MyLog.i(TAG, "返回");
                finish();
                break;
            case R.id.login_ok:
                phong=password.getText().toString();
                if(login86.getVisibility()==View.VISIBLE) {
                    //输入手机号码时
                    if (phong.equals("")){
                        new MyDialog(this, R.style.dialog, "电话号码错误","请输入正确的电话号码", new MyDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                             /*   if(!confirm){
                                }
                                dialog.dismiss();*/
                            }
                        }).show();
                    }


                    else {
                        loginPhong.setText("已发送验证码至" + phong);
                        loginOk.setImageResource(R.mipmap.denglu_btn_denglu);
                        loginPhong.setVisibility(View.VISIBLE);
                        password.setText("");
                        password.setHint("请填写您收到的验证码");
                        login86.setVisibility(View.GONE);
                        loginMiao.setVisibility(View.VISIBLE);
                        loginTry.setVisibility(View.VISIBLE);

                        time.start();
                    }
                }else {
                    //输入验证码时
                    if (phong.equals(""))
                        new MyDialog(this, R.style.dialog, "验证码错误","请输入正确的验证码", new MyDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                             /*   if(!confirm){
                                }
                                dialog.dismiss();*/
                            }
                        }).show();
                    else {
                        loginProgress.setVisibility(View.VISIBLE);
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                //验证成功进入主界面，失败请输入正确的验证码
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                finish();
                            }
                        };
                        new Timer().schedule(task,1000);
                    }
                }
                break;
            case R.id.login_weixin:
                MyLog.i(TAG, "打开微信");
                break;
            case R.id.login_qq:
                MyLog.i(TAG, "打开qq");
                break;
            case R.id.login_try:
                MyLog.i(TAG, "重发验证码");
                if(logintry==true){
                    loginMiao.setVisibility(View.VISIBLE);
                    time.start();
                    logintry=false;
                }
                break;
        }
    }
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            loginMiao.setText(millisUntilFinished / 1000 +"s后重新获取");
        }

        @Override
        public void onFinish() {
            logintry=true;
            loginMiao.setVisibility(View.GONE);
        }
    }
}
