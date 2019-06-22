package com.bw.movie.fragment.cinema;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.CinemaAdapter;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.presenter.cinema.NearbyPresenter;
import com.bw.movie.view.cinema.CinemaMovieActivity;

import java.util.ArrayList;
import java.util.List;

/*Time:2019/4/19
 *Author:chenxuewen
 *Description: 附近影院
 */
public class NearbyCinema extends Fragment implements InData.RecommendCinema {
    private int page=1;
    private NearbyPresenter nearbyPresenter;
    private SharedPreferences sp;
    private RecyclerView rec;
    private String longitude= "116.30551391385724";
    private String latitude="40.04571807462411";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.nearby_cinema_layout,container,false);
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        rec = view.findViewById(R.id.nearby_cinema_rec);
        nearbyPresenter = new NearbyPresenter(this);
        nearbyPresenter.attach(this);
        initData();
        return view;
    }

    private void initData() {

        String sessionId = sp.getString("sessionId", "");
        String userId = sp.getString("userId", "");
        //Log.i("ddd",userId);
        nearbyPresenter.dataNearbyPre(userId,sessionId,page,longitude,latitude);
    }

    /**
     * 接收返回来推荐影院的数据
     * @param onData
     */
    @Override
    public void onDada(final RecommendBean onData) {
        List<RecommendBean.ResultBean> result = onData.getResult();
        //Log.i("ssddd",onData.getMessage());
        CinemaAdapter cinemaAdapter = new CinemaAdapter(getActivity(), (ArrayList<RecommendBean.ResultBean>) result);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));
        cinemaAdapter.setItemListener(new CinemaAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                int id = onData.getResult().get(position).getId();
                String logo = onData.getResult().get(position).getLogo();
                String name = onData.getResult().get(position).getName();
                String address = onData.getResult().get(position).getAddress();
                Intent intent = new Intent(getActivity(), CinemaMovieActivity.class);
                intent.putExtra("id",id+"");
                intent.putExtra("logo",logo+"");
                intent.putExtra("name",name+"");
                intent.putExtra("address",address+"");
                startActivity(intent);
            }
        });
        rec.setAdapter(cinemaAdapter);
    }
    /**
     * 优化mvp内存泄漏
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        nearbyPresenter.destroy();
    }
}
