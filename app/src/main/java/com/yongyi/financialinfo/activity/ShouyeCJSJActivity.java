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
        for(int i=1;i<10;i++)
        rvCaijingshujuList.add("1");

    }
    private void initView() {
        String s1 = "UBIEX.CO上线波场（TRX）交易的公告";

        //设置Rv布局管理者
        CJSJLayoutManager = new LinearLayoutManager(ShouyeCJSJActivity.this);
        CJSJLayoutManager.setOrientation(RecyclerView.VERTICAL);
        caijingshuju_rv.setLayoutManager(CJSJLayoutManager);
        rvAdapter=new BaseRecyclerAdapter<String>(getBaseContext(),rvCaijingshujuList, R.layout.rv_caijingshuju_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,"家乐鸡粉多睡了副驾驶的垃圾分类时代峻峰");
                    holder.setBgResource(R.id.caijing_rv_iv,R.drawable.guoqi);
                    holder.setTxt(R.id.caijing_rv_contentUrl,"优币网");
                }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(ShouyeCJSJActivity.this,ShouyeCJSJconteActivity.class);
                    intent.putExtra("title","圣诞节了房间大");
                startActivity(intent);
            }
        };
        caijingshuju_rv.setAdapter(rvAdapter);

    }

}