package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.LoginPhoneYanzhengmaBean;
import com.yongyi.financialinfo.bean.ShequPinglunBean;
import com.yongyi.financialinfo.bean.ShequPinglunMsgBean;
import com.yongyi.financialinfo.bean.ShequRemenSsBean;
import com.yongyi.financialinfo.bean.ShequRemenTuijianGuanzhuBean;
import com.yongyi.financialinfo.custom.CircleImageTransformer;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShequPinglunActivity extends AppCompatActivity {
    private String Tag = "ShequPinglunActivity";
    @BindView(R.id.remen_dandu_iv)
    ImageView remenDanduIv;
    @BindView(R.id.pinglun_fasong)
    TextView pinglunFasong;
    @BindView(R.id.pinglun_rv)
    RecyclerView pinglunRv;
    ShequRemenSsBean shequRemenSsBean;
    @BindView(R.id.remen_name)
    TextView remenName;
    @BindView(R.id.remen_time)
    TextView remenTime;
    @BindView(R.id.remen_touxiang)
    ImageView remenHead;
    @BindView(R.id.remen_content)
    TextView remenContent;
    @BindView(R.id.pinglun_tv)
    TextView pinglunTv;
    @BindView(R.id.pinglun_et)
    TextView pinglunEt;
    @BindView(R.id.caijing_head_iv)
    ImageView caijingHeadIv;
    @BindView(R.id.content_jiantou)
    ImageView contentJiantou;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.remen_bukanta)
    TextView remenBukanta;
    @BindView(R.id.remen_quxiao)
    TextView remenQuxiao;
    @BindView(R.id.remen_ll)
    LinearLayout remenLl;
    @BindView(R.id.remen_line1)
    ImageView remenLine1;


    private String head;
    private String nickName;
    private String content;
    private String picture;
    private long publishTime;
    private long userId;
    private long talkId;
    private long videoId;
    private int pageNumber=1;
    private List<ShequPinglunMsgBean.Mydata.dateMsg> pinglunBean=new ArrayList<>();
    private boolean pinglunHasMore;
    private LinearLayoutManager pinglunManager;
    private BaseRecyclerAdapter<ShequPinglunMsgBean.Mydata.dateMsg> pinglunAdapter;
    private String type="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shequ_pinglun);
        ButterKnife.bind(this);
        RetrofitUtils.init();
        initMsg();
        initView();
        getPinglunMsg(pageNumber);
    }

    private void initView() {
        remenName.setText(nickName);
        Glide.with(this).load(head).into(remenHead);
        remenContent.setText(content);
        remenTime.setText(MyUtil.longToDate3(publishTime));
        if(!picture.equals("")){
            Glide.with(this).load(picture).into(remenDanduIv);
        }
        else
            remenDanduIv.setVisibility(View.GONE);
      /*  Picasso.with(this)
                .load(picture)
                .transform(new CircleImageTransformer())
                .into(remenDanduIv);*/
        pinglunManager=new LinearLayoutManager(this);
        pinglunAdapter=new BaseRecyclerAdapter<ShequPinglunMsgBean.Mydata.dateMsg>(this,pinglunBean,R.layout.activity_shequ_pinglun_rv) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShequPinglunMsgBean.Mydata.dateMsg s, int position) {
                MyLog.e(Tag, "getPublishTime: " + s.getPublishTime());
                holder.setTxt(R.id.rv_pinglun_content, s.getContent());
                holder.setTxt(R.id.rv_pinglun_name, s.getUser().getNickName());
                //设置日期，但是日期返回为相同的
                // holder.setTxt(R.id.rv_pinglun_time,MyUtil.longToDate2(s.getPublishTime()));
                MyLog.e(Tag, "head: " + s.getUser().getHead());
                holder.setImgGray(ShequPinglunActivity.this, s.getUser().getHead(), R.id.rv_pinglun_head);

            }
        };
        pinglunRv.setLayoutManager(pinglunManager);
        pinglunRv.setAdapter(pinglunAdapter);
        pinglunRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // MyLog.e(TAG, "onScrollStateChanged: "+"状态="+newState+"位置"+lastVisibleItem+"===="+layoutManager.getItemCount() );
                //SCROLL_STATE_IDLE当rv停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+2>= pinglunManager.getItemCount()) {
                    //加载更多
                    pageNumber++;
                    if (pinglunHasMore==true){
                        getPinglunMsg(pageNumber);
                        MyLog.e(Tag, "onScrollStateChanged: "+pageNumber);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // MyLog.e(TAG, "onScrolled: "+"dx="+dx+"dy="+dy );
                //当前rv最后一个item的index
                lastVisibleItem = pinglunManager.findLastVisibleItemPosition();
                if(pinglunRv.canScrollVertically(-1)){
                    pinglunRv.setNestedScrollingEnabled(false);
                    //  MyLog.e(TAG, "onScrolled : true");
                }else {
                    pinglunRv.setNestedScrollingEnabled(true);

                }

            }
        });
    }

    private void initMsg() {
        Intent intent = getIntent();
        head = intent.getStringExtra("Head");
        nickName = intent.getStringExtra("name");
        content = intent.getStringExtra("content");
        picture = intent.getStringExtra("picture");
        publishTime = intent.getLongExtra("time", 0);
        userId = intent.getLongExtra("userId", 0);
        talkId = intent.getLongExtra("talkId", 0);
        videoId = intent.getLongExtra("videoId", 0);
        MyLog.e(Tag, "userId : "+userId);
    }

    //获取评论列表
    private void getPinglunMsg(int _pageNumber){
        ShequPinglunBean bean=new ShequPinglunBean();
        bean.setUserId(String.valueOf(userId));
        bean.setTalkId(String.valueOf(talkId));
        bean.setMatchId("-1");
        bean.setVideoId(String.valueOf(-1));
        bean.set_orderBy("publish_time");
        bean.set_pageNumber(String.valueOf(_pageNumber));
        bean.set_pageSize("5");

        MyLog.e(Tag, "getPinglunMsg:"+bean.getUserId()+"   "+bean.getTalkId()+"  "+bean.getVideoId());

        Call<ShequPinglunMsgBean> call = RetrofitUtils.retrofit.create(InterService.class).postPinglunList(bean);
        call.enqueue(new Callback<ShequPinglunMsgBean>() {
            @Override
            public void onResponse(Call<ShequPinglunMsgBean> call, Response<ShequPinglunMsgBean> response) {
                MyLog.e(Tag, response.toString());

                if (response.body() != null) {
                    //评论成功，更新界面
                    if (1==pageNumber){
                        pinglunBean.clear();
                        pinglunBean.addAll(response.body().getData().getList());
                    }else {
                        pinglunBean.addAll(response.body().getData().getList());
                    }
                    pinglunHasMore=response.body().getData().isHasMore();
                    MyLog.e(Tag, "pinglunBean:"+pinglunBean.size());
                    pinglunAdapter.notifyDataSetChanged();
                }else{
                    MyLog.e(Tag, "获取数据为空");
                }
            }

            @Override
            public void onFailure(Call<ShequPinglunMsgBean> call, Throwable t) {
                MyLog.e(Tag, "获取数据失败");
            }
        });
    }

    private void postPinglun(String content) {
        MyLog.e(Tag,"评论："+ userId+" "+talkId+" "+content);
        Call<LoginPhoneYanzhengmaBean> call = RetrofitUtils.retrofit.create(InterService.class).postPinglun(userId, talkId, content);
        call.enqueue(new Callback<LoginPhoneYanzhengmaBean>() {
            @Override
            public void onResponse(Call<LoginPhoneYanzhengmaBean> call, Response<LoginPhoneYanzhengmaBean> response) {
                MyLog.e(Tag, response.toString());
                if (response.body() != null&&response.body().getSuccess()=="true") {
                    //评论成功，更新界面
                    MyLog.e(Tag, response.body().getSuccess());
                    MyToast.shortToast(ShequPinglunActivity.this,"评论成功");
                    pageNumber=1;
                    getPinglunMsg(pageNumber);
                }else{
                    new MyDialog(ShequPinglunActivity.this,"评论失败",response.body().getMsg()).show();
                }
            }

            @Override
            public void onFailure(Call<LoginPhoneYanzhengmaBean> call, Throwable t) {
                MyLog.e(Tag, "获取数据失败");
            }
        });
    }




    @OnClick({R.id.remen_dandu_iv, R.id.pinglun_fasong,R.id.content_jiantou})
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.content_jiantou:
                finish();
                break;
            case R.id.remen_dandu_iv:
                break;
            case R.id.pinglun_fasong:
                postPinglun(pinglunEt.getText().toString().trim());
                pinglunEt.setText("");
                break;

        }
    }
}