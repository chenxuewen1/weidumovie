package com.bw.movie.presenter.homepresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CheckBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.homemodel.CheckModel;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:查看电影详情
 */
public class CheckPresenter extends BasePresenter<InData.DetailMovieByID> implements InModel<CheckBean> {

    private final InData.DetailMovieByID movieByID;
    private final CheckModel checkModel;

    public CheckPresenter(InData.DetailMovieByID onDada){
        checkModel = new CheckModel(this);
        movieByID = onDada;
    }
    public void checkByIdDataPre(String sessionId, String userId,int page) {
        checkModel.dataModel(sessionId,userId,page);
    }


    @Override
    public void onResult(CheckBean onData) {
        movieByID.onDada(onData);
    }
}
