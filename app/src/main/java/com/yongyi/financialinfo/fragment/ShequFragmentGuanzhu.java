package com.yongyi.financialinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.ImageActivity;
import com.yongyi.financialinfo.activity.MainActivity;
import com.yongyi.financialinfo.activity.ShequPinglunActivity;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShequRemenGuanzhuBean;
import com.yongyi.financialinfo.bean.ShequRemenSsBean;
import com.yongyi.financialinfo.bean.ShequRemenTuijianGuanzhuBean;
import com.yongyi.financialinfo.bean.UserBean;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShequFragmentGuanzhu extends Fragment {
    private static String Tag = "ShequFragmentGuanzhu";

    @BindView(R.id.guanzhu_rv)
    RecyclerView guanzhuRv;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;

    private View view;
    private BaseRecyclerAdapter<ShequRemenSsBean.Mydata.dateMsg> rmRvAdapter;
    private LinearLayoutManager manager;
    private List<ShequRemenSsBean.Mydata.dateMsg> remenBeans=new ArrayList<>();
    private List<Long> guanzhuList=new ArrayList<>();
    private boolean guanzhuHasMore=true;
    private int guanzhuPage=1;
    private int  tuijianPageNb=1;
    private boolean tuijianHasMore=true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shequ_guanzhu, container, false);
        ButterKnife.bind(this, view);
        RetrofitUtils.init();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        //initMsg();
        //初始化界面
        initView();
       /* //获取关注用户
        getIsGuanzhu(guanzhuPage);*/

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            loginProgress.setVisibility(View.VISIBLE);
            guanzhuPage=1;
            tuijianPageNb=1;
            remenBeans.clear();
            guanzhuList.clear();
            getIsGuanzhu(guanzhuPage);
            MyLog.e(Tag,"onResume1");
        }else
            MyLog.e(Tag,"onResume2");

    }

    private void dianZan(long talkId, int type){
        MyLog.e(Tag,"dianZan:getId:"+ShequFragment.userBean.getData().getId()+"talkId:"+talkId);
        Call<ResponseBody> call=RetrofitUtils.retrofit.create(InterService.class).dianZan(ShequFragment.userBean.getData().getId(),talkId,type);
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
    private void getIsGuanzhu(int pageNumber) {
        MyLog.e(Tag,"getIsGuanzhu:getId:"+ShequFragment.userBean.getData().getId());
        Call<ShequRemenGuanzhuBean> call=RetrofitUtils.retrofit.create(InterService.class).getGuanzhu(ShequFragment.userBean.getData().getId(),1,pageNumber,10);
        call.enqueue(new Callback<ShequRemenGuanzhuBean>() {
            @Override
            public void onResponse(Call<ShequRemenGuanzhuBean> call, Response<ShequRemenGuanzhuBean> response) {
                MyLog.e(Tag,response.toString());

                if(response.body()!=null&&response.body().isSuccess()) {
                    MyLog.e(Tag,response.toString());
                    for(int i=0;i<response.body().getData().getList().size();i++){
                        guanzhuList.add(response.body().getData().getList().get(i).getId());
                        MyLog.e(Tag,"关注的人的id有："+guanzhuList.get(i)+"");
                    }
                    guanzhuHasMore = response.body().getData().isHasMore();
                    //获取说说推荐列表
                    if(guanzhuHasMore==false)
                        getTuijianMsg(tuijianPageNb);
                    else
                        getIsGuanzhu(guanzhuPage++);
                }

            }

            @Override
            public void onFailure(Call<ShequRemenGuanzhuBean> call, Throwable t) {
                MyLog.e(Tag,"获取数据失败");
            }
        });
    }

    private void setGuanzhu(long userId,boolean isGuanzhu) {
        Call<ResponseBody> call=RetrofitUtils.retrofit.create(InterService.class).setGuanzhu(ShequFragment.userBean.getData().getId(),userId,isGuanzhu);
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

    private void getTuijianMsg(int tuijianPageNb) {
        Call<ShequRemenSsBean> call=RetrofitUtils.retrofit.create(InterService.class).getShuoShuoTuijian(MainActivity.projectName,tuijianPageNb,50);
        call.enqueue(new Callback<ShequRemenSsBean>() {
            @Override
            public void onResponse(Call<ShequRemenSsBean> call, Response<ShequRemenSsBean> response) {
                MyLog.e(Tag,response.toString());

                if(response.body()!=null&&response.body().getData()!=null){
                    if ("1".equals(tuijianPageNb)){
                        remenBeans.clear();
                        for(int i=0;i<response.body().getData().getList().size();i++) {
                            if (guanzhuList.contains(response.body().getData().getList().get(i).getUser().getId())) {
                                response.body().getData().getList().get(i).setGuanzhu(true);
                                response.body().getData().getList().get(i).setGuanzhu_ing(false);
                                remenBeans.add(response.body().getData().getList().get(i));
                            }
                        }
                    }else{
                        for(int i=0;i<response.body().getData().getList().size();i++) {
                            if (guanzhuList.contains(response.body().getData().getList().get(i).getUser().getId())) {
                                response.body().getData().getList().get(i).setGuanzhu(true);
                                response.body().getData().getList().get(i).setGuanzhu_ing(false);
                                remenBeans.add(response.body().getData().getList().get(i));
                            }
                        }
                    }
                    loginProgress.setVisibility(View.GONE);
                    tuijianHasMore=response.body().getData().isHasMore();
                    if(!tuijianHasMore)
                       // MyToast.shortToast(getActivity(),"暂无更多内容！");
                    rmRvAdapter.notifyDataSetChanged();
                    MyLog.e(Tag,remenBeans.size()+"");
                }

            }

            @Override
            public void onFailure(Call<ShequRemenSsBean> call, Throwable t) {
                MyLog.e(Tag,"获取数据失败");
                loginProgress.setVisibility(View.GONE);
            }
        });
    }


    private void initView() {

        //初始化热门rv
        remenBeans=new ArrayList<>();
        manager=new LinearLayoutManager(getActivity());
        rmRvAdapter=new BaseRecyclerAdapter<ShequRemenSsBean.Mydata.dateMsg>(getActivity(),remenBeans,R.layout.rv_shequ_remen) {

            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShequRemenSsBean.Mydata.dateMsg bean, int position) {

                //设置共同参数
                holder.setInVisibility(R.id.remen_time,View.GONE);
                holder.setTxt(R.id.remen_name, bean.getUser().getNickName());
                holder.setTxt(R.id.remen_time, MyUtil.longToDate3(bean.getPublishTime()));
                holder.setTxt(R.id.remen_date, MyUtil.longToDate4(bean.getPublishTime()));
                holder.setImgUrlCrop(getContext(),bean.getUser().getHead(),R.id.remen_touxiang);
                holder.setTxt(R.id.remen_content, bean.getContent());
                holder.setTxt(R.id.remen_dianzan_tv, bean.getZanCount()+"");
                holder.setTxt(R.id.remen_pinlun_tv, bean.getCommentCount()+"");
                //holder.setTxt(R.id.remen_zhuanfa_tv, bean.getForwardCount()+"");
                if(!bean.getPicture().equals("")){
                    holder.setInVisibility(R.id.remen_dandu_iv,View.VISIBLE);
                    holder.setImg(getContext(), bean.getPicture(), R.id.remen_dandu_iv);
                }
                else
                    holder.setInVisibility(R.id.remen_dandu_iv,View.GONE);

                //设置rv中的点击事件
                holder.setClick(R.id.remen_guanzhu, bean, position, rmRvAdapter);
                holder.setClick(R.id.remen_dianzan_ll, bean, position, rmRvAdapter);
                holder.setClick(R.id.remen_pinlun_ll, bean, position, rmRvAdapter);
             //   holder.setClick(R.id.remen_zhuanfa_ll, bean, position, rmRvAdapter);
                holder.setClick(R.id.remen_bukanta, bean, position, rmRvAdapter);
                holder.setClick(R.id.remen_quxiao, bean, position, rmRvAdapter);
                holder.setClick(R.id.remen_dandu_iv, bean, position, rmRvAdapter);

                //设置不同参数
                if (bean.getGuanzhu()) {
                    if (bean.getGuanzhu_ing()) {
                        holder.setImgRes(getActivity(), R.id.remen_guanzhu, R.mipmap.shequ_icon_sandian);
                        holder.setInVisibility(R.id.remen_ll, View.VISIBLE);

                    } else {
                        holder.setImgRes(getActivity(), R.id.remen_guanzhu, R.mipmap.shequ_btn_yiguanzhu);
                        holder.setInVisibility(R.id.remen_ll, View.GONE);
                    }
                } else {
                    holder.setImgRes(getActivity(), R.id.remen_guanzhu, R.mipmap.shequ_btn_guanzhu);
                    holder.setInVisibility(R.id.remen_ll, View.GONE);
                }

                if (bean.getHasZan())
                    holder.setImgRes(getActivity(), R.id.remen_dianzan_iv, R.mipmap.shequ_icon_zan_h);
                else
                    holder.setImgRes(getActivity(), R.id.remen_dianzan_iv, R.mipmap.shequ_icon_zan1);

                //设置部分字体颜色
                // holder.setTxtPortion(R.id.remen_content,"比特币老矿工，中国玩客云链克币第一人，关注币姐@区块链币姐带你暴力挖矿！！",s+",中国玩客");

            }

            @Override
            public void clickEvent(int viewId, ShequRemenSsBean.Mydata.dateMsg bean, int position) {
                super.clickEvent(viewId, bean, position);
                switch (viewId){
                    case R.id.remen_guanzhu:
                        if(bean.getGuanzhu()==true&&bean.getGuanzhu_ing()==true){
                            remenBeans.get(position).setGuanzhu_ing(false);
                        }else if(bean.getGuanzhu()==true&&bean.getGuanzhu_ing()==false) {
                            remenBeans.get(position).setGuanzhu_ing(true);
                        }else{
                            MyLog.e(Tag,!remenBeans.get(position).getGuanzhu()+"");
                            setGuanzhu(bean.getUser().getId(),!remenBeans.get(position).getGuanzhu());
                            remenBeans.get(position).setGuanzhu(!remenBeans.get(position).getGuanzhu());
                        }
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_dianzan_ll:
                        remenBeans.get(position).setHasZan(!remenBeans.get(position).getHasZan());
                        if(!remenBeans.get(position).getHasZan()){
                            remenBeans.get(position).setZanCount(remenBeans.get(position).getZanCount()-1);
                        }
                        else{
                            remenBeans.get(position).setZanCount(remenBeans.get(position).getZanCount()+1);
                        }
                        dianZan(remenBeans.get(position).getId(),2);
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_pinlun_ll:

                        startActivity(new Intent(getContext(), ShequPinglunActivity.class)
                                .putExtra("Head",bean.getUser().getHead())
                                .putExtra("name",bean.getUser().getNickName())
                                .putExtra("content",bean.getContent())
                                .putExtra("picture",bean.getPicture())
                                .putExtra("time",bean.getPublishTime())
                                .putExtra("userId",ShequFragment.userBean.getData().getId())
                                .putExtra("talkId",bean.getId())
                                .putExtra("videoId",bean.getVideoId())
                                .putExtra("type","2")
                        );
                        break;

                    case R.id.remen_bukanta:
                        setGuanzhu(bean.getUser().getId(),!remenBeans.get(position).getGuanzhu());
                        for(int i=0;i<remenBeans.size();i++){
                            if(remenBeans.get(i).getUser().getId()==bean.getUser().getId()){
                                remenBeans.remove(i);
                                i--;
                            }
                        }
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_quxiao:
                    /*    remenBeans.get(position).setGuanzhu_ing(false);
                        MyLog.e(Tag,!remenBeans.get(position).getGuanzhu()+"");
                        setGuanzhu(bean.getUser().getId(),!remenBeans.get(position).getGuanzhu());
                        remenBeans.get(position).setGuanzhu(!remenBeans.get(position).getGuanzhu());
                        remenBeans.remove(position);
                        rmRvAdapter.notifyDataSetChanged();*/
                        setGuanzhu(bean.getUser().getId(),!remenBeans.get(position).getGuanzhu());
                        for(int i=0;i<remenBeans.size();i++){
                            if(remenBeans.get(i).getUser().getId()==bean.getUser().getId()){
                                remenBeans.remove(i);
                                i--;
                            }
                        }
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_dandu_iv:
                        //设置启动activity渐变动画
                        startActivity(new Intent(getContext(), ImageActivity.class).putExtra("imageurl",bean.getPicture()));
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                }
            }
        };
        guanzhuRv.setLayoutManager(manager);
        guanzhuRv.setAdapter(rmRvAdapter);
        guanzhuRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //SCROLL_STATE_IDLE当rv停止滑动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem+2>= manager.getItemCount()) {
                    //加载更多
                    tuijianPageNb++;
                    if (tuijianHasMore==true){
                        getTuijianMsg(tuijianPageNb);
                        MyLog.e(Tag, "onScrollStateChanged: "+tuijianPageNb);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // MyLog.e(TAG, "onScrolled: "+"dx="+dx+"dy="+dy );
                //当前rv最后一个item的index
                lastVisibleItem = manager.findLastVisibleItemPosition();
                if(guanzhuRv.canScrollVertically(-1)){
                    guanzhuRv.setNestedScrollingEnabled(false);
                    //  MyLog.e(TAG, "onScrolled : true");
                }else {
                    guanzhuRv.setNestedScrollingEnabled(true);
                    MyLog.e(Tag, "onScrolled : false");//滑动到顶部
                }

            }
        });
    }

}
