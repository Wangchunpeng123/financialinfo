package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;

import java.io.IOException;

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
    @BindView(R.id.userphone)
    EditText userphone;
    @BindView(R.id.login_denglu)
    ImageButton loginDenglu;
    @BindView(R.id.userpassword)
    EditText userpassword;
    @BindView(R.id.login_zhuche)
    TextView loginZhuche;
    @BindView(R.id.login_xieyi)
    TextView loginXieyi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyLog.e(TAG, "LoginActivity启动");
        // time = new TimeCount(60000, 1000);
        initView();
        //getMsg();
    }

    private void initView() {
        //设置底部字体颜色
        SpannableStringBuilder style=new SpannableStringBuilder(loginXieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),9,18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        loginXieyi.setText(style);

            //设置按钮按下特效,有可能和点击事件发生冲突
        loginDenglu.setOnTouchListener((v, event) -> {

                    //重新设置按下时的背景图片
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        MyLog.i(TAG, "按下");
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.denglu_btn_yanzhengma2));

                    }else if(event.getAction() == MotionEvent.ACTION_UP){
                        //再修改为抬起时的正常图片
                        MyLog.i(TAG, "抬起" );
                        ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.mipmap.denglu_btn_yanzhengma));
                    }

                return false;
            });

        }

    //获取服务器数据
    private void getMsg() {
        MyLog.e("onResponse", "1111111111");
        Retrofit retrofit = new Retrofit.Builder().baseUrl(InterService.baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        MyLog.e("onResponse", "222222222");
        Call<ResponseBody> result = retrofit.create(InterService.class).getData();
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    MyLog.e("onResponse", 111 + response.body().string() + 111);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e("onResponse", "3333333333");
            }
        });
    }



    @OnClick({R.id.back_iv, R.id.userphone, R.id.login_denglu, R.id.userpassword, R.id.login_zhuche})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                MyLog.i(TAG, "返回");
                finish();
                break;
            case R.id.userphone:
                break;
            case R.id.login_denglu:
                break;
            case R.id.userpassword:
                break;
            case R.id.login_zhuche:
                startActivity(new Intent(this,UserZhuCheActivity.class));
                break;
        }
    }
}
