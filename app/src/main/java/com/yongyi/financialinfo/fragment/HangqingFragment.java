package com.yongyi.financialinfo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.ShouyeHangqingActivity;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.HangqingBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HangqingFragment extends Fragment {

    @BindView(R.id.hangqing_rv_title)
    RecyclerView hangqingRvTitle;
    @BindView(R.id.hangqing_zhangdiefu)
    LinearLayout hangqingZhangdiefu;
    @BindView(R.id.hangqing_chicangliang)
    LinearLayout hangqingChicangliang;
    @BindView(R.id.hangqing_rv_msg)
    RecyclerView hangqingRvMsg;

    private String name="Binance";
    private View view;

    private BaseRecyclerAdapter<String> rvAdapter;
    private BaseRecyclerAdapter<HangqingBean> rvAdapter1;
    private List<String> rvList;
    private List<HangqingBean> rvList1=new ArrayList<>();
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;
    private int clickPosition;
    private static String Tag="HangqingFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hangqing, container, false);
        ButterKnife.bind(this, view);

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化数据
        initMsg();
        //初始化界面
        initView();
    }
    private void initMsg() {
        clickPosition=0;

        rvList=new ArrayList<>();
        rvList.add("Binance");
        rvList.add("Bitfinex");
        rvList.add("Bittrex");
        rvList.add("Huobipro");

        getMsg("Binance");
    }
    private void initView() {
       // swipe.setColorSchemeColors(R.color.colorPrimary);
  /*      swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getMsg(name);
            }
            });*/
        //设置Rv布局管理者
        layoutManager = new LinearLayoutManager(getActivity());
        //设置横向滑动
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        hangqingRvTitle.setLayoutManager(layoutManager);
        MyLog.e(Tag,"clickPosition INIT");
        rvAdapter=new BaseRecyclerAdapter<String>(getContext(),rvList,R.layout.rv_hangqing_title) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                MyLog.e("hangqingRvTitle:",s);
                //适配数据
                holder.setTxt(R.id.rv_hangqing_title_tv,s);
                //设置点击事件
                holder.setClick(R.id.rv_hangqing_title_ll,s,position,rvAdapter);
                if(clickPosition==position){
                    holder.setTxtSize(R.id.rv_hangqing_title_tv,15);
                    holder.setTxtColor(R.id.rv_hangqing_title_tv,"#FFFFFF");
                    holder.setInVisibility(R.id.rv_hangqing_title_iv,View.VISIBLE);
                }else {
                    holder.setTxtColor(R.id.rv_hangqing_title_tv,"#FFC5C5");
                    holder.setTxtSize(R.id.rv_hangqing_title_tv,14);
                    holder.setInVisibility(R.id.rv_hangqing_title_iv,View.GONE);
                }


            }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
                clickPosition=position;
                MyLog.e(Tag,"clickPosition:"+clickPosition);
                rvAdapter.notifyDataSetChanged();
                name=s;
                getMsg(s);
            }
        };
        hangqingRvTitle.setAdapter(rvAdapter);


       layoutManager1 = new LinearLayoutManager(getActivity());
        hangqingRvMsg.setLayoutManager(layoutManager1);
        rvAdapter1=new BaseRecyclerAdapter<HangqingBean>(getContext(),rvList1,R.layout.rv_hangqing_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, HangqingBean s, int position) {
                holder.setClick(R.id.hq_msg_ll,s,position,rvAdapter1);
                holder.setTxt( R.id.hangqing_msg_tv1,s.getTicker().split(":")[1]);
                holder.setTxt( R.id.hangqing_msg_tv3,s.getDegree());

                if(s.getClose().split("\\.")[1].length()>2)
                     holder.setTxt( R.id.hangqing_msg_tv2,s.getClose().substring(0,s.getClose().indexOf(".")+3));
                else
                    holder.setTxt( R.id.hangqing_msg_tv2,s.getClose());

                if(s.getVol().split("\\.")[1].length()>2)
                    holder.setTxt( R.id.hangqing_msg_tv4,s.getVol().substring(0,s.getVol().indexOf(".")+3));
                else
                    holder.setTxt( R.id.hangqing_msg_tv4,s.getVol());

                if(s.getDegree().contains("-"))
                 holder.setTxtBackgroundIv(R.id.hangqing_msg_tv3,R.mipmap.hangqing_main_shuzi_l_bg);
                else
                    holder.setTxtBackgroundIv(R.id.hangqing_msg_tv3,R.mipmap.hangqing_main_shuzi_h_bg);
        }

            @Override
            public void clickEvent(int viewId, HangqingBean s, int position) {
                super.clickEvent(viewId, s, position);
                Intent intent = new Intent(getContext(), ShouyeHangqingActivity.class);
                intent.putExtra("ticker",s.getTicker());
                intent.putExtra("exchangeName",s.getExchangeName());
                intent.putExtra("degree",s.getDegree());
                intent.putExtra("dateTime",s.getDateTime());
                intent.putExtra("open",s.getOpen());
                intent.putExtra("close",s.getClose());
                intent.putExtra("high",s.getHigh());
                intent.putExtra("low",s.getLow());
                startActivity(intent);
            }
        };
        hangqingRvMsg.setAdapter(rvAdapter1);
    }

    private void getMsg(String itemName) {
        RetrofitUtils.init("http://api.coindog.com/api/v1/");
        Call<List<HangqingBean>> call=RetrofitUtils.retrofit.create(InterService.class).getHangqing(itemName);
        call.enqueue(new Callback<List<HangqingBean>>() {
            @Override
            public void onResponse(Call<List<HangqingBean>> call, Response<List<HangqingBean>> response) {
                    MyLog.e(Tag,response.toString());
                    if(response.body()!=null){
                        rvList1.clear();
                        rvList1.addAll(response.body());
                        rvAdapter1.notifyDataSetChanged();
                   /*     getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipe.setRefreshing(false);
                            }
                        });*/
                    }else{
                        MyToast.shortToast(getContext(),"获取数据失败");
                    }
            }

            @Override
            public void onFailure(Call<List<HangqingBean>> call, Throwable t) {
                MyLog.e(Tag,"获取失败");
            }
        });
    }


    @OnClick({R.id.hangqing_zhangdiefu, R.id.hangqing_chicangliang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hangqing_zhangdiefu:
                break;
            case R.id.hangqing_chicangliang:
                break;
        }
    }
}
