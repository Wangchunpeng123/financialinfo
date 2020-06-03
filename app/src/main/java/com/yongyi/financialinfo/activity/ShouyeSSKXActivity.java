package com.yongyi.financialinfo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ExpandFoldTextAdapter;
import com.yongyi.financialinfo.bean.ExpandFoldTextBean;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ShouyeSSKXActivity extends Activity {
    @BindView(R.id.rv_layout_shishikuaixun)
    RecyclerView ShiShiKuaiXun;
    List<ExpandFoldTextBean> mList = new ArrayList<>();
    private BaseRecyclerAdapter<String> rvAdapter;
    private View view;
    private List<String> shishikuaixun1;
    private LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_shishikuaixun);
        ImageView jianTou = findViewById(R.id.finsh_shishikuaixun);
        jianTou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ButterKnife.bind(this);
        //初始化数据
        initMsg();
        ExpandFoldTextAdapter adapter =  new ExpandFoldTextAdapter(mList,this);
        ShiShiKuaiXun = (RecyclerView) findViewById(R.id.rv_layout_shishikuaixun);
        ShiShiKuaiXun.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ShiShiKuaiXun.setAdapter(adapter);
        //初始化界面
        initView();
    }

    private void initView() {
        layoutManager = new LinearLayoutManager(this);
        ShiShiKuaiXun.setLayoutManager(layoutManager);
        rvAdapter=new BaseRecyclerAdapter<String>(getBaseContext(),shishikuaixun1, R.layout.rv_shishikuaixun_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

            }
            };
        }

    private void initMsg() {
        String longContent = "地表最强量化基金正在布局比特币资产！近期一份监管文件显示，美国私募基金公司Renaissance Technologies（文艺复兴科技)的旗舰基金Medallion（大奖章基金）正考虑投资比特币期货，该基金已被允许进入芝加哥商品交易所（CME）的现金结算比特币期货市场毫无疑问，这个创办以...";

        for (int i = 0; i < 20; i++) {
            ExpandFoldTextBean bean = new ExpandFoldTextBean();
            if (i % 2 == 0) {
                bean.setContent(i + longContent);
                bean.setId(i);
            } else {
                bean.setContent(i + longContent);
                bean.setId(i);
            }
            mList.add(bean);
        }
    }

    }
