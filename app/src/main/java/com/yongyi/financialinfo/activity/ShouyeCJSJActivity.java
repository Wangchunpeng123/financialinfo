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

    @BindView(R.id.rv_caijingshuju)
    RecyclerView caijingshuju_rv;
   /*
    @BindView(R.id.rv_tv_time)
    TextView rv_time;
    @BindView(R.id.rv_tv_title)
    TextView rc_title;
    @BindView(R.id.rv_tv_neirong)
    TextView rc_neirong;
    @BindView(R.id.rv_iv_star1)
    ImageView rv_star1;
    @BindView(R.id.rv_iv_star2)
    ImageView rv_star2;
    @BindView(R.id.rv_iv_star3)
    ImageView rv_star3;
    @BindView(R.id.rv_iv_star4)
    ImageView rv_star4;
    @BindView(R.id.rv_iv_star5)
    ImageView rv_star5;
    @BindView(R.id.rv_iv_guoqi)
    ImageView rv_guoqi;
    */
    @BindView(R.id.iv_jiantou)
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


  /*        shouyeViewModel = ViewModelProviders.of(this).get(ShouyeViewModel.class);
        //让TextView观察ViewModel中数据的变化,并实时展示

        *shouyeViewModel.mUserLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {

            @Override
            public void onChanged(Integer integer) {
                // homeShouyeTv.setText(integer);
                MyLog.e("shouyeViewModel", integer.toString());
                // homeShouyeTv.setText(integer.toString());

            }
        }); */
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
