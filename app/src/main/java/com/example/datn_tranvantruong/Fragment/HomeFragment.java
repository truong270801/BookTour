package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.Adapter.ItemHome_Adapter;
import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.Model.Category;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment{
    private ItemHome_Adapter adapter;
    SearchView search_tour;
    ListView lv;
    List<Category> categoryList;
    CategoryHandler categoryHandler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lv = view.findViewById(R.id.lv_user_category);


        categoryList = new ArrayList<>();
        display();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Khởi tạo FragmentB
                Product_Fragment productFragment = new Product_Fragment();

                // Truyền dữ liệu từ FragmentA sang FragmentB
                Bundle args = new Bundle();
                Category category = (Category) adapter.getItem(i);
                args.putInt("category_id", category.getIdCategory());
                args.putString("category_name", category.getNameCategory());
                productFragment.setArguments(args);

                // Thực hiện thay thế FragmentA bằng FragmentB
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, productFragment);
                transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                transaction.commit();
            }
        });


        return view;
    }

    public void display() {
        categoryHandler = new CategoryHandler(getContext());
        categoryList = categoryHandler.getAllCategoriesWithIdCategory();
        adapter = new ItemHome_Adapter(getContext(), R.layout.category_item, categoryList);
        lv.setAdapter(adapter);
    }
}