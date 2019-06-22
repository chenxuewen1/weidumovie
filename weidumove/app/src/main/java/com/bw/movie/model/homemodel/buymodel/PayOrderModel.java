package com.bw.movie.model.homemodel.buymodel;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.CreateOrderBean;
import com.bw.movie.bean.PayOrderBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description: 支付
 */
public class PayOrderModel {
    public void dataPayModel(int userId, String sessionId, int flag, String orderId) {
        Log.i("sss",userId+" "+sessionId+" "+flag+" "+orderId);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
        Flowable<PayOrderBean> pay = retrofit.getPay(userId, sessionId, flag, orderId);
        pay.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSubscriber<PayOrderBean>() {
                @Override
                public void onNext(PayOrderBean payOrderBean) {
                    Log.i("scuon",payOrderBean.getMessage());
                    if(inModel!=null){
                        inModel.onResult(payOrderBean);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    Log.i("error",t.toString());
                }

                @Override
                public void onComplete() {

                }
            });
    }
    //定义接口
    private InModel inModel;

    public PayOrderModel(InModel inModel) {
        this.inModel = inModel;
    }
}
