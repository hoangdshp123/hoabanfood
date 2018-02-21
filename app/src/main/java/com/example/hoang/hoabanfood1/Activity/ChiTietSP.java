package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Adapter.CommentAdapter;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.Model.Giohang;
import com.example.hoang.hoabanfood1.Model.ModelComment;
import com.example.hoang.hoabanfood1.Model.Sanpham;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSP extends AppCompatActivity {
    Toolbar toolbarchitietsp;
    EditText edtNoidung;
    ImageView imghinhanhchitietsp,btnsend;
    TextView txtvtensp,txtvgiasp,txtvghichu;
    Spinner spinner;
    View footerview;
    Button btnthemgiohang;
    int masp;
    String tenchitiet;
    int giachitiet;
    String hinhanhchitiet;
    String ghichu;
    int maloaisp;
    ListView listViewComment;
    int makhachhang;
    String tenkh;
    ArrayList<ModelComment> arrayComment;
    CommentAdapter commentAdapter;
    boolean isloading = false;
    boolean limitdata = false;
    int page= 1;

    ImageButton btncart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_sp);
        Anhxa();
        Getiformation();
        CatchEventSpinner();
        Themvaogiohang();
        movetocart();
        Getdata();
        hienthithongtin();
        sendComment();
    }

    private void hienthithongtin() {
        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        tenkh = preferences.getString("c", "");
        makhachhang = preferences.getInt("f", 0);
    }

    private void sendComment() {
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ChiTietSP.this, "Send coment success !", Toast.LENGTH_SHORT).show();
                String noidung = edtNoidung.getText().toString();
                if (noidung.equals("")){
                    Toast.makeText(ChiTietSP.this, "Bạn chưa nhập nội dung đánh giá !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> listener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Comment success !", Toast.LENGTH_SHORT).show();
                            edtNoidung.setText("");
                            arrayComment = new ArrayList<>();
                            commentAdapter = new CommentAdapter(arrayComment,getApplicationContext());
                            listViewComment.setAdapter(commentAdapter);
                            Getdata();
                        }
                    };
                    CommentRequest request = new CommentRequest(makhachhang, masp, tenkh,noidung, listener);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    queue.add(request);
                }
                }
        });
    }

    private void movetocart() {
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movetoformlogin = new Intent(getApplicationContext(), Cart.class);
                startActivity(movetoformlogin);
            }
        });
    }

    private void Themvaogiohang() {
        btnthemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists =false;
                    for(int i=0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getMasp()== masp) {
                            MainActivity.manggiohang.get(i).setSoluong(MainActivity.manggiohang.get(i).getSoluong() + sl);
                            MainActivity.manggiohang.get(i).setTongtien(giachitiet * MainActivity.manggiohang.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if(exists==false){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long tongtien = soluong*giachitiet;
                        MainActivity.manggiohang.add(new Giohang(masp,tenchitiet,tongtien,hinhanhchitiet,soluong));
                    }
                }else{
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long tongtien = soluong*giachitiet;
                    MainActivity.manggiohang.add(new Giohang(masp,tenchitiet,tongtien,hinhanhchitiet,soluong));
                }
                Intent movetocart = new Intent(getApplicationContext(),Cart.class);
                startActivity(movetocart);
                finish();
            }
        });
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new  Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayadtapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayadtapter);
    }

    private void Getiformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsp");
        masp = sanpham.getMasp();
        tenchitiet = sanpham.getTensp();
        giachitiet = sanpham.getGiasp();
        hinhanhchitiet = sanpham.getHinhanhsp();
        ghichu = sanpham.getGhichu();
        maloaisp = sanpham.getMaloaisp();
        txtvtensp.setText(tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtvgiasp.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+"vnđ");
        txtvghichu.setText(ghichu);
        Picasso.with(getApplicationContext()).load(hinhanhchitiet)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imghinhanhchitietsp);
    }
    private void Anhxa() {
        toolbarchitietsp = (Toolbar)findViewById(R.id.toolbarchitietsp);
        imghinhanhchitietsp = (ImageView)findViewById(R.id.img_chitietsp);
        txtvtensp = (TextView)findViewById(R.id.txtv_tenchitietsp);
        txtvgiasp  = (TextView)findViewById(R.id.txtv_giachitietsp);
        txtvghichu = (TextView)findViewById(R.id.txtv_ghichu);
        spinner = (Spinner)findViewById(R.id.spinner);
        btnthemgiohang = (Button)findViewById(R.id.btn_themvaogiohang);
        btncart = (ImageButton) findViewById(R.id.btn_cartchitietsp);
        btnsend = (ImageView) findViewById(R.id.btn_sendcoment);
        edtNoidung = (EditText) findViewById(R.id.edt_noidung1);
        listViewComment = (ListView) findViewById(R.id.lv_comment);
        arrayComment = new ArrayList<>();
        commentAdapter = new CommentAdapter(arrayComment,getApplicationContext());
        listViewComment.setAdapter(commentAdapter);
    }

    private void Getdata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdangetcoment;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int madanhgia1;
                int makhachhang1;
                int masp1;
                String username1;
                String noidung1;
                if(response!=null && response.length()!=2){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for(int i = 0; i< response.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            madanhgia1 = jsonObject.getInt("madanhgia");
                            makhachhang1 = jsonObject.getInt("makhachhang");
                            masp1 = jsonObject.getInt("masp");
                            username1 = jsonObject.getString("username");
                            noidung1 = jsonObject.getString("noidung");
                            arrayComment.add(new ModelComment(username1,noidung1));
                            commentAdapter.notifyDataSetChanged();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    limitdata= true;
                    listViewComment.removeFooterView(footerview);
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
                param.put("masp",String.valueOf(masp));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}