package com.bw.movie.presenter.homepresenter.buypresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.PayOrderBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.homemodel.buymodel.PayOrderModel;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:支付
 */
public class PayOrderPresenter extends BasePresenter<InData.PayOrder> implements InModel<PayOrderBean> {

    private final PayOrderModel payOrderModel;
    private final InData.PayOrder payOrder;

    public PayOrderPresenter(InData.PayOrder onData){
        payOrderModel = new PayOrderModel(this);
        payOrder = onData;
    }

    public void dataPayPre(int userId, String sessionId, int flag, String orderId) {
        payOrderModel.dataPayModel(userId,sessionId,flag,orderId);
    }

    @Override
    public void onResult(PayOrderBean onData) {
        payOrder.onDada(onData);
    }
}
