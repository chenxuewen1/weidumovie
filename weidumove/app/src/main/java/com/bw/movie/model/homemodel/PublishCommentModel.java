package com.bw.movie.model.homemodel;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.CheckBean;
import com.bw.movie.bean.PublishCommentBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:发表评论获取数据
 */
public class PublishCommentModel {
    public void dataModel(String sessionId, String userId1, String movieId1,String commentContent) {
        //强转
        int movieId = Integer.parseInt(movieId1);
        int userId = Integer.parseInt(userId1);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
        Flowable<PublishCommentBean> publishComment = retrofit.getPublishComment(userId, sessionId, movieId, commentContent);
        publishComment.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<PublishCommentBean>() {
                    @Override
                    public void onNext(PublishCommentBean onData) {
                        //解析出来的数据
                        if(movieListener!=null){
                            movieListener.onResult(onData);
                            //Log.i("aaa",onData.getMessage());
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

    public PublishCommentModel(InModel movieListener) {
        this.movieListener = movieListener;
    }
}
