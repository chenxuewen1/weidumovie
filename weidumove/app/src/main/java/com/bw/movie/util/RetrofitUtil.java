package com.bw.movie.util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:封装单列模式
 */
public class RetrofitUtil {
    public static RetrofitUtil retrofitUtil;
    private RetrofitUtil(){}
    public static RetrofitUtil getInstance(){
        if(retrofitUtil==null){
            synchronized (RetrofitUtil.class){
                if(retrofitUtil==null){
                    retrofitUtil=new RetrofitUtil();
                }
            }
        }
        return retrofitUtil;
    }
    //retrofit
    public Retrofit getRetroFit(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
    //
    public <T> T getRetrofit(String url,Class<T> service){
        Retrofit retroFit = getRetroFit(url);
        T t = retroFit.create(service);
        return t;
    }
}
