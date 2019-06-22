package com.bw.movie.api;

import com.bw.movie.bean.CheckBean;
import com.bw.movie.bean.CinemaMoidById;
import com.bw.movie.bean.CommentBean;
import com.bw.movie.bean.CreateOrderBean;
import com.bw.movie.bean.DetailBean;
import com.bw.movie.bean.FindUserBean;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.PayOrderBean;
import com.bw.movie.bean.PublishCommentBean;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.ScreeningHall;
import com.bw.movie.bean.WeXinBean;
import com.bw.movie.bean.ZhiFuBaoBean;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/*Time:2019/4/10
 *Author:chenxuewen
 *Description:地址拼接
 */
public interface Apiservice {
    //注册
    @POST("registerUser")
    @FormUrlEncoded
    Flowable<RegisterBean> getReg(@Field("nickName") String nickName, @Field("phone")String phone, @Field("pwd")String pwd, @Field("pwd2")String pwd2, @Field("sex")int sex, @Field("birthday")String birthday, @Field("email")String email);
    //登录
    @POST("login")
    @FormUrlEncoded
    Flowable<LoginBean> getLogin(@Field("phone") String phone,@Field("pwd") String pwd);
    //微信登录
    @POST("weChatBindingLogin")
    @FormUrlEncoded
    Flowable<WeXinBean> getWeXin(@Field("code") String code);

    //查询热门电影
    @GET("findHotMovieList")
    Flowable<ReleaseBean> getHotMovie(@Header("userId")int userId,@Header("sessionId")String sessionId,@Query("page") int page,@Query("count") int count);
    //查询正在热映 findReleaseMovieList
    @GET("findReleaseMovieList")
    Flowable<ReleaseBean> getReleaseMovie(@Header("userId")int userId, @Header("sessionId")String sessionId, @Query("page") int page, @Query("count") int count);
    //查询即将上映 findComingSoonMovieList
    @GET("findComingSoonMovieList")
    Flowable<ReleaseBean> getComingMovie(@Header("userId")int userId, @Header("sessionId")String sessionId, @Query("page") int page, @Query("count") int count);
    //查看电影详情 findMoviesDetail findMoviesById
    @GET("findMoviesDetail")
    Flowable<DetailBean> getDetail(@Header("userId")int userId, @Header("sessionId")String sessionId,@Query("movieId")int movieId);
    //根据id查询详情
    @GET("findMoviesById")
    Flowable<CheckBean> getDetailById(@Header("userId")int userId, @Header("sessionId")String sessionId, @Query("movieId")int movieId);
    //查看评论
    @GET("findAllMovieComment")
    Flowable<CommentBean> getComment(@Header("userId")int userId, @Header("sessionId")String sessionId, @Query("movieId")int movieId, @Query("page")int page, @Query("count")int count);
    //发表评论
    @POST("verify/movieComment")
    @FormUrlEncoded
    Flowable<PublishCommentBean> getPublishComment(@Header("userId")int userId, @Header("sessionId")String sessionId, @Field("movieId") int movieId, @Field("commentContent") String commentContent);
    //选择影院 findCinemasListByMovieId
    @GET("findCinemasListByMovieId")
    Flowable<RecommendBean> getSelect(@Query("movieId") int movieId);
    // 电影档期 findMovieScheduleList
    @GET("findMovieScheduleList")
    Flowable<ScreeningHall> getScreening(@Query("movieId") int movieId,@Query("cinemasId") int cinemasId);
    //创建订单 verify/buyMovieTicket
    @POST("verify/buyMovieTicket")
    @FormUrlEncoded
    Flowable<CreateOrderBean> getCreateOrder(@Header("userId")int userId, @Header("sessionId")String sessionId, @Field("scheduleId") int scheduleId, @Field("amount") int amount,@Field("sign")String sign);
    //支付方式 微信
    @POST("verify/pay")
    @FormUrlEncoded
    Flowable<PayOrderBean> getPay(@Header("userId")int userId, @Header("sessionId")String sessionId, @Field("payType") int payType, @Field("orderId") String orderId);
    //支付方式 支付宝
    @POST("verify/pay")
    @FormUrlEncoded
    Flowable<ZhiFuBaoBean> getZhiFuBao(@Header("userId")int userId, @Header("sessionId")String sessionId, @Field("payType") int payType, @Field("orderId") String orderId);

    //推荐影院 findRecommendCinemas
    @GET("findRecommendCinemas")
    Flowable<RecommendBean> getRecommend(@Header("userId")int userId, @Header("sessionId")String sessionId, @Query("page")int page, @Query("count")int count);
    //附近影院 findRecommendCinemas
    @GET("findNearbyCinemas")
    Flowable<RecommendBean> getNearby(@Header("userId")int userId, @Header("sessionId")String sessionId,@Query("longitude") String longitude,@Query("latitude") String latitude, @Query("page")int page, @Query("count")int count);
    //查询用户信息 verify/findUserHomeInfo
    @GET("verify/findUserHomeInfo")
    Flowable<FindUserBean> getFindUser(@Header("userId")int userId, @Header("sessionId")String sessionId);
    //根据影院id查询当前电影列表
    @GET("findMovieListByCinemaId")
    Flowable<CinemaMoidById> getCineamMovieId(@Query("cinemaId")int cinemaId);

}
