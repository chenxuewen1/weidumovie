package com.bw.movie.view.buy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.bean.CreateOrderBean;
import com.bw.movie.bean.PayOrderBean;
import com.bw.movie.bean.ZhiFuBaoBean;
import com.bw.movie.interfac.InData;
import com.bw.movie.interfac.InPresenter;
import com.bw.movie.presenter.homepresenter.buypresenter.CreateOrderPresenter;
import com.bw.movie.presenter.homepresenter.buypresenter.PayOrderPresenter;
import com.bw.movie.presenter.homepresenter.buypresenter.ZhiFuBaoPresenter;
import com.bw.movie.util.encryption.Md5;
import com.bw.movie.view.custom.SeatTable;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeatSelectionActivity extends AppCompatActivity implements InPresenter<CreateOrderBean>,InData.PayOrder, InData.ZhiFuBao {

    @BindView(R.id.seat_seattable)
    SeatTable table;
    @BindView(R.id.seat_selection_cinemaname)
    TextView cinemaname;
    @BindView(R.id.seat_selection_cinemaaddress)
    TextView cinemaaddress;
    @BindView(R.id.seat_selection_moviename)
    TextView moviename;
    @BindView(R.id.seat_selection_date)
    TextView date;
    @BindView(R.id.seat_selection_time)
    TextView time;
    @BindView(R.id.seat_selection_ting)
    TextView ting;
    @BindView(R.id.seat_selection_sum)
    TextView sum;
    @BindView(R.id.seat_selection_queding)
    ImageView queding;
    @BindView(R.id.seat_selection_cancel)
    ImageView cancel;
    private Intent intent;
    int selectedTableCoun=0;
    private SharedPreferences sp;
    private CreateOrderPresenter createOrder;
    private int flag=1;
    private PayOrderPresenter payOrderPresenter;
    private ZhiFuBaoPresenter zhiFuBaoPresenter;
    private String sessionId;
    private int userId;
    private Button pay;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String result = (String) msg.obj;
                    if(result.equals("9000")){
                        Toast.makeText(SeatSelectionActivity.this, "订单支付成功", Toast.LENGTH_SHORT).show();
                    }
                    if(result.equals("4000")){
                        Toast.makeText(SeatSelectionActivity.this, "订单支付失败", Toast.LENGTH_SHORT).show();
                    }
                    if(result.equals("6001")){
                        Toast.makeText(SeatSelectionActivity.this, "取消订单", Toast.LENGTH_SHORT).show();
                    }
                    if(result.equals("6002")){
                        Toast.makeText(SeatSelectionActivity.this, "网络连接出错", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);
        sp = getSharedPreferences("login", Context.MODE_PRIVATE);
        //接收传过来的数据
        intent = getIntent();
        ButterKnife.bind(this);
        createOrder = new CreateOrderPresenter(this);
        //绑定
        createOrder.attach(this);
        payOrderPresenter = new PayOrderPresenter(this);
        payOrderPresenter.attach(this);
        zhiFuBaoPresenter = new ZhiFuBaoPresenter(this);

        initData();
    }

    /**
     * 数据
     */
    private void initData() {
        sessionId = sp.getString("sessionId", "");
         final String id1 = sp.getString("userId", "");
        userId = Integer.parseInt(id1);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        String cinemanamee = intent.getStringExtra("cinemaname");
        final String id = intent.getStringExtra("id");//排期id
        String  beginTime= intent.getStringExtra("beginTime");//开始时间
        String endTime= intent.getStringExtra("endTime");//结束时间
        final double price= Double.parseDouble(intent.getStringExtra("price"));//票价
        String screeningHall = intent.getStringExtra("screeningHall");//放映大厅
        cinemaname.setText(cinemanamee);//电影院名称
        cinemaaddress.setText(address);//影院地址
        moviename.setText(name);//电影名称
        time.setText(beginTime+"--"+endTime);//时间
        ting.setText(screeningHall+"");//几号厅


        NumberFormat nf = new DecimalFormat("0.00");
        String format = nf.format(price *selectedTableCoun);
        sum.setText(format);
        table.setScreenName(name);//设置屏幕名称
        table.setMaxSelected(3);//设置最多选中
        table.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                //relativeLayout.setVisibility(View.VISIBLE);
                selectedTableCoun++;
                NumberFormat nf = new DecimalFormat("0.00");
                String format = nf.format(price * selectedTableCoun);
                sum.setText(format);
            }

            @Override
            public void unCheck(int row, int column) {
                selectedTableCoun--;
                NumberFormat nf = new DecimalFormat("0.00");
                String format = nf.format(price * selectedTableCoun);
                sum.setText(format);

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }
        });
        table.setData(9, 10);//设置总的座位
        //确认订单
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先判断是否选中
               if(!sum.getText().toString().equals("0.00")){
                   //创建订单
                   String sign = Md5.MD5(id1 + id + selectedTableCoun + "movie");
                   createOrder.dataOrderPre(id1, sessionId,id,selectedTableCoun,sign);
                   View view = LayoutInflater.from(SeatSelectionActivity.this).inflate(R.layout.seat_pop_pay_layout, null, false);
                   ImageView hide = view.findViewById(R.id.pop_seat_pay_hide);
                   RadioButton weixin = view.findViewById(R.id.pop_seat_pay_weixin);
                   RadioButton zhifubao = view.findViewById(R.id.pop_seat_pay_zhifubao);
                   pay = view.findViewById(R.id.pop_seat_pay_zhifu);
                   String s = sum.getText().toString();
                   pay.setText("微信支付"+s+"元");
                   weixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                       @Override
                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                           if (isChecked){
                               String s = sum.getText().toString();
                               pay.setText("微信支付"+s+"元");
                               flag=1;
                               //Log.i("flagff",flag+"");
                           }
                       }
                   });
                   zhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                       @Override
                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                           if(isChecked){
                               String s = sum.getText().toString();
                               pay.setText("支付宝支付"+s+"元");
                               flag=2;
                               //Log.i("flagff",flag+"");
                           }
                       }
                   });
                   final PopupWindow pop3 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                   //设置响应点击事件
                   pop3.setTouchable(true);
                   //设置背景
                   pop3.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                   //设置响应外部点击事件
                   pop3.setOutsideTouchable(true);
                   //焦点
                   pop3.setFocusable(true);
                   //设置显示和消失的动画
                   pop3.setAnimationStyle(R.style.mypopwindow_anim_style);
                   //显示
                   pop3.showAtLocation(queding,  Gravity.BOTTOM,0, 0);
                   //点击让弹框消失
                   hide.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if (pop3.isShowing()) {
                               pop3.dismiss();
                           }
                       }
                   });
                }else{
                   Toast.makeText(SeatSelectionActivity.this, "请先选择座位", Toast.LENGTH_SHORT).show();
               }
            }
        });
        //取消订单
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 接收创建订单
     * @param data
     */
    @Override
    public void onData(CreateOrderBean data) {
        final String orderId = data.getOrderId();
        Log.i("sss",data.getOrderId()+"");
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag==1){
                    payOrderPresenter.dataPayPre(userId,sessionId,flag,orderId);
                    Log.i("sss",userId+" "+sessionId+" "+flag+" "+orderId);
                }if(flag==2){
                    zhiFuBaoPresenter.dataZhiPayPre(userId,sessionId,flag,orderId);
                    Log.i("sss",userId+" "+sessionId+" "+flag+" "+orderId);
                }
            }
        });
    }

    /**
     * 微信支付结果
     * @param onData
     */
    @Override
    public void onDada(PayOrderBean onData) {
        //Log.i("success",onData.getMessage()+"微信");
        IWXAPI api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp("wxb3852e6a6b7d9516");
        PayReq req = new PayReq();
        req.appId           = onData.getAppId();//你的微信appid
        req.partnerId       = onData.getPartnerId();//商户号
        req.prepayId        = onData.getPrepayId();//预支付交易会话ID
        req.nonceStr        = onData.getNonceStr();//随机字符串
        req.timeStamp       = onData.getTimeStamp();//时间戳
        req.packageValue    = onData.getPackageValue();//扩展字段,这里固定填写Sign=WXPay
        req.sign            = onData.getSign();//签名
//              req.extData         = "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        api.sendReq(req);
    }

    /**
     * 支付宝支付结果
     * @param onData
     */
    @Override
    public void onDada(final ZhiFuBaoBean onData) {
        //Log.i("sssss",onData.getResult()+"支付宝");
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(SeatSelectionActivity.this);
                Map<String,String> result = alipay.payV2(onData.getResult(),true);
                String s = result.get("resultStatus");
                Message msg = new Message();
                msg.what = 0;
                msg.obj = s;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        createOrder.destroy();
        payOrderPresenter.destroy();
    }

}
