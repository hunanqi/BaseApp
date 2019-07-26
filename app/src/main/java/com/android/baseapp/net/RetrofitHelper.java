package com.android.baseapp.net;

import com.android.baseapp.app.AppConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.L;
import utils.NullStringToEmptyAdapterFactory;

/**
 * Editor  Misuzu
 * Date   2017/7/26
 * Api接口管理类
 */

public class RetrofitHelper{

    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;
    private ApiService mApiService;

    private RetrofitHelper()
    {
        initOkHttp();
        initRetrofit();
    }
    public static RetrofitHelper getInstance()
    {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder
    {
        private static RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    //初始化OkHttp
    private void initOkHttp()
    {
        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        //设置打印
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    L.d(message);
                }
            });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mBuilder.addNetworkInterceptor(loggingInterceptor);
        mBuilder.addInterceptor(new TokenInterceptor());
        //设置超时
        mBuilder.connectTimeout(10, TimeUnit.SECONDS);
        mBuilder.readTimeout(10, TimeUnit.SECONDS);
        mBuilder.writeTimeout(10, TimeUnit.SECONDS);
        //错误重连
        mBuilder.retryOnConnectionFailure(true);

        mBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });
        // 跳过https 验证
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] x509Certificates,
                    String s) throws java.security.cert.CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            mBuilder.sslSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBuilder.protocols(Collections.singletonList(Protocol.HTTP_1_1));
        mOkHttpClient = mBuilder.build();
    }

    //初始化Retrofit
    private void initRetrofit() {
        // 将null 转换 为 ""
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .create();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
    }


    public ApiService getService()
    {
        if (mApiService == null)
            mApiService =  mRetrofit.create(ApiService.class);
        return mApiService;
    }

}
