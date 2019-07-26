package com.android.baseapp.net;

import com.android.baseapp.app.AppConstant;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Date   2017/8/8
 * 添加Token 拦截器
 */

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request mNewRequest =
                chain.request()
                .newBuilder()
                .build();
        // 如果用户已登录 传递uid token
       if (UserManager.isLogin())
            mNewRequest =
                     mNewRequest.newBuilder()
                    .addHeader(AppConstant.AUTHORIZATION, UserManager.getToken())
                    .build();
        return chain.proceed(mNewRequest);
    }

}
