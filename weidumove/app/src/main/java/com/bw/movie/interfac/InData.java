package com.bw.movie.interfac;

import android.widget.CheckBox;

import com.bw.movie.bean.CheckBean;
import com.bw.movie.bean.CinemaMoidById;
import com.bw.movie.bean.CommentBean;
import com.bw.movie.bean.FindUserBean;
import com.bw.movie.bean.PayOrderBean;
import com.bw.movie.bean.PublishCommentBean;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.bean.ZhiFuBaoBean;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:公共接口
 */
public interface InData {
    void onResult(ReleaseBean onData);
    //热映
    interface InComing{
        void onDada(ReleaseBean onData);
    }
    //根据id查看电影
    interface DetailMovieByID{
        void onDada(CheckBean onData);
    }
    //查看评论
    interface CheckComment{
        void onDada(CommentBean onData);
    }
    //发表评论
    interface PublishComment{
        void onDada(PublishCommentBean onData);
    }
    //推荐影院
    interface RecommendCinema{
        void onDada(RecommendBean onData);
    }
    //微信支付
    interface PayOrder{
        void onDada(PayOrderBean onData);
    }
    //微信支付
    interface ZhiFuBao{
        void onDada(ZhiFuBaoBean onData);
    }
    //查看用户信息
    interface FindUser{
        void onDada(FindUserBean onData);
    }
    //根据影院ID查询当前的电影列表
    interface CinemaMoidIdinface{
        void onDada(CinemaMoidById onData);
    }
}
