package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouyeCJSJActivity extends AppCompatActivity {

    @BindView(R.id.caijing_rv)
    RecyclerView caijingshuju_rv;
    @BindView(R.id.caijing_jiantou)
    ImageView rvCaijingshujuJiantou;

    private BaseRecyclerAdapter<String> rvAdapter;
    private View view;
    private List<String> rvCaijingshujuList;
    private List<String> rvCaijingshujuTitle;
    private LinearLayoutManager CJSJLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_caijinshuju);
        ButterKnife.bind(this);
        //初始化数据
        initMsg();
        //初始化界面
        initView();
        rvCaijingshujuJiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMsg() {
        rvCaijingshujuList = new ArrayList<>();
        rvCaijingshujuList.add("1");
        rvCaijingshujuList.add("2");
        rvCaijingshujuList.add("3");
        rvCaijingshujuList.add("4");
        rvCaijingshujuList.add("5");
        rvCaijingshujuList.add("6");
        rvCaijingshujuList.add("7");
        rvCaijingshujuList.add("8");
        rvCaijingshujuList.add("9");
        rvCaijingshujuList.add("10");
        rvCaijingshujuList.add("11");
        rvCaijingshujuList.add("12");
        rvCaijingshujuList.add("13");
        rvCaijingshujuList.add("14");
        rvCaijingshujuList.add("15");
        rvCaijingshujuList.add("16");

    }
    private void initView() {

        String s1 = "关于暂停TRX3L/USDT ，TRX3S/USDT交易的公告";
        String s2 = "BES重磅上线GX.COM";
        String s3 = "LOEx国际站6月24日14:00上线OMY";
        String s4 = "关于下线项目VDL补偿方案的公告";
        String s5 = "关于币君交易所下架";
        String s6 = "CITEX恢复DMCH/HUSH充提公告";
        String s7 = "XT关于恢复 UFO 充提的公告";
        String s8 = "关于管交所BGOEX开启“持仓BGO空投GTG”活动的公告";
        String s9 = "关于TokenBetter BR钱包维护的公告";
        String s10 = "关于下架BRC交易对的公告";
        String s11 = "中币（ZB）关于恢复ETZ充值和提币业务的公告";
        String s12 = "10000EX即将上线RMC";
        String s13 = "“大家好，我是WenX的吉祥物。欢迎为我命名！” 创意活动";
        String s14 = "关于支持ONE主网切换并暂停ONE充提业务的公告";
        String s15 = "BitMax关于暂停XLM充提公告";
        String s16 = "CEO全球站关于GTS换链完成公告";
        //设置Rv布局管理者
        CJSJLayoutManager = new LinearLayoutManager(ShouyeCJSJActivity.this);
        CJSJLayoutManager.setOrientation(RecyclerView.VERTICAL);
        caijingshuju_rv.setLayoutManager(CJSJLayoutManager);
        rvAdapter=new BaseRecyclerAdapter<String>(getBaseContext(),rvCaijingshujuList, R.layout.rv_caijingshuju_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

                if (s.equals("1")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s1);
                   // holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a1);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"C网");
                }
                if (s.equals("2")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s2);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a2);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"GX.COM");
                }
                if (s.equals("3")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s3);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a3);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"雷盾");
                }
                if (s.equals("4")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s4);
                 //   holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a4);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"久币网");
                }
                if (s.equals("5")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s5);
                   // holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a5);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"  ");
                }
                if (s.equals("6")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s6);
                 //   holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a6);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"C网");
                }
                if (s.equals("7")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s7);
                 //   holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a7);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"XT");
                }
                if (s.equals("8")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s8);
                 //   holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a8);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"管交所（BGOEX）");
                }
                if (s.equals("9")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s9);
                 //   holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a9);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"TokenBetter");
                }
                if (s.equals("10")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s10);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a10);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"BitMart");
                }
                if (s.equals("11")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s11);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a11);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"中币");
                }
                if (s.equals("12")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s12);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a12);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"万亿交易所");
                }
                if (s.equals("13")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s13);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a13);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"WenX");
                }
                if (s.equals("14")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s14);
                  //  holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a14);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"BitMax");
                }
                if (s.equals("15")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s15);
                   // holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a15);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"火币全球站");
                }
                if (s.equals("16")){
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s16);
                   // holder.setBgResource(R.id.caijing_rv_iv,R.drawable.a16);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"币星");
                }

                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);

                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.guoqi);
                   // holder.setTxt(R.id.caijing_rv_contentUrl,"优币网");
                }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(ShouyeCJSJActivity.this,ShouyeCJSJconteActivity.class);
                    intent.putExtra("title","优币网");
                startActivity(intent);
            }
        };
        caijingshuju_rv.setAdapter(rvAdapter);

    }

}