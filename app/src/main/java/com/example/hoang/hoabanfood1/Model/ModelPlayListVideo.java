package com.example.hoang.hoabanfood1.Model;

import java.util.List;

/**
 * Created by hoang on 12/28/2017.
 */

public class ModelPlayListVideo {
    String playlisttitle;
    List<VideoInfo> listplaylist;

    public ModelPlayListVideo(String playlisttitle, List<VideoInfo> listplaylist) {
        this.playlisttitle = playlisttitle;
        this.listplaylist = listplaylist;
    }

    public ModelPlayListVideo() {
    }

    public String getPlaylisttitle() {
        return playlisttitle;
    }

    public void setPlaylisttitle(String playlisttitle) {
        this.playlisttitle = playlisttitle;
    }

    public List<VideoInfo> getListplaylist() {
        return listplaylist;
    }

    public void setListplaylist(List<VideoInfo> listplaylist) {
        this.listplaylist = listplaylist;
    }
}
