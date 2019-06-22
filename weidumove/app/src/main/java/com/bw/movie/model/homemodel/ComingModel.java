package com.bw.movie.model.homemodel;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:热门电影获取网路数据
 */
public class ComingModel {

    public void dataModel(String sessionId, String userId, int page, int count) {
        //强转
        int i = Integer.parseInt(userId);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
        Flowable<ReleaseBean> release = retrofit.getComingMovie(i, sessionId, page, count);
        release.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<ReleaseBean>() {
                    @Override
                    public void onNext(ReleaseBean onData) {
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

    public ComingModel(InModel movieListener) {
        this.movieListener = movieListener;
    }
}
