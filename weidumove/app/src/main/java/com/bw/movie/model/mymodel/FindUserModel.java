package com.bw.movie.model.mymodel;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.FindUserBean;
import com.bw.movie.bean.WeXinBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:查询用户信息
 */
public class FindUserModel {

    public void dataFindModel(String userId1, String sessionId) {
        Log.i("ddd+code:",sessionId);
        int userId=Integer.parseInt(userId1);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.userUrl, Apiservice.class);
        Flowable<FindUserBean> findUser = retrofit.getFindUser(userId, sessionId);
        findUser.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSubscriber<FindUserBean>() {
                @Override
                public void onNext(FindUserBean weXinBean) {
                    //成功的结果
                    Log.i("ddd",weXinBean.getMessage());
                    if(inModel!=null){
                        inModel.onResult(weXinBean);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    //失败的结果
                    Log.i("errror",t.toString());
                }

                @Override
                public void onComplete() {

                }
            });
    }
    //定义接口
    private InModel inModel;

    public FindUserModel(InModel inModel) {
        this.inModel = inModel;
    }
}
