package com.bw.movie.presenter.homepresenter.buypresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.model.homemodel.SelectCinemaModel;

/*Time:2019/4/19
 *Author:chenxuewen
 *Description:选择影院
 */
public class SelectCinemaPresenter extends BasePresenter<InPresenter> implements  InModel<RecommendBean> {

    private final SelectCinemaModel selectCinemaModel;
    private final InPresenter inPresenter;

    public SelectCinemaPresenter(InPresenter onData){
        selectCinemaModel = new SelectCinemaModel(this);
        inPresenter = onData;
    }
    public void selectDataPre(String id) {
        selectCinemaModel.dataModel(id);
    }

    @Override
    public void onResult(RecommendBean onData) {
        inPresenter.onData(onData);
    }
}
