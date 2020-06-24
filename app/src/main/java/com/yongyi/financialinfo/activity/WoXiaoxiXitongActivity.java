package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShequRemenGuanzhuBean;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WoXiaoxiXitongActivity extends AppCompatActivity {

    @BindView(R.id.hangyefengbao_jiantou)
    ImageView hangyefengbaoJiantou;
    @BindView(R.id.xitong_rv)
    RecyclerView xitongRv;
    private List<String> guanZhuList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private BaseRecyclerAdapter<String> RvAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_xiaoxi_xitong);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //设置Rv布局管理者
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        xitongRv.setLayoutManager(layoutManager);
        guanZhuList.add("111");
        //行情资讯rv
        RvAdapter = new BaseRecyclerAdapter<String>(this, guanZhuList, R.layout.rv_xitong_xiaoxi) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                    holder.setImg(WoXiaoxiXitongActivity.this,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2457285391,3956866123&fm=26&gp=0.jpg",R.id.rv_xitong_head);
                    holder.setTxt(R.id.rv_xitong_tv,"欢迎登录本APP，APP致力于创建一个数字货币资讯" +
                            "社区，用户可在本APP中查看资讯，发布和评论用户的见解，查看行情和指数" +
                            "。请在APP中准守国家法律，违反者我们将封号处理。");
            }
        };
        xitongRv.setAdapter(RvAdapter);
    }
    @OnClick({R.id.hangyefengbao_jiantou, R.id.xitong_rv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hangyefengbao_jiantou:
                finish();
                break;
            case R.id.xitong_rv:
                break;
        }
    }
}