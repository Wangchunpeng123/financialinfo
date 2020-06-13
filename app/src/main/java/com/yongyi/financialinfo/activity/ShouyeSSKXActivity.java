package com.yongyi.financialinfo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.adapter.ExpandFoldTextAdapter;
import com.yongyi.financialinfo.bean.ExpandFoldTextBean;
import com.yongyi.financialinfo.bean.ShouyeKuaixunBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShouyeSSKXActivity extends AppCompatActivity {

    private String Tag="ShouyeSSKXActivity";

    @BindView(R.id.rv_layout_shishikuaixun)
    RecyclerView ShiShiKuaiXun;
    @BindView(R.id.shishi_jiantou)
    ImageView shishiJiantou;
    @BindView(R.id.shishi_date_tv)
    TextView shishiDateTv;

    List<ShouyeKuaixunBean.KuaixunDate.livesList> mList = new ArrayList<>();
    ExpandFoldTextAdapter adapter = new ExpandFoldTextAdapter(mList, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_shishikuaixun);

        ButterKnife.bind(this);
        RetrofitUtils.init("http://api.coindog.com/live/");
        //初始化界面
        initView();
        //获取快讯数据
        getMsg();
    }

    private void getMsg() {
        Call<ShouyeKuaixunBean> call=RetrofitUtils.retrofit.create(InterService.class).getKuaixun(100);
        call.enqueue(new Callback<ShouyeKuaixunBean>() {
            @Override
            public void onResponse(Call<ShouyeKuaixunBean> call, Response<ShouyeKuaixunBean> response) {
                MyLog.e(Tag,response.toString());
                if(response.body()!=null) {
                    mList.addAll(response.body().getList().get(0).getLives());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shishiDateTv.setText(response.body().getList().get(0).getDate() + "    " + MyUtil.dateToWeek(response.body().getList().get(0).getDate()));
                        }
                    });
                    //将标题和内容分离出来
                    for (int i = 0; i < mList.size(); i++) {
                        String[] strs = mList.get(i).getContent().split("】");
                        String titleStr = strs[0].replace("【", "");
                        titleStr = titleStr.replace("】", "");
                        mList.get(i).setTiltle(titleStr);
                        mList.get(i).setContent(strs[1]);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                    MyToast.shortToast(ShouyeSSKXActivity.this,"获取快讯失败！");
                }
            }

            @Override
            public void onFailure(Call<ShouyeKuaixunBean> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }
        });
    }

    private void initView() {
        ShiShiKuaiXun.setLayoutManager(new LinearLayoutManager(this));
        ShiShiKuaiXun.setAdapter(adapter);
    }


    @OnClick({R.id.shishi_jiantou, R.id.shishi_date_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shishi_jiantou:
                finish();
                break;
            case R.id.shishi_date_tv:
                break;
        }
    }
}
