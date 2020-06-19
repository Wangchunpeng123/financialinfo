package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.bean.User;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoSetUserMsgActivity extends AppCompatActivity {

    @BindView(R.id.camera_fabiao)
    TextView cameraFabiao;
    @BindView(R.id.content_jiantou)
    ImageView contentJiantou;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.camera_et)
    EditText cameraEt;
    private UserBean userBean;
    private String type;
    private String Tag="WoSetUserMsgActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_set_user_msg);
        ButterKnife.bind(this);
        initMsg();
    }

    private void initMsg() {
        Gson gson = new Gson();
        String json = SpSimpleUtils.getSp("UserBean", this, "LoginActivity");
        userBean = gson.fromJson(json, UserBean.class);
        type=getIntent().getStringExtra("type");
        if(type.equals("1"))
        contentTv.setText("编辑昵称");
        else
            contentTv.setText("编辑个性签名");
    }
    //上传图片
    private void postUserMsg(String str) {
        RetrofitUtils.init();
        User user = new User();
        user.setId(Long.valueOf(userBean.getData().getId()));
        if(type.equals("1")){
            user.setNickName(str);
        }else{
            user.setSignature(str);
        }

        MyLog.e(Tag, "str:" + str);
        Call<ResponseBody> result = RetrofitUtils.retrofit.create(InterService.class).postHeadIv(user);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag, "onResponse:" + response.toString());
                if (response.body() != null) {
                    try {
                        MyLog.e(Tag, "onResponse:" + response.body().string());
                        MyToast.shortToast(WoSetUserMsgActivity.this, "保存成功");
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    MyToast.shortToast(WoSetUserMsgActivity.this, "保存失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag, "获取失败");
            }

        });
    }
    @OnClick({R.id.camera_fabiao, R.id.content_jiantou})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.camera_fabiao:
                String str=cameraEt.getText().toString().trim();
                postUserMsg(str);
                break;
            case R.id.content_jiantou:
                finish();
                break;
        }
    }
}