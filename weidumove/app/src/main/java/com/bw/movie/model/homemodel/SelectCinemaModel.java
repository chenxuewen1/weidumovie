package com.bw.movie.model.homemodel;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.CheckBean;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:选择影院
 */
public class SelectCinemaModel {

    public void dataModel(String id) {
        //强转
        int i = Integer.parseInt(id);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
        Flowable<RecommendBean> select = retrofit.getSelect(i);
        select.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<RecommendBean>() {
                    @Override
                    public void onNext(RecommendBean onData) {
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

    public SelectCinemaModel(InModel movieListener) {
        this.movieListener = movieListener;
    }
}
