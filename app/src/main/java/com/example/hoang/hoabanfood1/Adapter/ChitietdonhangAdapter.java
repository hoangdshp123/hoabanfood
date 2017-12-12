package com.example.hoang.hoabanfood1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Model.Getchitietdonhang;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hoang on 9/30/2017.
 */

public class ChitietdonhangAdapter extends BaseAdapter {
    ArrayList<Getchitietdonhang> arrayListchitietdonhang;
    Context context;

    public ChitietdonhangAdapter(ArrayList<Getchitietdonhang> arrayListchitietdonhang, Context context) {
        this.arrayListchitietdonhang = arrayListchitietdonhang;
        this.context = context;
    }

    @Override
    public int getCount() {
        //tra ve cac gia tri trong mang
        return arrayListchitietdonhang.size();
    }

    @Override
    public Object getItem(int i) {
        //get gia tri tung thuoc tinh trong mang
        return arrayListchitietdonhang.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //Load du lieu lan dau se bat nhung anh xa,nhung lan sau khong phai load lai (ho tro load du lieu)
    public class ViewHolder{
        public TextView txtvmaspdonhang,txtvsoluongdonhang,txtvtongtiendonhang;
        public ImageView imghinhanh;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dongchitietdonhang,null);
            viewHolder.txtvmaspdonhang = view.findViewById(R.id.txtv_maspchitietdh);
            viewHolder.imghinhanh = view.findViewById(R.id.img_chitietdonhang);
            viewHolder.txtvsoluongdonhang = view.findViewById(R.id.txtv_soluongchitietdh);
            viewHolder.txtvtongtiendonhang = view.findViewById(R.id.txtv_tongtienchitietdh);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Getchitietdonhang getchitietdonhang = (Getchitietdonhang) getItem(i);
        viewHolder.txtvmaspdonhang.setText(String.valueOf(getchitietdonhang.getTensp()));
        Picasso.with(context).load(getchitietdonhang.getHinhanh())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imghinhanh);
        viewHolder.txtvsoluongdonhang.setText(String.valueOf(getchitietdonhang.getSoluong()));
        viewHolder.txtvtongtiendonhang.setText(String.valueOf(getchitietdonhang.getTongtien()));
        return view;
    }
}
