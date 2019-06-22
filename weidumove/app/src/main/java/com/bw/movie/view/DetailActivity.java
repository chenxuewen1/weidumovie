package com.bw.movie.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.adapter.DetailAnnounceAdapter;
import com.bw.movie.adapter.DetailCheckCommitAdapter;
import com.bw.movie.adapter.DetailStillAdapter;
import com.bw.movie.bean.CheckBean;
import com.bw.movie.bean.CommentBean;
import com.bw.movie.bean.DetailBean;
import com.bw.movie.bean.PublishCommentBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.homepresenter.CheckCommentPresenter;
import com.bw.movie.presenter.homepresenter.CheckPresenter;
import com.bw.movie.presenter.homepresenter.PublishCommentPresenter;
import com.bw.movie.presenter.UserPresenter;
import com.bw.movie.view.buy.SelectCinemaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @time:2019/4/14
 * @author: chenxuewen
 * @description: 详情页面
 */
@SuppressWarnings({"ALL", "FieldCanBeLocal"})
public class DetailActivity extends AppCompatActivity implements InPresenter<DetailBean>, InData.DetailMovieByID, InData.CheckComment, InData.PublishComment {

    @BindView(R.id._detail_follow)
    ImageView detailFollow;
    @BindView(R.id.detail_name)
    TextView detailName;
    @BindView(R.id.detail_image)
    ImageView detailImage;
    @BindView(R.id.detail_detail)
    Button detailDetail;
    @BindView(R.id.detail_trailer)
    Button detailTrailer;
    @BindView(R.id.detail_still)
    Button detailStill;
    @BindView(R.id.detail_cinecism)
    Button detailCinecism;
    @BindView(R.id.detail_back)
    ImageView detailBack;
    @BindView(R.id.detail_buy)
    ImageView detailBuy;
    private UserPresenter<DetailActivity> presenter;
    private String id;
    @SuppressWarnings("FieldCanBeLocal")
    private SharedPreferences sp;
    private PopupWindow pop1;
    private CheckPresenter checkPresenter;
    private String userId;
    private String sessionId;
    private DetailBean.ResultBean result;
    private int page=1;
    private CheckCommentPresenter commentPresenter;
    private RecyclerView rec3;
    private List<CommentBean.ResultBean> result1;
    private PublishCommentPresenter publishCommentPresenter;
    private JSONArray jsonArray1;
    private PopupWindow pop2;
    private ImageView hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        //查看电影详情
        presenter = new UserPresenter<DetailActivity>(this);
        presenter.attach(this);
        //根据id查询电影详情
        checkPresenter = new CheckPresenter(this);
        checkPresenter.attach(this);
        //查看评论
        commentPresenter = new CheckCommentPresenter(this);
        commentPresenter.attach(this);
        //发表评论
        publishCommentPresenter = new PublishCommentPresenter(this);
        publishCommentPresenter.attach(this);
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");
        //网络判断
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        jsonArray1 = new JSONArray();
        try {
            jsonObject.put("id", id);
            jsonObject.put("userId", userId);
            jsonObject.put("sessionId", sessionId);
            jsonArray.put(jsonObject);

            jsonObject1.put("id", id);
            jsonObject1.put("userId", userId);
            jsonObject1.put("sessionId", sessionId);
            jsonObject1.put("page", page+"");
            jsonArray1.put(jsonObject1);
                //根据id查看电影详情
                presenter.dataPre(jsonArray, 3);
                //查看评论
                commentPresenter.checkByIdDataPre(jsonArray1,4);
                //传递到根据id查询电影p层
                checkPresenter.checkByIdDataPre(sessionId, userId, Integer.parseInt(id));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 事件点击
     * @param view
     */
    @OnClick({R.id._detail_follow, R.id.detail_detail, R.id.detail_trailer, R.id.detail_still, R.id.detail_cinecism, R.id.detail_back, R.id.detail_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id._detail_follow:
                //关注
                break;
            case R.id.detail_detail:
                //详情
                detail();
                break;
            case R.id.detail_still:
                //剧照
                still();
                break;
            case R.id.detail_trailer:
                //预告
                announce();
                break;
            case R.id.detail_cinecism:
                //影评
                cinecism();
                break;
            case R.id.detail_back:
                //返回
                finish();
                break;
            case R.id.detail_buy:
                //购票
                //创建集合
                ArrayList<DetailBean.ResultBean> list = new ArrayList<>();
                String name = result.getName();//电影id
                String imageUrl = result.getImageUrl();//图片
                String movieTypes = result.getMovieTypes();//类型
                String duration = result.getDuration();//导演
                String director = result.getDirector();//时长
                String placeOrigin = result.getPlaceOrigin();//产地
                Intent intent = new Intent(DetailActivity.this, SelectCinemaActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("movieTypes",movieTypes);
                intent.putExtra("duration",duration);
                intent.putExtra("director",director);
                intent.putExtra("placeOrigin",placeOrigin);
                startActivity(intent);
                break;
        }
    }

    /**
     * 接收返回的详情的数据
     *
     * @param data
     */

    @Override
    public void onData(DetailBean data) {
        result = data.getResult();
    }


    /**
     * 详情的点击
     *
     * @param
     */
    private void detail() {

        //详情
        View view1 = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_pop_layout, null, false);
        TextView chandi = view1.findViewById(R.id.pop_detail_chandi);
        ImageView hide = view1.findViewById(R.id.pop_detail_hide);
        TextView daoyan = view1.findViewById(R.id.pop_detail_daoyan);
        ImageView image = view1.findViewById(R.id.pop_detail_image);
        TextView leixing = view1.findViewById(R.id.pop_detail_leixing);
        TextView time = view1.findViewById(R.id.pop_detail_time);
        TextView jianjie = view1.findViewById(R.id.pop_detail_jianjie);
        pop1 = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置响应点击事件
        pop1.setTouchable(true);
        //设置背景
        pop1.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置响应外部点击事件
        pop1.setOutsideTouchable(true);
        //焦点
        pop1.setFocusable(true);
        //设置显示和消失的动画
        pop1.setAnimationStyle(R.style.mypopwindow_anim_style);
        //显示
        pop1.showAsDropDown(detailName, 0, 0);
        //点击让弹框消失
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop1.isShowing()) {
                    pop1.dismiss();
                }
            }
        });
        //赋值
        Glide.with(DetailActivity.this).load(result.getImageUrl())
                .placeholder(R.drawable.loging)//加载中图片
                .error(R.mipmap.error)//加载失败
                .into(image);
        leixing.setText("类型: " + result.getMovieTypes());
        daoyan.setText("导演: " + result.getDirector());
        time.setText("时长: " + result.getDuration());
        chandi.setText("产地: " + result.getPlaceOrigin());
        jianjie.setText("简介: " + result.getSummary());
    }

    /**
     * 预告
     *
     * @param
     */
    private void announce() {
        View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_pop_announce_layout, null);
        hide = view.findViewById(R.id.pop_detail_announce_hide);
        RecyclerView rec = view.findViewById(R.id.pop_detail_announce_rec);
        //创建线性布局
        rec.setLayoutManager(new LinearLayoutManager(this));
        pop2 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置响应点击事件
        pop2.setTouchable(true);
        //设置背景
        JCVideoPlayer.releaseAllVideos();
        pop2.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置响应外部点击事件
        pop2.setOutsideTouchable(true);
        //焦点
        pop2.setFocusable(true);
        //设置显示和消失的动画
        pop2.setAnimationStyle(R.style.mypopwindow_anim_style);
        //显示
        pop2.showAsDropDown(detailName, 0, 20);
        //点击让弹框消失
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    pop2.dismiss();
                    //----
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int width = displayMetrics.widthPixels;
                    int heisht = displayMetrics.heightPixels;
                    if (width<heisht){
                        JCVideoPlayer.releaseAllVideos();
                    }
            }
        });
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;  //横向
        JCVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;  //纵向

        List<DetailBean.ResultBean.ShortFilmListBean> shortFilmList = result.getShortFilmList();
        DetailAnnounceAdapter adapter = new DetailAnnounceAdapter(DetailActivity.this, shortFilmList);
        rec.setAdapter(adapter);
        //消失监听听
       pop2.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int width = displayMetrics.widthPixels;
                int heisht = displayMetrics.heightPixels;
                if (width<heisht){
                    JCVideoPlayer.releaseAllVideos();
                }
            }
        });

    }

    /**
     * 剧照
     *
     * @param
     */
    private void still() {
        List<String> posterList = result.getPosterList();
        View view2 = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_pop_still_layout, null, false);
        RecyclerView rec2 = view2.findViewById(R.id.pop_detail_still_rec);
        ImageView hide = view2.findViewById(R.id.pop_detail_still_hide);
        //创建瀑布流布局
        rec2.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        final PopupWindow pop3 = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //设置响应点击事件
        pop3.setTouchable(true);
        //设置背景
        pop3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //设置响应外部点击事件
        pop3.setOutsideTouchable(true);
        //焦点
        pop3.setFocusable(true);
        //设置显示和消失的动画
        pop3.setAnimationStyle(R.style.mypopwindow_anim_style);
        //显示
        pop3.showAsDropDown(detailName, 0, 20);
        //点击让弹框消失
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pop3.isShowing()) {
                    pop3.dismiss();
                }
            }
        });
        //赋值
        DetailStillAdapter stillAdapter = new DetailStillAdapter(DetailActivity.this, posterList);
        rec2.setAdapter(stillAdapter);
    }

    /**
     * 影评
     */
    private void cinecism() {
                View view3 = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_pop_cinecism_layout, null, false);
                rec3 = view3.findViewById(R.id.pop_detail_cinecism_rec);
                ImageView hide = view3.findViewById(R.id.pop_detail_cinecism_hide);
                final ImageView pingimage = view3.findViewById(R.id.pop_detail_cinecism_pingimage);
                rec3.setLayoutManager(new LinearLayoutManager(DetailActivity.this));

                final PopupWindow pop3 = new PopupWindow(view3, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //设置响应点击事件
                pop3.setTouchable(true);
                //设置背景
                pop3.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //设置响应外部点击事件
                pop3.setOutsideTouchable(true);
                //焦点
                pop3.setFocusable(true);
                //设置显示和消失的动画
                pop3.setAnimationStyle(R.style.mypopwindow_anim_style);
                //显示
                pop3.showAsDropDown(detailName, 0, 0);
                //点击让弹框消失
                hide.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pop3.isShowing()) {
                            pop3.dismiss();
                        }
                    }
                });
        final DetailCheckCommitAdapter commitAdapter = new DetailCheckCommitAdapter(DetailActivity.this, result1);
        rec3.setAdapter(commitAdapter);
        //评论弹框
        pingimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view4 = LayoutInflater.from(DetailActivity.this).inflate(R.layout.detail_pop_cinecism_child_layout, null, false);
               TextView fabiao = view4.findViewById(R.id.pop_detail_cinecism_child_fabiao);
               final EditText pingtext = view4.findViewById(R.id.pop_detail_cinecism_child_pingtext);
                final PopupWindow pop4 = new PopupWindow(view4, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //设置响应点击事件
                pop4.setTouchable(true);
                //设置背景
                pop4.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                //设置响应外部点击事件
                pop4.setOutsideTouchable(true);
                //焦点
                pop4.setFocusable(true);
                //设置显示和消失的动画
                pop4.setAnimationStyle(R.style.mypopwindow_anim_style);
                //显示
                pop4.showAtLocation(detailName,  Gravity.BOTTOM,0, 10);
                //发表
                fabiao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //发表评论
                        String trim = pingtext.getText().toString().trim();
                        //查看评论
                        commentPresenter.checkByIdDataPre(jsonArray1,4);
                        publishCommentPresenter.PublishCommentDataPre(sessionId,userId,id,trim);
                        pop4.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 接收根据id查询的电影数据
     *
     * @param onData
     */
    @Override
    public void onDada(CheckBean onData) {
        CheckBean.ResultBean result = onData.getResult();
        detailName.setText(result.getName());
        Glide.with(this).load(result.getImageUrl())
                .placeholder(R.drawable.loging)//加载中图片
                .error(R.mipmap.error)//加载失败
                .fitCenter()//填充
                .into(detailImage);
    }
    /**
     * 接收查看评论的数据
     *
     * @param onData
     */
    @Override
    public void onDada(CommentBean onData) {
        result1 = onData.getResult();
        //Log.i("sdsdsd", result1.size()+"");
    }
    /**
     * 解决全屏问题
     */


    /**
     *
     * @param onData
     */
    @Override
    public void onDada(PublishCommentBean onData) {
        String message = onData.getMessage();
        //Log.i("sssd",message);
    }

    /**
     * 解决内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        commentPresenter.destroy();
        checkPresenter.destroy();
        presenter.destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        if (height<width) {
            pop2.dismiss();

        }else {
            pop2.showAtLocation(hide,Gravity.CENTER, 0, 0);
//            JZVideoPlayer.releaseAllVideos();
        }

    }


}
