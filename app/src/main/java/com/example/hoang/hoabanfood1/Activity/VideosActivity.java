package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.hoabanfood1.Adapter.VideoYoutubeAdapter;
import com.example.hoang.hoabanfood1.Interface.CallAPIYoutubeSuccess;
import com.example.hoang.hoabanfood1.Model.ModelApi.CallAPI;
import com.example.hoang.hoabanfood1.Model.ModelApi.model.MessageListVideo.Item;
import com.example.hoang.hoabanfood1.Model.VideoInfo;
import com.example.hoang.hoabanfood1.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;
import java.util.List;


public class VideosActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    String API_KEY = "AIzaSyCSyE2hOEVqFrOgLgyurzw70BmcoiypUz0";
    YouTubePlayerView youTubePlayerView;
    int REQUEST_VIDEO = 123;
    String videoid, playlistid,videotitle;
    ListView lvvideosub;
    TextView txtvvideotitle;
    ArrayList<VideoInfo> listvideo;
    VideoYoutubeAdapter videoYoutubeAdapter;
    CallAPI callAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        youTubePlayerView = findViewById(R.id.youtube_playerview);
        txtvvideotitle = findViewById(R.id.txtv_videotitle);
        youTubePlayerView.initialize(API_KEY, this);
        lvvideosub = findViewById(R.id.lv_videosub);
        Bundle extras = getIntent().getExtras();
        Intent bundle = getIntent();
        if (bundle != null) {
            playlistid = extras.getString("playlistid");
            videotitle = extras.getString("videotitle");
//            videoid = extras.getString("videoid");
        }
        txtvvideotitle.setText(videotitle);
        //Toast.makeText(this, playlistid, Toast.LENGTH_SHORT).show();
        GetlistVideo(playlistid);
        SetClicklistViewItem();
    }

    private void SetClicklistViewItem() {
        lvvideosub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String videoid1,videotitle1;
                videoid1 = listvideo.get(position).getVideoid();
                videotitle1 = listvideo.get(position).getTitle();
                Intent intent = new Intent(VideosActivity.this, Tam.class);
                intent.putExtra("videoid1",videoid1);
                intent.putExtra("playlistid1",playlistid);
                intent.putExtra("videotitle1",videotitle1);
                startActivity(intent);
                //Toast.makeText(VideosActivity.this, videoid1, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void GetlistVideo(String playlistid) {
        callAPI.Get_ListVideo(getApplicationContext(), playlistid, new CallAPIYoutubeSuccess() {
            @Override
            public void OnSuccess(List<Item> videoinfos) {
                listvideo = new ArrayList<>();
                for (int j = 0; j < videoinfos.size(); j++) {
                    String title, chaneltitle, playlistid, imgvideo, videoid;
                    Log.d("titlevideo", videoinfos.get(j).getSnippet().getTitle());
                    title = videoinfos.get(j).getSnippet().getTitle();
                    chaneltitle = videoinfos.get(j).getSnippet().getChannelTitle();
                    playlistid = videoinfos.get(j).getId();
                    videoid = videoinfos.get(j).getSnippet().getResourceId().getVideoId();
                    imgvideo = videoinfos.get(j).getSnippet().getThumbnails().getMedium().getUrl();
                    listvideo.add(new VideoInfo(title, chaneltitle, playlistid, videoid, imgvideo));
                }
                videoYoutubeAdapter = new VideoYoutubeAdapter(getApplicationContext(), listvideo);
                lvvideosub.setAdapter(videoYoutubeAdapter);
            }
        });
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

        Bundle extras = getIntent().getExtras();
        Intent bundle = getIntent();
//        if (bundle != null) {
            videoid = extras.getString("videoid");
//            Log.d("gia tri loaisp", videoid + "");
//        }
        youTubePlayer.cueVideo(videoid);
        //Toast.makeText(this, videoid, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(VideosActivity.this, REQUEST_VIDEO);
        } else Toast.makeText(this, "Error !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO) {
            youTubePlayerView.initialize(API_KEY, VideosActivity.this);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
