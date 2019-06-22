package com.bw.movie.fragment.classify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.Classify_film_Adapter;
import com.bw.movie.bean.ReleaseBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.presenter.homepresenter.ReleasePresenter;
import com.bw.movie.view.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/*Time:2019/4/13
 *Author:chenxuewen
 *Description:正在上映子布局
 */
public class ReleaseFragment extends Fragment implements InData {
    @BindView(R.id.release_fime_rec)
    RecyclerView releaseFimeRec;
    Unbinder unbinder;
    private ReleasePresenter releasePresenter;
    private SharedPreferences sp;
    private int page=1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.release_fime_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        releaseFimeRec.setLayoutManager(linearLayoutManager);
        releasePresenter = new ReleasePresenter(this);
        releasePresenter.attach(this);
        initView();
        return view;
    }
    //数据
    private void initView() {
        String userId = sp.getString("userId", "");
        String sessionId = sp.getString("sessionId", "");
        releasePresenter.releDataPre(sessionId,userId,1,10);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 正在上映 热映
     * @param onData
     */
    @Override
    public void onResult(final ReleaseBean onData) {
        List<ReleaseBean.ResultBean> result = onData.getResult();
        Classify_film_Adapter classify_hot = new Classify_film_Adapter(getActivity(), (ArrayList<ReleaseBean.ResultBean>) result);
        classify_hot.setItemListener(new Classify_film_Adapter.OnItemListener() {
            @Override
            public void onPosition(int position) {
                //点击的电影id
                int id = onData.getResult().get(position).getId();
                String id1= String.valueOf(id);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("id",id1);
                startActivity(intent);
            }
        });
        releaseFimeRec.setAdapter(classify_hot);
    }
    /**
     * 解决内存泄漏
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePresenter.destroy();
    }
}
