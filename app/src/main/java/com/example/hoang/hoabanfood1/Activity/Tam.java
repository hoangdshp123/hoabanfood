package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.hoang.hoabanfood1.R;

public class Tam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tam);
        String tam,playlist,videotitle;
        Bundle extras = getIntent().getExtras();
        tam = extras.getString("videoid1");
        playlist = extras.getString("playlistid1");
        videotitle = extras.getString("videotitle1");
        Intent intent = new Intent(Tam.this, VideosActivity.class);
        intent.putExtra("videoid",tam);
        intent.putExtra("playlistid",playlist);
        intent.putExtra("videotitle",videotitle);
        startActivity(intent);
        finish();
    }
}
