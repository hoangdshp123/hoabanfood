package com.example.hoang.hoabanfood1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Adapter.ChitietdonhangAdapter;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.Model.Getchitietdonhang;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chitietdonhang extends AppCompatActivity {
    ListView lvchitietdh;
    ChitietdonhangAdapter chitietdonhangAdapter;
    ArrayList <Getchitietdonhang> arraygetchitietdonhangs;
    Button btnhuydonhang;
    int madonhang;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietdonhang);
        Anhxa();
        Getmasp();
        Getdschitietdh();
        Huydonhang();
    }

    private void Huydonhang() {
        btnhuydonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Chitietdonhang.this);
                progressDialog.setTitle("Đang tiến hành hủy đơn hàng");
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanhuydonhang, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(Chitietdonhang.this, "Hủy đơn hàng thành công !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("madonhang", String.valueOf(madonhang));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void Getdschitietdh() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdangetchitietdonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String tensp;
                String hinhanh;
                int soluong;
                int tongtien;
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        tensp = jsonObject.getString("tensp");
                        hinhanh = jsonObject.getString("hinhanhsp");
                        soluong = jsonObject.getInt("soluong");
                        tongtien = jsonObject.getInt("tongtien");
                        arraygetchitietdonhangs.add(new Getchitietdonhang(tensp,hinhanh,soluong,tongtien));
                        chitietdonhangAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("madonhang", String.valueOf(madonhang));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Getmasp() {
        Bundle extras = getIntent().getExtras();
        Intent bundle=getIntent();
        if(bundle!=null) {
            madonhang = Integer.parseInt(extras.getString("madonhang"));
            Log.d("gia tri loaisp", madonhang + "");
        }
    }

    private void Anhxa() {
        lvchitietdh = (ListView) findViewById(R.id.lv_chitietdh);
        arraygetchitietdonhangs = new ArrayList<>();
        chitietdonhangAdapter = new ChitietdonhangAdapter(arraygetchitietdonhangs,getApplicationContext());
        lvchitietdh.setAdapter(chitietdonhangAdapter);
        btnhuydonhang = (Button) findViewById(R.id.btn_huydonhang);
    }
}
