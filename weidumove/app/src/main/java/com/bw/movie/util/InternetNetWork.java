package com.bw.movie.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*Time:2019/4/24
 *Author:chenxuewen
 *Description:网络状态
 */
public class InternetNetWork {
    public static boolean isNetwork(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null){

            return info.isAvailable();
        }

        return false;
    }
}
