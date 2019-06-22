package com.bw.movie.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CarrouselAdapter;
import com.bw.movie.adapter.ReleaseAdapter;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.homepresenter.ComingPresenter;
import com.bw.movie.presenter.homepresenter.HotMoviePresenter;
import com.bw.movie.presenter.homepresenter.ReleasePresenter;
import com.bw.movie.util.BDLocationUtils;
import com.bw.movie.util.Const;
import com.bw.movie.view.ClassifyActivity;
import com.bw.movie.view.DetailActivity;
import com.bw.movie.view.custom.CustomSearch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:首页电影
 */
public class HomeFragment extends Fragment implements InPresenter<ReleaseBean>, InData, InData.InComing {

    @BindView(R.id.home_rec1)
    RecyclerView homeRec1;
    Unbinder unbinder;
    @BindView(R.id.home_rec2)
    RecyclerView homeRec2;
    @BindView(R.id.home_rec3)
    RecyclerView homeRec3;
    @BindView(R.id.home_film_flow)
    RecyclerCoverFlow homeFilmFlow;
    @BindView(R.id.home_shangmovie)
    ImageView homeShangmovie;
    @BindView(R.id.home_reying)
    ImageView homeReying;
    @BindView(R.id.home_shangying)
    ImageView homeShangying;
    @BindView(R.id.customsearch)
    CustomSearch customsearch;
    @BindView(R.id.home_dingwei)
    ImageView homeDingwei;
    @BindView(R.id.home_city)
    TextView homeCity;
    private SharedPreferences sp;
    private HotMoviePresenter presenter;
    private ReleasePresenter releasePresenter;
    private ComingPresenter comingPresenter;
    private BDLocationUtils bdLocationUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        presenter = new HotMoviePresenter(this);
        releasePresenter = new ReleasePresenter(this);
        comingPresenter = new ComingPresenter(this);
        presenter.attach(this);
        releasePresenter.attach(this);
        comingPresenter.attach(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        homeRec1.setLayoutManager(linearLayoutManager);
        homeRec2.setLayoutManager(linearLayoutManager1);
        homeRec3.setLayoutManager(linearLayoutManager2);
        float translationX = customsearch.getTranslationX();
        ObjectAnimator animator = ObjectAnimator.ofFloat(customsearch, "translationX", translationX, 200);
        animator.setDuration(600);
        animator.start();

        initData();
        return view;
    }

    private void initData() {
        String sessionId = sp.getString("sessionId", "");
        String userId = sp.getString("userId", "");
        Log.i("sss", sessionId);
        Log.i("sss", userId + "");
        presenter.hotDataPre(sessionId, userId, 1, 10);
        //正在热映
        releasePresenter.releDataPre(sessionId, userId, 1, 10);
        comingPresenter.releDataPre(sessionId, userId, 1, 10);
        //显示动画
        customsearch.findViewById(R.id.custom_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float translationX = customsearch.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(customsearch, "translationX", translationX, 0);
                animator.setDuration(600);
                animator.start();

            }
        });
        //隐藏动画
        customsearch.setOnClick(new CustomSearch.OnClick() {
            @Override
            public void onSearch(EditText data, View view) {
                float translationX = customsearch.getTranslationX();
                ObjectAnimator animator = ObjectAnimator.ofFloat(customsearch, "translationX", translationX, 200);
                animator.setDuration(600);
                animator.start();
            }
        });
        //定位
        homeDingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
                    ActivityCompat.requestPermissions(getActivity(),mPermissionList,123);
                }
                Toast.makeText(getActivity(), "已开发待优化", Toast.LENGTH_SHORT).show();
                /*bdLocationUtils = new BDLocationUtils(getActivity());
                bdLocationUtils.doLocation();//开启定位
                bdLocationUtils.mLocationClient.start();//开始定位*/
                /*Log.i("sss",Const.ADDRESS);
                Log.i("sss",Const.LATITUDE+"");
                Log.i("sss",Const.LONGITUDE+"");*/
            }
        });
        //停止
        homeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bdLocationUtils.mLocationClient.stop();//停止定位
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("xxx","首页***onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("xxx","首页***onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i("xxx","首页***"+hidden);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 接收热门电影返回的数据
     *
     * @param data
     */
    @Override
    public void onData(ReleaseBean data) {
        /*String message = data.getMessage();
        Log.i("ssss",message );*/
        final List<ReleaseBean.ResultBean> result = data.getResult();
        ReleaseAdapter hotMovieAdapter = new ReleaseAdapter(getActivity(), (ArrayList<ReleaseBean.ResultBean>) result);
        hotMovieAdapter.setItemListener(new ReleaseAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                //点击的电影id
                int id = result.get(position).getId();
                String id1 = String.valueOf(id);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });
        homeRec1.setAdapter(hotMovieAdapter);
    }

    /**
     * 正在热映 上映
     *
     * @param onData
     */
    @Override
    public void onResult(ReleaseBean onData) {
        //Log.i("sss",onData.getMessage());
        final List<ReleaseBean.ResultBean> result = onData.getResult();
        ReleaseAdapter releaseAdapter = new ReleaseAdapter(getActivity(), (ArrayList<ReleaseBean.ResultBean>) result);
        homeRec2.setAdapter(releaseAdapter);
        releaseAdapter.setItemListener(new ReleaseAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                //点击的电影id
                int id = result.get(position).getId();
                String id1 = String.valueOf(id);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });
        CarrouselAdapter carrouselAdapter = new CarrouselAdapter(getActivity(), (ArrayList<ReleaseBean.ResultBean>) result);
        carrouselAdapter.setItemListener(new CarrouselAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                //点击的电影id
                int id = result.get(position).getId();
                String id1 = String.valueOf(id);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });
        homeFilmFlow.setAdapter(carrouselAdapter);
    }

    /**
     * 即将上映 indata返回的数据
     *
     * @param onData
     */
    @Override
    public void onDada(ReleaseBean onData) {
        final List<ReleaseBean.ResultBean> result = onData.getResult();
        ReleaseAdapter releaseAdapter = new ReleaseAdapter(getActivity(), (ArrayList<ReleaseBean.ResultBean>) result);
        releaseAdapter.setItemListener(new ReleaseAdapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                //点击的电影id
                int id = result.get(position).getId();
                String id1 = String.valueOf(id);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id", id1);
                startActivity(intent);
            }
        });
        homeRec3.setAdapter(releaseAdapter);
    }

    /**
     * 跳转到分类
     *
     * @param view
     */
    @OnClick({R.id.home_shangmovie, R.id.home_reying, R.id.home_shangying})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_shangmovie:
                startActivity(new Intent(getActivity(), ClassifyActivity.class));
                break;
            case R.id.home_reying:
                startActivity(new Intent(getActivity(), ClassifyActivity.class));
                break;
            case R.id.home_shangying:
                startActivity(new Intent(getActivity(), ClassifyActivity.class));
                break;
        }
    }

    /**
     * 解决mvp内存泄漏
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        releasePresenter.destroy();
        comingPresenter.destroy();
    }
}
