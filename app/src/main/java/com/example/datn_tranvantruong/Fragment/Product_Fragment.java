package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.Adapter.ProductUserAdapter;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Fragment.FragmentItem.DetailTour_Fragment;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class Product_Fragment extends Fragment {
    TextView txtCategoryName;
    ListView lv;
    List<Product> productList;
    SearchView search_tour;
    ProductHandler productHandler;
    ProductUserAdapter productUserAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_, container, false);

        txtCategoryName = view.findViewById(R.id.txtCategoryname);
        search_tour = view.findViewById(R.id.search_tour);
        lv = view.findViewById(R.id.lv_user_product);
        productList = new ArrayList<>();

        search_tour = view.findViewById(R.id.search_tour);
        search_tour.clearFocus();
        search_tour.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                ArrayList<Product> searchList = new ArrayList<>();
                for (Product product : productList){
                    if (product.getName().toLowerCase().contains(query.toLowerCase())){
                        searchList.add(product);

                    }
                    productUserAdapter.setData(searchList);
                }
                return true;
            }
        });
        Bundle bundle = getArguments();
        int category_id = bundle.getInt("category_id", 1);
        String category_name = bundle.getString("category_name");
        txtCategoryName.setText(category_name);

        productHandler = new ProductHandler();
        productList = productHandler.getAllProductByCategoryId(category_id);

        productUserAdapter = new ProductUserAdapter(getContext(), R.layout.product_item, productList);
        lv.setAdapter(productUserAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DetailTour_Fragment detailTourFragment = new DetailTour_Fragment();

                // Truyền dữ liệu từ FragmentA sang FragmentB (nếu cần)
                Bundle args = new Bundle();
                Product product = (Product) productUserAdapter.getItem(i);
                args.putInt("product_id", product.getId());
                args.putString("category_name", txtCategoryName.getText().toString().trim());
                detailTourFragment.setArguments(args);
                Toast.makeText(getContext(), ""+product.getId(), Toast.LENGTH_SHORT).show();

                // Thực hiện thay thế FragmentA bằng FragmentB
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, detailTourFragment);
                transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                transaction.commit();
            }
        });
        return view;
    }

}