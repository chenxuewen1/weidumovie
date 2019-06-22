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
import com.bw.movie.bean.CommentBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/*Time:2019/4/12
 *Author:chenxuewen
 *Description:查看评论适配器
 */
public class DetailCheckCommitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CommentBean.ResultBean> list;
    boolean flag=false;
    public DetailCheckCommitAdapter(Context context, List<CommentBean.ResultBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_pop_cinecism_adapter_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.name.setText(list.get(i).getCommentUserName());
        myViewHolder.pinglun.setText(list.get(i).getCommentContent());
        String s = longToDate(list.get(i).getCommentTime());
        myViewHolder.time.setText(s);
        myViewHolder.dianzanshu.setText(list.get(i).getGreatNum()+"");
        myViewHolder.edtext.setText(list.get(i).getReplyNum()+"");
        Glide.with(context).load(list.get(i).getCommentHeadPic())
                .placeholder(R.drawable.loging)//加载中图片
                .error(R.mipmap.error)//加载失败
                .fitCenter()//填充
                .into(myViewHolder.image);
        //点赞

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //自定义viewholdwr
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image,image1;
        private final TextView name;
        private final TextView pinglun;
        private final TextView time;
        private final TextView dianzanshu;
        private final TextView edtext;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.pop_detail_cinecism_adapter_image);
            name = itemView.findViewById(R.id.pop_detail_cinecism_adapter_name);
            pinglun = itemView.findViewById(R.id.pop_detail_cinecism_adapter_pinglun);
            time = itemView.findViewById(R.id.pop_detail_cinecism_adapter_time);
            dianzanshu = itemView.findViewById(R.id.pop_detail_cinecism_adapter_dianzannum);
            image1= itemView.findViewById(R.id.pop_detail_cinecism_adapter_dianzan);
            edtext = itemView.findViewById(R.id.pop_detail_cinecism_adapter_pinglun_edtext);
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
    //转化为时间单位
    public static String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }
}
