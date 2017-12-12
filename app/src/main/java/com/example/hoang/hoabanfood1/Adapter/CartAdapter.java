package com.example.hoang.hoabanfood1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.hoabanfood1.Activity.Cart;
import com.example.hoang.hoabanfood1.Activity.MainActivity;
import com.example.hoang.hoabanfood1.Model.Giohang;
import com.example.hoang.hoabanfood1.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by hoang on 10/16/2017.
 */

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arraycart;

    public CartAdapter(Context context, ArrayList<Giohang> arraycart) {
        this.context = context;
        this.arraycart = arraycart;
    }

    @Override
    public int getCount() {
        return arraycart.size();
    }

    @Override
    public Object getItem(int i) {
        return arraycart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class ViewHolder {
        public TextView txtvtenspcart, txtvgiaspcart,txtvslcart;
        public ImageView imgcart;
        public ImageButton btnxoakhoicart,btncong,btntru;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.donggiohang, null);
            viewHolder.txtvtenspcart = (TextView) view.findViewById(R.id.txtv_tenspcart);
            viewHolder.txtvgiaspcart = (TextView) view.findViewById(R.id.txtv_giaspcart);
            viewHolder.txtvslcart = (TextView) view.findViewById(R.id.txtv_slcart);
            viewHolder.imgcart = (ImageView) view.findViewById(R.id.img_cart);
            viewHolder.btncong = (ImageButton) view.findViewById(R.id.btn_cong);
            viewHolder.btntru = (ImageButton) view.findViewById(R.id.btn_tru);
            viewHolder.btnxoakhoicart = (ImageButton) view.findViewById(R.id.btn_xoakhoicart);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Giohang giohang = (Giohang) getItem(i);
        viewHolder.txtvtenspcart.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtvgiaspcart.setText("Giá: " + decimalFormat.format(giohang.getTongtien()) + "vnđ");
        viewHolder.txtvslcart.setText(giohang.getSoluong()+ "");
        Picasso.with(context).load(giohang.getHinhsp())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.imgcart);
        final int sl = Integer.parseInt(viewHolder.txtvslcart.getText().toString());
        if(sl<=1) {
            viewHolder.btntru.setVisibility(view.INVISIBLE);
        }
        else
        {
            viewHolder.btntru.setVisibility(view.VISIBLE);
            viewHolder.btncong.setVisibility(view.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewHolder.txtvslcart.getText().toString()) + 1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluong();
                long tongtienhientai = MainActivity.manggiohang.get(i).getTongtien();
                MainActivity.manggiohang.get(i).setSoluong(slmoi);
                long tongtienmoi = (tongtienhientai*slmoi)/slhientai;
                MainActivity.manggiohang.get(i).setTongtien(tongtienmoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtvgiaspcart.setText("Giá: " + decimalFormat.format(tongtienmoi) + "vnđ");
                Cart.TinhTong();
                if(slmoi>=2) {
                    finalViewHolder.btntru.setVisibility(view.VISIBLE);
                    finalViewHolder.txtvslcart.setText(String.valueOf(slmoi));

                }
            }
        });

        final ViewHolder finalViewHolder1 = viewHolder;
        viewHolder.btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi = Integer.parseInt(finalViewHolder.txtvslcart.getText().toString()) - 1;
                int slhientai = MainActivity.manggiohang.get(i).getSoluong();
                long tongtienhientai = MainActivity.manggiohang.get(i).getTongtien();
                MainActivity.manggiohang.get(i).setSoluong(slmoi);
                long tongtienmoi = (tongtienhientai*slmoi)/slhientai;
                MainActivity.manggiohang.get(i).setTongtien(tongtienmoi);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtvgiaspcart.setText("Giá: " + decimalFormat.format(tongtienmoi) + "vnđ");
                Cart.TinhTong();
                if(slmoi<2) {
                    finalViewHolder.btntru.setVisibility(view.INVISIBLE);
                    finalViewHolder.txtvslcart.setText(String.valueOf(slmoi));

                }
                else
                {
                    finalViewHolder.btntru.setVisibility(view.VISIBLE);
                    finalViewHolder.btncong.setVisibility(view.VISIBLE);
                    finalViewHolder.txtvslcart.setText(String.valueOf(slmoi));
                }
            }
        });
        final ViewHolder finalViewHolder2 = viewHolder;
        viewHolder.btnxoakhoicart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Giohang giohang = new Giohang(MainActivity.manggiohang.get(i).getMasp());
                if(MainActivity.manggiohang.contains(giohang)){
                    MainActivity.manggiohang.remove(giohang);
                    notifyDataSetChanged();
                }
                Cart.TinhTong();
            }
        });
        return view;
    }
}
