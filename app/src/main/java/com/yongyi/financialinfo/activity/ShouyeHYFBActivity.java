package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.adapter.GlideImageLoader;
import com.yongyi.financialinfo.bean.ShouyeNewBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.model.ShouyeViewModel;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShouyeHYFBActivity extends AppCompatActivity {
    private String TAG="ShouyeHYFBActivity";
    @BindView(R.id.rv_hangqingzixun)
    RecyclerView hangqingzixun_Rv;
    @BindView(R.id.rv_shishiremen)
    RecyclerView shishiremen_Rv;
    @BindView(R.id.hangyefengbao_jiantou)
    ImageView hangyefengbao_jiantou;
    private ScheduledExecutorService scheduledExecutorService;
    private BaseRecyclerAdapter<ShouyeNewBean.Mydata.dateMsg> zixunRvAdapter;
    private BaseRecyclerAdapter<ShouyeNewBean.Mydata.dateMsg> rvAdapter;
    private List<ShouyeNewBean.Mydata.dateMsg> zixunRvList=new ArrayList<>();
    private List<ShouyeNewBean.Mydata.dateMsg> rv_hangyefengbao_remen_List=new ArrayList<>();

    private LinearLayoutManager zixunLayoutManager;
    private GridLayoutManager HYFB_layoutManger1;
    private List<Integer> list_path;
    private int pageIndex=1;
    private boolean isPage=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sy_hangyefengbao);

        ButterKnife.bind(this);
        //初始化数据
        initMsg();
        //初始化界面
        initView();
        //初始化数据
        getMsg(pageIndex);
    }

    //RecyclerView
    private void initMsg() {

        list_path = new ArrayList<>();
        list_path.add(R.mipmap.pic_banner_1);
        list_path.add(R.mipmap.pic_banner_2);
        list_path.add(R.mipmap.pic_banner_3);
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
        实时热门
        */
        HYFB_layoutManger1 = new GridLayoutManager(getApplicationContext(), 2);
        HYFB_layoutManger1.setOrientation(RecyclerView.HORIZONTAL);
        //设置表格，根据position计算在该position处1列占几格数据
        shishiremen_Rv.setLayoutManager(HYFB_layoutManger1);
        rvAdapter = new BaseRecyclerAdapter<ShouyeNewBean.Mydata.dateMsg>(getApplicationContext(), rv_hangyefengbao_remen_List, R.layout.rv_hangyefengbao_shishiremen) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShouyeNewBean.Mydata.dateMsg s, int position) {
                holder.setClick(R.id.shishiremen_cons, s, position,rvAdapter);
                if (s.getTitle().length() < 10)
                        holder.setTxt(R.id.shishi_tv, s.getTitle());
                    else
                        holder.setTxt(R.id.shishi_tv, s.getTitle().substring(0, 10)+"...");
            }
            @Override
            public void clickEvent(int viewId, ShouyeNewBean.Mydata.dateMsg s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(ShouyeHYFBActivity.this, NewsContentActivity.class);
                intent.putExtra("title",s.getTitle());
                intent.putExtra("Content",s.getContent());
                intent.putExtra("Source",s.getSource());
                intent.putExtra("Hot",s.getHot());
                intent.putExtra("Author",s.getAuthor());
                intent.putExtra("Picture",s.getPicture());
                intent.putExtra("Time",MyUtil.longToDate2(s.getPublishTime()));
                startActivity(intent);
            }
        };
        shishiremen_Rv.setAdapter(rvAdapter);

        //设置Rv布局管理者
        zixunLayoutManager = new LinearLayoutManager(this);
        zixunLayoutManager.setOrientation(RecyclerView.VERTICAL);
        hangqingzixun_Rv.setLayoutManager(zixunLayoutManager);

        //行情资讯rv
        zixunRvAdapter=new BaseRecyclerAdapter<ShouyeNewBean.Mydata.dateMsg>(this,zixunRvList,R.layout.rv_shouye) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShouyeNewBean.Mydata.dateMsg s, int position) {
                holder.setClick(R.id.rv_shouye1, s, position,zixunRvAdapter);
                holder.setTxt( R.id.rv_shouye_tv,s.getTitle());
                holder.setTxt( R.id.rv_shouye_caijin,s.getSource());
                //MyLog.e(TAG,"bindData:"+s.getTitle());
                //设置图片为圆角
                holder.setImgGray(ShouyeHYFBActivity.this,s.getPicture(),R.id.rv_shouye_iv);
                holder.setTxt(R.id.rv_shouye_riqi, MyUtil.longToDate2(s.getPublishTime()));
            }
            @Override
            public void clickEvent(int viewId, ShouyeNewBean.Mydata.dateMsg s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(ShouyeHYFBActivity.this, NewsContentActivity.class);
                intent.putExtra("title",s.getTitle());
                intent.putExtra("Content",s.getContent());
                intent.putExtra("Source",s.getSource());
                intent.putExtra("Hot",s.getHot());
                intent.putExtra("Author",s.getAuthor());
                intent.putExtra("Picture",s.getPicture());
                intent.putExtra("Time",MyUtil.longToDate2(s.getPublishTime()));
                startActivity(intent);
            }
        };
        hangqingzixun_Rv.setAdapter(zixunRvAdapter);

        hangqingzixun_Rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                MyLog.e(TAG, "onScrollStateChanged: "+"状态="+newState+"位置"+lastVisibleItem+"===="+zixunLayoutManager.getItemCount() );
                //SCROLL_STATE_IDLE当rv停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+2>= zixunLayoutManager.getItemCount()) {
                    //加载更多
                    pageIndex++;
                    if (isPage==true){
                        getMsg(pageIndex);
                        MyLog.e(TAG, "onScrollStateChanged: "+pageIndex);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // MyLog.e(TAG, "onScrolled: "+"dx="+dx+"dy="+dy );
                //当前rv最后一个item的index
                lastVisibleItem = zixunLayoutManager.findLastVisibleItemPosition();
                if(hangqingzixun_Rv.canScrollVertically(-1)){
                    hangqingzixun_Rv.setNestedScrollingEnabled(false);
                    MyLog.e(TAG, "onScrolled : true");
                }else {
                    hangqingzixun_Rv.setNestedScrollingEnabled(true);
                    MyLog.e(TAG, "onScrolled : false");//滑动到顶部
                }
            }
        });
    }

    //获取新闻数据
    private void getMsg(int pageNo) {
        RetrofitUtils.init();
        Call<ShouyeNewBean> result= RetrofitUtils.retrofit.create(InterService.class).getNews(10,pageNo,MainActivity.projectName);
        result.enqueue(new Callback<ShouyeNewBean>() {
            @Override
            public void onResponse(Call<ShouyeNewBean> call, Response<ShouyeNewBean> response) {
                MyLog.e(TAG,"onResponse:"+response.toString());
                if(response.body()!=null&&response.body().getSuccess()=="true"){
                    if (pageNo==1){
                        zixunRvList.clear();
                        zixunRvList.addAll(response.body().getData().getList());
                        zixunRvAdapter.notifyDataSetChanged();

                        rv_hangyefengbao_remen_List.clear();
                        rv_hangyefengbao_remen_List.addAll(zixunRvList);
                        rvAdapter.notifyDataSetChanged();

                        MyLog.e(TAG, "rv_hangyefengbao_remen_List:"+rv_hangyefengbao_remen_List.size());//滑动到顶部
                        MyLog.e(TAG, "zixunRvList:"+zixunRvList.size());//滑动到顶部
                    }else {
                        zixunRvList.addAll(response.body().getData().getList());
                        zixunRvAdapter.notifyDataSetChanged();
                    }
                    isPage=response.body().getData().isHasMore();
                    if(!isPage)
                        MyToast.shortToast(ShouyeHYFBActivity.this,"暂无更多新闻！");

                }else{
                    MyToast.shortToast(ShouyeHYFBActivity.this,"暂无新闻");
                }
            }
            @Override
            public void onFailure(Call<ShouyeNewBean> call, Throwable t) {
                MyLog.e(TAG,"获取失败");
            }

        });

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



