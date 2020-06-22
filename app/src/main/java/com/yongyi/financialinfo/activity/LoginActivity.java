package com.yongyi.financialinfo.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
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


public class LoginActivity extends AppCompatActivity {
    private String Tag="LoginActivity";
    @BindView(R.id.back_iv)
    ImageView backIv;
    @BindView(R.id.userphone)
    EditText userphone;
    @BindView(R.id.login_denglu)
    ImageView loginDenglu;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.login_zhuche)
    TextView loginZhuche;
    @BindView(R.id.login_xieyi)
    TextView loginXieyi;
    @BindView(R.id.yonghuxieyi_cb)
    CheckBox yonghuxieyi_cb;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.forget)
    TextView forget;

    private String phone;
    private String password;
    private UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MyLog.e(Tag, "LoginActivity启动");
        initView();
    }


    private void initView() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (yonghuxieyi_cb.isChecked() && userphone.length() == 11 && userPassword.length() >=6){
                    loginDenglu.setBackgroundResource(R.mipmap.botton_dengluhover);
                }else {
                    loginDenglu.setBackgroundResource(R.mipmap.botton_denglu);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                yonghuxieyi_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(yonghuxieyi_cb == buttonView){
                            if (isChecked && userphone.length() == 11 && userPassword.length() >= 6){
                                // loginDenglu.setEnabled(true);
                                loginDenglu.setBackgroundResource(R.mipmap.botton_dengluhover);
                            }else {
                                //  loginDenglu.setEnabled(false);
                                loginDenglu.setBackgroundResource(R.mipmap.botton_denglu);
                            }
                        }
                    }
                });

            }

        };
        userphone.addTextChangedListener(textWatcher);
        userPassword.addTextChangedListener(textWatcher);

        //设置底部字体颜色
        SpannableStringBuilder style=new SpannableStringBuilder(loginXieyi.getText().toString());
        style.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")),9,18, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        loginXieyi.setText(style);
    }

    //登录
    private void login() {
        //获取当前的账号密码
        phone=userphone.getText().toString().trim();
        password=userPassword.getText().toString().trim();
        MyLog.e(Tag,"登录时的phone和password:"+phone+"###"+password);
        RetrofitUtils.init();
        Call<UserBean> result = RetrofitUtils.retrofit.create(InterService.class).login(phone,password,1,"futures");
        result.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {

                if(response.body()!=null&&response.body().getSuccess()=="true"){
                    MyLog.e(Tag,"##########login:" + response.body().getSuccess()+ "##########");
                    MyLog.e(Tag,"##########login:" + response.body().getData().getId()+ "##########");
                    userBean=response.body();
                    saveMsg();
                    //延时一秒进入主界面
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            //验证成功进入主界面，失败请输入正确的验证码
                            SpSimpleUtils.saveSp("startType","2",LoginActivity.this,"LoginActivity");
                            String userStr=new Gson().toJson(response.body());
                            SpSimpleUtils.saveSp("UserBean",userStr ,LoginActivity.this,"LoginActivity");
                            SpSimpleUtils.saveSp("userId",response.body().getData().getId()+"" ,LoginActivity.this,"LoginActivity");
                            startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                            finish();
                        }
                    };
                    new Timer().schedule(task,1000);
                }else{
                    loginProgress.setVisibility(View.GONE);
                    new MyDialog(LoginActivity.this,"登录失败",response.body().getMsg()).show();
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                loginProgress.setVisibility(View.GONE);
                new MyDialog(LoginActivity.this,"登录失败","请检查账号密码！").show();
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.userphone, R.id.login_denglu, R.id.user_password, R.id.login_zhuche,R.id.forget})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                MyLog.i(Tag, "返回");
                finish();
                break;
            case R.id.userphone:
                break;
            case R.id.login_denglu:
                if (userphone.getText().toString().length() < 11)
                    new MyDialog(this, "登录失败", "请输入11位号码").show();
                  else if( userPassword.getText().toString().length() < 6)
                    new MyDialog(this, "登录失败", "密码数大于六位").show();
                    else  if(yonghuxieyi_cb.isChecked()==false)
                    new MyDialog(this, "登录失败", "请勾选\"我已阅读\"").show();
                       else {
                             loginDenglu.setBackgroundResource(R.mipmap.botton_dengluhover);
                             loginProgress.setVisibility(View.VISIBLE);
                             login();
                           }
                break;
            case R.id.user_password:
                break;
            case R.id.login_zhuche:
                startActivityForResult(new Intent(this,UserZhuCheActivity.class),1);
                break;
            case R.id.forget:
                startActivity(new Intent(this,LoginWangjiActivity.class));
                break;
        }
    }

    private  void saveMsg(){
        SpSimpleUtils.saveSp("phone",phone,this,"LoginActivity");
        SpSimpleUtils.saveSp("password",password,this,"LoginActivity");
        MyLog.e(Tag,"登录成功重新保存的phone和password:"+phone+"###"+password);
        String userStr=new Gson().toJson(userBean);
        SpSimpleUtils.saveSp("UserBean",userStr ,this,"LoginActivity");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                phone =  data.getStringExtra("phone");
                password =  data.getStringExtra("password");
                if(!phone.equals("")||!password.equals("")){
                    userphone.setText(phone);
                    userPassword.setText(password);
                    userPassword.setSelection(password.length());
                    SpSimpleUtils.saveSp("phone",phone,this,"LoginActivity");
                    SpSimpleUtils.saveSp("password",password,this,"LoginActivity");
                    MyLog.e(Tag,"注册界面传来的phone和password:"+phone+"###"+password);
                }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        phone=SpSimpleUtils.getSp("phone",this,"LoginActivity");
        password=SpSimpleUtils.getSp("password",this,"LoginActivity");
        userphone.setText(phone);
        userPassword.setText(password);
        MyLog.e(Tag,"获取本地phone和password:"+phone+"###"+password);
    }

}