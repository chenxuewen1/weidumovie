package com.bw.movie.presenter.cinema;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CinemaMoidById;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.model.cinema.CinemaMovieModel;

/*Time:2019/4/26
 *Author:chenxuewen
 *Description: 根据影院ID查询当前的电影列表
 */
public class CinemaMoviePresenter extends BasePresenter<InData.CinemaMoidIdinface> implements InModel<CinemaMoidById> {

    private final CinemaMovieModel movieModel;
    private final InData.CinemaMoidIdinface inPresenter;

    public CinemaMoviePresenter(InData.CinemaMoidIdinface onData){
        movieModel = new CinemaMovieModel(this);
        inPresenter = onData;
    }

    /**
     * 传递到m层
     * @param id
     */
    public void onDataPre(String id) {
        movieModel.dataModel(id);
    }
    @Override
    public void onResult(CinemaMoidById onData) {
        inPresenter.onDada(onData);
    }

}
