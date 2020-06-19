package com.yongyi.financialinfo.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.activity.LoginActivity;
import com.yongyi.financialinfo.activity.MainActivity;
import com.yongyi.financialinfo.activity.ShequCameraActivity;
import com.yongyi.financialinfo.adapter.FragmentVpAdapter;
import com.yongyi.financialinfo.adapter.HorizontalCanScrollViewPager;
import com.yongyi.financialinfo.adapter.MyFragmentPagerAdapter;
import com.yongyi.financialinfo.util.SpSimpleUtils;
import com.yongyi.financialinfo.util.ViewUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShequFragment extends Fragment {
    @BindView(R.id.shequ_remen_line)
    ImageView shequRemenLine;
    @BindView(R.id.shequ_guanzhu_line)
    ImageView shequGuanzhuLine;
    @BindView(R.id.shequ_camera)
    ImageView shequCamera;
    @BindView(R.id.shequ_remen)
    TextView shequRemen;
    @BindView(R.id.shequ_guanzhu)
    TextView shequGuanzhu;
    @BindView(R.id.shequ_vp)
    ViewPager shequVp;
    private View view;
    private FragmentVpAdapter shequVpAdapter;
    private List<Fragment> listFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shequ, container, false);
        ButterKnife.bind(this,view);
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
        //将viewpager2需要的fragment添加进适配器中
        listFragment=new ArrayList<>();
        listFragment.add(new ShequFragmentRemen());
        listFragment.add(new ShequFragmentGuanzhu());
        shequVpAdapter= new FragmentVpAdapter(getParentFragmentManager(),listFragment) ;
    }

    private void initView() {
        //给viewpager添加适配器
        shequVp.setAdapter(shequVpAdapter);
        shequVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(position==0)
                    shequRemen.callOnClick();//回调控件的点击事件
                else
                    shequGuanzhu.callOnClick();//回调控件的点击事件
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        //初始化顶部显示
        ViewUtil.setTextClick(shequRemen,15,getResources().getColor(R.color.titleWhite),shequRemenLine,true);
    }
    @OnClick({R.id.shequ_camera, R.id.shequ_remen, R.id.shequ_guanzhu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shequ_camera:
              startActivity(new Intent(getContext(), ShequCameraActivity.class));
                break;
            case R.id.shequ_remen:
                ViewUtil.setTextClick(shequRemen,15,getResources().getColor(R.color.titleWhite),shequRemenLine,true);
                ViewUtil.setTextClick(shequGuanzhu,14,getResources().getColor(R.color.fenhong),shequGuanzhuLine,false);
                shequVp.setCurrentItem(0);
                break;
            case R.id.shequ_guanzhu:
                ViewUtil.setTextClick(shequGuanzhu,15,getResources().getColor(R.color.titleWhite),shequGuanzhuLine,true);
                ViewUtil.setTextClick(shequRemen,14,getResources().getColor(R.color.fenhong),shequRemenLine,false);
                shequVp.setCurrentItem(1);
                break;
        }
    }
}
