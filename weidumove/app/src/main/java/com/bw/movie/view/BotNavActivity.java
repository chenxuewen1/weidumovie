package com.bw.movie.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.UserBean;
import com.bw.movie.fragment.CinemaFragment;
import com.bw.movie.fragment.HomeFragment;
import com.bw.movie.fragment.MyFragment;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BotNavActivity extends FragmentActivity {

    @BindView(R.id.nav_radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.nav_radio0)
    RadioButton b1;
    @BindView(R.id.nav_radio1)
    RadioButton b2;
    @BindView(R.id.nav_radio2)
    RadioButton b3;
    private HomeFragment homeFragment;
    private MyFragment myFragment;
    private CinemaFragment cinemaFragment;
    /**
     * @time:2019/4/11
     * @author: chenxuewen
     * @description:底部导航
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botnav);
        ButterKnife.bind(this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        homeFragment = new HomeFragment();
        cinemaFragment = new CinemaFragment();
        myFragment = new MyFragment();
        transaction.add(R.id.nav_frag, homeFragment);
        transaction.add(R.id.nav_frag, cinemaFragment);
        transaction.add(R.id.nav_frag, myFragment);
        //第一个默认显示
        transaction.show(homeFragment).hide(cinemaFragment).hide(myFragment);
        //提交
        transaction.commit();
        //默认选中第一个
        radiogroup.check(radiogroup.getChildAt(0).getId());
        setEnlarge(b1);
        setNarrow(b2);
        setNarrow(b3);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (checkedId) {
                    case R.id.nav_radio0:
                        setEnlarge(b1);
                        setNarrow(b2);
                        setNarrow(b3);
                        transaction.show(homeFragment).hide(cinemaFragment).hide(myFragment).commit();
                        break;
                    case R.id.nav_radio1:
                        setEnlarge(b2);
                        setNarrow(b1);
                        setNarrow(b3);
                        transaction.hide(homeFragment).show(cinemaFragment).hide(myFragment).commit();
                        break;
                    case R.id.nav_radio2:
                        setEnlarge(b3);
                        setNarrow(b1);
                        setNarrow(b2);
                        transaction.hide(homeFragment).hide(cinemaFragment).show(myFragment).commit();
                        break;
                }
            }
        });
    }

    //放大的方法
    private void setEnlarge(View view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .start();
    }

    //缩小的方法
    private void setNarrow(View view) {
        ViewCompat.animate(view)
                .setDuration(200)
                .scaleX(1f)
                .scaleY(1f)
                .start();
    }

}
