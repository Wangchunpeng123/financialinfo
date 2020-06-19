package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShequRemenGuanzhuBean;
import com.yongyi.financialinfo.bean.ShequRemenSsBean;
import com.yongyi.financialinfo.bean.UserTalkBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
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

public class WoYuwoxiangguanActivity extends AppCompatActivity {

    @BindView(R.id.hangyefengbao_jiantou)
    ImageView hangyefengbaoJiantou;
    @BindView(R.id.fengbao_textView)
    TextView fengbaoTextView;
    @BindView(R.id.ywxg_recyler)
    RecyclerView ywxgRecyler;

    private int pageIndex=1;
    private boolean hasMore=true;
    private List<ShequRemenSsBean.Mydata.dateMsg> list=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private BaseRecyclerAdapter<ShequRemenSsBean.Mydata.dateMsg> RvAdapter;
    private String Tag="WoYuwoxiangguanActivity";
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wo_yuwoxiangguan);
        ButterKnife.bind(this);
        RetrofitUtils.init();
        initView();
        getMsg(pageIndex);
    }

    private void initView() {
        userId= SpSimpleUtils.getSp("userId",this,"LoginActivity");
        //设置Rv布局管理者
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        ywxgRecyler.setLayoutManager(layoutManager);

        //rv
        RvAdapter=new BaseRecyclerAdapter<ShequRemenSsBean.Mydata.dateMsg>(this,list,R.layout.rv_activity_wo_yuwoxiangguan) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShequRemenSsBean.Mydata.dateMsg bean, int position) {
                //设置共同参数
                holder.setTxt(R.id.remen_name, bean.getUser().getNickName());
                holder.setTxt(R.id.remen_time, MyUtil.longToDate3(bean.getPublishTime()));
                holder.setTxt(R.id.remen_date, MyUtil.longToDate4(bean.getPublishTime()));
                holder.setImg(WoYuwoxiangguanActivity.this,bean.getUser().getHead(),R.id.remen_touxiang);
                holder.setTxt(R.id.remen_content, bean.getContent());
                holder.setTxt(R.id.remen_dianzan_tv, bean.getZanCount()+"");
                holder.setTxt(R.id.remen_pinlun_tv, bean.getCommentCount()+"");
                if(!bean.getPicture().equals("")){
                    holder.setInVisibility(R.id.remen_dandu_iv,View.VISIBLE);
                    holder.setImg(WoYuwoxiangguanActivity.this, bean.getPicture(), R.id.remen_dandu_iv);
                }
                else
                    holder.setInVisibility(R.id.remen_dandu_iv,View.GONE);

                if (bean.getHasZan())
                    holder.setImgRes(WoYuwoxiangguanActivity.this, R.id.remen_dianzan_iv, R.mipmap.shequ_icon_zan_h);
                else
                    holder.setImgRes(WoYuwoxiangguanActivity.this, R.id.remen_dianzan_iv, R.mipmap.shequ_icon_zan1);
                //设置点击事件
                holder.setClick(R.id.remen_dianzan_ll, bean, position, RvAdapter);
                holder.setClick(R.id.remen_pinlun_ll, bean, position, RvAdapter);
                holder.setClick(R.id.remen_dandu_iv, bean, position, RvAdapter);
            }

            @Override
            public void clickEvent(int viewId, ShequRemenSsBean.Mydata.dateMsg bean, int position) {
                super.clickEvent(viewId, bean, position);
                switch (viewId){
                    case R.id.remen_dianzan_ll:
                        list.get(position).setHasZan(!list.get(position).getHasZan());
                        if(!list.get(position).getHasZan()){
                            list.get(position).setZanCount(list.get(position).getZanCount()-1);
                        }
                        else{
                            list.get(position).setZanCount(list.get(position).getZanCount()+1);
                        }
                        dianZan(list.get(position).getId(),2);

                        RvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_pinlun_ll:

                        startActivity(new Intent( WoYuwoxiangguanActivity.this, ShequPinglunActivity.class)
                                .putExtra("Head",bean.getUser().getHead())
                                .putExtra("name",bean.getUser().getNickName())
                                .putExtra("content",bean.getContent())
                                .putExtra("picture",bean.getPicture())
                                .putExtra("time",bean.getPublishTime())
                                .putExtra("userId",Long.valueOf(userId))
                                .putExtra("talkId",bean.getId())
                                .putExtra("videoId",bean.getVideoId())
                                .putExtra("type","1")
                        );
                        MyLog.e(Tag, "userId : "+userId);
                        break;

                    case R.id.remen_dandu_iv:
                        //设置启动activity渐变动画
                        startActivity(new Intent(WoYuwoxiangguanActivity.this, ImageActivity.class).putExtra("imageurl",bean.getPicture()));
                        WoYuwoxiangguanActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                }
            }
        };
        ywxgRecyler.setAdapter(RvAdapter);

        ywxgRecyler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                           MyLog.e(Tag, "onScrollStateChanged: "+pageIndex);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // MyLog.e(TAG, "onScrolled: "+"dx="+dx+"dy="+dy );
                //当前rv最后一个item的index
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if(ywxgRecyler.canScrollVertically(-1)){
                    ywxgRecyler.setNestedScrollingEnabled(false);
                    //   MyLog.e(Tag, "onScrolled : true");
                }else {
                    ywxgRecyler.setNestedScrollingEnabled(true);
                    // MyLog.e(Tag, "onScrolled : false");//滑动到顶部
                }
            }
        });
    }

    private void dianZan(long talkId,int type){
        MyLog.e(Tag,"dianZan:getId:"+userId+"talkId:"+talkId);
        Call<ResponseBody> call=RetrofitUtils.retrofit.create(InterService.class).dianZan(Long.valueOf(userId),talkId,type);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                MyLog.e(Tag,"###########dianZan:"+response.toString());
                if(response.body()!=null) {
                    try {
                        MyLog.e(Tag,response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    MyLog.e(Tag,"点赞失败");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e(Tag,"获取数据失败");
            }
        });
    }
    private void getMsg(int pageIndex) {
        UserTalkBean userTalkBean=new UserTalkBean();
        userTalkBean.set_pageNumber(String.valueOf(pageIndex));
        userTalkBean.set_pageSize("2");
        userTalkBean.setUserId(userId);
        Call<ShequRemenSsBean> result= RetrofitUtils.retrofit.create(InterService.class).getYuwoxiangguan(Long.parseLong(userId),userTalkBean);
        result.enqueue(new Callback<ShequRemenSsBean>() {
            @Override
            public void onResponse(retrofit2.Call<ShequRemenSsBean> call, Response<ShequRemenSsBean> response) {
                MyLog.e(Tag,"onResponse:"+response.toString());
                if(response.body()!=null/*&&response.body().isSuccess()*/){
                    if(pageIndex==1){
                        list.clear();
                        list.addAll(response.body().getData().getList());
                    }else{
                        list.addAll(response.body().getData().getList());
                    }
                    hasMore=response.body().getData().isHasMore();
                    MyLog.e(Tag,"size:"+list.size());
                    RvAdapter.notifyDataSetChanged();
                /*    try {
                        MyLog.e(Tag,response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }else{
                    MyToast.shortToast(WoYuwoxiangguanActivity.this,"获取列表失败");
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ShequRemenSsBean> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }

        });
    }

    @OnClick(R.id.hangyefengbao_jiantou)
    public void onClick() {
        finish();
    }
}