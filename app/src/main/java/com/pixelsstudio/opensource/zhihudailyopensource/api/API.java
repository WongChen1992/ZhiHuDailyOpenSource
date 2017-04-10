package com.pixelsstudio.opensource.zhihudailyopensource.api;

import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.ListNews;
import com.pixelsstudio.opensource.zhihudailyopensource.jsonbean.NewsDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by WongChen on 2017/2/20.
 */

public interface API {
    String HTTP = "http://";
    String HOST = "news-at.zhihu.com/api/";
    String URL_API_HOST = HTTP + HOST;

    /**
     * 最新新闻
     * @return
     */
    @GET("4/news/latest")
    Call<ListNews> listNews();

    /**
     * 新闻详情
     * @return
     */
    @GET("4/news/{id}")
    Call<NewsDetails> newsDetails(@Path("id") int id);

    /**
     * 过往新闻
     * @return
     */
    @GET("4/news/before/{date}")
    Call<ListNews> newsBefore(@Path("date") String date);
}
