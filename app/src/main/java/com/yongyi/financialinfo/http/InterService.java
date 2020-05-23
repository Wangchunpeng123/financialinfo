package com.yongyi.financialinfo.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface InterService {

    String baseURL="https://www.wanandroid.com/article/list/0/";
    String baseURL1="http://192.168.1.11/";
    @GET("json?cid=60")
    Call<ResponseBody> getData();

    @GET("getdata.json")
    Call<ResponseBody> getMyData();
}
