package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.bean.ReleaseBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:旋转木马
 */
public class CarrouselAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ReleaseBean.ResultBean> list;

    public CarrouselAdapter(Context context, ArrayList<ReleaseBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carrousel_adapter_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        String name = list.get(i).getName();
        String imageUrl = list.get(i).getImageUrl();
        myViewHolder.name.setText(name);
        Glide.with(context).load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(8)))//图片圆角
                .placeholder(R.drawable.loging)//加载中图片
                .error(R.mipmap.error)//加载失败
                .fitCenter()//填充
                .into(myViewHolder.image);
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

        private final ImageView image;
        private final TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.carrousel_name);
            image= itemView.findViewById(R.id.carrousel_image);
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
