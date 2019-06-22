package com.bw.movie.presenter.homepresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CommentBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:查看评论
 */
public class CheckCommentPresenter extends BasePresenter<InData.CheckComment> implements InModel<CommentBean> {

    private final InData.CheckComment comment;
    private final UserModel userModel;

    public CheckCommentPresenter(InData.CheckComment onDada){
        userModel = new UserModel(this);
        comment = onDada;
    }
    public void checkByIdDataPre(JSONArray json, int page) {
        try {
            userModel.dataModel(json,page);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResult(CommentBean onData) {
        comment.onDada(onData);
    }
}
