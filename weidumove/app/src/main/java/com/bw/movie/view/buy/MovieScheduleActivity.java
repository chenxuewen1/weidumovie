package com.bw.movie.view.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.adapter.MovieScheduleAdapter;
import com.bw.movie.bean.ScreeningHall;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.UserPresenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 *@time:2019/4/20
 *@author: chenxuewen
 *@description:电影档期
 */
public class MovieScheduleActivity extends AppCompatActivity implements InPresenter<ScreeningHall> {
    @BindView(R.id.movie_schedule_cinemaname)
    TextView movieScheduleCinemaname;
    @BindView(R.id.movie_schedule_image)
    ImageView movieScheduleImage;
    @BindView(R.id.movie_schedule_name)
    TextView movieScheduleName;
    @BindView(R.id.movie_schedule_leixing)
    TextView movieScheduleLeixing;
    @BindView(R.id.movie_schedule_daoyan)
    TextView movieScheduleDaoyan;
    @BindView(R.id.movie_schedule_time)
    TextView movieScheduleTime;
    @BindView(R.id.movie_schedule_back)
    ImageView movieScheduleBack;
    @BindView(R.id.movie_schedulecinema_address)
    TextView movieSchedulecinemaAddress;
    @BindView(R.id.movie_schedule_chandi)
    TextView movieScheduleChandi;
    @BindView(R.id.movie_schedule_rec)
    RecyclerView movieScheduleRec;
    private String cinemaid;
    private String imageUrl;
    private String name;
    private String movieTypes;
    private String duration;
    private String director;
    private String placeOrigin;
    private String moveid;
    private String address;
    private String cinemaname;
    private UserPresenter<MovieScheduleActivity> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_schedule);
        ButterKnife.bind(this);
        //接收传过来的数据
        Intent intent = getIntent();
        cinemaid = intent.getStringExtra("cinemaid");
        moveid = intent.getStringExtra("moveid");
        imageUrl = intent.getStringExtra("imageUrl");
        name = intent.getStringExtra("name");
        movieTypes = intent.getStringExtra("movieTypes");
        duration = intent.getStringExtra("duration");
        director = intent.getStringExtra("director");
        placeOrigin = intent.getStringExtra("placeOrigin");
        address = intent.getStringExtra("address");
        address = intent.getStringExtra("address");
        cinemaname = intent.getStringExtra("cinemaname");
        presenter = new UserPresenter<MovieScheduleActivity>(this);
        presenter.attach(this);
        //数据
        initData();
    }

    /**
     * 数据
     */
    private void initData() {
        movieScheduleCinemaname.setText(cinemaname);//影院名称
        movieSchedulecinemaAddress.setText(address);//影院地址
        Glide.with(this).load(imageUrl).into(movieScheduleImage);//影院图片
        movieScheduleName.setText(name);//影片名
        movieScheduleLeixing.setText("类型:"+movieTypes);//类型
        movieScheduleDaoyan.setText("导演:"+director);//导演
        movieScheduleTime.setText("时长:"+duration);//时长
        movieScheduleChandi.setText("产地:"+placeOrigin);//产地
        //返回
        movieScheduleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        //Log.i("sssssss",cinemaid+moveid);
        try {
            jsonObject.put("moveid",moveid);
            jsonObject.put("cinemasId",cinemaid);
            jsonArray.put(jsonObject);
            presenter.dataPre(jsonArray,5);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回的电影档期数据
     * @param data
     */
    @Override
    public void onData(ScreeningHall data) {
        final List<ScreeningHall.ResultBean> result = data.getResult();
        movieScheduleRec.setLayoutManager(new LinearLayoutManager(this));
        MovieScheduleAdapter scheduleAdapter = new MovieScheduleAdapter(MovieScheduleActivity.this, (ArrayList<ScreeningHall.ResultBean>) result);
        scheduleAdapter.setItemListener(new MovieScheduleAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                int id = result.get(position).getId();
                String beginTime = result.get(position).getBeginTime();
                String endTime = result.get(position).getEndTime();
                double price = result.get(position).getPrice();
                String screeningHall = result.get(position).getScreeningHall();
                Intent intent = new Intent(MovieScheduleActivity.this, SeatSelectionActivity.class);
                intent.putExtra("id",id+"");//排期id
                intent.putExtra("beginTime",beginTime);//开始时间
                intent.putExtra("endTime",endTime);//结束时间
                intent.putExtra("price",price+"");//票价
                intent.putExtra("screeningHall",screeningHall);//放映大厅
                intent.putExtra("name",name);//电影名称
                intent.putExtra("address",address);//影院地址
                intent.putExtra("cinemaname",cinemaname);//影院名称
                startActivity(intent);
            }
        });
        movieScheduleRec.setAdapter(scheduleAdapter);
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
