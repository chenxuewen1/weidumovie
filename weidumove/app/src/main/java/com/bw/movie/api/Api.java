package com.bw.movie.api;

/*Time:2019/4/10
 *Author:chenxuewen
 *Description:地址接口
 */
public class Api {
    //内网 172.17.8.100
    //外网 mobile.bwstudent.com
    //http://172.17.8.100/movieApi/user/v1/registerUser
    //private static String path="http://172.17.8.100/";
    private static String path="http://mobile.bwstudent.com/";
    //登录 注册
    public static final String userUrl=path+"movieApi/user/v1/";
    //首页 分类 详情 查看评论 发表评论 选择影院 电影档期 创建订单 根据影院id查询电影列表
    public static final String movieUrl=path+"movieApi/movie/v1/";
    //影院
    public static final String cinemaUrl=path+"movieApi/cinema/v1/";

}
