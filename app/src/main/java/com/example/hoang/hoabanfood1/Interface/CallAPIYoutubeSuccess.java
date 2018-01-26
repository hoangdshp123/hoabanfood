package com.example.hoang.hoabanfood1.Interface;

import com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Item;

import java.util.List;

/**
 * Created by hoang on 12/28/2017.
 */

public interface CallAPIYoutubeSuccess {
    void OnSuccess(List<Item> videoinfos);
}
