package com.bw.movie.view.mymessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 *@time:2019/4/17
 *@author: chenxuewen
 *@description:我的信息
 */
public class MyMessageActivity extends AppCompatActivity {

    @BindView(R.id.my_message_back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.my_message_back)
    public void onViewClicked() {
        //销毁
        finish();
    }
}
