package com.yongyi.financialinfo.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.LoginActivity;
import com.yongyi.financialinfo.activity.MainActivity;
import com.yongyi.financialinfo.activity.WoGuanzhuActivity;
import com.yongyi.financialinfo.activity.WoSetUserActivity;
import com.yongyi.financialinfo.activity.WoXiaoxiActivity;
import com.yongyi.financialinfo.activity.WoYuwoxiangguanActivity;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.custom.CircleImageTransformer;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyDialog;
import com.yongyi.financialinfo.util.MyDialog1;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.wo_ll_guanzhu)
    ConstraintLayout woLlGuanzhu;
    @BindView(R.id.wo_ll_xiaoxi)
    ConstraintLayout woLlXiaoxi;
    @BindView(R.id.wo_ll_tuichu)
    ConstraintLayout woLlTuichu;
    private View view;
    private UserBean userBean;
    private String Tag="WoFragment";
    private String startType;
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
        startType = SpSimpleUtils.getSp("startType",getContext(),"LoginActivity");
        if(startType.equals("2"))
        //初始化数据
        initMsg();

    }

    private void initMsg() {
        Gson gson = new Gson();
        String json =SpSimpleUtils.getSp("UserBean",getContext(),"LoginActivity");
        userBean= gson.fromJson(json, UserBean.class);
        MyLog.e("WoFragment:",userBean.getSuccess());
        //初始化界面
        initView();
    }
    private void initView() {
        MyLog.e(Tag,"getHead:"+userBean.getData().getHead());
        if(!userBean.getData().getHead().equals("http://video.cqscrb.top/logo.jpg")&&!userBean.getData().getHead().equals("") ){
            Picasso.with(getActivity())
                    .load(userBean.getData().getHead())
                    .error(R.mipmap.pic_morentouxiang)
                    .transform(new CircleImageTransformer())
                    .into(woHead);
        }
        woBiaoqian.setText(userBean.getData().getSignature());
        woName.setText(userBean.getData().getNickName());
    }


    @OnClick({R.id.wo_head, R.id.wo_name, R.id.wo_biaoqian, R.id.wo_shezhi, R.id.wo_ll_fensi, R.id.wo_ll_xiangguan, R.id.wo_ll_guanzhu, R.id.wo_ll_xiaoxi, R.id.wo_ll_tuichu})
    public void onClick(View view) {
        startType = SpSimpleUtils.getSp("startType",getContext(),"LoginActivity");
        switch (view.getId()) {
            case R.id.wo_head:
                if(!startType.equals("1"))
                startActivity(new Intent(getActivity(), WoSetUserActivity.class));
            else
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_name:
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoSetUserActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_biaoqian:
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoSetUserActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_shezhi:
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoSetUserActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_ll_fensi:
                //打开粉丝界面
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoGuanzhuActivity.class).putExtra("flag",2));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_ll_xiangguan:
                //打开与我相关
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoYuwoxiangguanActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_ll_guanzhu:
                //打开关注界面
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoGuanzhuActivity.class).putExtra("flag",1));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_ll_xiaoxi:
                if(!startType.equals("1"))
                    startActivity(new Intent(getActivity(), WoXiaoxiActivity.class));
                else
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.wo_ll_tuichu:
               /* startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();*/
                new MyDialog1(getContext(), R.style.dialog, "确认", "您即将退出登录", new MyDialog1.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if(confirm){
                            MyLog.e("WoFragment:","true");
                            SpSimpleUtils.saveSp("UserBean","" , getContext(),"LoginActivity");
                            SpSimpleUtils.saveSp("userId","" ,getContext(),"LoginActivity");
                            SpSimpleUtils.saveSp("startType","1",getContext(),"LoginActivity");
                            SpSimpleUtils.saveSp("phone","" ,getContext(),"LoginActivity");
                            SpSimpleUtils.saveSp("password","" ,getContext(),"LoginActivity");
                            woName.setText("名字");
                            woBiaoqian.setText("个性签名");
                            woHead.setImageResource(R.mipmap.pic_morentouxiang);
                            userBean=null;
                        }else{
                            MyLog.e("WoFragment:","false");
                        }
                    }
                }).show();

                break;
        }
    }

    private void getUserMsg() {
            RetrofitUtils.init();
            Call<UserBean> result = RetrofitUtils.retrofit.create(InterService.class).getUserMsg(userBean.getData().getId());
            result.enqueue(new Callback<UserBean>() {
                @Override
                public void onResponse(Call<UserBean> call, Response<UserBean> response) {
                    if (response.body() != null && response.body().getSuccess() == "true") {
                                String userStr = new Gson().toJson(response.body());
                                SpSimpleUtils.saveSp("UserBean", userStr, getContext(), "LoginActivity");
                                initMsg();
                            }
                        }

                @Override
                public void onFailure(Call<UserBean> call, Throwable t) {
                    MyLog.e("WoFragment:","获取用户数据失败");
                }
            });

    }
    @Override
    public void onResume() {
        super.onResume();
        if(userBean!=null)
        getUserMsg();
    }

}
