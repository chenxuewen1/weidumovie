package com.bw.movie.adapter;

import android.content.Context;
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
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.bean.ReleaseBean;

import java.util.ArrayList;
import java.util.List;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:影院
 */
public class CinemaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<RecommendBean.ResultBean> list;

    public CinemaAdapter(Context context, ArrayList<RecommendBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recommend_cinema_adapter_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.name.setText(list.get(i).getName());
        myViewHolder.address.setText(list.get(i).getAddress());
        myViewHolder.km.setText(list.get(i).getDistance()+"km");
        Glide.with(context).load(list.get(i).getLogo())
                .placeholder(R.drawable.loging)//加载中图片
                .error(R.mipmap.error)//加载失败
                .fitCenter()//填充
                .into(myViewHolder.logo);
        //点击
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = myViewHolder.getAdapterPosition();
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

        private final ImageView logo;
        private final TextView name;
        private final TextView km;
        private final TextView address;
        private final ImageView guanzhu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.recommend_cinema_adapter_name);
            km = itemView.findViewById(R.id.recommend_cinema_adapter_km);
            logo= itemView.findViewById(R.id.recommend_cinema_adapter_logo);
            guanzhu = itemView.findViewById(R.id.recommend_cinema_adapter_follow);
            address = itemView.findViewById(R.id.recommend_cinema_adapter_address);
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
