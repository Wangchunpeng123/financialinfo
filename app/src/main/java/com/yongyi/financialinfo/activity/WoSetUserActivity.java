package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.bean.User;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.custom.CircleImageTransformer;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WoSetUserActivity extends AppCompatActivity {

    @BindView(R.id.set_touxiang_iv)
    ImageView setTouxiangIv;
    @BindView(R.id.caijing_jiantou)
    ImageView caijingJiantou;
    @BindView(R.id.set_touxiang_cons)
    ConstraintLayout setTouxiangCons;
    @BindView(R.id.set_nicheng_cons)
    ConstraintLayout setNichengCons;
    @BindView(R.id.set_qianming_cons)
    ConstraintLayout setQianmingCons;
    @BindView(R.id.set_nicheng_tv)
    TextView setNichengTv;
    @BindView(R.id.set_qianming_tv)
    TextView setQianmingTv;

    private String Tag = "WoSetUserActivity";
    private File file;
    private String imageUrl;
    private Bitmap bitmap;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_set_user);
        ButterKnife.bind(this);
        initMsg();

    }

    private void initView() {
        if (!userBean.getData().getHead().equals("http://video.cqscrb.top/logo.jpg") && !userBean.getData().getHead().equals("")) {
            Picasso.with(this)
                    .load(userBean.getData().getHead())
                    .transform(new CircleImageTransformer())
                    .into(setTouxiangIv);
        }
        setQianmingTv.setText(userBean.getData().getSignature());
        setNichengTv.setText(userBean.getData().getNickName());
    }

    private void initMsg() {
        Gson gson = new Gson();
        String json = SpSimpleUtils.getSp("UserBean", this, "LoginActivity");
        userBean = gson.fromJson(json, UserBean.class);
        initView();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK && data != null) {
            //获取选中文件的定位符
            Uri uri = data.getData();

            //uri转换成file
            String[] arr = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, arr, null, null, null);
            int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(img_index);
            file = new File(img_path);
            MyLog.e(Tag, "img_path:" + img_path);
            //压缩图片
            bitmap = MyUtil.getimage(img_path);
            MyUtil.saveBitmapFile(bitmap, file);
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                //将图片打包，并上传
                setHead();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(WoSetUserActivity.this,
                        "读取文件失败",
                        Toast.LENGTH_LONG).show();
                MyLog.e(Tag, "Exception:" + e.getMessage());
            }
            setTouxiangIv.setImageBitmap(bitmap);

        } else {
            //操作错误或没有选择图片
            MyLog.e(Tag, "operation error");
        }

    }

    private void setHead() {
        //打包图片成url
        RetrofitUtils.init("http://image.yysc.online/");
        MyLog.e(Tag, "进入upload");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        MyLog.e(Tag, "upload:getAbsolutePath():" + file.getAbsolutePath());
        Call<ResponseBody> result = RetrofitUtils.retrofit.create(InterService.class).upload(body);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag, "onResponse:" + response.toString());
                if (response.body() != null) {
                    try {
                     //   MyLog.e(Tag, "onResponse1:" + response.body().string());
                         imageUrl = response.body().string();
                        MyLog.e(Tag, "onResponse2:" + imageUrl);
                        postHead(imageUrl);//打包成功，开始上传
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    MyToast.shortToast(WoSetUserActivity.this, "上传图片失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag, "返回失败");
            }
        });

    }

    //上传图片
    private void postHead(String imageUrl) {
        RetrofitUtils.init();
        User user = new User();
        user.setId(Long.valueOf(userBean.getData().getId()));
        user.setHead(imageUrl);
        MyLog.e(Tag, "imageUrl:" + imageUrl);
        Call<ResponseBody> result = RetrofitUtils.retrofit.create(InterService.class).postHeadIv(user);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag, "onResponse:" + response.toString());
                if (response.body() != null) {
                    try {
                        MyLog.e(Tag, "onResponse:" + response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    MyToast.shortToast(WoSetUserActivity.this, "更新图片成功");
                } else {
                    MyToast.shortToast(WoSetUserActivity.this, "上传图片失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag, "获取失败");
            }

        });
    }

    @OnClick({R.id.caijing_jiantou, R.id.set_touxiang_cons, R.id.set_nicheng_cons, R.id.set_qianming_cons})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.caijing_jiantou:
                finish();
                break;
            case R.id.set_touxiang_cons:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/");
                startActivityForResult(intent, 2);
                break;
            case R.id.set_nicheng_cons:
                startActivity(new Intent(this, WoSetUserMsgActivity.class).putExtra("type","1"));
                break;
            case R.id.set_qianming_cons:
                startActivity(new Intent(this, WoSetUserMsgActivity.class).putExtra("type","2"));
                break;
        }
    }

    private void getUserMsg() {
        RetrofitUtils.init();
        Call<UserBean> result = RetrofitUtils.retrofit.create(InterService.class).getUserMsg(userBean.getData().getId());
        result.enqueue(new Callback<UserBean>() {
            @Override
            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                if (response.body() != null && response.body().getSuccess() == "true") {
                    String userStr = new Gson().toJson(response.body());
                    SpSimpleUtils.saveSp("UserBean", userStr, WoSetUserActivity.this, "LoginActivity");
                    initMsg();
                }
            }

            @Override
            public void onFailure(Call<UserBean> call, Throwable t) {
                MyLog.e("WoFragment:","获取用户数据失败");
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        getUserMsg();
    }
}