package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WoXiaoxiActivity extends AppCompatActivity {

    @BindView(R.id.hangyefengbao_jiantou)
    ImageView hangyefengbaoJiantou;
    @BindView(R.id.xiaoxi_xitong_cons)
    ConstraintLayout xiaoxiXitongCons1;
    @BindView(R.id.xiaoxi_hudong_cons)
    ConstraintLayout xiaoxiXitongCons2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_xiaoxi);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.hangyefengbao_jiantou, R.id.xiaoxi_xitong_cons, R.id.xiaoxi_hudong_cons})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hangyefengbao_jiantou:
                finish();
                break;
            case R.id.xiaoxi_xitong_cons:
                //打开系统消息
                startActivity(new Intent(this,WoXiaoxiXitongActivity.class));
                break;
            case R.id.xiaoxi_hudong_cons:
                //打开互动消息
                startActivity(new Intent(this,WoXiaoxiHudongActivity.class));
                break;
        }
    }
}