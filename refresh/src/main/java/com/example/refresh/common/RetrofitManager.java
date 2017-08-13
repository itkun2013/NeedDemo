package com.example.refresh.common;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Shinelon on 2017/3/26.
 */
public class RetrofitManager {
    public static final String BASE_URL = " https://api.douban.com/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    public NetWorkUtil netWorkUtil;
    //构造方法私有
    private RetrofitManager() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        //创建代理对象
        netWorkUtil = retrofit.create(NetWorkUtil.class);
    }
    private static class SingletonHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }
    //获取单例
    public static RetrofitManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
