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
    ImageView rv_caijingshuju_jiantou;

    private BaseRecyclerAdapter<String> rvAdapter;
    private View view;
    private List<String> rv_caijingshuju_List;
    private List<String> rv_caijingshuju_title;
   //private ShouyeViewModel shouyeViewModel;
    private LinearLayoutManager CJSJ_layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_caijinshuju);
        ButterKnife.bind(this);
        //初始化数据
        initMsg();
        //初始化界面
        initView();
        rv_caijingshuju_jiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMsg() {
        rv_caijingshuju_List = new ArrayList<>();
        rv_caijingshuju_List.add("1");
        rv_caijingshuju_List.add("2");
        rv_caijingshuju_List.add("3");
        rv_caijingshuju_List.add("4");
        rv_caijingshuju_List.add("5");
        rv_caijingshuju_List.add("6");
        rv_caijingshuju_List.add("7");
        rv_caijingshuju_List.add("8");
        rv_caijingshuju_List.add("9");
    }
    private void initView() {
        String s1 = "UBIEX.CO上线波场（TRX）交易的公告";
        String s2 = "BBKX平台关于部分币种钱包升级的公告";
        String s3 = "BKEX Global 新版 APP（V5.8.0）“大家一起来找茬“活动";
        String s4 = "关于今日20:00开启FF矿机抢购的公告（第二十三期）";
        String s5 = "Asobi Coin (ABX) 交易大赛 — 豪送100,000 ABX！";
        String s6 = "公告 | 关于部分币种钱包升级";
        String s7 = "火币合约6月12日系统升级公告";
        String s8 = "BW.io: ORMEUS生态代币两轮交易大赛以及存币奖励限时开启";
        String s9 = "关于关闭PHTM交易的公告（0609）";

        //设置Rv布局管理者
        CJSJ_layoutManager = new LinearLayoutManager(ShouyeCJSJActivity.this);
        CJSJ_layoutManager.setOrientation(RecyclerView.VERTICAL);
        caijingshuju_rv.setLayoutManager(CJSJ_layoutManager);
        rvAdapter=new BaseRecyclerAdapter<String>(getBaseContext(),rv_caijingshuju_List, R.layout.rv_caijingshuju_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                if (s.equals("1")){
                    holder.setTxt(R.id.caijing_rv_title,s1);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.one);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"优币网");
                }
                if (s.equals("2")){
                    holder.setTxt(R.id.caijing_rv_title,s2);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.two);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"BBKX");
                }
                if (s.equals("3")){
                    holder.setTxt(R.id.caijing_rv_title,s3);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.three);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"币客");
                }
                if (s.equals("4")){
                    holder.setTxt(R.id.caijing_rv_title,s4);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.four);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"法拉第");
                }
                if (s.equals("5")){
                    holder.setTxt(R.id.caijing_rv_title,s5);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.five);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"BitMart");
                }
                if (s.equals("6")){
                    holder.setTxt(R.id.caijing_rv_title,s6);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.six);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"BiBull");
                }
                if (s.equals("7")){
                    holder.setTxt(R.id.caijing_rv_title,s7);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.seven);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"火币全球站");
                }
                if (s.equals("8")){
                    holder.setTxt(R.id.caijing_rv_title,s8);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.eight);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"币网");
                }
                if (s.equals("9")){
                    holder.setTxt(R.id.caijing_rv_title,s9);
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.nine);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"BiKi");

                }

            }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
                TextView caijingTitle = findViewById(R.id.caijing_rv_title);
                Intent intent = new Intent(ShouyeCJSJActivity.this,ContentActivity.class);
                if (s.equals("1")){
                    intent.putExtra("title",s1);
                }
                if (s.equals("2")){
                    intent.putExtra("title",s2);
                }
                if (s.equals("3")){
                    intent.putExtra("title",s3);
                }
                if (s.equals("4")){
                    intent.putExtra("title",s4);
                }
                if (s.equals("5")){
                    intent.putExtra("title",s5);
                }
                if (s.equals("6")){
                    intent.putExtra("title",s6);
                }
                if (s.equals("7")){
                    intent.putExtra("title",s7);
                }
                if (s.equals("8")){
                    intent.putExtra("title",s8);
                }
                if (s.equals("9")){
                    intent.putExtra("title",s9);
                }

                startActivity(intent);
            }
        };caijingshuju_rv.setAdapter(rvAdapter);

    }

}
