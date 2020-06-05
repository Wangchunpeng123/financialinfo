package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.bean.LoginYanzhengmaBean;
import com.yongyi.financialinfo.http.InterService;

import com.yongyi.financialinfo.util.Base64Utils;
import com.yongyi.financialinfo.util.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.login_head_iv)
    ImageView  loginHeadIv;

    private Bitmap decodedByte;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyLog.e(TAG, "LoginActivity启动");

        initView();
        getMsg();
    }

    private void initView() {
        //设置底部字体颜色
        SpannableStringBuilder style=new SpannableStringBuilder(loginXieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),9,18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        loginXieyi.setText(style);


        }

    //获取服务器数据
    private void getMsg() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(InterService.baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        Call<LoginYanzhengmaBean> result = retrofit.create(InterService.class).getImageYanzhengma();
        result.enqueue(new Callback<LoginYanzhengmaBean>() {
            @Override
            public void onResponse(Call<LoginYanzhengmaBean> call, Response<LoginYanzhengmaBean> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loginHeadIv.setImageBitmap(Base64Utils.toBitmap(response.body().getData()));
                    }
                });

            }

            @Override
            public void onFailure(Call<LoginYanzhengmaBean> call, Throwable t) {
                
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
