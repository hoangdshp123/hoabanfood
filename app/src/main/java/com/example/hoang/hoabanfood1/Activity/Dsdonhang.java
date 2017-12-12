package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Adapter.DsdonhangAdapter;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.Model.Donhang;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dsdonhang extends AppCompatActivity {
    ListView lvdsdh;
    ArrayList<Donhang> arrayListdonhang;
    DsdonhangAdapter dsdonhangAdapter;
    String username1;
    View footerview;
    boolean isloading = false;
    boolean limitdata = false;
    mHandler mhandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsdonhang);
        Anhxa();
        Getdsdonhang();
        //LoadMore();
        Movetochitietdh();
    }

    private void LoadMore() {
        lvdsdh.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int Firstitem, int Visibaleitem, int Totalitem) {
                //firstitem: item dau tien--visibaleitem: cac item co the nhin thay-- totalitem: tong so cac item
                if(Firstitem+ Visibaleitem == Totalitem && Totalitem !=0 && isloading==false && limitdata==false){
                    //vi tri cuoi cung
                    isloading=true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    private void Movetochitietdh() {
        lvdsdh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CheckConection.haveNetworkConnection(getApplicationContext())) {
                    Intent movetochitietdh = new Intent(Dsdonhang.this, Chitietdonhang.class);
                    String madonhang = String.valueOf(arrayListdonhang.get(i).getMadonhang());
                    movetochitietdh.putExtra("madonhang", madonhang);
                    startActivity(movetochitietdh);
                }
            }
        });
    }

    private void Getdsdonhang() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandsdonhang, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String username;
                int madonhang;
                String sdt;
                String tenkh;
                if(response!=null && response.length()!=2) {
                    lvdsdh.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            username = jsonObject.getString("username");
                            madonhang = jsonObject.getInt("madonhang");
                            sdt = jsonObject.getString("sdt");
                            tenkh = jsonObject.getString("tenkh");
                            arrayListdonhang.add(new Donhang(username, madonhang, tenkh, sdt));
                            dsdonhangAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    limitdata= true;
                    lvdsdh.removeFooterView(footerview);
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
                param.put("username",username1);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Anhxa() {
        lvdsdh = (ListView) findViewById(R.id.lv_donhang);
        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        username1 = preferences.getString("c", "");
        arrayListdonhang = new ArrayList<>();
        dsdonhangAdapter = new DsdonhangAdapter(arrayListdonhang, getApplicationContext());
        lvdsdh.setAdapter(dsdonhangAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mhandler = new mHandler();
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0: lvdsdh.addFooterView(footerview);break;
                case 1: Getdsdonhang();isloading=false;break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mhandler.obtainMessage(1);
            mhandler.sendMessage(message);
            super.run();
        }
    }
}
