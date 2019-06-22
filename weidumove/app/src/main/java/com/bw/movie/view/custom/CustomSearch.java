package com.bw.movie.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;

/*Time:2019/4/16
 *Author:chenxuewen
 *Description:自定义组合控件
 */
public class CustomSearch extends LinearLayout {
    public CustomSearch(Context context) {
        super(context);
    }

    public CustomSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        final View view= LayoutInflater.from(context).inflate(R.layout.search_layout,null,false);
        addView(view);
        TextView search= view.findViewById(R.id.custom_search);
        final EditText data =view.findViewById(R.id.custom_search_data);
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick!=null){
                    onClick.onSearch(data,view);
                }
            }
        });
    }
    //自定义接口回调
    public interface OnClick{
        void onSearch(EditText data, View view);
    }
    private OnClick onClick;

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }
}
