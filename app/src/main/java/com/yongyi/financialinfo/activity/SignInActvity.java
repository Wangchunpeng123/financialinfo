package com.yongyi.financialinfo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
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

    long currentTimeMillis = System.currentTimeMillis();//获取系统时间 long类型
    private List<SignListBean.Data> list=new ArrayList<>();
    boolean monIsSignin;
    boolean tueIsSignin;
    boolean wedIsSignin;
    boolean thuIsSignin;
    boolean friIsSignin;
    boolean satIsSignin;
    boolean sunIsSignin;
    int cDays;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        RetrofitUtils.init();
        userId= SpSimpleUtils.getSp("userId",this,"LoginActivity");
        hassgin();
        getmsg();
        MyAdvertisementView myAdvertisementView = new MyAdvertisementView(this);
        qiandaoImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signNow();
                myAdvertisementView.showDialog();
                int count = 0;
                count++;

                if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期一")){
                    Ball1.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball1.setTextColor(getResources().getColor(R.color.titleWhite));
                    monIsSignin = true ;
                }
                if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期二")){
                    Ball2.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball2.setTextColor(getResources().getColor(R.color.titleWhite));
                    tueIsSignin = true ;
                }
                if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期三")){
                    Ball3.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball3.setTextColor(getResources().getColor(R.color.titleWhite));
                    wedIsSignin = true ;
                }
                if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期四")){
                    Ball4.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball4.setTextColor(getResources().getColor(R.color.titleWhite));
                    thuIsSignin = true ;
                }
                if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期五")){
                    Ball5.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball5.setTextColor(getResources().getColor(R.color.titleWhite));
                    friIsSignin = true ;
                }
                if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期六")){
                    Ball6.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball6.setTextColor(getResources().getColor(R.color.titleWhite));
                    satIsSignin = true ;
                }if (MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)).equals("星期日")){
                    Ball7.setBackgroundResource(R.drawable.button_circle_shape);
                    Ball7.setTextColor(getResources().getColor(R.color.titleWhite));
                    sunIsSignin = true ;
                }
                SharedPreferences sp = getSharedPreferences("mData",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();//获取编辑器

                continuousDays.setText(count+"");
                editor.putInt("continuousDays",count);
                editor.putString("week", MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)));
                MyLog.e(TAG,String.valueOf(count)+"#######"+ MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)));
                editor.commit();
                if (cDays == 1){
                    jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
                }
                if (cDays == 2){
                    jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
                    jiangbei2Img.setImageResource(R.mipmap.pic_yiqiandao1);
                }
                if (cDays > 2){
                    jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
                    jiangbei2Img.setImageResource(R.mipmap.pic_yiqiandao1);
                    jiangbei3Img.setImageResource(R.mipmap.pic_yiqiandao1);
                }
                qiandaoImgButton.setEnabled(false);
            }

        });
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //用户签到历史,默认只会返回当月签到记录
    private void getmsg(){
        Call<SignListBean> call = RetrofitUtils.retrofit.create(InterService.class).getSignList(Long.valueOf(userId));
        call.enqueue(new Callback<SignListBean>() {
            @Override
            public void onResponse(Call<SignListBean> call, Response<SignListBean> response) {
                MyLog.e(TAG,response.toString());
                if (response.body() != null && response.body().getSuccess() == true){
                    list.addAll(response.body().getData());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            allDays.setText(list.size()+"");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<SignListBean> call, Throwable t) {
                MyLog.e(TAG,"什么情况");
            }
        });
    }




    //今日是否已经签到
    private void hassgin(){
        Call<HasSignBean> call = RetrofitUtils.retrofit.create(InterService.class).gethasSign(4186);
        call.enqueue(new Callback<HasSignBean>() {
            @Override
            public void onResponse(Call<HasSignBean> call, Response<HasSignBean> response) {
                MyLog.e(TAG,response.toString());
                if (response.body() != null && response.body().isSuccess() == true){

                    if (response.body().isData() == false){

                        Toast.makeText(SignInActvity.this,"今日未签到",Toast.LENGTH_LONG).show();
                        qiandaoImgButton.setEnabled(true);
                        weiqiandaoInitView();
                        }else {
                        Toast.makeText(SignInActvity.this,"今日已签到",Toast.LENGTH_LONG).show();
                        qiandaoImgButton.setEnabled(false);
                        initView();
                        MyLog.e(TAG,"是你吗"+ MyUtil.longToDate1(currentTimeMillis)+"星期"+ MyUtil.dateToyesterdayweek(MyUtil.longToDate1(currentTimeMillis)));
                    }
                }
            }
            @Override
            public void onFailure(Call<HasSignBean> call, Throwable t) {
                MyLog.e(TAG,"错误111");
            }
        });
    }
    //今日签到
    private void signNow(){
        Call<SignNowBean> call = RetrofitUtils.retrofit.create(InterService.class).signNow(4186);
        call.enqueue(new Callback<SignNowBean>() {
            @Override
            public void onResponse(Call<SignNowBean> call, Response<SignNowBean> response) {
                if (response.body() != null && response.body().isSuccess() == true){
                    response.body().setData(true);
                    if (response.body().isData() == true){

                    }else {
                        Toast.makeText(SignInActvity.this,"签到失败1",Toast.LENGTH_SHORT).show();
                    }

                }
            }
            @Override
            public void onFailure(Call<SignNowBean> call, Throwable t) {
                MyLog.e(TAG,"错误2222");
            }
        });
    }
    private void initView(){
        SharedPreferences sharedPreferences = getSharedPreferences("mData",MODE_PRIVATE);

        String week = sharedPreferences.getString("week","");
        //cDays = sharedPreferences.getInt("continuousDays",0);

        if (week.equals("星期一")){
            sharedPreferences.edit().remove("week");
            week = "星期一";

        }

        if (week.equals("星期一")){
            Ball1.setBackgroundResource(R.drawable.button_circle_shape);
            Ball1.setTextColor(getResources().getColor(R.color.titleWhite));

            Ball2.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball2.setTextColor(getResources().getColor(R.color.ball_text));

            Ball3.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball3.setTextColor(getResources().getColor(R.color.ball_text));

            Ball4.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball4.setTextColor(getResources().getColor(R.color.ball_text));

            Ball5.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball5.setTextColor(getResources().getColor(R.color.ball_text));

            Ball6.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball6.setTextColor(getResources().getColor(R.color.ball_text));

            Ball7.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball7.setTextColor(getResources().getColor(R.color.ball_text));
            if (sunIsSignin = false){
                cDays = 1;
            }

        }
        if (week.equals("星期二")){
            Ball2.setBackgroundResource(R.drawable.button_circle_shape);
            Ball2.setTextColor(getResources().getColor(R.color.titleWhite));
            if (monIsSignin = false){
                cDays = 1;
            }
        }
        if (week.equals("星期三")){
            Ball3.setBackgroundResource(R.drawable.button_circle_shape);
            Ball3.setTextColor(getResources().getColor(R.color.titleWhite));
            if (tueIsSignin = false){
                cDays = 1;
            }
        }
        if (week.equals("星期四")){
            Ball4.setBackgroundResource(R.drawable.button_circle_shape);
            Ball4.setTextColor(getResources().getColor(R.color.titleWhite));
            if (wedIsSignin = false){
                cDays = 1;
            }
        }
        if (week.equals("星期五")){
            Ball5.setBackgroundResource(R.drawable.button_circle_shape);
            Ball5.setTextColor(getResources().getColor(R.color.titleWhite));
            if (thuIsSignin = false){
                cDays = 1;
            }
        }
        if (week.equals("星期六")){
            Ball6.setBackgroundResource(R.drawable.button_circle_shape);
            Ball6.setTextColor(getResources().getColor(R.color.titleWhite));
            if (friIsSignin = false){
                cDays = 1;
            }
        }if (week.equals("星期日")){
            Ball7.setBackgroundResource(R.drawable.button_circle_shape);
            Ball7.setTextColor(getResources().getColor(R.color.titleWhite));
            if (satIsSignin = false){
                cDays = 1;
            }
        }

        continuousDays.setText(cDays+"");
        MyLog.e(TAG,String.valueOf(cDays)+"#######"+week);

        if (cDays == 1){
            jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
        }
        if (cDays == 2){
            jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
            jiangbei2Img.setImageResource(R.mipmap.pic_yiqiandao1);
        }
        if (cDays > 2){
            jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
            jiangbei2Img.setImageResource(R.mipmap.pic_yiqiandao1);
            jiangbei3Img.setImageResource(R.mipmap.pic_yiqiandao1);
        }
    }
    private void weiqiandaoInitView(){
        SharedPreferences sharedPreferences = getSharedPreferences("mData",MODE_PRIVATE);

        String week = sharedPreferences.getString("week","");
        cDays = sharedPreferences.getInt("continuousDays",0);

        allDays.setText(cDays +"");


        if (week.equals("星期一")){
            sharedPreferences.edit().remove("week");
            week = "星期一";

        }

        if (week.equals("星期一")){
            Ball1.setBackgroundResource(R.drawable.button_circle_shape);
            Ball1.setTextColor(getResources().getColor(R.color.titleWhite));

            Ball2.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball2.setTextColor(getResources().getColor(R.color.ball_text));

            Ball3.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball3.setTextColor(getResources().getColor(R.color.ball_text));

            Ball4.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball4.setTextColor(getResources().getColor(R.color.ball_text));

            Ball5.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball5.setTextColor(getResources().getColor(R.color.ball_text));

            Ball6.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball6.setTextColor(getResources().getColor(R.color.ball_text));

            Ball7.setBackgroundResource(R.drawable.button_circle_shape1);
            Ball7.setTextColor(getResources().getColor(R.color.ball_text));
            if (sunIsSignin = false){
                cDays = 0;
            }

        }
        if (week.equals("星期二")){
            Ball2.setBackgroundResource(R.drawable.button_circle_shape);
            Ball2.setTextColor(getResources().getColor(R.color.titleWhite));
            if (monIsSignin = false){
                cDays = 0;
            }
        }
        if (week.equals("星期三")){
            Ball3.setBackgroundResource(R.drawable.button_circle_shape);
            Ball3.setTextColor(getResources().getColor(R.color.titleWhite));
            if (tueIsSignin = false){
                cDays = 0;
            }
        }
        if (week.equals("星期四")){
            Ball4.setBackgroundResource(R.drawable.button_circle_shape);
            Ball4.setTextColor(getResources().getColor(R.color.titleWhite));
            if (wedIsSignin = false){
                cDays = 0;
            }
        }
        if (week.equals("星期五")){
            Ball5.setBackgroundResource(R.drawable.button_circle_shape);
            Ball5.setTextColor(getResources().getColor(R.color.titleWhite));
            if (thuIsSignin = false){
                cDays = 0;
            }
        }
        if (week.equals("星期六")){
            Ball6.setBackgroundResource(R.drawable.button_circle_shape);
            Ball6.setTextColor(getResources().getColor(R.color.titleWhite));
            if (friIsSignin = false){
                cDays = 0;
            }
        }if (week.equals("星期日")){
            Ball7.setBackgroundResource(R.drawable.button_circle_shape);
            Ball7.setTextColor(getResources().getColor(R.color.titleWhite));
            if (satIsSignin = false){
                cDays = 0;
            }
        }

        continuousDays.setText(cDays+"");
        MyLog.e(TAG,String.valueOf(cDays)+"#######"+week);

        if (cDays == 1){
            jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
        }
        if (cDays == 2){
            jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
            jiangbei2Img.setImageResource(R.mipmap.pic_yiqiandao1);
        }
        if (cDays > 2){
            jiangbei1Img.setImageResource(R.mipmap.pic_yiqiandao1);
            jiangbei2Img.setImageResource(R.mipmap.pic_yiqiandao1);
            jiangbei3Img.setImageResource(R.mipmap.pic_yiqiandao1);
        }
    }




}