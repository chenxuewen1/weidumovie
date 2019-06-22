package com.bw.movie.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.view.user.LoginActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @time:2019/4/10
 * @author: chenxuewen
 * @description:引导页
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_pager)
    ViewPager pager;
    @BindView(R.id.main_radiogroup)
    RadioGroup radiogroup;
    private static int[] imags = {R.mipmap.tu1, R.mipmap.tu2, R.mipmap.tu3, R.mipmap.tu4};
    @BindView(R.id.main_name)
    TextView mainName;
    @BindView(R.id.main_jing)
    TextView mainJing;
    @BindView(R.id.main_xianshi)
    LinearLayout mainXianshi;
    @BindView(R.id.main_zhu)
    ImageView mainZhu;
    private int time = 2;
    private SharedPreferences sp;
    private Handler myHandler=new Handler();
    private boolean b=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sp = getSharedPreferences("main", Context.MODE_PRIVATE);
            if (sp.getBoolean("第一次", false)) {
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        //销毁
                        finish();
                    }
                }, 2000);
                return;
            }
        final ArrayList<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < imags.length; i++) {
            //创建图片
            ImageView imageView = new ImageView(this);
            //设置图片
            imageView.setImageResource(imags[i]);
            imageView.setPadding(100,150,100,100);
            //加入集合
            imageViews.add(imageView);
            RadioButton radioButton = new RadioButton(this);
            radiogroup.addView(radioButton);
        }
        //默认选中第一个
        radiogroup.check(radiogroup.getChildAt(0).getId());
        //滑动监听
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                radiogroup.check(radiogroup.getChildAt(i).getId());
                //判断
                if(i==0){
                    mainName.setText("一场电影");
                    mainJing.setText("净化你的灵魂");
                }if(i==1){
                    mainName.setText("一场电影");
                    mainJing.setText("看遍人生百态");
                }if(i==2){
                    mainName.setText("一场电影");
                    mainJing.setText("荡条你的心灵");
                }if(i==3){
                    mainName.setText("八维移动通信学院作品");
                    mainJing.setText("带您开启一段美好的电影之旅");
                }
                //判断是否最后一个
                if(i==3){
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            //销毁
                            finish();
                        }
                    },500);

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //适配器
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViews.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }
            //实列化
            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                //获取图片
                ImageView imageView = imageViews.get(position);
                container.addView(imageView);
                //返回
                return imageView;
            }
            //删除
            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        //延迟发送
        if(b==false){
            b=true;
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainXianshi.setVisibility(View.VISIBLE);
                    mainZhu.setVisibility(View.GONE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("第一次", true);
                    edit.commit();
                }
            },2000);
        }
    }

}
