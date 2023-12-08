package com.example.datn_tranvantruong.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.Adapter.CategoryAdapter;
//import com.example.datn_tranvantruong.Adapter.ProductsAdapter;
import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.Category;
import com.example.datn_tranvantruong.R;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;
    private List<Category> categoryList;
    private CategoryHandler categoryHandler;
    private ImageView img_avatar;
    private TextView tv_name;
    private SearchView search_tour;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rcv_user_category);
        img_avatar = view.findViewById(R.id.img_avatar);
        tv_name = view.findViewById(R.id.tv_name);
        search_tour = view.findViewById(R.id.search_tour);

        search_tour.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                ArrayList<Category> searchList = new ArrayList<>();
                for (Category category : categoryList){
                    if (category.getNameCategory().toLowerCase().contains(query.toLowerCase())){
                        searchList.add(category);

                    }
                    adapter.setData(searchList);
                }


                return true;
            }
        });
        showUserInformation();
        display();
        return view;
    }

    public void display() {
        adapter = new CategoryAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        categoryList = new ArrayList<>();
        categoryHandler = new CategoryHandler();
        categoryList = categoryHandler.getAllCategoriesWithIdCategory();
        adapter.setData(categoryList);
    }
    private void  showUserInformation(){
        CustomerHandler customerHandler = new CustomerHandler();

        String name = customerHandler.getCustomerInfo(MainActivity.user_id).getFullname();
        byte[] avatar = customerHandler.getCustomerInfo(MainActivity.user_id).getImage_avatar();
        // Gán giá trị "email" vào TextView
        tv_name.setText(name);
        if (avatar != null) {
            Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
            img_avatar.setImageBitmap(avatarBitmap);

        }
    }

}

