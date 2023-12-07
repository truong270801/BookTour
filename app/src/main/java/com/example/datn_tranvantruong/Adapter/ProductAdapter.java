package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, int resource, List<Product> objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.productadmin_item, null);
        }
        Product product = getItem(position);
        if (product != null) {
            ImageView image = view.findViewById(R.id.image);
            TextView txtName = view.findViewById(R.id.tvName);
            TextView txtLocation = view.findViewById(R.id.tvLocation);
            TextView txtPrice = view.findViewById(R.id.tvPrice);
            TextView txtCategory =  view.findViewById(R.id.tvCategory);
            byte[] productImage = product.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
            image.setImageBitmap(bitmap);
            txtName.setText(product.getName());
            txtLocation.setText(String.valueOf(product.getLocation()));
            txtPrice.setText(String.valueOf(product.getPrice()));
            txtCategory.setText(String.valueOf(product.getCategoryName()));
        }
        return view;
    }
}
