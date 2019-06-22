package com.bw.movie.view.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.UserPresenter;
import com.bw.movie.util.InternetNetWork;
import com.bw.movie.util.encryption.EncryptUtil;
import com.bw.movie.util.encryption.ValidateTest;
import com.bw.movie.util.wexin.WeiXinUtil;
import com.bw.movie.view.BotNavActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements InPresenter<LoginBean> {
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_eye)
    ImageView loginEye;
    @BindView(R.id.login_reg)
    TextView loginReg;
    @BindView(R.id.login_log)
    Button loginLog;
    @BindView(R.id.login_wechat)
    ImageView loginWechat;
    @BindView(R.id.login_checkbox_zi)
    CheckBox checkboxZi;
    @BindView(R.id.login_checkbox_ji)
    CheckBox checkboxJi;
    private UserPresenter<LoginActivity> login;
    private SharedPreferences sp;
    private boolean network;

    /**
     * @time:2019/4/11
     * @author: chenxuewen
     * @description:账号登录
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        login = new UserPresenter<>(this);
        login.attach(this);
        //网络判断
        network = InternetNetWork.isNetwork(this);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String phone = sp.getString("phone", "");
        String pass = sp.getString("password", "");
        loginPhone.setText(phone);
        //判断自动登录
        if(sp.getBoolean("自动登录",false)){
            loginPassword.setText(pass);
            checkboxJi.setChecked(true);
            String encrypt = EncryptUtil.encrypt(pass);
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("phone",phone);
                jsonObject.put("pwd",encrypt);
                jsonArray.put(jsonObject);
                login.dataPre(jsonArray,1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(sp.getBoolean("记住密码",false)){
            loginPassword.setText(pass);
            checkboxJi.setChecked(true);
        }
    }

    @OnClick({R.id.login_eye, R.id.login_reg, R.id.login_log, R.id.login_wechat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_eye:
                //新建图片
                break;
            case R.id.login_reg:
                //注册
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivityForResult(intent,0);
                break;
            case R.id.login_log:
                //登录
                String phone = loginPhone.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                String encrypt = EncryptUtil.encrypt(password);
                boolean phoneNumber = ValidateTest.isTelPhoneNumber(phone);
                if(network){
                    if(phoneNumber && password.length()>=6){
                        //获取编辑器
                        SharedPreferences.Editor edit = sp.edit();
                        edit.putBoolean("自动登录",checkboxZi.isChecked());
                        edit.putBoolean("记住密码",checkboxJi.isChecked());
                        edit.putString("phone",phone);
                        edit.putString("password",password);
                        edit.commit();
                        JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("phone",phone);
                            jsonObject.put("pwd",encrypt);
                            jsonArray.put(jsonObject);
                            login.dataPre(jsonArray,1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(this, "手机号或密码不正确", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "哎呀！网络跑走了", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_wechat:
                //第三方登录
                // 微信登录
                if(network){
                    if (!WeiXinUtil.success(LoginActivity.this)) {
                        Toast.makeText(LoginActivity.this, "请先安装应用", Toast.LENGTH_SHORT).show();
                    } else {
                        //  验证
                        SendAuth.Req req = new SendAuth.Req();
                        req.scope = "snsapi_userinfo";
                        req.state = "wechat_sdk_demo_test_neng";
                        WeiXinUtil.reg(LoginActivity.this).sendReq(req);
                    }
                }else{
                    Toast.makeText(this, "哎呀！网络跑走了", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 登录返回的数据
     * @param data
     */
    @Override
    public void onData(LoginBean data) {
        //Log.i("sss",data.getMessage());
        LoginBean.ResultBean result = data.getResult();
        String sessionId = result.getSessionId();
        int userId = result.getUserId();
        int birthday = (int) result.getUserInfo().getBirthday();
        int id = result.getUserInfo().getId();
        int lastLoginTime = (int) result.getUserInfo().getLastLoginTime();
        String nickName = result.getUserInfo().getNickName();
        String phone = result.getUserInfo().getPhone();
        int sex = result.getUserInfo().getSex();
        String headPic = result.getUserInfo().getHeadPic();
        if(data.getStatus().equals("0000")){
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, BotNavActivity.class);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("sessionId",sessionId);
            edit.putString("userId",userId+"");
            edit.putString("birthday",birthday+"");
            edit.putString("id",id+"");
            edit.putString("lastLoginTime",lastLoginTime+"");
            edit.putString("nickName",nickName);
            edit.putString("phone",phone);
            edit.putString("sex",sex+"");
            edit.putString("headPic",headPic);
            edit.commit();
            startActivity(intent);
            //销毁页面
            finish();
        }
    }
    /**
     * 接收注册返回的值
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            String pass = data.getStringExtra("pass");
            String phone = data.getStringExtra("phone");
            loginPhone.setText(phone);
            loginPassword.setText(pass);
        }
    }

    /**
     * 解决内存泄漏
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        login.destroy();
    }
}
