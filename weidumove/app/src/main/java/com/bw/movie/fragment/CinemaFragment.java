package com.bw.movie.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.fragment.cinema.NearbyCinema;
import com.bw.movie.fragment.cinema.RecommendCinema;
import com.bw.movie.util.BDLocationUtils;
import com.bw.movie.util.Const;
import com.bw.movie.view.custom.CustomSearch;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:影院
 */
public class CinemaFragment extends Fragment {
    @BindView(R.id.cinema_radiogroup)
    RadioGroup group;
    @BindView(R.id.cinema_pager)
    ViewPager pager;
    Unbinder unbinder;
    @BindView(R.id.cinema_dingwei)
    ImageView cinemaDingwei;
    @BindView(R.id.cinema_city)
    TextView cinemaCity;
    @BindView(R.id.cinema_search)
    CustomSearch cinemaSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cinema_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        float translationX = cinemaSearch.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(cinemaSearch, "translationX", translationX, 250);
        animator.setDuration(600);
        animator.start();
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("xxx","电影院***onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("xxx","电影院***onResume");
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("xxx","电影院***"+hidden);
    }
    /**
     * 数据
     */
    private void initData() {
        //创建集合
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendCinema());
        fragments.add(new NearbyCinema());
        //适配器
        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        //默认选中第一个
        group.check(group.getChildAt(0).getId());
        //滑动选中监听
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//选中
                group.check(group.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //点击请切换
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.cinema_radio1:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.cinema_radio2:
                        pager.setCurrentItem(1);
                        break;
                }
            }
        });
        //点击定位
        cinemaDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BDLocationUtils bdLocationUtils = new BDLocationUtils(getActivity());
                bdLocationUtils.doLocation();//开启
                bdLocationUtils.mLocationClient.start();//开始定位
                double latitude = Const.LATITUDE;
                Log.i("地址",latitude+"");
            }
        });
        //显示动画
        cinemaSearch.findViewById(R.id.custom_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float translationX = cinemaSearch.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(cinemaSearch, "translationX", translationX, 0);
                animator.setDuration(600);
                animator.start();

            }
        });
        //隐藏动画
        cinemaSearch.setOnClick(new CustomSearch.OnClick() {
            @Override
            public void onSearch(EditText data, View view) {
                float translationX = cinemaSearch.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(cinemaSearch, "translationX", translationX, 250);
                animator.setDuration(600);
                animator.start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
