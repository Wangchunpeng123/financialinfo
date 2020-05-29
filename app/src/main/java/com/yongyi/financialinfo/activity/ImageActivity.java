package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.util.MyLog;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        String imageUrl =getIntent().getStringExtra("imageurl");
        if (!"".equals(imageUrl)) {
            Picasso.with(this).load(imageUrl).into(image);
        }
    }

    @OnClick(R.id.image)
    public void onClick() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
