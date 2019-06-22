package com.bw.movie.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:共用p层
 */
public class UserPresenter<T> extends BasePresenter<InPresenter> implements InModel<T> {

    private final InPresenter inPresenter;
    private final UserModel loginModel;

    public UserPresenter(InPresenter onData){
        loginModel = new UserModel(this);
        inPresenter = onData;
    }
    //传输数据
    public void dataPre(JSONArray data,int page) throws JSONException {
        loginModel.dataModel(data,page);
    }
    //回传
    @Override
    public void onResult(T onData) {
        inPresenter.onData(onData);
    }


}
