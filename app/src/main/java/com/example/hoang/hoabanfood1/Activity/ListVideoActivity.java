package com.example.hoang.hoabanfood1.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Adapter.PlaylistAdapter;
import com.example.hoang.hoabanfood1.Interface.CallAPIYoutubeSuccess;
import com.example.hoang.hoabanfood1.Model.ModelApi.CallAPI;
import com.example.hoang.hoabanfood1.Model.ModelPlayListVideo;
import com.example.hoang.hoabanfood1.Model.VideoInfo;
import com.example.hoang.hoabanfood1.Model.YouTubeVideoView;
import com.example.hoang.hoabanfood1.R;
import com.example.hoang.hoabanfood1.utils.SharedPrefsUtils;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class ListVideoActivity extends AppCompatActivity implements YouTubeVideoView.Callback, YouTubePlayer.PlaybackEventListener {
    ArrayList<ModelPlayListVideo> playlistlist;
    ArrayList<VideoInfo> infoArrayList;
    PlaylistAdapter playlistAdapter;
    ListView listplayid;
    public static ListView listViewsub;
    public static YouTubeVideoView mYouTubeVideoView;
    public static TextView txtvvideotitle;
    CallAPI callAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_video);
        init();
        GetJsonYoutube();
        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youtubeFragment);
        youtubeFragment.initialize("YOUR API KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(PlaylistAdapter.videoid);
                        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
        mYouTubeVideoView = (YouTubeVideoView) findViewById(R.id.youtube_view);
        mYouTubeVideoView.setCallback(this);
    }
//        else {
//            Toast.makeText(this, "Không có video !", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void GetJsonYoutube() {
        playlistlist = new ArrayList<>();
        callAPI.Get_Video(getApplicationContext());
        int i;
        //Toast.makeText(this, SharedPrefsUtils.getStringPreference(getApplicationContext(),"listplaylistidsize"), Toast.LENGTH_SHORT).show();
        for (i = 0; i < Integer.parseInt(SharedPrefsUtils.getStringPreference(getApplicationContext(), "listplaylistidsize")); i++) {
            final int finalI = i;
            callAPI.Get_ListVideo(getApplicationContext(), SharedPrefsUtils.getStringPreference(getApplicationContext(), "playlistid" + i), new CallAPIYoutubeSuccess() {
                @Override
                public void OnSuccess(List<com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Item> videoinfos) {
                    infoArrayList = new ArrayList<>();
                    for (int j = 0; j < videoinfos.size(); j++) {
                        String title, chaneltitle, playlistid, imgvideo, videoid;
                        Log.d("titlevideo", videoinfos.get(j).getSnippet().getTitle());
                        title = videoinfos.get(j).getSnippet().getTitle();
                        chaneltitle = videoinfos.get(j).getSnippet().getChannelTitle();
                        playlistid = videoinfos.get(j).getSnippet().getPlaylistId();
                        videoid = videoinfos.get(j).getSnippet().getResourceId().getVideoId();
                        imgvideo = videoinfos.get(j).getSnippet().getThumbnails().getMedium().getUrl();
                        infoArrayList.add(new VideoInfo(title, chaneltitle, playlistid, videoid, imgvideo));
                        Log.d("kkkk", String.valueOf(infoArrayList.size()));
                    }
                    playlistlist.add(new ModelPlayListVideo(SharedPrefsUtils.getStringPreference(getApplicationContext(), "playlisttitle" + finalI), infoArrayList));
                    playlistAdapter = new PlaylistAdapter(getApplicationContext(), playlistlist);
                    listplayid.setAdapter(playlistAdapter);
                }
            });
        }
        setListViewHeightBasedOnChildren(listplayid);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += (listItem.getMeasuredHeight() + 17);
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    private void init() {
        listplayid = (ListView) findViewById(R.id.lv_playlist);
        listViewsub = (ListView) findViewById(R.id.lv_videosub1);
        mYouTubeVideoView = (YouTubeVideoView) findViewById(R.id.youtube_view);
        txtvvideotitle = (TextView) findViewById(R.id.txtv_videotitle1);
    }

    @Override
    public void onVideoViewHide() {

    }

    @Override
    public void onVideoClick() {
    }

    @Override
    public void onPlaying() {
        mYouTubeVideoView.show();
    }

    @Override
    public void onPaused() {
        mYouTubeVideoView.show();
    }

    @Override
    public void onStopped() {
        mYouTubeVideoView.show();
    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}
