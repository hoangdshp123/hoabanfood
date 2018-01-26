package com.example.hoang.hoabanfood1.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hoang.hoabanfood1.Adapter.RCVSanphamAdapter;
import com.example.hoang.hoabanfood1.Connect.CheckConection;
import com.example.hoang.hoabanfood1.Connect.Server;
import com.example.hoang.hoabanfood1.Model.Sanpham;
import com.example.hoang.hoabanfood1.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hoang on 10/20/2017.
 */

public class SecondFragment extends Fragment {
    Context context;
    RecyclerView recyclerViewspgiare;
    RCVSanphamAdapter sanphamgiareAdapter;
    ArrayList<Sanpham> mangspgiare;
    private View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragmentsecond,container,false);
        anhxa();
        Getdulieuspgiare();
        return rootview;
    }
    private void Getdulieuspgiare(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanspgiare, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int masp=0;
                    String tensp="";
                    int maloaisp=0;
                    String hinhanhsp="";
                    Integer giasp=0;
                    String ghichu="";
                    for(int i = 0; i< response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            masp = jsonObject.getInt("masp");
                            tensp = jsonObject.getString("tensp");
                            maloaisp = jsonObject.getInt("maloaisp");
                            hinhanhsp = jsonObject.getString("hinhanhsp");
                            giasp = jsonObject.getInt("giasp");
                            ghichu = jsonObject.getString("ghichu");
                            mangspgiare.add(new Sanpham(masp,tensp,maloaisp,hinhanhsp,giasp,ghichu));
                            sanphamgiareAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConection.Thongbao(context,error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void anhxa() {
        mangspgiare = new ArrayList<>();
        sanphamgiareAdapter = new RCVSanphamAdapter(context,mangspgiare);
        recyclerViewspgiare = rootview.findViewById(R.id.rcv_spnhohon400);
        recyclerViewspgiare.setHasFixedSize(true);
        recyclerViewspgiare.setLayoutManager(new GridLayoutManager(context,2));
        recyclerViewspgiare.setAdapter(sanphamgiareAdapter);
    }
}
