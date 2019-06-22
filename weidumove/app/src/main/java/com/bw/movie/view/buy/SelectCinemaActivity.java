package com.bw.movie.view.buy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaAdapter;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.homepresenter.buypresenter.SelectCinemaPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @time:2019/4/19
 * @author: chenxuewen
 * @description: 选择影院
 */
public class SelectCinemaActivity extends AppCompatActivity implements InPresenter<RecommendBean> {

    @BindView(R.id.select_cinema_name)
    TextView selectCinemaName;
    @BindView(R.id.select_cinema_rec)
    RecyclerView selectCinemaRec;
    @BindView(R.id.select_cinema_back)
    ImageView back;
    private SelectCinemaPresenter presenter;
    private String id;
    private String imageUrl;
    private String movieTypes;
    private String duration;
    private String director;
    private String placeOrigin;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_cinema);
        ButterKnife.bind(this);
        //接收intent数据
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        imageUrl = intent.getStringExtra("imageUrl");
        name = intent.getStringExtra("name");
        movieTypes = intent.getStringExtra("movieTypes");
        duration = intent.getStringExtra("duration");
        director = intent.getStringExtra("director");
        placeOrigin = intent.getStringExtra("placeOrigin");
        selectCinemaName.setText(name);
        presenter = new SelectCinemaPresenter(this);
        //绑定
        presenter.attach(this);
        initData();

    }

    /**
     * 数据
     */
    private void initData() {
        presenter.selectDataPre(id);
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onData(RecommendBean data) {
        final List<RecommendBean.ResultBean> result = data.getResult();
        CinemaAdapter cinemaAdapter = new CinemaAdapter(SelectCinemaActivity.this, (ArrayList<RecommendBean.ResultBean>) result);
        selectCinemaRec.setLayoutManager(new LinearLayoutManager(this));
        cinemaAdapter.setItemListener(new CinemaAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                //传值
                int cinemaid = result.get(position).getId();
                String address = result.get(position).getAddress();
                String cinemaname = result.get(position).getName();
                Intent intent = new Intent(SelectCinemaActivity.this, MovieScheduleActivity.class);
                intent.putExtra("cinemaid",cinemaid+"");
                intent.putExtra("moveid",id);
                intent.putExtra("name",name);
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("movieTypes",movieTypes);
                intent.putExtra("duration",duration);
                intent.putExtra("director",director);
                intent.putExtra("placeOrigin",placeOrigin);
                intent.putExtra("address",address);
                intent.putExtra("cinemaname",cinemaname);
                startActivity(intent);
            }
        });
        selectCinemaRec.setAdapter(cinemaAdapter);
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
