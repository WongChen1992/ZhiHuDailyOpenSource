package com.pixelsstudio.opensource.zhihudailyopensource.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by WongChen on 2017/2/20.
 */

public class ApiClient {
    private static API mRestAPI;

    public static API getAPI() {
        if (mRestAPI == null) {
            Retrofit retrofit =
                    new Retrofit.Builder().baseUrl(API.URL_API_HOST)
                            .addConverterFactory(GsonConverterFactory.create()).build();

            mRestAPI = retrofit.create(API.class);
        }
        return mRestAPI;
    }

}
