package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YinsiZhengceActivity extends AppCompatActivity {

    @BindView(R.id.agreement_jiantou)
    ImageView agreementJiantou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yinsi_zhengce);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.agreement_jiantou)
    public void onClick() {
        finish();
    }
}