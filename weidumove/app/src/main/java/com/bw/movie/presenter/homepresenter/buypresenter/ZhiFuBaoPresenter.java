package com.bw.movie.presenter.homepresenter.buypresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.ZhiFuBaoBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.homemodel.buymodel.ZhiFuBaoModel;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:支付宝支付
 */
public class ZhiFuBaoPresenter extends BasePresenter<InData.ZhiFuBao> implements InModel<ZhiFuBaoBean> {

    private final ZhiFuBaoModel zhiFuBaoModel;
    private final InData.ZhiFuBao zhiFuBao;

    public ZhiFuBaoPresenter(InData.ZhiFuBao onData){
        zhiFuBaoModel = new ZhiFuBaoModel(this);
        zhiFuBao = onData;
    }

    /**
     * 向m层传递数据
     * @param userId
     * @param sessionId
     * @param flag
     * @param orderId
     */
    public void dataZhiPayPre(int userId, String sessionId, int flag, String orderId) {
        zhiFuBaoModel.dataPayModel(userId,sessionId,flag,orderId);
    }
    @Override
    public void onResult(ZhiFuBaoBean onData) {
        zhiFuBao.onDada(onData);
    }



}
