package com.example.hoang.hoabanfood1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Model.LoaiSP;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hoang on 9/30/2017.
 */

public class LoaispAdapter extends BaseAdapter {
    ArrayList<LoaiSP> arrayListLoaisp;
    Context context;
    public LoaispAdapter(ArrayList<LoaiSP> arrayListLoaisp, Context context) {
        this.arrayListLoaisp = arrayListLoaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        //tra ve cac gia tri trong mang
        return arrayListLoaisp.size();
    }

    @Override
    public Object getItem(int i) {
        //get gia tri tung thuoc tinh trong mang
        return arrayListLoaisp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    //Load du lieu lan dau se bat nhung anh xa,nhung lan sau khong phai load lai (ho tro load du lieu)
    public class ViewHolder{
        TextView txtvtenloaisp;
        ImageView imgloaisp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.donglistviewloaisp,null); //nap vao donglisviewloaisp
            viewHolder.txtvtenloaisp = view.findViewById(R.id.txtv_loaisp);
            viewHolder.imgloaisp = view.findViewById(R.id.img_loaisp);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSP loaiSP = (LoaiSP) getItem(i);
        viewHolder.txtvtenloaisp.setText(loaiSP.getTenloaisp());
        Picasso.with(context).load(loaiSP.getHinhanhloaisp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgloaisp);
        return view;
    }
}
