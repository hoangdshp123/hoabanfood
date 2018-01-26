package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.hoang.hoabanfood1.R;

public class ThanhToanActivity extends AppCompatActivity {
    ImageButton btnagribank,btntechcombank;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        Anhxa();
        btnagribank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movetowebview = new Intent(ThanhToanActivity.this, WebViewActivity.class);
                url = "http://www.agribank.com.vn/default.aspx";
                movetowebview.putExtra("url", url);
                startActivity(movetowebview);
            }
        });

        btntechcombank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent movetowebview = new Intent(ThanhToanActivity.this, WebViewActivity.class);
                url = "https://www.techcombank.com.vn/gap-mot-lan-lay-xe-ngay?utm_source=google&utm_medium=mvv-mvvi-cpc&utm_campaign=onetouch&utm_term=gap-mot-lan-lay-xe-ngay&utm_content=search-BrandCampaign";
                movetowebview.putExtra("url", url);
                startActivity(movetowebview);
            }
        });
    }

    private void Anhxa() {
        btnagribank = (ImageButton) findViewById(R.id.btn_agribank);
        btntechcombank = (ImageButton) findViewById(R.id.btn_techcombank);
    }
}
