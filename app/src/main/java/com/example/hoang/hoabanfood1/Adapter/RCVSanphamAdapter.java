package com.example.hoang.hoabanfood1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Activity.ChiTietSP;
import com.example.hoang.hoabanfood1.Model.Sanpham;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by hoang on 10/1/2017.
 */

public class RCVSanphamAdapter extends RecyclerView.Adapter<RCVSanphamAdapter.ItemHolder>{
    Context context;
    ArrayList<Sanpham> arraysp;

    public RCVSanphamAdapter(Context context, ArrayList<Sanpham> arraysp) {
        this.context = context;
        this.arraysp = arraysp;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //khoi tao view da thiet ke o layout ben ngoai
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongrecyclerviewsp,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        //lay du lieu trong mang va gan len khuon
        Sanpham sanpham = arraysp.get(position);
        holder.txtv_tensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtv_giasp.setText("Giá: "+decimalFormat.format(sanpham.getGiasp())+"vnđ");
        Picasso.with(context).load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.imghinhanhsp);
    }

    @Override
    public int getItemCount() {
        return arraysp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhanhsp;
        public TextView txtv_tensp,txtv_giasp;

        public ItemHolder(final View itemView) {
            super(itemView);
            imghinhanhsp = itemView.findViewById(R.id.img_sanpham);
            txtv_tensp = itemView.findViewById(R.id.txtv_tensp);
            txtv_giasp = itemView.findViewById(R.id.txtv_giasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent movetottsp = new Intent(itemView.getContext(),ChiTietSP.class);
                    movetottsp.putExtra("thongtinsp",arraysp.get(getAdapterPosition()));
                    itemView.getContext().startActivity(movetottsp);
                }
            });
        }
    }
}
