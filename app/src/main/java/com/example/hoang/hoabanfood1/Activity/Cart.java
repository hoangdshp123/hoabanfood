package com.example.hoang.hoabanfood1.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.hoabanfood1.Adapter.CartAdapter;
import com.example.hoang.hoabanfood1.R;

import java.text.DecimalFormat;

public class Cart extends AppCompatActivity {
    ListView lvcart;
    TextView txtvthongbao;
    static TextView txtvtongtien;
    TextView txtvslcart;
    Button btndathang, btnmuatiep;
    ImageButton btndsdh;
    Toolbar toolbargiohang;
    CartAdapter cartAdapter;
    View header;
    TextView txtvusername;
    String tam;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Anhxa();
        Checkdata();
        TinhTong();
        Muatiep();
        Dathang();
        Movetodsdh();
    }

    private void Movetodsdh() {
        btndsdh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tam.equals("")) {
//                    AlertDialog.Builder builder;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        builder = new AlertDialog.Builder(Cart.this);
//                    } else
//                        builder = new AlertDialog.Builder(Cart.this);
//
//                    builder.setTitle("Bạn chưa đăng nhập").setMessage("Bạn có muốn đăng nhập ?").
//                            setPositiveButton("No",new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            });
//                    builder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intentdialog = new Intent(getApplicationContext(),Login.class);
//                            startActivity(intentdialog);
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();
                    displayAlertDialog();
                } else {
                    Intent intentdsdh = new Intent(Cart.this, Dsdonhang.class);
                    intentdsdh.putExtra("2", "type4");
                    startActivity(intentdsdh);
                    finish();
                }
            }
        });
    }

    private void Dathang() {
        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.manggiohang.size() > 0) {
                    if (tam.equals("")) {
                        Intent intentttkh = new Intent(getApplicationContext(), ThongtinKH.class);
                        intentttkh.putExtra("1", "type1");
                        startActivity(intentttkh);
                        finish();
                    } else {

                        Intent intentttkh = new Intent(getApplicationContext(), ThongtinKH.class);
                        intentttkh.putExtra("1", "type2");
                        startActivity(intentttkh);
                        finish();
                    }
                } else
                    Toast.makeText(Cart.this, "Giỏ hàng của bạn đang trống !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Muatiep() {
        btnmuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentmuatuep = new Intent(Cart.this, MainActivity.class);
                startActivity(intentmuatuep);
                finish();
            }
        });
    }

    public static void TinhTong() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
            tongtien = tongtien + MainActivity.manggiohang.get(i).getTongtien();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtvtongtien.setText(decimalFormat.format(tongtien) + "vnđ");
    }

    private void Checkdata() {
        if (MainActivity.manggiohang.size() <= 0) {
            cartAdapter.notifyDataSetChanged();
            txtvthongbao.setVisibility(View.VISIBLE);
            lvcart.setVisibility(View.INVISIBLE);
        } else {
            cartAdapter.notifyDataSetChanged();
            txtvthongbao.setVisibility(View.INVISIBLE);
            lvcart.setVisibility(View.VISIBLE);
        }
    }

    private void Anhxa() {
        View header = MainActivity.navigationView.getHeaderView(0);
        txtvusername = header.findViewById(R.id.txtv_nameheader);
        lvcart = (ListView) findViewById(R.id.lv_giohang);
        txtvthongbao = (TextView) findViewById(R.id.txtv_thongbao);
        txtvtongtien = (TextView) findViewById(R.id.txtv_tongtien);
        btndathang = (Button) findViewById(R.id.btn_dathang);
        btnmuatiep = (Button) findViewById(R.id.btn_muatiep);
        toolbargiohang = (Toolbar) findViewById(R.id.toolbargiohang);
        cartAdapter = new CartAdapter(Cart.this, MainActivity.manggiohang);
        txtvslcart = (TextView) findViewById(R.id.txtv_slcart);
        lvcart.setAdapter(cartAdapter);
        btndsdh = (ImageButton) findViewById(R.id.btn_dsdonhang);
        tam = txtvusername.getText().toString();
    }

    public void displayAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.customdialog, null);
        final EditText etUsername = (EditText) alertLayout.findViewById(R.id.edt_account_dh);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Nhập username");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        alert.setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("Xem đơn hàng", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // code for matching password
                String user = etUsername.getText().toString();
                if(user.equals("")){
                    Toast.makeText(Cart.this, "Chưa nhập username !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intentdsdh = new Intent(Cart.this, Dsdonhang.class);
                    intentdsdh.putExtra("2", "type3");
                    intentdsdh.putExtra("name", user);
                    startActivity(intentdsdh);
                }
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}
