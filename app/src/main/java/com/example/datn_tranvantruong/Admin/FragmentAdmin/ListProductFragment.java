package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.Adapter.ProductAdapter;
import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class ListProductFragment extends Fragment {

    ImageView createProductButton;
    List<Product> productArrayList;
    ProductAdapter productAdapter;
    ImageView btnCreateProduct;
    ProductHandler productHandler;

    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_product, container, false);
        lv = (ListView) view.findViewById(R.id.lv_Product);
        btnCreateProduct = view.findViewById(R.id.btnCreateProduct);
        productHandler = new ProductHandler();
        display();



        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // Khởi tạo FragmentB
                EditProduct_Fragment editProductFragment = new EditProduct_Fragment();

                // Tạo Bundle để truyền dữ liệu sản phẩm được chọn từ Adapter sang Fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable("Edit", productAdapter.getItem(arg2));
                editProductFragment.setArguments(bundle);

                // Thực hiện thay thế FragmentA bằng FragmentB
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_admin, editProductFragment);
                transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                transaction.commit();
            }
        });


        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                AddProduct_Fragment addProductFragment = new AddProduct_Fragment();

                // Thực hiện thay thế FragmentA bằng FragmentB
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_admin, addProductFragment);
                transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                transaction.commit();
            }
        });


        return  view;
    }
    public void display() {
        productArrayList = productHandler.getAllProducts();
        productAdapter = new ProductAdapter(requireContext(), R.layout.productadmin_item, productArrayList);
        lv.setAdapter(productAdapter);
    }
}