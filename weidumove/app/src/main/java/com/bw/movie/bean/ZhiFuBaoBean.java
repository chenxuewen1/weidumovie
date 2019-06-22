package com.bw.movie.bean;

/*Time:2019/4/23
 *Author:chenxuewen
 *Description:支付宝
 */
public class ZhiFuBaoBean {
    /**
     * result : alipay_sdk=alipay-sdk-java-3.1.0&app_id=2018080760951276&biz_content=%7B%22out_trade_no%22%3A%2220190423155900610%22%2C%22subject%22%3A%22%E5%85%AB%E7%BB%B4%E7%A7%BB%E5%8A%A8%E9%80%9A%E4%BF%A1%E5%AD%A6%E9%99%A2-%E7%BB%B4%E5%BA%A6%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.28%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fmobile.bwstudent.com%2FpayApiProd%2FaliPay%2FmovieNotification&sign=g7HoKL677qavGLzt6jBW0cceihc1YnH7BhL%2FC9cemhIYU1cHcKsAOR3nrFIf8zOLF1Mh67ScmOQxvvdBUK4QwQBs0QW89pPV8LwLTIipxYyAQt62h0vzs7%2FqoFmu5Zx5YHRXlZ4eesOmr1Ph5Sw96n3%2FA7JNIsAYrtNT6sxFrkoonq5wBze%2FvDeBEW%2BrQpkZ6j9u0bSQkJQBAYokT7UDogNy7EprL2Kg0mQ4HRtTS9TfxuhTxLkJzPfhx2gq9AIofmrPCOGV7FdsFiQc%2BZ5JtzR2zRkocvk24iQEMboGE6NKhJDnVu4VAIbWDL8ffMterp26JW7pBXICAsMKBV8Awg%3D%3D&sign_type=RSA2&timestamp=2019-04-23+16%3A03%3A10&version=1.0
     * message : ok
     * status : 0000
     */

    private String result;
    private String message;
    private String status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
