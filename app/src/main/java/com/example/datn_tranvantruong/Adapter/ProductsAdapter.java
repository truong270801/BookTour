package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Fragment.FragmentItem.DetailTour_Fragment;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>{
    private List<Product> productList;
    private Context context;
    public ProductsAdapter(Context context) {
        this.context = context;
    }

    public void setData (List<Product> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);

        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
    Product product = productList.get(position);
    if (product == null){
    return;
    }
    holder.name_tour.setText(product.getName());
    holder.location_tour.setText(product.getLocation());
    byte[] productImage = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        holder.image_tour.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailTour_Fragment detailTourFragment = new DetailTour_Fragment();

                // Truyền dữ liệu từ FragmentA sang FragmentB (nếu cần)
                Bundle args = new Bundle();
                Product product = productList.get(position);
                args.putInt("product_id", product.getId());
                args.putString("category_name", product.getCategoryName());
                detailTourFragment.setArguments(args);
                Toast.makeText(context, "" + product.getId(), Toast.LENGTH_SHORT).show();

                // Thực hiện thay thế FragmentA bằng FragmentB
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detailTourFragment);
                transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null){
            return  productList.size();
        }
        return 0;
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder{
        private ImageView image_tour;
        private TextView name_tour,location_tour;

        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            image_tour = itemView.findViewById(R.id.image_tour);
            name_tour = itemView.findViewById(R.id.name_tour);
            location_tour = itemView.findViewById(R.id.loaction_tour);

        }
    }
}
