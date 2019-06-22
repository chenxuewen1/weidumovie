package com.bw.movie.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.fragment.classify.ComingFragment;
import com.bw.movie.fragment.classify.HotFragment;
import com.bw.movie.fragment.classify.ReleaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 *@time:2019/4/27
 *@author: chenxuewen
 *@description: 电影分类
 */
public class ClassifyActivity extends AppCompatActivity {

    @BindView(R.id.classify_radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.classify_back)
    ImageView back;
    @BindView(R.id.classify_pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify);
        ButterKnife.bind(this);

        initData();
    }

    //数据
    private void initData() {
        //返回点击
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HotFragment());
        fragments.add(new ReleaseFragment());
        fragments.add(new ComingFragment());
        //适配器
        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        radiogroup.check(radiogroup.getChildAt(0).getId());
        //滑动选中监听
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//选中
                radiogroup.check(radiogroup.getChildAt(i).getId());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //点击请切换
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.classify_radio0:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.classify_radio1:
                        pager.setCurrentItem(1);
                        break;
                    case R.id.classify_radio2:
                        pager.setCurrentItem(2);
                        break;
                }
            }
        });
    }
}
