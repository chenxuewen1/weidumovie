package com.bw.movie.presenter.homepresenter.buypresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CreateOrderBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.model.homemodel.buymodel.CreateOrderModel;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:创建订单
 */
public class CreateOrderPresenter extends BasePresenter<InPresenter> implements InModel<CreateOrderBean> {

    private final CreateOrderModel orderModel;
    private final InPresenter inPresenter;

    public CreateOrderPresenter(InPresenter onData){
        orderModel = new CreateOrderModel(this);
        inPresenter = onData;
    }

    public void dataOrderPre(String userId, String sessionId, String id, int selectedTableCoun, String sign) {
        orderModel.dataOrderModel(userId,sessionId,id,selectedTableCoun,sign);
    }

    /**
     * inpresenter公共接口
     * @param onData
     */
    @Override
    public void onResult(CreateOrderBean onData) {
        inPresenter.onData(onData);
    }
}
