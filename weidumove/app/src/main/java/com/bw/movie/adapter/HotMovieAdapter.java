package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.HotMovieBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:热门电影
 */
public class HotMovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<HotMovieBean.ResultBean> list;

    public HotMovieAdapter(Context context, ArrayList<HotMovieBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotmovie_adapter_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        String name = list.get(i).getName();
        String imageUrl = list.get(i).getImageUrl();
        myViewHolder.name.setText(name);
        myViewHolder.image.setImageURI(Uri.parse(imageUrl));
        final int position = myViewHolder.getAdapterPosition();
        //条目点击
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemListener!=null){
                    itemListener.onPosition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    //自定义viewholdwr
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final SimpleDraweeView image;
        private final TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hot_filmname);
            image= itemView.findViewById(R.id.hot_image);
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
