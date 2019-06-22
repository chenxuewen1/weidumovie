package com.bw.movie.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.WeXinBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.model.WeXinModel;

/*Time:2019/4/22
 *Author:chenxuewen
 *Description:微信登录
 */
public class WeXinPresenter extends BasePresenter<InPresenter<WeXinBean>> implements InModel<WeXinBean> {

    private final WeXinModel weXinModel;
    private final InPresenter inPresenter;

    public WeXinPresenter (InPresenter onData){
        weXinModel = new WeXinModel(this);
        inPresenter = onData;
    }

    public void codeDataPre(String code) {
        weXinModel.codeDataModel(code);
    }

    /**
     * 传入公共接口inpresenter
     * @param onData
     */
    @Override
    public void onResult(WeXinBean onData) {
        inPresenter.onData(onData);
    }
}
