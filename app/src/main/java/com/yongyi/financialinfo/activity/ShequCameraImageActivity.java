package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShequCameraImageActivity extends AppCompatActivity {
    @BindView(R.id.image_fanhui)
    ImageView imageFanhui;
    @BindView(R.id.image_sanchu)
    TextView imageSanchu;
    @BindView(R.id.image_iv)
    ImageView imageIv;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shequ_camera_image);
        ButterKnife.bind(this);
        //接收传过来的照片
        Intent mIntent = getIntent();
        byte buff[] = mIntent.getByteArrayExtra("image");
        bitmap = BitmapFactory.decodeByteArray(buff, 0, buff.length);
        BitmapDrawable mBitmapDrawable = new BitmapDrawable(bitmap);
        imageIv.setBackgroundDrawable(mBitmapDrawable);
    }

    @OnClick({R.id.image_fanhui, R.id.image_sanchu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_fanhui:
                finish();
                break;
            case R.id.image_sanchu:
                ShequCameraActivity.isImage=false;
                finish();
                break;
        }
    }
}