package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.util.MyUtil;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShouyeCJSJconteActivity extends AppCompatActivity {
    @BindView(R.id.content_jiantou)
    ImageView backImg;
    @BindView(R.id.caijing_head_iv)
    ImageView caijingHeadIv;
    @BindView(R.id.jy_title)
    TextView jyTitle;
    @BindView(R.id.jy_sours)
    TextView jySours;
    @BindView(R.id.jy_zuozhe)
    TextView jyZuozhe;
    @BindView(R.id.jy_time)
    TextView jyTime;
    @BindView(R.id.jy_content)
    TextView jyContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_cjsj_content);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        jyTitle.setText(intent.getStringExtra("title"));
        jySours.setText(intent.getStringExtra("source"));
        jyZuozhe.setText(intent.getStringExtra("author"));
        jyTime.setText(MyUtil.longToDate2(intent.getLongExtra("getPublishTime",0)));
        jyContent.setText(intent.getStringExtra("content"));
    }

    @OnClick({R.id.caijing_head_iv, R.id.content_jiantou})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.caijing_head_iv:
                break;
            case R.id.content_jiantou:
                finish();
                break;
        }
    }
}