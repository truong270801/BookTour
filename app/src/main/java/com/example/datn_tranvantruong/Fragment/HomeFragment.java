package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

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

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(view.getContext(), Tour_Fragment.class);
//                Category category = (Category) adapter.getItem(i);
//                intent.putExtra("category_id", category.getIdCategory());
//                intent.putExtra("category_name", category.getNameCategory());
//                startActivity(intent);
//            }
//        });






        return view;
    }

    public void display() {
        categoryHandler = new CategoryHandler(getContext());
        categoryList = categoryHandler.getAllCategoriesWithIdCategory();
        adapter = new ItemHome_Adapter(getContext(), R.layout.category_item, categoryList);
        lv.setAdapter(adapter);
    }
}