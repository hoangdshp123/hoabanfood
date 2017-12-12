package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Adapter.LoaispAdapter;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoaiSP extends AppCompatActivity {

    ListView lvloaisp;
    ArrayList<com.example.hoang.hoabanfood1.Model.LoaiSP> mangloaisp;
    LoaispAdapter loaispAdapter;
    int maloaisp;
    String tenloaisp;
    String hinhanhloaisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_sp);
        Anhxa();
        Getdulieuloaisp();
        Movetoviewsanpham();
    }


    private void Getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            maloaisp = jsonObject.getInt("maloaisp");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new com.example.hoang.hoabanfood1.Model.LoaiSP(maloaisp, tenloaisp, hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.Thongbao(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void Movetoviewsanpham() {
        lvloaisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                    Intent movetottkhachhang = new Intent(LoaiSP.this, ViewSP.class);
                    String mlsp = String.valueOf(mangloaisp.get(i).getMaloaisp());
                    movetottkhachhang.putExtra("maloaisp", mlsp);
                    startActivity(movetottkhachhang);
                }
            }
        });
    }
    private void Anhxa() {
        lvloaisp = (ListView) findViewById(R.id.lv_loaisp);
        mangloaisp = new ArrayList<>();
        loaispAdapter = new LoaispAdapter(mangloaisp, getApplicationContext());
        lvloaisp.setAdapter(loaispAdapter);
    }
}
