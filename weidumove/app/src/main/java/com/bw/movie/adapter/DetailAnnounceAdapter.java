package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.bean.DetailBean;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:视频适配器
 */
public class DetailAnnounceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<DetailBean.ResultBean.ShortFilmListBean> list;

    public DetailAnnounceAdapter(Context context, List<DetailBean.ResultBean.ShortFilmListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_announce_adapter_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;

        String videoUrl = list.get(i).getVideoUrl();
        //Log.e("gs", "onBindViewHolder: "+videoUrl );
        boolean b = myViewHolder.video.setUp(videoUrl, JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if(b){
            Glide.with(context).load(list.get(i).getImageUrl())
                    .placeholder(R.drawable.loging)//加载中图片
                    .error(R.mipmap.error)//加载失败
                    .fitCenter()//填充
                    .into(myViewHolder.video.thumbImageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //自定义viewholdwr
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final JCVideoPlayerStandard video;

        //private final ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            video = itemView.findViewById(R.id.detail_announce_adapter_video);
            //image= itemView.findViewById(R.id.carrousel_image);
        }
    }
    //定义接口
    public interface OnItemListener{
        void onPosition(int position);
    }
    //声明
    private OnItemListener itemListener;
    //监听
    public void setItemListener(OnItemListener itemListener) {
        this.itemListener = itemListener;
    }
}
