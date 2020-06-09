package com.yongyi.financialinfo.http;

import com.yongyi.financialinfo.bean.LoginPhoneYanzhengmaBean;
import com.yongyi.financialinfo.bean.LoginYanzhengmaBean;
import com.yongyi.financialinfo.bean.UserBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface InterService {


    String baseURL="http://api.yysc.online/";

    //登录
    @GET("/system/login/{phone}/{password}/{type}/{project}/{code}")
    Call<ResponseBody> login(@Path("phone") String phone,
                             @Path("password") String password,
                             @Path("type") int type,
                             @Path("project") String project,
                             @Path("code") String code);
    //获取图片验证码
    @GET("/system/sendVerify/")
    Call<LoginYanzhengmaBean> getImageYanzhengma();

    //获取手机验证码
    @FormUrlEncoded
    @POST("/system/sendCode")
    Call<LoginPhoneYanzhengmaBean> getPhoneYanzhengma(@Field("phone") String phone,
                                                      @Field("type") Integer type,
                                                      @Field("project") String project,
                                                      @Field("code") String code);
    //注册账号请求
    @FormUrlEncoded
    @POST("/system/register")
    Call<UserBean> postZhuce(@Field("phone") String phone,
                             @Field("password") String password,
                             @Field("confirmPassword") String confirmPassword,
                             @Field("code") String code,
                             @Field("type") int type,
                             @Field("project") String project);

}
