package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongtinKH extends AppCompatActivity {
    EditText edtusername, edtname, edtsdt;
    Button btntrolai, btnxacnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_kh);
        Anhxa();
        hienthithongtin();
        eventtrolai();
        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
            eventxacnhan();
        } else
            CheckConection.Thongbao(getApplicationContext(), "Kiểm tra lại kết nối Internet !");
    }

    private void eventxacnhan() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtusername.getText().toString().trim();
                final String name = edtname.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                if (username.length() > 0 && name.length() > 0 && sdt.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", madonhang);
                            if (Integer.parseInt(madonhang) > 0) {
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            MainActivity.manggiohang.clear();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Dữ liệu của bạn đang lỗi", Toast.LENGTH_SHORT);
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("masp", MainActivity.manggiohang.get(i).getMasp());
                                                jsonObject.put("soluong", MainActivity.manggiohang.get(i).soluong);
                                                jsonObject.put("tongtien", MainActivity.manggiohang.get(i).tongtien);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> param = new HashMap<String, String>();
                                        param.put("json", jsonArray.toString());
                                        return param;
                                    }
                                };
                                queue.add(request);
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
                            param.put("username", username);
                            param.put("tenkh", name);
                            param.put("sdt", sdt);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                    Intent movetocart = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(movetocart);
                    Toast.makeText(ThongtinKH.this, "Thêm đơn hàng thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ThongtinKH.this, "Thông tin không được để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void eventtrolai() {
        btntrolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttrolai = new Intent(getApplicationContext(), Cart.class);
                startActivity(intenttrolai);
            }
        });
    }

    private void hienthithongtin() {
        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        edtusername.setText(preferences.getString("c", ""));
        edtname.setText(preferences.getString("d", ""));
        edtsdt.setText(preferences.getString("e", ""));
    }

    private void Anhxa() {
        edtusername = (EditText) findViewById(R.id.edt_infoaccount);
        edtname = (EditText) findViewById(R.id.edt_infoname);
        edtsdt = (EditText) findViewById(R.id.edt_infophonenumber);
        btntrolai = (Button) findViewById(R.id.btn_trolai);
        btnxacnhan = (Button) findViewById(R.id.btn_xacnhan);
    }
}
