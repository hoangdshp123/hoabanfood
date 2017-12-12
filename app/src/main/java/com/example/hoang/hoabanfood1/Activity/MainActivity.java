package com.example.hoang.hoabanfood1.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.hoang.hoabanfood1.Adapter.TablayoutAdapter;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Model.Giohang;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager;
    Toolbar toolbar;
    ViewFlipper viewFlipperquangcao;
    NavigationView navigationViewmenu;
    DrawerLayout drawerLayout;
    ImageButton btn_formlogin;
    LinearLayout header;
    View headerview;
    public static ArrayList<Giohang> manggiohang;
    public static NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (CheckConection.haveNetworkConnection(getApplicationContext())) {
            Anhxa();
            EventNavigation();
            ActionBar();
            ActionViewFiliper();
            Movetologin();
            Setclickrecyclerview();
            initView();
            Dangxuat();
        } else {
            CheckConection.Thongbao(getApplicationContext(), "Kiểm tra lại kết nối Internet !!!");
            finish();
        }

    }

    private void EventNavigation() {
        navigationViewmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_loaisp:{
                        Intent intentloaoisp = new Intent(MainActivity.this, LoaiSP.class);
                        startActivity(intentloaoisp);
                    }break;
                    case R.id.nav_diachi:{
                        Intent intentdiachi = new Intent(MainActivity.this, Adress.class);
                        startActivity(intentdiachi);
                    }
                }
                return false;
            }
        });
    }

    private void Dangxuat() {
        navigationView = (NavigationView) findViewById(R.id.Navigationmenu);
        View hView = navigationView.getHeaderView(0);
        TextView txtv_headerlogn = hView.findViewById(R.id.txtv_headerlogn);
        TextView txtv_nameheader = hView.findViewById(R.id.txtv_nameheader);
        SharedPreferences preferences = getSharedPreferences("dangnhap", MODE_PRIVATE);
        btn_formlogin.setImageResource(preferences.getInt("1", R.mipmap.ic_hearder));
        txtv_headerlogn.setText(preferences.getString("b", "Đăng nhập"));
        txtv_nameheader.setText(preferences.getString("c", ""));
        if(txtv_headerlogn.getText().equals("Đăng xuất")){
            txtv_headerlogn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    finish();
                    SharedPreferences settings = getSharedPreferences("dangnhap", MODE_PRIVATE);
                    settings.edit().clear().commit();
                    startActivity(intent);
                }
            });
        }
    }


    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TablayoutAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void Setclickrecyclerview() {
    }


    private void ActionViewFiliper() {
        ArrayList<String> maquangcao = new ArrayList<>();
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/mat-ong-hoa-nhan-tay-bac-7.jpg");
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/thit-ca-gac-bep-banner-2.jpg");
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/Ca-Chep-Gac-Bep-17-680x363.jpg");
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/ruou-sau-chit-hoabanfood-cover-250x140.jpg");
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/mang-chua-kho-2016-13-250x140.jpg");
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/mac-khen-uop-ca-thit-7.jpg");
        maquangcao.add("http://hoabanfood.com/wp-content/uploads/hat-mac-khen-2017-700px-250x140.jpg");
        for (int i = 0; i < maquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(maquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperquangcao.addView(imageView);
        }
        viewFlipperquangcao.setFlipInterval(5000);
        viewFlipperquangcao.setAutoStart(true);
        //animation viewFilipper
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipperquangcao.setInAnimation(animation_in);
        viewFlipperquangcao.setOutAnimation(animation_out);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Movetologin() {
        headerview = LayoutInflater.from(this).inflate(R.layout.header, null);
        btn_formlogin = (ImageButton) headerview.findViewById(R.id.btn_formlogin);
        navigationViewmenu.addHeaderView(headerview);
        btn_formlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movetoformlogin = new Intent(MainActivity.this, Login.class);
                startActivity(movetoformlogin);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menucart: {
                Intent movetoformcart = new Intent(getApplicationContext(), Cart.class);
                startActivity(movetoformcart);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Anhxa() {
        toolbar = (Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipperquangcao = (ViewFlipper) findViewById(R.id.quangcao);
        navigationViewmenu = (NavigationView) findViewById(R.id.Navigationmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        header = (LinearLayout) findViewById(R.id.nav_header);
        if (manggiohang != null) {
        } else {
            manggiohang = new ArrayList<>();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
