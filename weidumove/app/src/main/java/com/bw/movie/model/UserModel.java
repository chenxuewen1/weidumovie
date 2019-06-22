package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.api.Api;
import com.bw.movie.api.Apiservice;
import com.bw.movie.bean.CinemaMoidById;
import com.bw.movie.bean.CommentBean;
import com.bw.movie.bean.DetailBean;
import com.bw.movie.bean.HotMovieBean;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.ScreeningHall;
import com.bw.movie.interfac.InModel;
import com.bw.movie.util.RetrofitUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:获取网络数据
 */
public class UserModel {
    public void dataModel(JSONArray data,int page) throws JSONException {
        switch (page){
            case 1:
                //登录
                JSONObject jsonObject = data.getJSONObject(0);
                String phone = jsonObject.getString("phone");
                String pwd = jsonObject.getString("pwd");
                Apiservice retrofit = RetrofitUtil.getInstance().getRetrofit(Api.userUrl, Apiservice.class);
                Flowable<LoginBean> login = retrofit.getLogin(phone, pwd);
                initData(login);
                break;
            case 2:
                //***************************注册********************************
                JSONObject jsonObject1 = data.getJSONObject(0);
                String name = jsonObject1.getString("name");
                String regphone = jsonObject1.getString("phone");
                String regpwd = jsonObject1.getString("pwd");
                String regpwd2 = jsonObject1.getString("pwd2");
                int regflag = Integer.parseInt(jsonObject1.getString("flag"));
                String regdate = jsonObject1.getString("date");
                String regemail = jsonObject1.getString("email");
                Apiservice retrofit1 = RetrofitUtil.getInstance().getRetrofit(Api.userUrl, Apiservice.class);
                Flowable<RegisterBean> reg = retrofit1.getReg(name, regphone, regpwd,regpwd2, regflag, regdate, regemail);
                initData(reg);
                break;
            case 3:
                //****************************详情******************************
                JSONObject detail = data.getJSONObject(0);
                String detailsessionId = detail.getString("sessionId");
                int detailuserId = Integer.parseInt(detail.getString("userId"));
                int detailid = Integer.parseInt(detail.getString("id"));
                //Log.i("ddd",detail+"");
                Apiservice retrofit2 = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
                Flowable<DetailBean> detail1 = retrofit2.getDetail(detailuserId, detailsessionId, detailid);
                initData(detail1);
                break;
            case 4:
                //****************************查看评论******************************
                JSONObject comment = data.getJSONObject(0);
                String commsessionId = comment.getString("sessionId");
                int commuserId = Integer.parseInt(comment.getString("userId"));
                int commid = Integer.parseInt(comment.getString("id"));
                int commpage = Integer.parseInt(comment.getString("page"));
                //Log.i("ddd",commid+"");
                Apiservice retrofit3 = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
                Flowable<CommentBean> comment1 = retrofit3.getComment(commuserId, commsessionId, commid, commpage, 5);
                initData(comment1);
                break;
            case 5:
                //****************************电影档期******************************
                JSONObject schedule = data.getJSONObject(0);
                int moveid = Integer.parseInt(schedule.getString("moveid"));
                int cinemasId = Integer.parseInt(schedule.getString("cinemasId"));
                //Log.i("ddd",moveid+"");
                Apiservice retrofit4 = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
                Flowable<ScreeningHall> screening = retrofit4.getScreening(moveid, cinemasId);
                initData(screening);
                break;
            case 6:
                //****************************根据影院ID查询当前的电影列表******************************
               /* JSONObject cinemaMovieId = data.getJSONObject(0);
                int id = Integer.parseInt(cinemaMovieId.getString("id"));
                Log.i("CinemaMovieActivityid:",id+"");
                Apiservice retrofit5 = RetrofitUtil.getInstance().getRetrofit(Api.movieUrl, Apiservice.class);
                Flowable<CinemaMoidById> cineamMovieId = retrofit5.getCineamMovieId(id);
                initData(cineamMovieId);*/
                break;
        }

    }

    private <T>T initData(Flowable<T> data) {
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<T>() {
                    @Override
                    public void onNext(T onData) {
                        //Log.i("sss","走了"+onData.toString());
                        if(inModel!=null){
                            inModel.onResult(onData);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.i("throwable",t.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return null;
    }

    /**
     * 定义接口
     */
    private InModel inModel;

    public UserModel(InModel inModel) {
        this.inModel = inModel;
    }
}
