package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.hoang.hoabanfood1.R;

/**
 * Created by hoang on 12/20/2017.
 */

public class WebViewActivity extends AppCompatActivity {
    WebView wvbank;
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        Bundle extras = getIntent().getExtras();
        Intent bundle=getIntent();
        if(bundle!=null) {
            url = extras.getString("url");
        }
        wvbank = (WebView) findViewById(R.id.wv_bank);
        wvbank.setWebViewClient(new WebViewClient());

        wvbank.getSettings().setLoadsImagesAutomatically(true);
        wvbank.getSettings().setJavaScriptEnabled(true);
        wvbank.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvbank.getSettings().setDomStorageEnabled(true);
        wvbank.getSettings().setAppCacheEnabled(true);

        wvbank.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvbank.getSettings().setJavaScriptEnabled(true);
        wvbank.getSettings().setUseWideViewPort(true);
        wvbank.getSettings().setLoadWithOverviewMode(true);
        wvbank.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvbank.getSettings().setAppCachePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/cache");
        wvbank.getSettings().setDatabaseEnabled(true);
        wvbank.getSettings().setJavaScriptEnabled(true);
        wvbank.getSettings().setDatabasePath(getApplicationContext().getFilesDir().getAbsolutePath() + "/databases");
        wvbank.loadUrl(url);
//        webView.loadUrl("http://gamek.vn");
    }
}
