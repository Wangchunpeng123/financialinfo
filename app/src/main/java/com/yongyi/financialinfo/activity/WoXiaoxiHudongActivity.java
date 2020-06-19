package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShequRemenGuanzhuBean;
import com.yongyi.financialinfo.bean.WoHudongBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
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

public class WoXiaoxiHudongActivity extends AppCompatActivity {

        @BindView(R.id.hangyefengbao_jiantou)
        ImageView hangyefengbaoJiantou;
        @BindView(R.id.xitong_rv)
        RecyclerView xitongRv;
    private List<WoHudongBean.DateMsg.Msg> list=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private BaseRecyclerAdapter<WoHudongBean.DateMsg.Msg> RvAdapter;
         private String Tag="WoXiaoxiHudongActivity";
         private String userId;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_wo_xiaoxi_hudong);
            ButterKnife.bind(this);
            userId= SpSimpleUtils.getSp("userId",this,"LoginActivity");
              RetrofitUtils.init();
            initView();
             getMsg();
        }

        private void initView() {
            //设置Rv布局管理者
            layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            xitongRv.setLayoutManager(layoutManager);

            //行情资讯rv
            RvAdapter = new BaseRecyclerAdapter<WoHudongBean.DateMsg.Msg>(this, list, R.layout.rv_wo_hudong) {
                @Override
                public void bindData(BaseRecyclerViewHolder holder, WoHudongBean.DateMsg.Msg s, int position) {
                    holder.setImgUrlCrop(WoXiaoxiHudongActivity.this,s.getUser().getHead(),R.id.rv_xitong_head);
                    holder.setTxt(R.id.rv_xitong_name,s.getUser().getNickName());
                    holder.setTxt(R.id.rv_xitong_tv,s.getTalk().getContent());
                  //  MyLog.e(Tag,"getContent:"+s.getContent());
                }
            };
            xitongRv.setAdapter(RvAdapter);
        }

    private void getMsg() {
        Call<WoHudongBean> result= RetrofitUtils.retrofit.create(InterService.class).getUserXiaoxi(Long.parseLong(userId));
        result.enqueue(new Callback<WoHudongBean>() {
            @Override
            public void onResponse(retrofit2.Call<WoHudongBean> call, Response<WoHudongBean> response) {
                MyLog.e(Tag,"onResponse:"+response.toString());
                if(response.body()!=null){
                    list.clear();
                    list.addAll(response.body().getData().getUsertalk());
                    MyLog.e(Tag,"size:"+list.size());
                    RvAdapter.notifyDataSetChanged();
                    if(list.size()==0){
                        MyToast.shortToast(WoXiaoxiHudongActivity.this,"暂无互动！");
                    }
                  /*  try {
                        MyLog.e(Tag,  response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }else{
                    MyToast.shortToast(WoXiaoxiHudongActivity.this,"获取列表失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<WoHudongBean> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }

        });
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