package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.bean.LoginPhoneYanzhengmaBean;
import com.yongyi.financialinfo.bean.LoginYanzhengmaBean;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.Base64Utils;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.RetrofitUtils;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.yongyi.financialinfo.R.id.userzhuche_et_qr;
import static com.yongyi.financialinfo.R.id.userzhuche_et_tuxing_yanzhengma;
import static com.yongyi.financialinfo.R.id.userzhuche_zhuche;

public class UserZhuCheActivity extends AppCompatActivity {
    @BindView(R.id.back_iv)
    ImageView back;
    @BindView(R.id.userzhuche_iv_tuxing_yanzhengma)
    ImageView tuxingYanzhengma;
    @BindView(R.id.userzhuche_et_shoujihao)
    EditText shoujihaoEt;
    @BindView(R.id.userzhuche_tv_shouji_yanzhengma_click)
    TextView shoujiYanzhengmaClick;
    @BindView(userzhuche_zhuche)
    ImageView imageBtnZhuche;
    @BindView(R.id.userzhuche_cb)
    CheckBox userzhucheCb;
    @BindView(R.id.userzhuche_et_tuxing_yanzhengma)
    EditText tuxiangYanzhengmaEt;
    @BindView(R.id.userzhuche_et_shouji_yanzhengma)
    EditText shoujiYanzhengmaEt;
    @BindView(R.id.userzhuche_et_shezhimima)
    EditText mimaEt;
    @BindView(R.id.userzhuche_et_qr)
    EditText userzhucheEtQr;

    private TimeCount timeCount;
    private String TAG="UserZhuCheActivity";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userzhuche);
        ButterKnife.bind(this);
        timeCount = new TimeCount(60000,1000);
        getImageYanzhengma();
    }
    //获取图形验证码
    private void getImageYanzhengma() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterService.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<LoginYanzhengmaBean> result = retrofit.create(InterService.class).getImageYanzhengma();
        result.enqueue(new Callback<LoginYanzhengmaBean>() {
            @Override
            public void onResponse(Call<LoginYanzhengmaBean> call, Response<LoginYanzhengmaBean> response) {
               // MyLog.e(TAG,"##########" + response.body().getData() + "##########");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tuxingYanzhengma.setImageBitmap(Base64Utils.toBitmap(response.body().getData()));
                    }
                });
            }

            @Override
            public void onFailure(Call<LoginYanzhengmaBean> call, Throwable t) {
                MyLog.e(TAG,"获取验证码失败");
            }
        });
    }

    //获取手机验证码
    private void getPhoneYanzhengma() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(InterService.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyLog.e(TAG,"手机号：" + shoujihaoEt.getText().toString() + "图形验证码："+tuxiangYanzhengmaEt.getText().toString());
        Call<LoginPhoneYanzhengmaBean> result = retrofit.create(InterService.class).getPhoneYanzhengma(shoujihaoEt.getText().toString(),1,"futures",tuxiangYanzhengmaEt.getText().toString());
        result.enqueue(new Callback<LoginPhoneYanzhengmaBean>() {
            @Override
            public void onResponse(Call<LoginPhoneYanzhengmaBean> call, Response<LoginPhoneYanzhengmaBean> response) {
                    MyLog.e(TAG,"##########" + response.body().getSuccess() + "##########");
                    if(response.body().getSuccess()=="true"){
                        timeCount.start();
                    }else{
                        getImageYanzhengma();
                        tuxiangYanzhengmaEt.setText("");
                        new MyDialog(UserZhuCheActivity.this,"获取验证码失败",response.body().getMsg()).show();
                    }
            }
            //18183229574
            @Override
            public void onFailure(Call<LoginPhoneYanzhengmaBean> call, Throwable t) {
                new MyDialog(UserZhuCheActivity.this,"获取验证码失败","手机号或者验证码有误");
            }
        });
    }
    //上传注册信息
    private void postZhuce() {
        RetrofitUtils.init();
        Call<UserBean> result = RetrofitUtils.retrofit.create(InterService.class)
                .postZhuce(shoujihaoEt.getText().toString(),mimaEt.getText().toString(),userzhucheEtQr.getText().toString(),shoujiYanzhengmaEt.getText().toString(),1,"futures");
        result.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                    MyLog.e(TAG,"##########" + response.body().getSuccess() + "##########");
                    if(response.body().getSuccess()=="true"){
                        Intent intent = new Intent();
                        intent.putExtra("phone",shoujihaoEt.getText().toString());
                        intent.putExtra("password",mimaEt.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }else{
                        new MyDialog(UserZhuCheActivity.this,"注册失败","请重新设置").show();
                    }

            }
            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                new MyDialog(UserZhuCheActivity.this,"注册失败","请重新设置").show();
            }
        });
    }


    @OnClick({R.id.back_iv, R.id.userzhuche_iv_tuxing_yanzhengma, R.id.userzhuche_tv_shouji_yanzhengma_click, userzhuche_zhuche})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.back_iv:
                Intent intent1 = new Intent(UserZhuCheActivity.this,LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.userzhuche_iv_tuxing_yanzhengma:
                getImageYanzhengma();
                break;
            case R.id.userzhuche_tv_shouji_yanzhengma_click:
                if (shoujihaoEt.length() < 11){
                    Toast.makeText(this,"手机号输入错误",Toast.LENGTH_SHORT).show();
                }
                else if(tuxiangYanzhengmaEt.length()<4){
                    Toast.makeText(this,"验证码输入错误",Toast.LENGTH_SHORT).show();
                   }else{
                    getPhoneYanzhengma();
                }
                break;
            case R.id.userzhuche_zhuche:
                if (shoujihaoEt.length() == 11){
                    if (userzhucheEtQr.length() >=6){
                        if (shoujiYanzhengmaEt.length() ==6){
                            if (mimaEt.length() >= 6){
                                if (userzhucheCb.isChecked()){
                                    //上传注册信息
                                    postZhuce();

                                }else {
                                    Toast.makeText(this,"请勾选用户协议",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(this,"密码不得小于6位数",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(this,"手机验证码错误",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this,"密码不得小于6位数",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this,"检查手机号码是否正确",Toast.LENGTH_SHORT).show();
                }
        }
    }
    //设置60秒重新获取验证码倒计时
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            shoujiYanzhengmaClick.setText(millisUntilFinished / 1000 +"s后重新获取");
            shoujiYanzhengmaClick.setClickable(false);
        }

        @Override
        public void onFinish() {
            shoujiYanzhengmaClick.setText("重新获取");
            shoujiYanzhengmaClick.setClickable(true);
        }
    }
    
}
