package com.bw.movie.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/*Time:2019/4/13
 *Author:chenxuewen
 *Description:全局配置
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
