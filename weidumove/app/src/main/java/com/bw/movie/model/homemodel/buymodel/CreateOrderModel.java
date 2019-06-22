package com.bw.movie.model.homemodel.buymodel;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.CreateOrderBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:创建订单获取网络数据
 */
public class CreateOrderModel {

    public void dataOrderModel(String userId, String sessionId, String id, int selectedTableCoun, String sign) {
        int userId1=Integer.parseInt(userId);
        int movieid=Integer.parseInt(id);
        Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
        Flowable<CreateOrderBean> createOrder = retrofit.getCreateOrder(userId1, sessionId, movieid, selectedTableCoun, sign);
        createOrder.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSubscriber<CreateOrderBean>() {
                @Override
                public void onNext(CreateOrderBean createOrderBean) {
                    String message = createOrderBean.getMessage();
                    Log.i("sssss",message);
                    if(inModel!=null){
                        inModel.onResult(createOrderBean);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    //失败返回的信息
                    Log.i("sss",t.toString());
                }

                @Override
                public void onComplete() {

                }
            });

    }
    //定义接口
    private InModel inModel;

    public CreateOrderModel(InModel inModel) {
        this.inModel = inModel;
    }
}
