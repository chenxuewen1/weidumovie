package com.bw.movie.presenter.cinema;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.cinema.RecommendModel;

/*Time:2019/4/19
 *Author:chenxuewen
 *Description:推荐影院
 */
public class RecommendPresenter extends BasePresenter<InData.RecommendCinema> implements InModel<RecommendBean> {

    private final RecommendModel recommendModel;
    private final InData.RecommendCinema recommendCinema;

    public RecommendPresenter (InData.RecommendCinema onData){
        recommendModel = new RecommendModel(this);
        recommendCinema = onData;
    }

    public void dataRecommendPre(String userId, String sessionId, int page) {
        recommendModel.dataModel(userId,sessionId,page);
    }

    @Override
    public void onResult(RecommendBean onData) {
        recommendCinema.onDada(onData);
    }
}
