package com.yongyi.financialinfo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
    private View view;

    private BaseRecyclerAdapter<String> rvAdapter;
    private BaseRecyclerAdapter<String> rvAdapter1;
    private List<String> rvList;
    private List<String> rvList1;
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
        rvList.add("主力");
        rvList.add("大连");
        rvList.add("上海");
        rvList.add("上期能源");
        rvList.add("黄金白银");

        rvList1=new ArrayList<>();
        rvList1.add("泸铜1901");
        rvList1.add("泸铜1902");
        rvList1.add("泸铜1903");
        rvList1.add("泸铜1904");
    }
    private void initView() {
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
                    holder.setInVisibility(R.id.rv_hangqing_title_iv,View.VISIBLE);
                }else {
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
            }
        };
        hangqingRvTitle.setAdapter(rvAdapter);


       layoutManager1 = new LinearLayoutManager(getActivity());
        hangqingRvMsg.setLayoutManager(layoutManager1);
        rvAdapter1=new BaseRecyclerAdapter<String>(getContext(),rvList1,R.layout.rv_hangqing_msg) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setTxt( R.id.hangqing_msg_tv1,s);
                if(s.equals("泸铜1902")){
                    holder.setTxtBackgroundIv(R.id.hangqing_msg_tv3,R.mipmap.hangqing_main_shuzi_l_bg);

                }
            }


        };
        hangqingRvMsg.setAdapter(rvAdapter1);
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
