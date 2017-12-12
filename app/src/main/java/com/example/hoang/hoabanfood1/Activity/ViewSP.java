package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Adapter.DsspAdapter;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.Model.Sanpham;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewSP extends AppCompatActivity {

    Toolbar toolbarsp;
    ListView lvdssp;
    DsspAdapter dsspAdapter;
    ArrayList<Sanpham> arraysp;
    View footerview;
    boolean isloading = false;
    boolean limitdata = false;
    ImageButton btncart;
    mHandler mhandler;
    int malsp;
    int page= 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_sp);
            Anhxa();
            if(CheckConection.haveNetworkConnection(getApplicationContext())){
                Getmalsp();
                Getdata(page);
                LoadMore();
                Movetochitietsp();
                Movetocart();
            }
            else
                CheckConection.Thongbao(getApplicationContext(),"Vui lòng kiểm tra lại kết nối...!");
    }
    private void Movetocart() {
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movetoformlogin = new Intent(getApplicationContext(), Cart.class);
                startActivity(movetoformlogin);
            }
        });
    }
    private void Movetochitietsp() {
        lvdssp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (CheckConection.haveNetworkConnection(getApplicationContext())){
                    Intent movetottsp = new Intent(ViewSP.this,ChiTietSP.class);
                    movetottsp.putExtra("thongtinsp",arraysp.get(i));
                    startActivity(movetottsp);
                }
            }
        });
    }

    private void LoadMore() {
        lvdssp.setOnScrollListener(new AbsListView.OnScrollListener() {
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

    private void Getdata(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdandssp+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int masp;
                String tensp;
                int maloaisp;
                String hinhanhsp;
                Integer giasp;
                String ghichu;
                if(response!=null && response.length()!=2){
                    lvdssp.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i< response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            masp = jsonObject.getInt("masp");
                            tensp = jsonObject.getString("tensp");
                            maloaisp = jsonObject.getInt("maloaisp");
                            hinhanhsp = jsonObject.getString("hinhanhsp");
                            giasp = jsonObject.getInt("giasp");
                            ghichu = jsonObject.getString("ghichu");
                            arraysp.add(new Sanpham(masp,tensp,maloaisp,hinhanhsp,giasp,ghichu));
                            dsspAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata= true;
                    lvdssp.removeFooterView(footerview);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("maloaisp",String.valueOf(malsp));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Getmalsp() {
        Bundle extras = getIntent().getExtras();
        Intent bundle=getIntent();
        if(bundle!=null) {
            malsp = Integer.parseInt(extras.getString("maloaisp"));
            Log.d("gia tri loaisp", malsp + "");
        }
    }

    private void Anhxa() {
        toolbarsp = (Toolbar)findViewById(R.id.toolbardssp);
        lvdssp = (ListView)findViewById(R.id.lv_dssp);
        arraysp = new ArrayList<>();
        dsspAdapter = new DsspAdapter(getApplicationContext(),arraysp);
        lvdssp.setAdapter(dsspAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mhandler = new mHandler();
        btncart = (ImageButton) findViewById(R.id.btn_cartdssp);
    }
    public class mHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0: lvdssp.addFooterView(footerview);break;
                case 1: Getdata(++page);isloading=false;break;
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
