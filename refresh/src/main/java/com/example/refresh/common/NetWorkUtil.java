package com.example.refresh.common;

import com.example.refresh.bean.DouBan;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Shinelon on 2017/3/26.
 */
public interface NetWorkUtil {
    //    https://api.douban.com/v2/movie/top250?start=0&count=10
    @GET("v2/movie/top250")
    Observable<DouBan> getDouBan(@Query("start") int start,
                                 @Query("count") int count);
}
