package com.bw.movie.model.cinema;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.CheckBean;
import com.bw.movie.bean.CinemaMoidById;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:根据影院ID查询当前的电影列表
 */
public class CinemaMovieModel {

    public void dataModel(String id) {
        //强转
        int i = Integer.parseInt(id);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
        Flowable<CinemaMoidById> cineamMovieId = retrofit.getCineamMovieId(i);
        cineamMovieId.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<CinemaMoidById>() {
                    @Override
                    public void onNext(CinemaMoidById onData) {
                            //解析出来的数据
                            if(movieListener!=null){
                                movieListener.onResult(onData);
                            }
                    }

                    @Override
                    public void onError(Throwable t) {
                        //失败调用的方法
                        Log.i("sss",t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * 定义接口
     */
    private InModel movieListener;

    public CinemaMovieModel(InModel movieListener) {
        this.movieListener = movieListener;
    }
}
