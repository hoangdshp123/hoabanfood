package com.example.hoang.hoabanfood1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Model.Donhang;
import com.example.hoang.hoabanfood1.R;

import java.util.ArrayList;

/**
 * Created by hoang on 9/30/2017.
 */

public class DsdonhangAdapter extends BaseAdapter {
    ArrayList<Donhang> arrayListdsdonhang;
    Context context;
    public DsdonhangAdapter(ArrayList<Donhang> arrayListdsdonhang, Context context) {
        this.arrayListdsdonhang = arrayListdsdonhang;
        this.context = context;
    }

    @Override
    public int getCount() {
        //tra ve cac gia tri trong mang
        return arrayListdsdonhang.size();
    }

    @Override
    public Object getItem(int i) {
        //get gia tri tung thuoc tinh trong mang
        return arrayListdsdonhang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //Load du lieu lan dau se bat nhung anh xa,nhung lan sau khong phai load lai (ho tro load du lieu)
    public class ViewHolder{
        TextView txtvusernamedonhang,txtvmadonhang,txtvsdtdonhang;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongdsdonhang,null);
            viewHolder.txtvusernamedonhang = view.findViewById(R.id.txtv_usernamedonhang);
            viewHolder.txtvmadonhang = view.findViewById(R.id.txtv_madonhang);
            viewHolder.txtvsdtdonhang = view.findViewById(R.id.txtv_sdtdonhang);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Donhang donhang = (Donhang) getItem(i);
        viewHolder.txtvusernamedonhang.setText(donhang.getUsername());
        viewHolder.txtvmadonhang.setText(String.valueOf(donhang.getMadonhang()));
        viewHolder.txtvsdtdonhang.setText(donhang.getSdt());
        return view;
    }
}
