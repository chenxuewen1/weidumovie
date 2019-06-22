package com.bw.movie.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.UserPresenter;
import com.bw.movie.util.InternetNetWork;
import com.bw.movie.util.encryption.EncryptUtil;
import com.bw.movie.util.encryption.ValidateTest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @time:2019/4/11
 * @author: chenxuewen
 * @description:账号注册
 */
public class RegistActivity extends AppCompatActivity implements InPresenter<RegisterBean> {

    @BindView(R.id.regist_name)
    EditText registName;
    @BindView(R.id.regist_sex)
    EditText registSex;
    @BindView(R.id.regist_date)
    EditText registDate;
    @BindView(R.id.regist_phone)
    EditText registPhone;
    @BindView(R.id.regist_emaile)
    EditText registEmaile;
    @BindView(R.id.regist_password)
    EditText registPassword;
    @BindView(R.id.regist_reg)
    Button registReg;
    private boolean phoneNumber;
    private String pass;
    private  int flag=1;
    private UserPresenter<RegistActivity> reginster;
    private String phone;
    private String pass1;
    private boolean network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        //registerPresenter = new RegisterPresenter(this);
        reginster = new UserPresenter<>(this);
        reginster.attach(this);
        network = InternetNetWork.isNetwork(this);
    }


    @OnClick({R.id.regist_reg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.regist_reg:
                phone = registPhone.getText().toString().trim();
                pass1 = registPassword.getText().toString().trim();
                String email = registEmaile.getText().toString().trim();
                String date = registDate.getText().toString().trim();
                String name = registName.getText().toString().trim();
                String sex = registSex.getText().toString().trim();
                phoneNumber = ValidateTest.isTelPhoneNumber(phone);
                if(!phoneNumber || phone.length()!=11){
                    Toast.makeText(RegistActivity.this, "手机格式不正确或不够11位", Toast.LENGTH_SHORT).show();
                    return;
                }
                String encrypt = EncryptUtil.encrypt(pass1);
                if(pass1.length()<6){
                    Toast.makeText(RegistActivity.this, "密码最低六位数", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(sex.equals("男")){
                    flag=1;
                }else{
                    flag=2;
                }
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name",name);
                    jsonObject.put("phone", phone);
                    jsonObject.put("pwd",encrypt);
                    jsonObject.put("pwd2",encrypt);
                    jsonObject.put("flag",flag);
                    jsonObject.put("date",date);
                    jsonObject.put("email",email);
                    jsonArray.put(jsonObject);
                    if(network){
                        reginster.dataPre(jsonArray,2);
                    }else{
                        Toast.makeText(this, "哎呀！网络跑走了", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
    /**
     * 接收公共接口p的数据
     * @param data
     */
    @Override
    public void onData(RegisterBean data) {
        String message = data.getMessage();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if(data.getStatus().equals("0000")){
            Intent intent = new Intent();
            intent.putExtra("phone",phone);
            intent.putExtra("pass",pass);
            setResult(1,intent);
            //销毁页面
            finish();
        }
    }

    /**
     * 解决内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        reginster.destroy();
    }
}
