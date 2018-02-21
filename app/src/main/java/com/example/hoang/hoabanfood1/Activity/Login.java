package com.example.hoang.hoabanfood1.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    EditText edt_account;
    EditText edt_password;
    Button btn_login;
    TextView tv_register;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Anhxa();
        Movetoregister();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edt_account.getText().toString();
                final String passwword = edt_password.getText().toString();
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            boolean success = object.getBoolean("success");
                            progressDialog = new ProgressDialog(Login.this);
                            progressDialog.setTitle("Đang tiến hành đăng nhập");
                            progressDialog.setMessage("Loading...");
                            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressDialog.show();
                            if (success) {
                                //dang nhap thanh cong!
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                String tenkh1 = object.getString("tenkh");
                                String sdt1 = object.getString("sdt");
                                int makh = object.getInt("makhachhang");
                                editor.putInt("1", R.drawable.iconuser1);
                                editor.putString("c", username);
                                editor.putString("b", "Đăng xuất");
                                editor.putInt("f",makh);
                                editor.putString("d",tenkh1);
                                editor.putString("e",sdt1);
                                editor.commit();
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this,"Tài khoản hoặc mật khẩu không đúng !", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest request = new LoginRequest(username, passwword, listener);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }



    private void Movetoregister()
    {
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movetoregister = new Intent(Login.this,Register.class);
                startActivity(movetoregister);
            }
        });
    }

    private void Anhxa()
    {
        edt_account = (EditText)findViewById(R.id.edt_account);
        btn_login = (Button)findViewById(R.id.btn_login);
        edt_password = (EditText)findViewById(R.id.edt_password);
        tv_register = (TextView)findViewById(R.id.tv_regiter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
