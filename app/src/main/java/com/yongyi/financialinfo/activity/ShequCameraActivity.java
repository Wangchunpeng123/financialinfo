package com.yongyi.financialinfo.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.bean.ShouyeNewBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class ShequCameraActivity extends AppCompatActivity {

    @BindView(R.id.caijing_head_iv)
    ImageView caijingHeadIv;
    @BindView(R.id.camera_fabiao)
    TextView cameraFabiao;
    @BindView(R.id.content_jiantou)
    ImageView contentJiantou;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.camera_et)
    EditText cameraEt;
    @BindView(R.id.camera_iv)
    ImageView cameraIv;
    private String Tag="ShequCameraActivity";
    private String userId;
    private File file;
    private String imageUrl=null;
    public static  boolean isImage=false;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shequ_camera);
        ButterKnife.bind(this);
        initMsg();

    }

    private void initMsg() {
        userId= SpSimpleUtils.getSp("userId",this,"LoginActivity");
    }

    @OnClick({R.id.caijing_head_iv, R.id.camera_fabiao, R.id.content_jiantou, R.id.content_tv, R.id.camera_et, R.id.camera_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.caijing_head_iv:
                break;
            case R.id.camera_fabiao:
               if(!cameraEt.getText().toString().equals("")) {
                   if(isImage)
                       upload();//图片+说说
                   else
                       postShuoshuo();//说说
               }else {
                   MyToast.shortToast(ShequCameraActivity.this,"请输入说说内容！");
               }
                break;
            case R.id.content_jiantou:
                finish();
                break;
            case R.id.content_tv:
                break;
            case R.id.camera_et:
                break;
            case R.id.camera_iv:
                if(!isImage){
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/");
                    startActivityForResult(intent,2);
                }else{
                    byte buff[] = new byte[125*250];
                    buff = Bitmap2Bytes(bitmap);
                    startActivity(new Intent(this,ShequCameraImageActivity.class).putExtra("image",buff));
                }

                break;
        }
    }

    private byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //打包图片成url
    private void upload() {
        RetrofitUtils.init("http://image.yysc.online/");
        MyLog.e(Tag,"进入upload");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("*/*"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        MyLog.e(Tag,"upload:getAbsolutePath():"+file.getAbsolutePath());
        Call<ResponseBody> result=RetrofitUtils.retrofit.create(InterService.class).upload(body);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag,"onResponse:"+response.toString());
                if(response.body()!=null){
                    try {
                       // MyLog.e(Tag,"onResponse:"+response.body().string());
                        imageUrl=response.body().string();
                        postShuoshuo();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //MyLog.e(Tag,"onResponse:"+response.body().toString());
                   // MyToast.shortToast(ShequCameraActivity.this,"上传图片成功");
                }else{
                    MyToast.shortToast(ShequCameraActivity.this,"上传图片失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag,"返回失败");
            }
        });
    }
    //上传说说
    private void postShuoshuo() {

        RetrofitUtils.init();
        Call<ResponseBody> result=RetrofitUtils.retrofit.create(InterService.class).postShuoshuo(userId,cameraEt.getText().toString().trim(),imageUrl);
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag,"onResponse:"+response.toString());
                if(response.body()!=null){
                    MyToast.shortToast(ShequCameraActivity.this,"发布成功");
                    finish();
                }else{
                    MyToast.shortToast(ShequCameraActivity.this,"发表失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //用户操作完成，结果码返回是-1，即RESULT_OK
        if(resultCode==RESULT_OK&&data!=null){
            //获取选中文件的定位符
            Uri uri = data.getData();

            //uri转换成file
            String[] arr = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, arr, null, null, null);
            int img_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String img_path = cursor.getString(img_index);
             file = new File(img_path);
            MyLog.e(Tag,"img_path:"+img_path);
            //压缩图片
            bitmap= MyUtil.getimage(img_path);
            MyUtil.saveBitmapFile(bitmap,file);
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                isImage=true;
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText(ShequCameraActivity.this,
                        "读取文件失败",
                        Toast.LENGTH_LONG).show();
                MyLog.e(Tag,"Exception:"+e.getMessage());
            }
            cameraIv.setImageBitmap(bitmap);

        }else{
            //操作错误或没有选择图片
            MyLog.e(Tag, "operation error");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isImage==false){
            cameraIv.setImageResource(R.drawable.tianjia);
        }
    }
}