package com.yongyi.financialinfo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.LoginActivity;
import com.yongyi.financialinfo.activity.MainActivity;
import com.yongyi.financialinfo.activity.NewsContentActivity;
import com.yongyi.financialinfo.activity.ShouyeCJSJActivity;
import com.yongyi.financialinfo.activity.ShouyeHYFBActivity;
import com.yongyi.financialinfo.activity.ShouyeHangqingActivity;
import com.yongyi.financialinfo.activity.ShouyeSSKXActivity;
import com.yongyi.financialinfo.activity.SignInActvity;
import com.yongyi.financialinfo.activity.YinsiZhengceActivity;
import com.yongyi.financialinfo.activity.YongHuXieYiActivity;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.HangqingBean;
import com.yongyi.financialinfo.bean.ShouyeNewBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.model.ShouyeViewModel;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyDialog1;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouyeFragment extends Fragment {
    String TAG = "ShouyeFragment";
    @BindView(R.id.shouye_iv)
    ImageView shouyeIv;
    @BindView(R.id.shouye_fengbao)
    ImageView shouyeFengbao;
    @BindView(R.id.shouye_kuaixun)
    ImageView shouyeKuaixun;
    @BindView(R.id.shouye_shuju)
    ImageView shouyeShuju;
    @BindView(R.id.shouye_qiandao)
    ImageView shouyeQiandao;
    @BindView(R.id.shouye_remen)
    TextView shouyeRemen;
    @BindView(R.id.shouye_rv)
    RecyclerView shouyeRv;
    @BindView(R.id.shouye_rv_jinxuan)
    RecyclerView shouyeRvJinxuan;
    @BindView(R.id.shouye_fengbao_tv)
    TextView shouyeFengbaoTv;
    @BindView(R.id.shouye_kuaixun_tv)
    TextView shouyeKuaixunTv;
    @BindView(R.id.shouye_jiaoyisuo_tv)
    TextView shouyeJiaoyisuoTv;
    @BindView(R.id.shouye_jingxuan)
    TextView shouyeJingxuan;
    private ShouyeViewModel shouyeViewModel;
    private View view;
    private BaseRecyclerAdapter<HangqingBean> rvAdapter;
    private BaseRecyclerAdapter<ShouyeNewBean.Mydata.dateMsg> rvAdapter1;

    private List<ShouyeNewBean.Mydata.dateMsg> newsRvList = new ArrayList<>();
    private List<HangqingBean> hangQingrvList = new ArrayList<>();

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;
    private int value;
    private int pageIndex = 1;
    private boolean isPage = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shouye, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //判断是否同意隐私政策和用户协议
        isGreat();
    }

    private void isGreat() {
        //SpSimpleUtils.saveSp("great","" , getContext(),"ShouyeFragment");

       String isgreat= SpSimpleUtils.getSp("great",getContext(),"ShouyeFragment");
        if(isgreat.equals("")){
            //没有同意
            MyLog.e(TAG, "进入没有同意");
            new MyDialog1(getContext(), R.style.dialog, "隐私政策和用户协议说明", "矿池币区重视用户隐私并严格遵守相关法律法规的要求。" +
                    "在您使用矿池币区前，请认真阅读《隐私政策》与《用户协议》，" +
                    "您同意并接受全部条款后可开始使用我们的服务。" +
                    "矿池币区将严格按照《隐私政策》与《用户协议》中的说明进行获取、使用、管理及保护您的隐私。","不同意","同意不再提示",true, new MyDialog1.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if(confirm){
                        MyLog.e(TAG,"不同意");
                        new MyDialog(getContext(), R.style.dialog, "提示", "该隐私政策和用户协议说明必须同意否则无法使用", new MyDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                MyLog.e(TAG, "进入没有同意");
                                new MyDialog1(getContext(), R.style.dialog, "隐私政策和用户协议说明", "矿池币区重视用户隐私并严格遵守相关法律法规的要求。" +
                                        "在您使用矿池币区前，请认真阅读《隐私政策》与《用户协议》，" +
                                        "您同意并接受全部条款后可开始使用我们的服务。" +
                                        "矿池币区将严格按照《隐私政策》与《用户协议》中的说明进行获取、使用、管理及保护您的隐私。","不同意","同意不再提示",true, new MyDialog1.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        if(confirm){
                                            MyLog.e(TAG,"还是不同意");
                                            getActivity().finish();
                                        }else{
                                            SpSimpleUtils.saveSp("great","2" , getContext(),"ShouyeFragment");
                                            MyLog.e(TAG,"终于同意了");
                                            //初始化界面
                                            initView();
                                            //初始化数据
                                            getNewsMsg(pageIndex);
                                            getHangqingMsg();
                                        }
                                    }

                                    @Override
                                    public void onClickYinsi(Dialog dialog, String str) {
                                        if(str.equals("yinsi")){
                                            startActivity(new Intent(getActivity(), YinsiZhengceActivity.class));
                                            MyLog.e(TAG,"点击了隐私政策");
                                        }else{
                                            startActivity(new Intent(getActivity(), YongHuXieYiActivity.class));
                                            MyLog.e(TAG,"点击了用户协议");
                                        }
                                    }
                                }).show();
                            }
                        }).show();
                    }else{
                        SpSimpleUtils.saveSp("great","2" , getContext(),"ShouyeFragment");
                        MyLog.e(TAG,"同意不再提示");
                        //初始化界面
                        initView();
                        //初始化数据
                        getNewsMsg(pageIndex);
                        getHangqingMsg();
                    }
                }

                @Override
                public void onClickYinsi(Dialog dialog, String str) {
                    if(str.equals("yinsi")){
                        startActivity(new Intent(getActivity(), YinsiZhengceActivity.class));
                        MyLog.e(TAG,"点击了隐私政策");
                    }else{
                        startActivity(new Intent(getActivity(), YongHuXieYiActivity.class));
                        MyLog.e(TAG,"点击了用户协议");
                    }
                }
            }).show();
        }else {
            //同意了
            MyLog.e(TAG, "进入同意了");
            //初始化界面
            initView();
            //初始化数据
            getNewsMsg(pageIndex);
            getHangqingMsg();
        }
    }

    //获取行情数据
    private void getHangqingMsg() {
        RetrofitUtils.init("http://api.coindog.com/api/v1/");
        Call<List<HangqingBean>> call = RetrofitUtils.retrofit.create(InterService.class).getHangqing("Binance");
        call.enqueue(new Callback<List<HangqingBean>>() {
            @Override
            public void onResponse(Call<List<HangqingBean>> call, Response<List<HangqingBean>> response) {
                MyLog.e(TAG, response.toString());
                hangQingrvList.clear();
                if (response.body() != null) {
                    for (int i = 1; i < response.body().size() && hangQingrvList.size() < 9; i++) {
                        hangQingrvList.add(response.body().get(i));
                        hangQingrvList.get(i - 1).setIndex(i);
                        if (hangQingrvList.get(i - 1).getClose().length() > 8)
                            hangQingrvList.get(i - 1).setClose(hangQingrvList.get(i - 1).getClose().substring(0, 8));
                    }
                    rvAdapter.notifyDataSetChanged();
                } else {
                    MyToast.shortToast(getContext(), "暂无行情");
                }
            }

            @Override
            public void onFailure(Call<List<HangqingBean>> call, Throwable t) {
                MyLog.e(TAG, "获取失败");
            }
        });
    }

    //获取新闻数据
    private void getNewsMsg(int pageNo) {
        RetrofitUtils.init();
        Call<ShouyeNewBean> result = RetrofitUtils.retrofit.create(InterService.class).getNews(10, pageNo, MainActivity.projectName);
        result.enqueue(new Callback<ShouyeNewBean>() {
            @Override
            public void onResponse(Call<ShouyeNewBean> call, Response<ShouyeNewBean> response) {
                MyLog.e(TAG, "onResponse:" + response.toString());
                if (response.body() != null && response.body().getSuccess() == "true") {
                    if ("1".equals(pageNo)) {
                        newsRvList.clear();
                        newsRvList.addAll(response.body().getData().getList());
                    } else {
                        newsRvList.addAll(response.body().getData().getList());
                    }
                    isPage = response.body().getData().isHasMore();
                    if (!isPage)
                        MyToast.shortToast(getActivity(), "暂无更多新闻！");
                    rvAdapter1.notifyDataSetChanged();
                } else {
                    MyToast.shortToast(getContext(), "暂无热门");
                }
            }

            @Override
            public void onFailure(Call<ShouyeNewBean> call, Throwable t) {
                MyLog.e(TAG, "获取失败");
            }

        });

    }

    private void initView() {

        //设置Rv布局管理者
        layoutManager = new LinearLayoutManager(getActivity());
        shouyeRv.setLayoutManager(layoutManager);
        layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        shouyeRvJinxuan.setLayoutManager(layoutManager1);

        //绑定行情数据
        rvAdapter = new BaseRecyclerAdapter<HangqingBean>(getContext(), hangQingrvList, R.layout.rv_shouye_jinxuan) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, HangqingBean s, int position) {
                holder.setTxt(R.id.rv_shouye_xuhao, s.getIndex() + "");
                holder.setTxt(R.id.rv_shouye_biaoti, s.getTicker().split(":")[1]);
                holder.setTxt(R.id.rv_shouye_money, s.getClose());
                holder.setTxt(R.id.rv_shouye_shijian, MyUtil.longToDate3(s.getDateTime()));
                holder.setTxt(R.id.rv_shouye_baifen, s.getDegree());
                holder.setClick(R.id.jinxuan_cons, s, position, rvAdapter);
                if (s.getDegree().contains("-"))
                    holder.setTxtBackgroundIv(R.id.rv_shouye_baifen, R.mipmap.shouye_hanqing_shuju_l_bg);
                else
                    holder.setTxtBackgroundIv(R.id.rv_shouye_baifen, R.mipmap.shouye_hanqing_shuju_h_bg);
            }

            @Override
            public void clickEvent(int viewId, HangqingBean s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(getContext(), ShouyeHangqingActivity.class);
                intent.putExtra("ticker", s.getTicker());
                intent.putExtra("exchangeName", s.getExchangeName());
                intent.putExtra("degree", s.getDegree());
                intent.putExtra("dateTime", s.getDateTime());
                intent.putExtra("open", s.getOpen());
                intent.putExtra("close", s.getClose());
                intent.putExtra("high", s.getHigh());
                intent.putExtra("low", s.getLow());
                startActivity(intent);
            }
        };
        shouyeRvJinxuan.setAdapter(rvAdapter);

        //绑定新闻数据
        rvAdapter1 = new BaseRecyclerAdapter<ShouyeNewBean.Mydata.dateMsg>(getContext(), newsRvList, R.layout.rv_shouye) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShouyeNewBean.Mydata.dateMsg s, int position) {
                holder.setClick(R.id.rv_shouye1, s, position, rvAdapter1);
                holder.setTxt(R.id.rv_shouye_tv, s.getTitle());
                holder.setTxt(R.id.rv_shouye_caijin, s.getSource());
                //MyLog.e(TAG,"bindData:"+s.getTitle());
                //设置图片为圆角
                holder.setImgGray(getContext(), s.getPicture(), R.id.rv_shouye_iv);
                holder.setTxt(R.id.rv_shouye_riqi, MyUtil.longToDate2(s.getPublishTime()));
            }

            @Override
            public void clickEvent(int viewId, ShouyeNewBean.Mydata.dateMsg s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(getContext(), NewsContentActivity.class);
                intent.putExtra("title", s.getTitle());
                intent.putExtra("Content", s.getContent());
                intent.putExtra("Source", s.getSource());
                intent.putExtra("Hot", s.getHot());
                intent.putExtra("Time", MyUtil.longToDate2(s.getPublishTime()));
                intent.putExtra("Author", s.getAuthor());
                intent.putExtra("Picture", s.getPicture());
                startActivity(intent);
            }
        };
        shouyeRv.setAdapter(rvAdapter1);

        shouyeRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // MyLog.e(TAG, "onScrollStateChanged: "+"状态="+newState+"位置"+lastVisibleItem+"===="+layoutManager.getItemCount() );
                //SCROLL_STATE_IDLE当rv停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 2 >= layoutManager.getItemCount()) {
                    //加载更多
                    pageIndex++;
                    if (isPage == true) {
                        getNewsMsg(pageIndex);
                        MyLog.e(TAG, "onScrollStateChanged: " + pageIndex);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // MyLog.e(TAG, "onScrolled: "+"dx="+dx+"dy="+dy );
                //当前rv最后一个item的index
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                if (shouyeRv.canScrollVertically(-1)) {
                    shouyeRv.setNestedScrollingEnabled(false);
                    //  MyLog.e(TAG, "onScrolled : true");
                } else {
                    shouyeRv.setNestedScrollingEnabled(true);
                    MyLog.e(TAG, "onScrolled : false");//滑动到顶部
                }

            }
        });

    }

    @OnClick({R.id.shouye_fengbao_tv, R.id.shouye_kuaixun_tv, R.id.shouye_jiaoyisuo_tv,R.id.shouye_iv, R.id.shouye_fengbao, R.id.shouye_kuaixun, R.id.shouye_shuju, R.id.shouye_qiandao, R.id.shouye_remen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shouye_iv:
                break;
            case R.id.shouye_fengbao:
                startActivity(new Intent(getActivity(), ShouyeHYFBActivity.class));
                break;
            case R.id.shouye_kuaixun:
                startActivity(new Intent(getActivity(), ShouyeSSKXActivity.class));
                break;
            case R.id.shouye_shuju:
                startActivity(new Intent(getActivity(), ShouyeCJSJActivity.class));
                break;
            case R.id.shouye_qiandao:
                String startType = SpSimpleUtils.getSp("startType", getContext(), "LoginActivity");
                if (startType.equals("1"))
                    startActivity(new Intent(getContext(), LoginActivity.class));
                else
                    startActivity(new Intent(getActivity(), SignInActvity.class));
                break;
            case R.id.shouye_remen:
                break;
            case R.id.shouye_fengbao_tv:
                startActivity(new Intent(getActivity(), ShouyeHYFBActivity.class));
                break;
            case R.id.shouye_kuaixun_tv:
                startActivity(new Intent(getActivity(), ShouyeSSKXActivity.class));
                break;
            case R.id.shouye_jiaoyisuo_tv:
                startActivity(new Intent(getActivity(), ShouyeCJSJActivity.class));
                break;
        }
    }

}
