package com.bw.movie.presenter.mypresenter;

import com.bw.movie.bean.FindUserBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.mymodel.FindUserModel;

/*Time:2019/4/25
 *Author:chenxuewen
 *Description:查询用户信息
 */
public class FindUserPresenter implements InModel<FindUserBean> {

    private final FindUserModel findUserModel;
    private final InData.FindUser findUser;

    public FindUserPresenter(InData.FindUser onData){
        findUserModel = new FindUserModel(this);
        findUser = onData;
    }
    public void dataFindPre(String userId, String sessionId) {
        findUserModel.dataFindModel(userId,sessionId);
    }

    @Override
    public void onResult(FindUserBean onData) {
        findUser.onDada(onData);
    }
}
