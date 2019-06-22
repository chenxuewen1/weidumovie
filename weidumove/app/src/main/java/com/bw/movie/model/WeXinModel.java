package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.WeXinBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:微信登录
 */
public class WeXinModel {

    public void codeDataModel(String code) {
        Log.i("ddd+code:",code);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.userUrl, Apiservice.class);
        Flowable<WeXinBean> weXin = retrofit.getWeXin(code);
        weXin.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSubscriber<WeXinBean>() {
                @Override
                public void onNext(WeXinBean weXinBean) {
                    //成功的结果
                    Log.i("ddd",weXinBean.getMessage());
                    if(inModel!=null){
                        inModel.onResult(weXinBean);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    //失败的结果
                }

                @Override
                public void onComplete() {

                }
            });
    }
    //定义接口
    private InModel inModel;

    public WeXinModel(InModel inModel) {
        this.inModel = inModel;
    }
}
