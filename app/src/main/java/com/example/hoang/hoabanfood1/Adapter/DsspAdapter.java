package com.example.hoang.hoabanfood1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Model.Sanpham;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by hoang on 10/5/2017.
 */

public class DsspAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arraydssp = new ArrayList<>();

    public DsspAdapter(Context context, ArrayList<Sanpham> arraydssp) {
        this.context = context;
        this.arraydssp = arraydssp;
    }

    @Override
    public int getCount() {
        return arraydssp.size();
    }

    @Override
    public Object getItem(int i) {
        return arraydssp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txttenspds,txtgiads;
        public ImageView imghinhanhsp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.donglistviewdssp,null);
            viewHolder.txttenspds =  (TextView) view.findViewById(R.id.txtv_tenspds);
            viewHolder.txtgiads = (TextView) view.findViewById(R.id.txtv_giaspds);
            viewHolder.imghinhanhsp = (ImageView) view.findViewById(R.id.img_dssp);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(i);
        viewHolder.txttenspds.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiads.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+"vnđ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imghinhanhsp);
        return view;
    }

}
