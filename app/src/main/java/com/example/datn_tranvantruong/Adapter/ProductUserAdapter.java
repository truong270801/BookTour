package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class ProductUserAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Product> productList;
    public ProductUserAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }
    public void setData(List<Product> newData) {
        this.productList = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.productImage = view.findViewById(R.id.productImage);
            holder.txtProductName = view.findViewById(R.id.txtProductName);
            holder.txtProductLocation = view.findViewById(R.id.txtProductLocation);
            holder.txtProductName = view.findViewById(R.id.txtProductName);
            holder.txtProductPrice = view.findViewById(R.id.txtProductPrice);
            holder.rating_Bar = view.findViewById(R.id.rating_Bar);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Product product = (Product) getItem(i);

        byte[] productImage = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);

        holder.productImage.setImageBitmap(bitmap);
        holder.txtProductName.setText(product.getName());
        holder.txtProductLocation.setText(product.getLocation());
        holder.txtProductPrice.setText("" + product.getPrice());
        holder.rating_Bar.setRating(product.getRating());

        return view;
    }

    private class ViewHolder {
        ImageView productImage;
        RatingBar rating_Bar;
        TextView txtProductName, txtProductPrice,txtProductLocation;
    }
}
