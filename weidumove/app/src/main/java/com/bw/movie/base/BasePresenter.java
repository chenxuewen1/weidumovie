package com.bw.movie.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:基类p层
 */
public abstract  class BasePresenter<V> {
    protected Reference<V> reference;

    public void  attach(V v){
        reference=new WeakReference<>(v);
    }
    public void destroy(){
        if(reference!=null){
            reference.clear();
            reference=null;
        }
    }
}
