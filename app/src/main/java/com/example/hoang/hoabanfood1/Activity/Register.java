package com.example.hoang.hoabanfood1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Register extends AppCompatActivity{
    EditText edtaccount,edtpassword,edtretypepass,edttenkh,edtphonenumber;
    Button btnregister;
    int k;
    ArrayList<String> username1 = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Anhxa();
        getusername1();
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtaccount.getText().toString();
                String password = edtpassword.getText().toString();
                String retypepass = edtretypepass.getText().toString();
                String name = edttenkh.getText().toString();
                String phonenumber = edtphonenumber.getText().toString();
                progressDialog = new ProgressDialog(Register.this);
                progressDialog.setTitle("Đang tiến hành đăng ký");
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                if(username.equals("")||password.equals("")||name.equals("")||phonenumber.equals("")){
                    progressDialog.dismiss();
                    Toast.makeText(Register.this, "Chưa nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < username1.size(); i++) {
                        if (username.equals(username1.get(i).toString())) {
                            k++;
                        }
                    }
                    if (k==0) {
                            if(password.equals(retypepass))
                            {
                                Response.Listener<String> listener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                                Toast.makeText(getApplicationContext(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                                                startActivity(intent);
                                        finish();
                                    }
                                };
                                RegisterRequest request = new RegisterRequest(username, password, name, phonenumber, listener);
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                queue.add(request);

                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Nhập lại mật khẩu sai !",Toast.LENGTH_SHORT).show();
                            }
                    }
                    else{
                        progressDialog.dismiss();
                        Toast.makeText(Register.this, "Trùng tài khoản đã đăng ký !", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void getusername1() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanusername, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    for(int i = 0; i< response.length();i++)
                    {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            username1.add(jsonObject.getString("username"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.Thongbao(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Anhxa() {
        edtaccount = (EditText) findViewById(R.id.edt_rgtaccount);
        edtpassword = (EditText) findViewById(R.id.edt_rgtpassword);
        edtretypepass = (EditText) findViewById(R.id.edt_rgtrepassword);
        edttenkh = (EditText) findViewById(R.id.edt_name);
        edtphonenumber = (EditText) findViewById(R.id.edt_phonenumber);
        btnregister = (Button) findViewById(R.id.btn_register);
    }
}
