package com.yongyi.financialinfo.http;

import com.yongyi.financialinfo.bean.LoginYanzhengmaBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterService {

    String baseURL1="https://www.wanandroid.com/article/list/0/";
    String baseURL="http://api.yysc.online/";

    @GET("json?cid=60")
    Call<ResponseBody> getData();

    @GET("/system/sendVerify/")
    Call<LoginYanzhengmaBean> getImageYanzhengma();

}
