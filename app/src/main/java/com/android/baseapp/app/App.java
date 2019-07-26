package com.android.baseapp.app;

import android.content.Context;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import androidx.multidex.MultiDex;
import base.LibApp;
import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;
import utils.L;

/**
 * 全局上下文
 */

public class App extends LibApp {
    //全局变量
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initThreePart();
    }

    /**
     * 初始化第三方服务
     */
    private void initThreePart() {
        setRxJavaErrorHandler();
        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
    }

    public Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            //下面为调试用的代码，发布时可注释
            Writer info = new StringWriter();
            PrintWriter printWriter = new PrintWriter(info);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = info.toString();
            L.h(result);
        }
    };

    public static Context getContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            MultiDex.install(this);
    }

    /**
     * 捕捉Rxjava 额外扔出的异常 当解除订阅后
     */
    private void setRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                L.dm("throw ---->" + throwable.getMessage());
            }
        });
    }

}
