package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.adapter.GlideImageLoader;
import com.yongyi.financialinfo.model.ShouyeViewModel;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShouyeHYFBActivity extends AppCompatActivity {

    @BindView(R.id.rv_hangqingzixun)
    RecyclerView hangqingzixun_Rv;
    @BindView(R.id.rv_shishiremen)
    RecyclerView shishiremen_Rv;
    @BindView(R.id.hangyefengbao_jiantou)
    ImageView hangyefengbao_jiantou;
    private ScheduledExecutorService scheduledExecutorService;
    private BaseRecyclerAdapter<String> rvAdapter;
    private View view;
    private ShouyeViewModel shouyeViewModel;
    private List<String> rv_hangyefengbao_list;
    private List<String> rv_hangyefengbao_remen_List;
    private LinearLayoutManager HYFB_layoutManager;
    private GridLayoutManager HYFB_layoutManger1;
    private List<String> list_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_hangyefengbao);

        ButterKnife.bind(this);
        //初始化数据
        initMsg();
        //初始化界面
        initView();

    }

    //RecyclerView
    private void initMsg() {
        rv_hangyefengbao_list = new ArrayList<>();
        rv_hangyefengbao_list.add("1");
        rv_hangyefengbao_list.add("2");
        rv_hangyefengbao_list.add("3");
        rv_hangyefengbao_list.add("4");
        rv_hangyefengbao_list.add("5");
        rv_hangyefengbao_list.add("6");
        rv_hangyefengbao_list.add("7");
        rv_hangyefengbao_list.add("8");
        rv_hangyefengbao_list.add("9");
        rv_hangyefengbao_list.add("10");
        rv_hangyefengbao_list.add("11");
        rv_hangyefengbao_list.add("12");
        rv_hangyefengbao_list.add("13");
        rv_hangyefengbao_list.add("14");
        rv_hangyefengbao_list.add("15");
        rv_hangyefengbao_remen_List = new ArrayList<>();
        rv_hangyefengbao_remen_List.add("新");
        rv_hangyefengbao_remen_List.add("新");
        rv_hangyefengbao_remen_List.add("新");
        rv_hangyefengbao_remen_List.add("热");
        rv_hangyefengbao_remen_List.add("热");
        rv_hangyefengbao_remen_List.add("热");

        list_path = new ArrayList<>();
        list_path.add("https://gw.alicdn.com/tfs/TB1HYd0GVP7gK0jSZFjXXc5aXXa-1050-1050.png");
        list_path.add("https://gw.alicdn.com/bao/upload/TB1KcWYGQL0gK0jSZFAXXcA9pXa.jpg_Q75.jpg");
        list_path.add("https://gw.alicdn.com/bao/upload/TB17wP7F2b2gK0jSZK9XXaEgFXa.jpg_Q75.jpg");


    }

    private void initView() {

        Banner banner = (Banner) findViewById(R.id.banner_hyfb);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(list_path);
        ////设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        /*
        传入所有列数的最小公倍数，1和42的最小公倍数为2，即意味着每一列将被分为2格
        */
        HYFB_layoutManger1 = new GridLayoutManager(getApplicationContext(), 2);
        //设置表格，根据position计算在该position处1列占几格数据
        shishiremen_Rv.setLayoutManager(HYFB_layoutManger1);
        rvAdapter = new BaseRecyclerAdapter<String>(getApplicationContext(), rv_hangyefengbao_remen_List, R.layout.rv_hangyefengbao_shishiremen) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setTxt(R.id.shishi_tv_xin, s);
                if (s.equals("热")) {
                    holder.setTxtColor(R.id.shishi_tv_xin, "#FF424B");
                }
                if (s.equals("热")) {
                    holder.setTxt(R.id.shishi_tv, "李佳琪抽烟");
                }
            }
        };
        shishiremen_Rv.setAdapter(rvAdapter);
        //设置Rv布局管理者
        HYFB_layoutManager = new LinearLayoutManager(this);
        HYFB_layoutManager.setOrientation(RecyclerView.VERTICAL);
        hangqingzixun_Rv.setLayoutManager(HYFB_layoutManager);
        rvAdapter = new BaseRecyclerAdapter<String>(getBaseContext(), rv_hangyefengbao_list, R.layout.rv_hangyefengbao_hangqingzixun) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {

            }
        };
        hangqingzixun_Rv.setAdapter(rvAdapter);
    }

    @OnClick({R.id.hangyefengbao_jiantou, R.id.fengbao_textView})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hangyefengbao_jiantou:
                finish();
                break;
            case R.id.fengbao_textView:
                break;
        }
    }
}



