package com.bw.movie.view.cinema;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.adapter.CinemaCarrouselAdapter;
import com.bw.movie.adapter.MovieScheduleAdapter;
import com.bw.movie.bean.CinemaMoidById;
import com.bw.movie.bean.ScreeningHall;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.UserPresenter;
import com.bw.movie.presenter.cinema.CinemaMoviePresenter;
import com.bw.movie.view.DetailActivity;
import com.bw.movie.view.buy.SeatSelectionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * @time:2019/4/26
 * @author: chenxuewen
 * @description: 根据影院id 查询该影院当前排期的电影列表
 */
public class CinemaMovieActivity extends AppCompatActivity implements InData.CinemaMoidIdinface, InPresenter<ScreeningHall> {

    @BindView(R.id.cinema_movie_image)
    ImageView cinemaMovieImage;
    @BindView(R.id.cinema_movie_cinemaname)
    TextView cinemaMovieCinemaname;
    @BindView(R.id.cinema_movie_cinemaaddress)
    TextView cinemaMovieCinemaaddress;
    @BindView(R.id.cinema_movie_flow)
    RecyclerCoverFlow cinemaMovieFlow;
    @BindView(R.id.cinema_movie_rec)
    RecyclerView cinemaMovieRec;
    @BindView(R.id.cinema_back)
    ImageView back;
    private Intent intent;
    private CinemaMoviePresenter presenter;
    private UserPresenter<CinemaMovieActivity> userPresenter;
    private List<CinemaMoidById.ResultBean> result;
    private String id;
    private String address;
    private String logo;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_movie);
        ButterKnife.bind(this);
        intent = getIntent();
        presenter = new CinemaMoviePresenter(this);
        userPresenter = new UserPresenter<CinemaMovieActivity>(this);
        //绑定
        presenter.attach(this);
        initData();
    }

    /**
     * 数据
     */
    private void initData() {

        //影院地址
        address = intent.getStringExtra("address");
        id = intent.getStringExtra("id");//影院id
        //影院logo
        logo = intent.getStringExtra("logo");
        //影院名称
        name = intent.getStringExtra("name");
        cinemaMovieCinemaname.setText(name);
        cinemaMovieCinemaaddress.setText(address);
        Glide.with(this).load(logo)
                .placeholder(R.drawable.loging)//加载中图片
                .error(R.mipmap.error)//加载失败图片
                .into(cinemaMovieImage);
        presenter.onDataPre(id);
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁本页面
                finish();
            }
        });
        //点击弹框
        cinemaMovieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view3 = LayoutInflater.from(CinemaMovieActivity.this).inflate(R.layout.cinema_pop_detail_layout, null, false);
                RecyclerView rec = view3.findViewById(R.id.pop_cinema_rec);
                ImageView hide = view3.findViewById(R.id.pop_cinema_hide);
                TextView detail = view3.findViewById(R.id.pop_cinema_detail);
                TextView ping = view3.findViewById(R.id.pop_cinema_ping);
                rec.setLayoutManager(new LinearLayoutManager(CinemaMovieActivity.this));

                final PopupWindow pop3 = new PopupWindow(view3, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //设置响应点击事件
                pop3.setTouchable(true);
                //设置背景
                pop3.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                //设置响应外部点击事件
                pop3.setOutsideTouchable(true);
                //焦点
                pop3.setFocusable(true);
                //设置显示和消失的动画
                pop3.setAnimationStyle(R.style.mypopwindow_anim_style);
                //显示
                pop3.showAsDropDown(cinemaMovieImage, 0, 100);
                //点击让弹框消失
                hide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pop3.isShowing()) {
                            pop3.dismiss();
                        }
                    }
                });
            }
        });
    }

    /**
     * 查询 查询该影院当前排期的电影列表返回数据
     *
     * @param onData
     */
    @Override
    public void onDada(CinemaMoidById onData) {
        result = onData.getResult();
        if (onData.getResult() != null) {
            CinemaCarrouselAdapter adapter = new CinemaCarrouselAdapter(CinemaMovieActivity.this, (ArrayList<CinemaMoidById.ResultBean>) onData.getResult());
            adapter.setItemListener(new CinemaCarrouselAdapter.OnItemListener() {
                @Override
                public void onPosition(int position) {
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        int id1 = result.get(position).getId();
                        jsonObject.put("moveid", id1);
                        jsonObject.put("cinemasId", id);
                        jsonArray.put(jsonObject);
                        userPresenter.dataPre(jsonArray, 5);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            cinemaMovieFlow.setAdapter(adapter);
        } else {
            Toast.makeText(this, onData.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 电影档期返回数据
     *
     * @param data
     */
    @Override
    public void onData(ScreeningHall data) {
        final List<ScreeningHall.ResultBean> result1 = data.getResult();
        cinemaMovieRec.setLayoutManager(new LinearLayoutManager(this));
        MovieScheduleAdapter scheduleAdapter = new MovieScheduleAdapter(CinemaMovieActivity.this, (ArrayList<ScreeningHall.ResultBean>) result1);
        scheduleAdapter.setItemListener(new MovieScheduleAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                int movieid = result1.get(position).getId();
                String beginTime = result1.get(position).getBeginTime();
                String endTime = result1.get(position).getEndTime();
                double price = result1.get(position).getPrice();
                String moviewname = result.get(position).getName();
                String screeningHall = result1.get(position).getScreeningHall();
                Intent intent = new Intent(CinemaMovieActivity.this, SeatSelectionActivity.class);
                intent.putExtra("id", movieid + "");//排期id
                intent.putExtra("beginTime", beginTime);//开始时间
                intent.putExtra("endTime", endTime);//结束时间
                intent.putExtra("price", price + "");//票价
                intent.putExtra("screeningHall", screeningHall);//放映大厅
                intent.putExtra("name", moviewname);//电影名称
                intent.putExtra("address", address);//影院地址
                intent.putExtra("cinemaname", name);//影院名称
                startActivity(intent);
            }
        });
        cinemaMovieRec.setAdapter(scheduleAdapter);
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}
