package com.yongyi.financialinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongyi.financialinfo.R;
import com.yongyi.financialinfo.bean.HasSignBean;
import com.yongyi.financialinfo.bean.SignListBean;
import com.yongyi.financialinfo.bean.SignNowBean;
import com.yongyi.financialinfo.http.InterService;
import com.yongyi.financialinfo.util.MyAdvertisementView;
import com.yongyi.financialinfo.util.MyLog;
import com.yongyi.financialinfo.util.MyUtil;
import com.yongyi.financialinfo.util.RetrofitUtils;
import com.yongyi.financialinfo.util.SpSimpleUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActvity extends AppCompatActivity {
    private static final String TAG = "SignInActvity";
    @BindView(R.id.signin_iv_lianxuqiandaobeijing)
    ImageView qiandaoImgButton;
    @BindView(R.id.signin_tv_days)
    TextView continuousDays;
    @BindView(R.id.signin_iv_back)
    ImageView backImg;
    @BindView(R.id.signin_iv_first)
    ImageView jiangbei1Img;
    @BindView(R.id.signin_iv_second)
    ImageView jiangbei2Img;
    @BindView(R.id.signin_iv_third)
    ImageView jiangbei3Img;
    @BindView(R.id.signin_tv_days1)
    TextView allDays;
    @BindView(R.id.ball_btn_1)
    Button Ball1;
    @BindView(R.id.ball_btn_2)
    Button Ball2;
    @BindView(R.id.ball_btn_3)
    Button Ball3;
    @BindView(R.id.ball_btn_4)
    Button Ball4;
    @BindView(R.id.ball_btn_5)
    Button Ball5;
    @BindView(R.id.ball_btn_6)
    Button Ball6;
    @BindView(R.id.ball_btn_7)
    Button Ball7;

    private boolean chongfu=false;
    long currentTimeMillis = System.currentTimeMillis();//获取系统时间 long类型
    private List<SignListBean.Data> list = new ArrayList<>();
    private String userId;
    private int lianxuQiandao=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        RetrofitUtils.init();
        userId = SpSimpleUtils.getSp("userId", this, "LoginActivity");
        hassgin();
        getmsg();

        MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this);

    }

    //用户签到历史,默认只会返回当月签到记录
    private void getmsg() {
        Call<SignListBean> call = RetrofitUtils.retrofit.create(InterService.class).getSignList(Long.valueOf(userId));
        call.enqueue(new Callback<SignListBean>() {
            @Override
            public void onResponse(Call<SignListBean> call, Response<SignListBean> response) {
                MyLog.e(TAG, response.toString());
                if (response.body() != null && response.body().getSuccess() == true) {
                    list.clear();
                    list.addAll(response.body().getData());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            allDays.setText(list.size() + "");
                            if(list.size()>1&&list.size()<10){
                                jiangbei1Img.setImageResource(R.mipmap.pic_ziyoutianjiashuziyi);
                            }else if(list.size()>10&&list.size()<20){
                                jiangbei1Img.setImageResource(R.mipmap.pic_ziyoutianjiashuziyi);
                                jiangbei2Img.setImageResource(R.mipmap.pic_ziyoutianjiashuziyi);
                            }else if(list.size()>20&&list.size()<30){
                                jiangbei1Img.setImageResource(R.mipmap.pic_ziyoutianjiashuziyi);
                                jiangbei2Img.setImageResource(R.mipmap.pic_ziyoutianjiashuziyi);
                                jiangbei3Img.setImageResource(R.mipmap.pic_ziyoutianjiashuziyi);
                            }
                        }
                    });
                    if(list.size()!=0)
                    initQiaodaojilu();
                }
            }

            @Override
            public void onFailure(Call<SignListBean> call, Throwable t) {
                MyLog.e(TAG, "错误");
            }
        });
    }

    private void initQiaodaojilu() {

        //设置连续签到
        List<String> allDay=new ArrayList<>();
        String toDay;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        MyLog.e(TAG,"Date获取当前日期时间"+simpleDateFormat.format(date));
        toDay=simpleDateFormat.format(date);
        for(int i=list.size();i>0;i--){
            allDay.add(MyUtil.longToDate5(list.get(i-1).getTime()));
        }
        //判断昨天有没有签到，有的话加上，没有就从1开始
        List<Integer> iList=new ArrayList<>();
        for(String str:allDay){
            iList.add(Integer.valueOf(str.substring(str.length()-1,str.length())));
                MyLog.e(TAG,"获取list时间"+str);
        }
        MyLog.e(TAG,"iList.size:"+iList.size());
        for(Integer it:iList)
            MyLog.e(TAG,"iList:"+it);
        for(int i=0;i<iList.size();i++){
            if(iList.get(i)!=1){
             //   MyLog.e(TAG,"11111:");
                if(i+1<iList.size()&&iList.get(i)-iList.get(i+1)==1){
                  //  MyLog.e(TAG,"22222:");
                    lianxuQiandao++;
                } else{
                   // MyLog.e(TAG,"33333:");
                    lianxuQiandao++;
                    break;
                }
            }else {
                if(i+1<iList.size()&&iList.get(i+1)==9){
                 //   MyLog.e(TAG,"444444:");
                    lianxuQiandao++;
                }
            }
        }
        continuousDays.setText(lianxuQiandao+"");
        MyLog.e(TAG,"lianxuQiandao:"+lianxuQiandao);
        //设置小圆球
        List<Integer> day=new ArrayList<>();
        int index=0;
        for(int i=list.size();i>0;i--){
            day.add(MyUtil.dateToWeek(MyUtil.longToDate4(list.get(i-1).getTime())));
          if(day.get(index)==1){
              //本周签到记录完毕
              break;
          }
          index++;
        }

        MyLog.e(TAG,"length: "+ day.size());
        for(int i=0;i<day.size();i++){
            MyLog.e(TAG," "+ day.get(i));
            switch (day.get(i)){
                case 1:    Ball1.setBackgroundResource(R.drawable.button_circle_shape);
                             Ball1.setTextColor(getResources().getColor(R.color.titleWhite));
                             continue;
                case 2:    Ball2.setBackgroundResource(R.drawable.button_circle_shape);
                            Ball2.setTextColor(getResources().getColor(R.color.titleWhite));
                             continue;
                case 3:
                         Ball3.setBackgroundResource(R.drawable.button_circle_shape);
                         Ball3.setTextColor(getResources().getColor(R.color.titleWhite));
                            continue;
                case 4:    Ball4.setBackgroundResource(R.drawable.button_circle_shape);
                            Ball4.setTextColor(getResources().getColor(R.color.titleWhite));
                         continue;
                case 5:   Ball5.setBackgroundResource(R.drawable.button_circle_shape);
                         Ball5.setTextColor(getResources().getColor(R.color.titleWhite));
                            continue;
                case 6:    Ball6.setBackgroundResource(R.drawable.button_circle_shape);
                         Ball6.setTextColor(getResources().getColor(R.color.titleWhite));
                            continue;
                case 0:   Ball7.setBackgroundResource(R.drawable.button_circle_shape);
                         Ball7.setTextColor(getResources().getColor(R.color.titleWhite));
                         continue;
            }
        }



    }


    //今日是否已经签到
    private  void hassgin() {
        Call<HasSignBean> call = RetrofitUtils.retrofit.create(InterService.class).gethasSign(Long.valueOf(userId));
        call.enqueue(new Callback<HasSignBean>() {
            @Override
            public void onResponse(Call<HasSignBean> call, Response<HasSignBean> response) {
                MyLog.e(TAG, response.toString());
                if (response.body() != null && response.body().isSuccess() == true) {
                    if (response.body().isData() == false) {
                        Toast.makeText(SignInActvity.this, "今日未签到", Toast.LENGTH_SHORT).show();
                    } else {
                        chongfu=true;
                        Toast.makeText(SignInActvity.this, "今日已签到", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<HasSignBean> call, Throwable t) {
                MyLog.e(TAG, "错误111");
            }
        });
    }

    //今日签到
    private void signNow() {
        Call<SignNowBean> call = RetrofitUtils.retrofit.create(InterService.class).signNow(Long.valueOf(userId));
        call.enqueue(new Callback<SignNowBean>() {
            @Override
            public void onResponse(Call<SignNowBean> call, Response<SignNowBean> response) {
                if (response.body() != null && response.body().isSuccess() == true) {
                    response.body().setData(true);
                    if (response.body().isData() == true) {
                        Toast.makeText(SignInActvity.this, "签到成功", Toast.LENGTH_SHORT).show();
                        chongfu=true;
                        //获取网络签到记录
                        getmsg();
                    } else {
                        Toast.makeText(SignInActvity.this, "签到失败", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SignNowBean> call, Throwable t) {
                MyLog.e(TAG, "错误2222");
            }
        });
    }


    @OnClick({R.id.signin_iv_back, R.id.signin_iv_lianxuqiandaobeijing})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_iv_back:
                finish();
                break;
            case R.id.signin_iv_lianxuqiandaobeijing:
                if(chongfu){
                    Toast.makeText(SignInActvity.this, "今日已签到", Toast.LENGTH_SHORT).show();
                }else
                    signNow();//签到
                break;
        }
    }
}