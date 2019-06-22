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
import com.bw.movie.R;
import com.bw.movie.bean.RecommendBean;
import com.bw.movie.bean.ScreeningHall;

import java.util.ArrayList;
import java.util.List;

/*Time:2019/4/12
 *Author:chenxuewen
 *Description:电影档期
 */
public class MovieScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ScreeningHall.ResultBean> list;

    public MovieScheduleAdapter(Context context, ArrayList<ScreeningHall.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movieschedule_adapter_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.starttime.setText(list.get(i).getBeginTime()+"-----");
        myViewHolder.endtime.setText(list.get(i).getEndTime()+" end");
        myViewHolder.price.setText("￥"+list.get(i).getPrice());
        myViewHolder.movie.setText(list.get(i).getScreeningHall());
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

        private final TextView movie;
        private final TextView starttime;
        private final TextView endtime;
        private final TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            starttime = itemView.findViewById(R.id.movie_schedule_adapter_starttime);
            endtime = itemView.findViewById(R.id.movie_schedule_adapter_endtime);
            movie= itemView.findViewById(R.id.movie_schedule_adapter_movie);
            price = itemView.findViewById(R.id.movie_schedule_adapter_price);
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
