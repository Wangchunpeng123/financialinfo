package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

public class ShouyeHangqingActivity extends AppCompatActivity {

    @BindView(R.id.caijing_head_iv)
    ImageView caijingHeadIv;
    @BindView(R.id.jingxuanhangqing_jiantou)
    ImageView jingxuanhangqingJiantou;
    @BindView(R.id.jingxuanhangqing_tv_title)
    TextView jingxuanhangqingTvTitle;
    @BindView(R.id.jingxuanhangqing_name)
    TextView jingxuanhangqingName;
    @BindView(R.id.jingxuanhangqing_zhangdiefu)
    TextView jingxuanhangqingZhangdiefu;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.chengse)
    View chengse;
    @BindView(R.id.bizhongxinxi)
    TextView bizhongxinxi;
    @BindView(R.id.jinrikaipanjia)
    TextView jinrikaipanjia;
    @BindView(R.id.kaipanjia_number)
    TextView kaipanjiaNumber;
    @BindView(R.id.zuorishoupanjia)
    TextView zuorishoupanjia;
    @BindView(R.id.shoupanjia_number)
    TextView shoupanjiaNumber;
    @BindView(R.id.jinrizuigaojia)
    TextView jinrizuigaojia;
    @BindView(R.id.zuigaojia_number)
    TextView zuigaojiaNumber;
    @BindView(R.id.jinrizuidijia)
    TextView jinrizuidijia;
    @BindView(R.id.zuidijia_number)
    TextView zuidijiaNumber;
    @BindView(R.id.jiazhi)
    TextView jiazhi;
    @BindView(R.id.jiazhi_number)
    TextView jiazhiNumber;
    @BindView(R.id.yongjin)
    TextView yongjin;
    @BindView(R.id.yongjin_number)
    TextView yongjinNumber;
    @BindView(R.id.liangbi)
    TextView liangbi;
    @BindView(R.id.liangbi_number)
    TextView liangbiNumber;
    @BindView(R.id.huanshou)
    TextView huanshou;
    @BindView(R.id.huanshou_number)
    TextView huanshouNumber;
    @BindView(R.id.hangqing_name)
    TextView hangqingName;
    @BindView(R.id.hangqing_time)
    TextView hangqingTime;

    private String ticker;
    private String exchangeName;
    private String degree;
    private Long dateTime;
    private String open;
    private String close;
    private String high;
    private String low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouye_hangqing);
        ButterKnife.bind(this);
        initMsg();
        initView();
    }

    private void initMsg() {
        Intent intent = getIntent();
        ticker = intent.getStringExtra("ticker");
        exchangeName = intent.getStringExtra("exchangeName");
        degree = intent.getStringExtra("degree");
        dateTime = intent.getLongExtra("dateTime", 0);
        open = intent.getStringExtra("open");
        close = intent.getStringExtra("close");
        high = intent.getStringExtra("high");
        low = intent.getStringExtra("low");

    }

    private void initView() {
        jingxuanhangqingName.setText(ticker);
        hangqingName.setText(exchangeName);
        jingxuanhangqingZhangdiefu.setText(degree+"");
        hangqingTime.setText(MyUtil.longToDate1(dateTime));

        if(open.split("\\.")[1].length()>2)
            kaipanjiaNumber.setText(open.substring(0,open.indexOf(".")+3));
        else
            kaipanjiaNumber.setText(open);

        if(close.split("\\.")[1].length()>2)
            shoupanjiaNumber.setText(close.substring(0,close.indexOf(".")+3));
        else
            shoupanjiaNumber.setText(close);

        if(high.split("\\.")[1].length()>2)
            zuigaojiaNumber.setText(high.substring(0,high.indexOf(".")+3));
        else
            zuigaojiaNumber.setText(high);

        if(low.split("\\.")[1].length()>2)
            zuidijiaNumber.setText(low.substring(0,low.indexOf(".")+3));
        else
            zuidijiaNumber.setText(low);
    }


    @OnClick(R.id.jingxuanhangqing_jiantou)
    public void onClick() {
        finish();
    }
}