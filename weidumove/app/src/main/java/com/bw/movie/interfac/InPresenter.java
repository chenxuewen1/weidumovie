package com.bw.movie.interfac;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:公共接口泛型
 */
public interface InPresenter<T> {
    void onData(T data);
}
