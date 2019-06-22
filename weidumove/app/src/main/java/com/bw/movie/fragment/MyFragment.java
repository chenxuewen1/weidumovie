package com.bw.movie.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.bean.FindUserBean;
import com.bw.movie.bean.UpLoadBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.presenter.mypresenter.FindUserPresenter;
import com.bw.movie.view.mymessage.MyBayActivity;
import com.bw.movie.view.mymessage.MyMessageActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/*Time:2019/4/11
 *Author:chenxuewen
 *Description:我的
 */
public class MyFragment extends Fragment implements InData.FindUser {

    @BindView(R.id.my_user_tou)
    ImageView myUserTou;
    @BindView(R.id.my_user_name)
    TextView myUserName;
    @BindView(R.id.my_user_sign)
    Button myUserSign;
    @BindView(R.id.my_user_message)
    ImageView myUserMessage;
    @BindView(R.id.my_user_with)
    ImageView myUserWith;
    @BindView(R.id.my_user_bay)
    ImageView myUserBay;
    @BindView(R.id.my_user_suggestion)
    ImageView myUserSuggestion;
    @BindView(R.id.my_user_check)
    ImageView myUserCheck;
    @BindView(R.id.my_user_quit)
    ImageView myUserQuit;
    Unbinder unbinder;
    private SharedPreferences sp;
    private String userId;
    private String sessionId;
    private String s;
    private static String result;
    private FindUserPresenter findUserPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        findUserPresenter = new FindUserPresenter(this);
        initData();
        return view;
    }

    /**
     * 数据
     */
    private void initData() {
        String headPic = sp.getString("headPic", "");
        String nickName = sp.getString("nickName", "");
        userId = sp.getString("userId", "");
        sessionId = sp.getString("sessionId", "");

        myUserName.setText(nickName);
        Glide.with(getActivity()).load(headPic)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(myUserTou);
        //点击选择图像
        myUserTou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.cinema_pop_layout, null, false);
                Button cinema = view.findViewById(R.id.pop_image_cinema);
                Button phote = view.findViewById(R.id.pop_image_phote);
                Button cancel = view.findViewById(R.id.pop_image_cancel);
                final PopupWindow pop4 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //设置响应点击事件
                pop4.setTouchable(true);
                //设置背景
                pop4.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //设置响应外部点击事件
                pop4.setOutsideTouchable(true);
                //焦点
                pop4.setFocusable(true);
                //设置显示和消失的动画
                pop4.setAnimationStyle(R.style.mypopwindow_anim_style);
                //显示
                pop4.showAtLocation(myUserTou,  Gravity.BOTTOM,0, 0);
                //相机
                cinema.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //6.0权限
                        if(Build.VERSION.SDK_INT>=23){
                            String[] mPermissionList = new String[]{Manifest.permission.CAMERA};
                            ActivityCompat.requestPermissions(getActivity(),mPermissionList,123);
                        }
                        //创建相机意图
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.addCategory("android.intent.category.DEFAULT");
                        startActivityForResult(intent,1);
                        pop4.dismiss();
                    }
                });
                //相册
                phote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //创建相册意图
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent,2);
                        pop4.dismiss();
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop4.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_user_tou, R.id.my_user_name, R.id.my_user_sign, R.id.my_user_message, R.id.my_user_with, R.id.my_user_bay, R.id.my_user_suggestion, R.id.my_user_check, R.id.my_user_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_user_sign:
                //签到
                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_user_message:
                //我的信息
                startActivity(new Intent(getActivity(),MyMessageActivity.class));
                break;
            case R.id.my_user_with:
                //我的关注
                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_user_bay:
                //购票记录
                startActivity(new Intent(getActivity(),MyBayActivity.class));
                break;
            case R.id.my_user_suggestion:
                //意见反馈
                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_user_check:
                //检查更新
                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_user_quit:
                //退出登录
                Toast.makeText(getActivity(), "正在开发中...", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    /**
     * 获取请求码
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final Gson gson = new Gson();
        final String message = "上传成功";
        //判断 data是否为空---------解决取消崩溃
        if (null != data) {

            if (requestCode== 2){
                Uri data1 = data.getData();
                try {
                    final Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data1);
                    Log.i("sss",bitmap+"");
                    final File file = compressImage(bitmap);
                    Log.i("sss",file+"");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String post = getPost(getActivity(), file, userId, sessionId);
                            UpLoadBean successBean = gson.fromJson(post, UpLoadBean.class);

                            if (successBean.getMessage().equals(message)){
                                findUserPresenter.dataFindPre(userId,sessionId);
                            }
                        }
                    }).start();
                }catch (IOException e) {

                }
            }else  if(requestCode==1){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        final File file = compressImage(bitmap);
                        String post = getPost(getActivity(), file, userId, sessionId);

                        UpLoadBean successBean = gson.fromJson(post, UpLoadBean.class);

                        if (successBean.getMessage().equals(message)){
                            findUserPresenter.dataFindPre(userId,sessionId);
                        }

                    }
                }).start();
            }
        }else {
            Toast.makeText(getActivity(), "您取消了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 图片上传
     */
    public static String getPost(Context context,File file,String userId,String sessionId){

        String apiurl ="http://mobile.bwstudent.com/movieApi/user/v1/verify/uploadHeadPic";
        String BOUNDARY = "******";
        //抛异常
        /*try {
            //创建网络地址请求
            URL url = new URL(apiurl);
            //建立连接
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            //设置参数
            conn.setRequestMethod("POST");//设置请求方式
            conn.setReadTimeout(5000);//设置读取超时
            conn.setConnectTimeout(5000);//设置连接超时
            conn.setDoInput(true);//设置支持输入流
            conn.setDoOutput(true);//设置支持输出流
            //设置请求属性
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            //请求的网址 创建一个URL对象 --抛异常
            URL url = new URL(apiurl);
            //建立一个链接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //设置  请求方式
            httpURLConnection.setRequestMethod("POST");
            //设置 超时时间
            httpURLConnection.setReadTimeout(5000);
            //允许输入流
            httpURLConnection.setDoInput(true);
            //允许 输出流
            httpURLConnection.setDoOutput(true);

            //请求头----http://tools.jb51.net/table/http_header

            //指定客户端能够接受的内容类型*/* 表示愿意接受任意类型的资源
            httpURLConnection.setRequestProperty("Accept", "*/*");
            //表示是否需要持久连接
            httpURLConnection.setRequestProperty("Connection", "keep-alive");
            //设置服务器可以接受的字符编码集
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            //HTTP授权的授权证书
            httpURLConnection.setRequestProperty("X-bocang-Authorization", "token");
            //请求的与实体对应的MIME信息 MIME区分资源类型
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            //请求头参数
            httpURLConnection.setRequestProperty("userId",userId);
            //请求头参数
            httpURLConnection.setRequestProperty("sessionId",sessionId);

            httpURLConnection.setRequestProperty("Content-Type","multipart/form-data;boundary="+BOUNDARY);
            //建立连接
            httpURLConnection.connect();

            //创建DataOutStream写入数据
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());

            StringBuffer stringBuffer = new StringBuffer();

            stringBuffer.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
            stringBuffer.append("Content-Disposition: form-data; name=\"" + "image" + "\"; filename=\"" + file + "\"\r\n");
            stringBuffer.append("Content-Type:" + "image/jpg" + "\r\n\r\n");
            dataOutputStream.write(stringBuffer.toString().getBytes());
            //创建DataInputStream读取
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            //转换为字节
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                //将所有字节写入
                dataOutputStream.write(bytes, 0, len);
            }
            in.close();

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();

            dataOutputStream.write(endData);
            // 关闭流
            dataOutputStream.flush();
            dataOutputStream.close();
            // 获取响应
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode==200){
                //获取输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                //读取输入流
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = bufferedReader.read()) != -1){
                    //通过while循环去一行行读取响应数据
                    sb1.append((char) ss);
                }
                //获取到所有响应数据
                result = sb1.toString();
            }else{

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
        }


        return result;
    }
    /**
     * 用来转换类型 将Bitmap类型转换为Uri类型
     */
    public  static  File compressImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到outputStream中
        int options=100;
        while (outputStream.toByteArray().length/1024>500){
            //循环判断如果压缩后图片是否大于500kb，大于继续压缩
            outputStream.reset();//重置outputStream即清空outputStream
            options-=10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG,options,outputStream);//这里压缩options，把压缩后的数据存放到outputStream中
        }
        //以当前时间命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        //图片名
        String filmname = format.format(date);
        //存储到外存空间
        File file = new File(Environment.getExternalStorageDirectory(), filmname + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(outputStream.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 查询用户信息返回来的信息
     * @param onData
     */
    @Override
    public void onDada(FindUserBean onData) {
        //重新赋值
        FindUserBean.ResultBean result = onData.getResult();
        myUserName.setText(result.getNickName());
        Glide.with(getActivity()).load(result.getHeadPic())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(myUserTou);
    }
}
