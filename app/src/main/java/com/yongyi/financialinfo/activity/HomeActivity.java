package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.adapter.MyFragmentPagerAdapter;
import com.yongyi.financialinfo.app.BaseActivity;
import com.yongyi.financialinfo.fragment.HangqingFragment;
import com.yongyi.financialinfo.fragment.ShequFragment;
import com.yongyi.financialinfo.fragment.ShouyeFragment;
import com.yongyi.financialinfo.fragment.WoFragment;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyToast;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Px;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_view_pager2)
    ViewPager2 mViewPager2;
    @BindView(R.id.home_shouye_iv)
    ImageView homeShouyeIv;
    @BindView(R.id.home_shouye_tv)
    TextView homeShouyeTv;
    @BindView(R.id.home_shouye)
    LinearLayout homeShouye;
    @BindView(R.id.home_hangqing_iv)
    ImageView homeHangqingIv;
    @BindView(R.id.home_hangqing_tv)
    TextView homeHangqingTv;
    @BindView(R.id.home_hangqing)
    LinearLayout homeHangqing;
    @BindView(R.id.home_shequ_iv)
    ImageView homeShequIv;
    @BindView(R.id.home_shequ_tv)
    TextView homeShequTv;
    @BindView(R.id.home_shequ)
    LinearLayout homeShequ;
    @BindView(R.id.home_wo_iv)
    ImageView homeWoIv;
    @BindView(R.id.home_wo_tv)
    TextView homeWoTv;
    @BindView(R.id.home_wo)
    LinearLayout homeWo;
    @BindView(R.id.home_ll)
    LinearLayout homeLl;

    private  String startType ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        startType = SpSimpleUtils.getSp("startType",this,"MainActivity");

        List<Fragment> mFragments = new ArrayList<>();
        mFragments .add(new ShouyeFragment());
        mFragments .add(new HangqingFragment());
        mFragments .add(new ShequFragment());
        mFragments .add(new WoFragment());
        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(this, mFragments);
        mViewPager2.setAdapter(mAdapter);
        //设置viewpager2是否可以滑动
        mViewPager2.setUserInputEnabled(false);
        mViewPager2.registerOnPageChangeCallback(new OnPageChangeCallback(){
            @Override
            public void onPageSelected(int position) {
                MyLog.e(TAG,"onPageSelected:"+position);
                changeUi(position);
            }

        });
        changeUi(0);
    }

    @OnClick({R.id.home_shouye, R.id.home_hangqing, R.id.home_shequ, R.id.home_wo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_shouye:
                changeUi(0);
                break;
            case R.id.home_hangqing:
                changeUi(1);
                break;
            case R.id.home_shequ:
                //检查是否登录

                if (startType.equals("1"))
                    startActivity(new Intent(this, LoginActivity.class));
                else
                changeUi(2);
                break;
            case R.id.home_wo:
                if (startType.equals("1"))
                    startActivity(new Intent(this, LoginActivity.class));
                else
                changeUi(3);
                break;
        }
    }

    private void changeUi(int i) {
        qingkongUi();
        switch (i){
            case 0:
                    mViewPager2.setCurrentItem(0);
                   homeShouyeIv.setImageResource(R.mipmap.icon_shouye);
                   homeShouyeTv.setTextColor(this.getResources().getColor(R.color.black));
                   break;
            case 1:
                mViewPager2.setCurrentItem(1);
                homeHangqingIv.setImageResource(R.mipmap.icon_hangqing);
                homeHangqingTv.setTextColor(this.getResources().getColor(R.color.black));
                break;
            case 2:
                mViewPager2.setCurrentItem(2);
                homeShequIv.setImageResource(R.mipmap.icon_shequ);
                homeShequTv.setTextColor(this.getResources().getColor(R.color.black));
                break;
            case 3:
                mViewPager2.setCurrentItem(3);
                homeWoIv.setImageResource(R.mipmap.icon_wo);
                homeWoTv.setTextColor(this.getResources().getColor(R.color.black));
                break;
        }
    }
    //清空点击效果，方便显示新的点击效果
    private  void qingkongUi(){
        homeShouyeIv.setImageResource(R.mipmap.icon_x_shouye);
        homeShouyeTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        homeHangqingIv.setImageResource(R.mipmap.icon_x_hangqing);
        homeHangqingTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        homeShequIv.setImageResource(R.mipmap.icon_x_shequ);
        homeShequTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        homeWoIv.setImageResource(R.mipmap.icon_x_wo);
        homeWoTv.setTextColor(this.getResources().getColor(R.color.text_gray));
    }

}
