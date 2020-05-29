package com.yongyi.financialinfo.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.BaseRecyclerAdapter;
import com.yongyi.financialinfo.adapter.BaseRecyclerViewHolder;
import com.yongyi.financialinfo.model.ShouyeViewModel;
import com.yongyi.financialinfo.util.MyLog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouyeFragment extends Fragment {


    @BindView(R.id.shouye_iv)
    ImageView shouyeIv;
    @BindView(R.id.shouye_fengbao)
    ImageView shouyeFengbao;
    @BindView(R.id.shouye_kuaixun)
    ImageView shouyeKuaixun;
    @BindView(R.id.shouye_shuju)
    ImageView shouyeShuju;
    @BindView(R.id.shouye_ll_shousuo)
    LinearLayout shouyeLlShousuo;
    @BindView(R.id.shouye_qiandao)
    ImageView shouyeQiandao;
    @BindView(R.id.shouye_remen)
    TextView shouyeRemen;
    @BindView(R.id.shouye_rv)
    RecyclerView shouyeRv;
    @BindView(R.id.shouye_rv_jinxuan)
    RecyclerView shouyeRvJinxuan;
    private ShouyeViewModel shouyeViewModel;
    private View view;
    private BaseRecyclerAdapter<String> rvAdapter;
    private List<String> rvList;
    private List<String> rvList1;
    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;
    private int value;
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
        //初始化数据
        initMsg();
        //初始化界面
        initView();
    }

    private void initMsg() {
        rvList=new ArrayList<>();
        rvList.add("1111111111");
        rvList.add("2222222222");
        rvList.add("333333333333");
        rvList.add("44444444444444");
        rvList.add("5555555");
        rvList.add("6666");
        rvList.add("7777777777777777777777777777777");
        rvList.add("44444444444444");
        rvList.add("5555555");
        rvList.add("6666");
        rvList.add("7777777777777777777777777777777");
        rvList.add("1111111111");
        rvList.add("222222222222222222222222222222222222222222222222222");
        rvList1=new ArrayList<>();
        rvList1.add("1");
        rvList1.add("2");
        rvList1.add("3");
        rvList1.add("4");

        shouyeViewModel = ViewModelProviders.of(this).get(ShouyeViewModel.class);
        //让TextView观察ViewModel中数据的变化,并实时展示
        shouyeViewModel.mUserLiveData.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                MyLog.e("shouyeViewModel", integer.toString());
                value=integer;
                MyLog.e("shouyeViewModel", value+"");
            }
        });
        shouyeViewModel.getShouye();

    }

    private void initView() {
        //设置Rv布局管理者
        layoutManager = new LinearLayoutManager(getActivity());
        shouyeRv.setLayoutManager(layoutManager);
        layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        shouyeRvJinxuan.setLayoutManager(layoutManager1);
        rvAdapter=new BaseRecyclerAdapter<String>(getContext(),rvList1,R.layout.rv_shouye_jinxuan) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setTxt(R.id.rv_shouye_xuhao,s);
                if(s.equals("2")){
                    holder.setTxtBackground(R.id.rv_shouye_baifen, "#00C72A");
                }
            }
        };
        shouyeRvJinxuan.setAdapter(rvAdapter);

        rvAdapter=new BaseRecyclerAdapter<String>(getContext(),rvList,R.layout.rv_shouye) {
            @Override
            public void bindData(BaseRecyclerViewHolder holder, String s, int position) {
                holder.setTxt( R.id.rv_shouye_tv,s);
                //设置图片为圆角
               // holder.setImgGray(getContext(),s.getFImg(),R.id.rv_shouye_iv);
            }
        };
        shouyeRv.setAdapter(rvAdapter);
    }


    @OnClick({R.id.shouye_iv, R.id.shouye_fengbao, R.id.shouye_kuaixun, R.id.shouye_shuju, R.id.shouye_ll_shousuo, R.id.shouye_qiandao, R.id.shouye_remen})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shouye_iv:
                break;
            case R.id.shouye_fengbao:
                break;
            case R.id.shouye_kuaixun:
                break;
            case R.id.shouye_shuju:
                break;
            case R.id.shouye_ll_shousuo:
                break;
            case R.id.shouye_qiandao:
                break;
            case R.id.shouye_remen:
                break;
        }
    }
}
