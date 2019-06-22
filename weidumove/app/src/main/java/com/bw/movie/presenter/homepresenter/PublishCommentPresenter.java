package com.bw.movie.presenter.homepresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.PublishCommentBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.homemodel.PublishCommentModel;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:发表评论
 */
public class PublishCommentPresenter extends BasePresenter<InData.PublishComment> implements InModel<PublishCommentBean> {

    private final InData.PublishComment comment;
    private final PublishCommentModel publishCommentModel;

    public PublishCommentPresenter(InData.PublishComment onDada){
        publishCommentModel = new PublishCommentModel(this);
        comment = onDada;
    }
    public void PublishCommentDataPre(String sessionId, String userId,String movieId,String commentContent) {
        publishCommentModel.dataModel(sessionId,userId,movieId,commentContent);
    }


    @Override
    public void onResult(PublishCommentBean onData) {
        comment.onDada(onData);
    }
}
