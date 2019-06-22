package com.bw.movie.presenter.cinema;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.cinema.NearbyModel;

/*Time:2019/4/19
 *Author:chenxuewen
 *Description:附近影院
 */
public class NearbyPresenter extends BasePresenter<InData.RecommendCinema> implements InModel<RecommendBean> {

    private final NearbyModel nearbyModel;
    private final InData.RecommendCinema recommendCinema;

    public NearbyPresenter(InData.RecommendCinema onData){
        nearbyModel = new NearbyModel(this);
        recommendCinema = onData;
    }

    public void dataNearbyPre(String userId, String sessionId, int page, String longitude, String latitude) {
        nearbyModel.dataModel(userId,sessionId,page,longitude,latitude);
    }

    @Override
    public void onResult(RecommendBean onData) {
        recommendCinema.onDada(onData);
    }
}
