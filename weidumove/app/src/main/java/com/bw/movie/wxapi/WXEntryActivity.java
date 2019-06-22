package com.bw.movie.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.bean.WeXinBean;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.WeXinPresenter;
import com.bw.movie.util.wexin.WeiXinUtil;
import com.bw.movie.view.BotNavActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, InPresenter<WeXinBean> {

    private WeXinPresenter weXinPresenter;

    /**
  *@time:2019/4/22
  *@author: chenxuewen
  *@description: 微信登录
  */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        //必须得写
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);

    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {

        //pan'duan
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                // code = ((SendAuth.Resp) baseResp).code;
                String code = ((SendAuth.Resp) baseResp).code;
                //Toast.makeText(WXEntryActivity.this, "code" + code, Toast.LENGTH_SHORT).show();
                //Log.i("hahahahah", code);
                weXinPresenter = new WeXinPresenter(this);
                weXinPresenter.codeDataPre(code);
                weXinPresenter.attach(this);
                break;
            default:
                break;
        }
    }

    /**
     * 接收微信返回来的数据
     * @param data
     */
    @Override
    public void onData(WeXinBean data) {
       // Log.i("dd",data.getMessage());
        WeXinBean.ResultBean result = data.getResult();
        Intent intent = new Intent(WXEntryActivity.this, BotNavActivity.class);
        //intent.putExtra("result", (Serializable) result);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        weXinPresenter.destroy();
    }
}
