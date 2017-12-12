package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Model.Giohang;
import com.example.hoang.hoabanfood1.Model.Sanpham;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSP extends AppCompatActivity {
    Toolbar toolbarchitietsp;
    ImageView imghinhanhchitietsp;
    TextView txtvtensp,txtvgiasp,txtvghichu;
    Spinner spinner;
    Button btnthemgiohang;
    int masp;
    String tenchitiet;
    int giachitiet;
    String hinhanhchitiet;
    String ghichu;
    int maloaisp;

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
    }
}