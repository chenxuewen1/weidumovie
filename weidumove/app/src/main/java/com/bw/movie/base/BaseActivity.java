package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/*Time:2019/4/10
 *Author:chenxuewen
 *Description:基类
 */
public abstract  class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());
        //初始化控件
        initView();
        //初始化数据
        initData();
    }

    protected abstract int layoutResID();

    protected abstract void initView();

    protected abstract void initData();

}
