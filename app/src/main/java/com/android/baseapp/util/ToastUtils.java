package com.android.baseapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hunanqi on 2017/8/24 0024.
 * ToastUtil 防止重复显示
 */

public class ToastUtils {
    private static Toast mToast;

    public static void showToast(Context mContext, String text, int duration) {
            mToast = Toast.makeText(mContext, text, duration);
        mToast.show();
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }

    public static void showToast(Context mContext,String text) {
        showToast(mContext, text, Toast.LENGTH_SHORT);
    }

}
