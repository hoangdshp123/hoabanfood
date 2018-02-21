package com.example.hoang.hoabanfood1.Model.ModelApi;

import com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageYoutube.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    @GET("/youtube/v3/search?key=AIzaSyCSyE2hOEVqFrOgLgyurzw70BmcoiypUz0&channelId=UCBhgBmuPFbLLxnejr09lnAQ&part=snippet,id&order=date&maxResults=50")
    Call<Example> getMyJSON();

    @GET("/youtube/v3/playlistItems?part=snippet&key=AIzaSyCSyE2hOEVqFrOgLgyurzw70BmcoiypUz0&maxResults=20")
    Call<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example> getMyJSON1(@Query("playlistId") String id);

}
