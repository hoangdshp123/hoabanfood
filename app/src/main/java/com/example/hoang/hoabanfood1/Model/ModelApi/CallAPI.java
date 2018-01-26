package com.example.hoang.hoabanfood1.Model.ModelApi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.hoang.hoabanfood1.Interface.CallAPIYoutubeSuccess;
import com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageYoutube.Example;
import com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageYoutube.Item;
import com.example.hoang.hoabanfood1.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vancu on 20/11/2017.
 */

public class CallAPI {

    public static void Get_Video(final Context context){

        Call<Example> mCall = ApiUtils.getApiService().getMyJSON();
        mCall.enqueue(new Callback<Example>() {

            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                Example example = response.body();
                List<Item> list = example.getItems();
                List<String> listplaylistid = new ArrayList<>();
                List<String> listplaylisttitle = new ArrayList<>();
                for (int i = 0; i< list.size();i++){
                    if(list.get(i).getId().getPlaylistId() != null) {
                        listplaylistid.add(list.get(i).getId().getPlaylistId());
                        listplaylisttitle.add(list.get(i).getSnippet().getTitle());
                    }
                }
                for(int j = 0 ; j< listplaylisttitle.size();j++){
                    SharedPrefsUtils.setStringPreference(context,"playlisttitle"+j,listplaylisttitle.get(j));

                    Log.d("nnn",listplaylisttitle.get(j));
                }
                for(int j = 0 ; j< listplaylistid.size();j++){
                    SharedPrefsUtils.setStringPreference(context,"playlistid"+j,listplaylistid.get(j));

                    Log.d("hhh",listplaylistid.get(j));
                }
                SharedPrefsUtils.setStringPreference(context,"listplaylistidsize", String.valueOf(listplaylistid.size()));

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(context, "Không thể kết nối !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void Get_ListVideo(final Context context, String id, final CallAPIYoutubeSuccess callAPIYoutubeSuccess){

        Call<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example> mCall = ApiUtils.getApiService().getMyJSON1(id);
        mCall.enqueue(new Callback<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example>() {

            @Override
            public void onResponse(Call<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example> call, Response<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example> response) {
                com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example example = response.body();
                List<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Item> listvideo1 = example.getItems();
                Log.d("lol", String.valueOf(listvideo1.size()));
                callAPIYoutubeSuccess.OnSuccess(listvideo1);
            }

            @Override
            public void onFailure(Call<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Example> call, Throwable t) {
                Toast.makeText(context, "Không thể kết nối !", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
