package com.bw.movie.presenter.homepresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.homemodel.ComingModel;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:即将上映
 */
public class ComingPresenter extends BasePresenter<InData.InComing> implements InModel<ReleaseBean> {

    private final InData.InComing inComing;
    private final ComingModel comingModel;

    public ComingPresenter(InData.InComing onDada){
        comingModel = new ComingModel(this);
        inComing = onDada;
    }
    public void releDataPre(String sessionId, String userId,int page, int count) {
        comingModel.dataModel(sessionId,userId,page,count);
    }


    @Override
    public void onResult(ReleaseBean onData) {
        inComing.onDada(onData);
    }
}
