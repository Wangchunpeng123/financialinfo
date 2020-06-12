package com.yongyi.financialinfo.http;

import com.yongyi.financialinfo.bean.HangqingBean;
import com.yongyi.financialinfo.bean.LoginPhoneYanzhengmaBean;
import com.yongyi.financialinfo.bean.LoginYanzhengmaBean;
import com.yongyi.financialinfo.bean.ShouyeNewBean;
import com.yongyi.financialinfo.bean.UserBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InterService {

    String baseURL="http://api.yysc.online/";

    //登录-登录
    @GET("system/login")
    Call<UserBean> login(@Query("phone") String phone,
                             @Query("password") String password,
                             @Query("type") Integer type,
                             @Query("project") String project);

    //登录-获取图片验证码
    @GET("/system/sendVerify/")
    Call<LoginYanzhengmaBean> getImageYanzhengma();

    //登录-获取手机验证码
    @FormUrlEncoded
    @POST("/system/sendCode")
    Call<LoginPhoneYanzhengmaBean> getPhoneYanzhengma(@Field("phone") String phone,
                                                      @Field("type") Integer type,
                                                      @Field("project") String project,
                                                      @Field("code") String code);
    //登录-注册账号请求
    @FormUrlEncoded
    @POST("/system/register")
    Call<UserBean> postZhuce(@Field("phone") String phone,
                             @Field("password") String password,
                             @Field("confirmPassword") String confirmPassword,
                             @Field("code") String code,
                             @Field("type") int type,
                             @Field("project") String project);

    //首页-获取新闻
    @GET("/news/getNewListByProject.do")
    Call<ShouyeNewBean> getNews(@Query("pageSize") Integer pageSize,
                                @Query("pageNum") Integer pageNum,
                                @Query("project") String project);

    //首页-财经数据
    @GET("/admin/getFinanceAffairs")
    Call<ResponseBody> getFinance(@Query("pageSize") Integer pageSize,
                                  @Query("pageNum") Integer pageNum,
                               @Query("date") String date);

    //首页-实时快讯
    @GET("list/")
    Call<ResponseBody> getKuaixun(@Query("limit") Integer limit);

    //行情
    @GET("ticks/{itemName}")
    Call<List<HangqingBean>> getHangqing(@Path("itemName") String itemName);


}
