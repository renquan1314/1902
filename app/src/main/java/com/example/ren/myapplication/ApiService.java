package com.example.ren.myapplication;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by ren on 2019/9/18.
 */

public interface ApiService {
    String gankurl = "http://gank.io/api/";

    @GET("data/%E7%A6%8F%E5%88%A9/10/0")
    Observable<Bean> getjson();
}
