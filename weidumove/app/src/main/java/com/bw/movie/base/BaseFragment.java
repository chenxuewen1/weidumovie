package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*Time:2019/4/10
 *Author:chenxuewen
 *Description:fragment基类
 */
public  abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(layoutID(),null,false);
        return view;
    }

    protected abstract int layoutID();

    protected abstract void initView(View view);

    protected abstract void initData();
}
