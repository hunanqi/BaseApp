package com.android.baseapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author:clj
 * Date:2018/7/9
 * Description:
 */
public class NetWorkUtils {
    public static  boolean isNetworkConnected(Context context) {
         if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                 return mNetworkInfo.isAvailable();
                 }
             }
         return false;
         }
}
