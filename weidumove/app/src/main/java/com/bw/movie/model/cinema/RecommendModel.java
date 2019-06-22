package com.bw.movie.model.cinema;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:推荐影院获取网路数据
 */
public class RecommendModel {


    public void dataModel(String userId, String sessionId, int page) {
        //强转
        int i = Integer.parseInt(userId);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.cinemaUrl, Apiservice.class);
        Flowable<RecommendBean> recommend = retrofit.getRecommend(i, sessionId, page, 10);
        recommend.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<RecommendBean>() {
                    @Override
                    public void onNext(RecommendBean onData) {
                        if(movieListener!=null){
                            movieListener.onResult(onData);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
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

    public RecommendModel(InModel movieListener) {
        this.movieListener = movieListener;
    }

}
