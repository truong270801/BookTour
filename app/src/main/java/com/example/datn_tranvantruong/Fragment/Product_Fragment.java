package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Adapter.ProductUserAdapter;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class Product_Fragment extends Fragment {
    TextView txtCategoryName;
    ListView lv;
    List<Product> productList;
    ProductHandler productHandler;
    ProductUserAdapter productUserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_, container, false);

        txtCategoryName = view.findViewById(R.id.txtCategoryname);
        lv = view.findViewById(R.id.lv_user_product);
        productList = new ArrayList<>();

        Bundle bundle = getArguments();
        int category_id = bundle.getInt("category_id", 1);
        String category_name = bundle.getString("category_name");
        txtCategoryName.setText(category_name);

        productHandler = new ProductHandler(getContext());
        productList = productHandler.getAllProductByCategoryId(category_id);

        productUserAdapter = new ProductUserAdapter(getContext(), R.layout.product_item, productList);
        lv.setAdapter(productUserAdapter);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(view.getContext(), Tour_Fragment.class);
//                Product product = (Product) productUserAdapter.getItem(i);
//                intent.putExtra("product_id", product.getId());
//                intent.putExtra("category_name", txtCategoryName.getText());
////                Toast.makeText(ProductListUserActivity.this, ""+product.getId(), Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
//        });
        return view;
    }

}