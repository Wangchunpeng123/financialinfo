package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShequRemenGuanzhuBean;
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

public class WoGuanzhuActivity extends AppCompatActivity {

    @BindView(R.id.caijing_head_iv)
    ImageView caijingHeadIv;
    @BindView(R.id.caijing_jiantou)
    ImageView caijingJiantou;
    @BindView(R.id.caijing_tv)
    TextView caijingTv;
    @BindView(R.id.recyler)
    RecyclerView recyler;
    private String Tag= "RecylerActivity";
    private String userId;
    private int pageIndex=1;
    private boolean hasMore=true;
    private int flag;
    private List<ShequRemenGuanzhuBean.Mydata.dateMsg> guanZhuList=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private BaseRecyclerAdapter<ShequRemenGuanzhuBean.Mydata.dateMsg> RvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyler);
        ButterKnife.bind(this);
        flag = getIntent().getIntExtra("flag",0);
        RetrofitUtils.init();
        userId= SpSimpleUtils.getSp("userId",this,"LoginActivity");
        getMsg(pageIndex);
        initView();
    }

    private void initView() {
        if(flag==1)
            caijingTv.setText("关注列表");
        else
            caijingTv.setText("粉丝列表");
        //设置Rv布局管理者
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyler.setLayoutManager(layoutManager);

        //行情资讯rv
        RvAdapter=new BaseRecyclerAdapter<ShequRemenGuanzhuBean.Mydata.dateMsg>(this,guanZhuList,R.layout.rv_recyler) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShequRemenGuanzhuBean.Mydata.dateMsg s, int position) {
                holder.setTxt(R.id.recyler_name,s.getNickName());
                holder.setImgUrlCrop(WoGuanzhuActivity.this,s.getHead(),R.id.recyler_head);
                if(s.getSignature().equals(""))
                    holder.setTxt(R.id.recyler_qianming,"暂无签名");
                else
                     holder.setTxt(R.id.recyler_qianming,s.getSignature());
                //判断是关注还是粉丝界面
               if(flag==1){
                if(s.isGuanzhu())
                    holder.setImgRes(WoGuanzhuActivity.this,R.id.recyler_isguanzhu,R.mipmap.shequ_icon_yiguanzhu);
                else
                    holder.setImgRes(WoGuanzhuActivity.this,R.id.recyler_isguanzhu,R.mipmap.shequ_icon_guanzhu);
                holder.setClick(R.id.recyler_isguanzhu,s,position,RvAdapter);
               }
               else{
                   holder.setInVisibility(R.id.recyler_isguanzhu, View.GONE);
               }
            }

            @Override
            public void clickEvent(int viewId, ShequRemenGuanzhuBean.Mydata.dateMsg dateMsg, int position) {
                super.clickEvent(viewId, dateMsg, position);
                setGuanzhu(dateMsg.getId(),!guanZhuList.get(position).isGuanzhu());
                guanZhuList.get(position).setGuanzhu(!guanZhuList.get(position).isGuanzhu());
                notifyDataSetChanged();
            }
        };
        recyler.setAdapter(RvAdapter);

        recyler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
               // MyLog.e(Tag, "onScrollStateChanged: "+"状态="+newState+"位置"+lastVisibleItem+"===="+layoutManager.getItemCount() );
                //SCROLL_STATE_IDLE当rv停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+2>= layoutManager.getItemCount()) {
                    //加载更多
                    pageIndex++;
                    if (hasMore==true){
                        getMsg(pageIndex);
                     //   MyLog.e(Tag, "onScrollStateChanged: "+pageIndex);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // MyLog.e(TAG, "onScrolled: "+"dx="+dx+"dy="+dy );
                //当前rv最后一个item的index
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if(recyler.canScrollVertically(-1)){
                    recyler.setNestedScrollingEnabled(false);
                 //   MyLog.e(Tag, "onScrolled : true");
                }else {
                    recyler.setNestedScrollingEnabled(true);
                   // MyLog.e(Tag, "onScrolled : false");//滑动到顶部
                }
            }
        });
    }

    private void setGuanzhu(long quxiaoUserId,boolean isGuanzhu) {
        Call<ResponseBody> call=RetrofitUtils.retrofit.create(InterService.class).setGuanzhu(Long.parseLong(userId),quxiaoUserId,isGuanzhu);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag,response.toString());
                try {
                    MyLog.e(Tag,response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag,"获取数据失败");
            }
        });
    }

    private void getMsg(int pageIndex) {
        Call<ShequRemenGuanzhuBean> result=RetrofitUtils.retrofit.create(InterService.class).getGuanzhu(Long.parseLong(userId),flag,pageIndex,10);
        result.enqueue(new Callback<ShequRemenGuanzhuBean>() {
            @Override
            public void onResponse(retrofit2.Call<ShequRemenGuanzhuBean> call, Response<ShequRemenGuanzhuBean> response) {
                MyLog.e(Tag,"onResponse:"+response.toString());
                if(response.body()!=null&&response.body().isSuccess()){
                    if(pageIndex==1){
                        guanZhuList.clear();
                        guanZhuList.addAll(response.body().getData().getList());
                    }else{
                        guanZhuList.addAll(response.body().getData().getList());
                    }
                    hasMore=response.body().getData().isHasMore();
                   for(int i=0;i<guanZhuList.size();i++)
                    guanZhuList.get(i).setGuanzhu(true);
                    MyLog.e(Tag,"size:"+guanZhuList.size());
                    RvAdapter.notifyDataSetChanged();

                }else{
                    MyToast.shortToast(WoGuanzhuActivity.this,"获取列表失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ShequRemenGuanzhuBean> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }

        });
    }

    @OnClick(R.id.caijing_jiantou)
    public void onClick() {
        finish();
    }
}