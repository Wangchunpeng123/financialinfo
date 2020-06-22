package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShouyeJiaoyisuoBean;
import com.yongyi.financialinfo.bean.ShouyeNewBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShouyeCJSJActivity extends AppCompatActivity {

    @BindView(R.id.caijing_rv)
    RecyclerView caijingshuju_rv;
    @BindView(R.id.caijing_jiantou)
    ImageView rvCaijingshujuJiantou;

    private BaseRecyclerAdapter<ShouyeJiaoyisuoBean.Mydata.dateMsg> rvAdapter;
    private List<ShouyeJiaoyisuoBean.Mydata.dateMsg> rvCaijingshujuList= new ArrayList<>();
    private LinearLayoutManager CJSJLayoutManager;
    private String Tag="ShouyeCJSJActivity";
    private boolean isPage=true;
    private int pageIndex=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_caijinshuju);
        ButterKnife.bind(this);
        RetrofitUtils.init();
        //初始化数据
        initMsg();
        //初始化界面
        initView();
        getMsg(pageIndex);
        rvCaijingshujuJiantou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initMsg() {



    }

    private void getMsg(int pageNo) {
        Call<ShouyeJiaoyisuoBean> result= RetrofitUtils.retrofit.create(InterService.class).getJiaoyisuo("exchange",pageNo,10);
        result.enqueue(new Callback<ShouyeJiaoyisuoBean>() {
            @Override
            public void onResponse(Call<ShouyeJiaoyisuoBean> call, Response<ShouyeJiaoyisuoBean> response) {
                MyLog.e(Tag,"onResponse:"+response.toString());
            /*    try {
                    MyLog.e(Tag,"onResponse:"+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                if(response.body()!=null&&response.body().getSuccess()=="true"){
                    if (pageNo==1){
                        rvCaijingshujuList.clear();
                        rvCaijingshujuList.addAll(response.body().getData().getList());
                        rvAdapter.notifyDataSetChanged();

                    }else {
                        rvCaijingshujuList.addAll(response.body().getData().getList());
                        rvAdapter.notifyDataSetChanged();
                    }
                    isPage=response.body().getData().isHasMore();
                    if(!isPage)
                        MyToast.shortToast(ShouyeCJSJActivity.this,"暂无更多公告！");

                }else{
                    MyToast.shortToast(ShouyeCJSJActivity.this,"暂无公告");
                }
            }
            @Override
            public void onFailure(Call<ShouyeJiaoyisuoBean> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }

        });

    }
    private void initView() {

        //设置Rv布局管理者
        CJSJLayoutManager = new LinearLayoutManager(ShouyeCJSJActivity.this);
        CJSJLayoutManager.setOrientation(RecyclerView.VERTICAL);
        caijingshuju_rv.setLayoutManager(CJSJLayoutManager);
        rvAdapter=new BaseRecyclerAdapter<ShouyeJiaoyisuoBean.Mydata.dateMsg>(getBaseContext(),rvCaijingshujuList, R.layout.rv_caijingshuju_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShouyeJiaoyisuoBean.Mydata.dateMsg s, int position) {

                    holder.setClick(R.id.cj_msg_ly,s,position,rvAdapter);
                    holder.setTxt(R.id.caijing_rv_title,s.getTitle());
                    holder.setImg(ShouyeCJSJActivity.this,s.getPicture(),R.id.caijing_rv_iv);
                    holder.setTxt(R.id.caijing_rv_contentUrl,s.getSource());
                     holder.setTxt(R.id.caijing_rv_contentTime, MyUtil.longToDate2(s.getPublishTime()));
                }

            @Override
            public void clickEvent(int viewId, ShouyeJiaoyisuoBean.Mydata.dateMsg s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(ShouyeCJSJActivity.this,ShouyeCJSJconteActivity.class);
                intent.putExtra("title",s.getTitle());
                intent.putExtra("source",s.getSource());
                intent.putExtra("publishTime",s.getPublishTime());
                intent.putExtra("content",s.getContent());
                intent.putExtra("picture",s.getPicture());
                intent.putExtra("author",s.getAuthor());
                startActivity(intent);
            }
        };
        caijingshuju_rv.setAdapter(rvAdapter);
        caijingshuju_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                MyLog.e(Tag, "onScrollStateChanged: "+"状态="+newState+"位置"+lastVisibleItem+"===="+CJSJLayoutManager.getItemCount() );
                //SCROLL_STATE_IDLE当rv停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+2>= CJSJLayoutManager.getItemCount()) {
                    //加载更多
                    pageIndex++;
                    if (isPage==true){
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
                lastVisibleItem = CJSJLayoutManager.findLastVisibleItemPosition();
                if(caijingshuju_rv.canScrollVertically(-1)){
                    caijingshuju_rv.setNestedScrollingEnabled(false);
                    MyLog.e(Tag, "onScrolled : true");
                }else {
                    caijingshuju_rv.setNestedScrollingEnabled(true);
                    MyLog.e(Tag, "onScrolled : false");//滑动到顶部
                }
            }
        });
    }

}