package com.yongyi.financialinfo.util;

import com.yongyi.financialinfo.activity.UserZhuCheActivity;
import com.yongyi.financialinfo.bean.LoginPhoneYanzhengmaBean;
import com.yongyi.financialinfo.http.InterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    public static Retrofit retrofit;
    public static void init() {
                 retrofit = new Retrofit.Builder()
                .baseUrl(InterService.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public static void init(String url) {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
}
