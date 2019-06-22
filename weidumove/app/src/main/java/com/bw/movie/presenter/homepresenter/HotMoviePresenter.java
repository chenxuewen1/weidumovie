package com.bw.movie.presenter.homepresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InModel;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.model.homemodel.HotMovieModel;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:热门电影
 */
public class HotMoviePresenter extends BasePresenter<InPresenter<ReleaseBean>> implements InModel<ReleaseBean>  {

    private final InPresenter inPresenter;
    private final HotMovieModel hotMovieModel;

    public HotMoviePresenter(InPresenter onData){
        hotMovieModel = new HotMovieModel(this);
        inPresenter = onData;
    }
    public void hotDataPre(String sessionId, String userId, int page, int count) {
        hotMovieModel.dataModel(sessionId,userId,page,count);
    }

    @Override
    public void onResult(ReleaseBean onData) {
        inPresenter.onData(onData);
    }
}
