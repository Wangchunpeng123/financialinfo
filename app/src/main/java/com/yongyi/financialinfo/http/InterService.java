package com.yongyi.financialinfo.http;

import com.yongyi.financialinfo.bean.HangqingBean;
import com.yongyi.financialinfo.bean.HasSignBean;
import com.yongyi.financialinfo.bean.LoginPhoneYanzhengmaBean;
import com.yongyi.financialinfo.bean.LoginYanzhengmaBean;
import com.yongyi.financialinfo.bean.ShequPinglunBean;
import com.yongyi.financialinfo.bean.ShequPinglunMsgBean;
import com.yongyi.financialinfo.bean.ShequRemenGuanzhuBean;
import com.yongyi.financialinfo.bean.ShequRemenSsBean;
import com.yongyi.financialinfo.bean.ShequRemenTuijianGuanzhuBean;
import com.yongyi.financialinfo.bean.ShouyeJiaoyisuoBean;
import com.yongyi.financialinfo.bean.ShouyeKuaixunBean;
import com.yongyi.financialinfo.bean.ShouyeNewBean;
import com.yongyi.financialinfo.bean.SignListBean;
import com.yongyi.financialinfo.bean.SignNowBean;
import com.yongyi.financialinfo.bean.SuccessBean;
import com.yongyi.financialinfo.bean.UserBean;
import com.yongyi.financialinfo.bean.User;
import com.yongyi.financialinfo.bean.UserTalkBean;
import com.yongyi.financialinfo.bean.WoHudongBean;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
    Call<ShouyeKuaixunBean> getKuaixun(@Query("limit") Integer limit);

    //行情
    @GET("ticks/{itemName}")
    Call<List<HangqingBean>> getHangqing(@Path("itemName") String itemName);

    //获取推荐说说列表
    @GET("user/talk/getRecommandTalk/")
    Call<ShequRemenSsBean> getShuoShuoTuijian(@Query("project") String project,
                                              @Query("pageNumber") int pageNumber,
                                              @Query("pageSize") int pageSize);

    //获取用户关注/粉丝列表
    @GET("user/follow/getUserFollowList")
    Call<ShequRemenGuanzhuBean> getGuanzhu(@Query("userId") long userId,
                                           @Query("type") int type,
                                           @Query("pageNumber") int pageNumber,
                                           @Query("pageSize") int pageSize);

    //关注/取关用户
    @FormUrlEncoded
    @POST("user/follow/follow/")
    Call<ResponseBody> setGuanzhu(@Field("userId") long userId,
                                  @Field("followerId") long followerId,
                                  @Field("isFollow") boolean isFollow);



    //设置点赞评论转发

    @PUT("user/userTalk/userTalkOperation")
    Call<ResponseBody> dianZan(@Query("userId") long userId,
                               @Query("talkId") long talkId,
                               @Query("type") int type);

    //获取推荐关注list
    @GET("user/follow/getRecommandUserList")
    Call<ShequRemenTuijianGuanzhuBean> getTuijianGuanzhu(@Query("userId") long userId);

    //给说说评论
    @FormUrlEncoded
    @POST("/user/talk/commentTalk")
    Call<LoginPhoneYanzhengmaBean> postPinglun(@Field("userId") long userId,
                                   @Field("talkId") long talkId,
                                   @Field("content") String content);

    //获取说说评论列表
    @POST("/user/talk/getCommentList/")
    Call<ShequPinglunMsgBean> postPinglunList(@Body ShequPinglunBean bean);

    //发表说说
    @FormUrlEncoded
    @POST("/user/talk/publishTalk")
    Call<SuccessBean> postShuoshuo(@Field("userId") String userId,
                                   @Field("content") String content,
                                   @Field("picture") String picture);
    //打包图片
    @Multipart
    @POST("upload")
    Call<ResponseBody> upload(@Part MultipartBody.Part file);

    //获取与我相关
    @POST("user/talk/getTalkList/{userId}")
    Call<ShequRemenSsBean> getYuwoxiangguan(@Path("userId") long userId,
                                        @Body UserTalkBean talkQueryCondition);

    //更新用户信息
    @PUT("/user/personal/updateUser")
    Call<ResponseBody> postHeadIv(@Body User bean);

    //获取用户信息
    @FormUrlEncoded
    @POST("/user/personal/queryUser")
    Call<UserBean> getUserMsg(@Field("userId") Long userId);


    //获取与我相关
    @GET("user/personal/userMessage")
    Call<WoHudongBean> getUserXiaoxi(@Query("userId") long userId);

    //用户签到历史,默认只会返回当月签到记录
    @GET("/user/sign/getSignList")
    Call<SignListBean> getSignList(@Query("userId") long userId);

    //今日是否已经签到
    @GET("/user/sign/hasSign")
    Call<HasSignBean> gethasSign(@Query("userId") long userId);

    //今日签到
    @POST("/user/sign/signNow")
    Call<SignNowBean> signNow(@Query("userId") long userId);

    //今日是否已经签到
    @GET("/news/getNewListByProject.do")
    Call<ShouyeJiaoyisuoBean> getJiaoyisuo(@Query("project") String project,
                                           @Query("pageNumber") int pageNumber,
                                           @Query("pageSize") int pageSize);
    //重置密码
    @FormUrlEncoded
    @POST("/system/resetPassword")
    Call<ResponseBody> resetPassword(@Field("phone") String phone,
                                     @Field("newPassword") String newPassword,
                                     @Field("confirmPassword") String confirmPassword,
                                     @Field("code") String code,
                                     @Field("project") String project);
}

