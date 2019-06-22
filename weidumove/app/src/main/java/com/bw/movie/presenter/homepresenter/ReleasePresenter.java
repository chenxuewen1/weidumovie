package com.bw.movie.presenter.homepresenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InModel;
import com.bw.movie.model.homemodel.ReleaseModel;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:正在上映
 */
public class ReleasePresenter extends BasePresenter<InData> implements InModel<ReleaseBean> {

    private final InData inData;
    private final ReleaseModel releaseModel;

    public ReleasePresenter(InData onData){
        releaseModel = new ReleaseModel(this);
        inData = onData;
    }

    /**
     * 向m层传递数据
     * @param sessionId
     * @param userId
     * @param page
     * @param count
     */
    public void releDataPre(String sessionId, String userId,int page, int count) {
        releaseModel.dataModel(sessionId,userId,page,count);
    }

    /**
     * 向inpresneter公共接口传入参数
     * @param onData
     */
    @Override
    public void onResult(ReleaseBean onData) {
        inData.onResult(onData);
    }
}
