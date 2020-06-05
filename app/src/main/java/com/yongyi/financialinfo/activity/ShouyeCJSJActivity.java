package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
        rv_caijingshuju_List.add("6");

    }
    private void initView() {
        //设置Rv布局管理者
        CJSJ_layoutManager = new LinearLayoutManager(ShouyeCJSJActivity.this);
        CJSJ_layoutManager.setOrientation(RecyclerView.VERTICAL);
        caijingshuju_rv.setLayoutManager(CJSJ_layoutManager);
        rvAdapter=new BaseRecyclerAdapter<String>(getBaseContext(),rv_caijingshuju_List, R.layout.rv_caijingshuju_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

            }
        };caijingshuju_rv.setAdapter(rvAdapter);

    }

}
