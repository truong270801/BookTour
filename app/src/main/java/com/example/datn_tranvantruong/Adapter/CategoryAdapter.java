package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Fragment.Product_Fragment;
import com.example.datn_tranvantruong.Model.Category;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;



    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }
    public String getData() {
        return categoryList.toString();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if (category == null) {
            return;
        }
        holder.txtCategoryName.setText(category.getNameCategory());
        holder.seall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product_Fragment productFragment = new Product_Fragment();

                // Truyền dữ liệu từ FragmentA sang FragmentB
                Bundle args = new Bundle();
                args.putInt("category_id", category.getIdCategory());
                args.putString("category_name", category.getNameCategory());
                productFragment.setArguments(args);

                // Thực hiện thay thế FragmentA bằng FragmentB
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, productFragment);
                transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                transaction.commit();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcv_product.setLayoutManager(linearLayoutManager);
        ProductHandler productHandler = new ProductHandler();
        List<Product> productList = productHandler.getAllProductByCategoryId(category.getIdCategory());
        ProductsAdapter productsAdapter = new ProductsAdapter(context);
        productsAdapter.setData(productList);
        holder.rcv_product.setAdapter(productsAdapter);
    }

    @Override
    public int getItemCount() {
        return categoryList != null ? categoryList.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCategoryName,seall;
        private RecyclerView rcv_product;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            seall = itemView.findViewById(R.id.seall);
            rcv_product = itemView.findViewById(R.id.rcv_product);
        }
    }
}
