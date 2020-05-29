package com.yongyi.financialinfo.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.ImageActivity;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.bean.ShequRemenBean;
import com.yongyi.financialinfo.model.ShequViewModel;
import com.yongyi.financialinfo.model.ShouyeViewModel;
import com.yongyi.financialinfo.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShequFragmentRemen extends Fragment {
    private static String Tag = "ShequFragmentRemen";

    @BindView(R.id.sq_rm_fm)
    RecyclerView sqRmFm;

    private View view;
    private BaseRecyclerAdapter<ShequRemenBean> rmRvAdapter;
    private LinearLayoutManager manager;
    private BaseRecyclerAdapter<String> rmRvAdapter2;
    private ShequViewModel shequViewModel;
    private List<ShequRemenBean> remenBeans;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shequ_remen, container, false);
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

        shequViewModel = ViewModelProviders.of(this).get(ShequViewModel.class);
        //让TextView观察ViewModel中数据的变化,并实时展示
        shequViewModel.mUserLiveData.observe(getViewLifecycleOwner(), new Observer<List<ShequRemenBean>>() {
            @Override
            public void onChanged(List<ShequRemenBean> bean) {
               //观察的数据变化，就会执行此方法
                remenBeans.clear();
                remenBeans.addAll(bean);
                MyLog.e(Tag,"remenBeans:"+remenBeans.size());
                rmRvAdapter.notifyDataSetChanged();
            }
        });
        //设置rv的数据
        shequViewModel.setMsg();
    }

    private void initView() {
        remenBeans=new ArrayList<>();
        manager=new LinearLayoutManager(getActivity());
        rmRvAdapter=new BaseRecyclerAdapter<ShequRemenBean>(getActivity(),remenBeans,R.layout.rv_shequ_remen) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, ShequRemenBean bean, int position) {

                //设置共同参数
                holder.setTxt(R.id.remen_name,bean.getName());
                holder.setTxt(R.id.remen_time,bean.getTime());
                holder.setTxt(R.id.remen_date,bean.getDate());

                //设置rv中的点击事件
                holder.setClick(R.id.remen_guanzhu,bean,position,rmRvAdapter);
                holder.setClick(R.id.remen_dianzan_ll,bean,position,rmRvAdapter);
                holder.setClick(R.id.remen_pinlun_ll,bean,position,rmRvAdapter);
                holder.setClick(R.id.remen_zhuanfa_ll,bean,position,rmRvAdapter);
                holder.setClick(R.id.remen_bukanta,bean,position,rmRvAdapter);
                holder.setClick(R.id.remen_quxiao,bean,position,rmRvAdapter);
                holder.setClick(R.id.remen_dandu_iv,bean,position,rmRvAdapter);

               //设置不同参数
               if(bean.getIsGuanzhu()){
                   if(bean.getGetIsGuanzhu_ing()){
                       holder.setImgRes(getActivity(),R.id.remen_guanzhu,R.mipmap.shequ_icon_sandian);
                       holder.setInVisibility(R.id.remen_ll,View.VISIBLE);

                   }else{
                       holder.setImgRes(getActivity(),R.id.remen_guanzhu,R.mipmap.shequ_btn_yiguanzhu);
                       holder.setInVisibility(R.id.remen_ll,View.GONE);
                   }
               } else{
                   holder.setImgRes(getActivity(),R.id.remen_guanzhu,R.mipmap.shequ_btn_guanzhu);
                   holder.setInVisibility(R.id.remen_ll,View.GONE);
               }


                if(bean.getIsDianzan())
                    holder.setImgRes(getActivity(),R.id.remen_dianzan_iv,R.mipmap.shequ_icon_zan_h);
                else
                    holder.setImgRes(getActivity(),R.id.remen_dianzan_iv,R.mipmap.shequ_icon_zan1);

                //设置部分字体颜色
                // holder.setTxtPortion(R.id.remen_content,"比特币老矿工，中国玩客云链克币第一人，关注币姐@区块链币姐带你暴力挖矿！！",s+",中国玩客");
                if(bean.getImages().size()>=2){
                    holder.setInVisibility(R.id.remen_dandu_iv,View.GONE);
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
                    initRvAdapter2(bean.getImages());
                    holder.setRecyclerViewItem(R.id.remen_rv, layoutManager, rmRvAdapter2);
                }else if(bean.getImages().size()==1){
                  //  holder.setImgRes(getActivity(),R.id.remen_dandu_iv,R.drawable.touxiang);
                    holder.setImg(getActivity(),bean.getImages().get(0),R.id.remen_dandu_iv);
                    holder.setInVisibility(R.id.remen_rv,View.GONE);
                    holder.setInVisibility(R.id.remen_jinghao,View.GONE);
                    holder.setInVisibility(R.id.remen_huati,View.GONE);
                }
            }

            @Override
            public void clickEvent(int viewId, ShequRemenBean bean, int position) {
                super.clickEvent(viewId, bean, position);
                switch (viewId){
                    case R.id.remen_guanzhu:
                        if(bean.getIsGuanzhu()==true&&bean.getGetIsGuanzhu_ing()==true){
                            remenBeans.get(position).setGetIsGuanzhu_ing(false);
                        }else if(bean.getIsGuanzhu()==true&&bean.getGetIsGuanzhu_ing()==false) {
                            remenBeans.get(position).setGetIsGuanzhu_ing(true);
                        }else{
                            remenBeans.get(position).setIsGuanzhu(!remenBeans.get(position).getIsGuanzhu());
                        }
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_dianzan_ll:
                        remenBeans.get(position).setIsDianzan(!remenBeans.get(position).getIsDianzan());
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_pinlun_ll:

                        break;
                    case R.id.remen_zhuanfa_ll:

                        break;
                    case R.id.remen_bukanta:
                        remenBeans.remove(position);
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_quxiao:
                        remenBeans.get(position).setGetIsGuanzhu_ing(false);
                        remenBeans.get(position).setIsGuanzhu(!remenBeans.get(position).getIsGuanzhu());
                        rmRvAdapter.notifyDataSetChanged();
                        break;
                    case R.id.remen_dandu_iv:
                        //设置启动activity渐变动画
                        startActivity(new Intent(getContext(), ImageActivity.class).putExtra("imageurl",bean.getImages().get(0)));
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        break;

                }
            }
        };
        sqRmFm.setLayoutManager(manager);
        sqRmFm.setAdapter(rmRvAdapter);

    }

    private void initRvAdapter2(List<String> i) {
        rmRvAdapter2=new BaseRecyclerAdapter<String>(getActivity(),i,R.layout.rv_shequ_remen_iv) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                //holder.setImgRes(getActivity(),R.id.remen_rv_iv,R.drawable.touxiang);
                holder.setImg(getActivity(),s,R.id.remen_rv_iv);
                holder.setClick(R.id.remen_rv_iv,s,position,rmRvAdapter2);
            }

            @Override
            public void clickEvent(int viewId, String s, int position) {
                super.clickEvent(viewId, s, position);
                //设置启动activity渐变动画
                startActivity(new Intent(getContext(), ImageActivity.class).putExtra("imageurl",s));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        };
    }

}
