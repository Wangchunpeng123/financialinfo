package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YongHuXieYiActivity extends AppCompatActivity {
    @BindView(R.id.agreement_jiantou)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yong_hu_xie_yi);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.agreement_jiantou)
    public void onClick() {
        finish();
    }
}
