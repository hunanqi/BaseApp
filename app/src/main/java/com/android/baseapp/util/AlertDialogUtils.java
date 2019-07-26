package com.android.baseapp.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.baseapp.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Created by hunanqi on 2017/9/7 0007.
 * 提示对话框
 */

public class AlertDialogUtils {
    public static String password="";
    private static boolean isShow=false;//登录对话框是否显示，防止出现多个
    //正常提示对话框
    public static void showDialog(Context context, String msg, final OnPositiveListener postiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setNegativeButton(context.getString(R.string.user_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(context.getString(R.string.user_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postiveListener.onPostive();
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    //正常提示对话框
    public static void showDialog(Context context, String msg, final OnLoginListener postiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg)
                .setNegativeButton(context.getString(R.string.user_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postiveListener.onNegative();
                        dialog.cancel();
                    }
                })
                .setPositiveButton(context.getString(R.string.user_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        postiveListener.onPostive();
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    public interface OnPositiveListener {
        void onPostive();
    }

    public interface OnLoginListener {
        void onPostive();
        void onNegative();
    }

    /**
     * 加载对话框
     *
     * @param context
     * @param msg
     */
    public static AlertDialog showLoding(AppCompatActivity context, String msg, boolean isCancel) {
         AlertDialog dialog;
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading_layout, null);
        TextView textMsg = (TextView) inflate.findViewById(R.id.loading_text);
        if (!TextUtils.isEmpty(msg))
            textMsg.setText(msg);
        dialog = new AlertDialog.Builder(context, R.style.dialog)
                .setView(inflate)
                .setCancelable(isCancel)
                .show();
        return dialog;
    }


}
