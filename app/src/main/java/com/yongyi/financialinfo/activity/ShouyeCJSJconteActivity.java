package com.yongyi.financialinfo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.util.MyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  ShouyeCJSJconteActivity extends AppCompatActivity {
    @BindView(R.id.content_tv_main)
    TextView mainContent;
    @BindView(R.id.content_tv)
    TextView titleContent;
    @BindView(R.id.content_jiantou)
    ImageView backImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_cjsj_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String s = intent.getStringExtra("title");
        titleContent.setText(s);
        mainContent.setText("尊敬的UBIEX用户：\n" +
                "UBIEX将上线TRX/AUSD，TRX/USDT，具体时间如下：\n" +
                "TRX充值：6月10日14:00(UTC+8)\n" +
                "TRX交易：6月10日15:00(UTC+8)\n" +
                "TRX提现：6月10日16:00(UTC+8)\n" +
                "\n" +
                "【项目简介】\n" +
                "波场TRON以推动互联网去中心化为己任，致力于为去中心化互联网搭建基础设施。旗下的TRON协议是全球最大的基于区块链的去中心化应用操作系统协 议之一，为协议上的去中心化应用运行提供高吞吐，高扩展，高可靠性的底层公链支持。波场TRON还通过创新的可插拔智能合约平台为以太坊智能合约提供更好的兼容性。\n" +
                "\n" +
                "【代币情况】\n" +
                "是否ERC20：否\n" +
                "代币总量：99,214,066,142枚\n" +
                "官网地址：\n" +
                "区块站：\n" +
                "\n" +
                "【风险提示】\n" +
                "数字资产是一种高风险的投资方式，请投资者谨慎购买，并注意投资风险。UBIEX不对项目或投资行为承担担保、赔偿等责任。\n" +
                "\n" +
                "【进入UBIEX社群】\n" +
                "中文电报：英文电报：\n" +
                "Twitter：\n" +
                "\n" +
                "UBIEX将持续不断地为您提供更优秀的产品和更优质的服务\n" +
                "\n" +
                "UBIEX团队\n" +
                "2020年6月09日");
        mainContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });
    }
}