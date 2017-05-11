package com.pixelsstudio.opensource.zhihudailyopensource.api;

import android.util.Log;

import com.pixelsstudio.opensource.zhihudailyopensource.app.AppContext;
import com.pixelsstudio.opensource.zhihudailyopensource.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WongChen on 2017/2/20.
 */

public class ApiClient {
    private static API mRestAPI;

    //有无网络都将缓存的拦截器
//    private static Interceptor netInterceptor = new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request();
//            Response response = chain.proceed(request);
//            int maxAge = 60 * 60 * 24 * 7;//缓存过期时间为七天
//            return response.newBuilder()
//                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                    .removeHeader("Cache-Control")
//                    .header("Cache-Control", "public, max-age=" + maxAge)
//                    .build();
//        }
//    };

    //拦截器
    private static Interceptor netInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetworkUtils.isNetworkAvailable(AppContext.getContextObject())){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if(NetworkUtils.isNetworkAvailable(AppContext.getContextObject())){
                //有网的时候读接口上的@Headers里的配置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }else{
                int maxTime = 60 * 60 * 24 * 7;//缓存过期时间为七天
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale="+maxTime)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    public static API getAPI() {
        if (mRestAPI == null) {
            File cacheFile = new File(AppContext.getContextObject().getCacheDir(), "newsList");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //10Mb
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(netInterceptor)
                    .addNetworkInterceptor(netInterceptor)
                    .cache(cache).build();

            Retrofit retrofit =
                    new Retrofit.Builder().baseUrl(API.URL_API_HOST).client(client)
                            .addConverterFactory(GsonConverterFactory.create()).build();

            mRestAPI = retrofit.create(API.class);
        }
        return mRestAPI;
    }
}
