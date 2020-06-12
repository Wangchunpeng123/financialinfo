package com.yongyi.financialinfo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.HomeActivity;
import com.yongyi.financialinfo.activity.LoginActivity;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.custom.CircleImageTransformer;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.lang.reflect.Type;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class WoFragment extends Fragment {

    @BindView(R.id.wo_head)
    ImageView woHead;
    @BindView(R.id.wo_name)
    TextView woName;
    @BindView(R.id.wo_biaoqian)
    TextView woBiaoqian;
    @BindView(R.id.wo_shezhi)
    ImageView woShezhi;
    @BindView(R.id.wo_ll_fensi)
    ConstraintLayout woLlFensi;
    @BindView(R.id.wo_ll_xiangguan)
    ConstraintLayout woLlXiangguan;
    @BindView(R.id.wo_ll_dongtai)
    ConstraintLayout woLlDongtai;
    @BindView(R.id.wo_ll_xiaoxi)
    ConstraintLayout woLlXiaoxi;
    @BindView(R.id.wo_ll_tuichu)
    ConstraintLayout woLlTuichu;
    private View view;
    private UserBean userBean;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wo, container, false);
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
        Gson gson = new Gson();
        String json =SpSimpleUtils.getSp("UserBean",getContext(),"LoginActivity");
        userBean= gson.fromJson(json, UserBean.class);
        MyLog.e("WoFragment:",userBean.getSuccess());
    }
    private void initView() {
        Picasso.with(getActivity())
                .load(userBean.getData().getHead())
                .transform(new CircleImageTransformer())
                .into(woHead);
        woBiaoqian.setText(userBean.getData().getSignature());
        woName.setText(userBean.getData().getNickName());
    }


    @OnClick({R.id.wo_head, R.id.wo_name, R.id.wo_biaoqian, R.id.wo_shezhi, R.id.wo_ll_fensi, R.id.wo_ll_xiangguan, R.id.wo_ll_dongtai, R.id.wo_ll_xiaoxi, R.id.wo_ll_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wo_head:
                break;
            case R.id.wo_name:
                break;
            case R.id.wo_biaoqian:
                break;
            case R.id.wo_shezhi:
                break;
            case R.id.wo_ll_fensi:
                break;
            case R.id.wo_ll_xiangguan:
                break;
            case R.id.wo_ll_dongtai:
                break;
            case R.id.wo_ll_xiaoxi:
                break;
            case R.id.wo_ll_tuichu:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
        }
    }
}
