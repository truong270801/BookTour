package com.example.datn_tranvantruong.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.Adapter.CartAdapter;
import com.example.datn_tranvantruong.DBHandler.CartHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.CartStatistic;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private ArrayList<CartStatistic> cartStatistics = new ArrayList<>();
    private ListView lv;
    private CartAdapter cartAdapter;
    private int userId;
    CartHandler cartHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        lv = view.findViewById(R.id.lv_Cart);
        userId = Intro_Activity.user_id;
        display();
        return view;
    }

    private void display() {
        ProductHandler productHandler = new ProductHandler(getContext());
       Cursor cursor = cartHandler.getCartByUserID(getContext(),Intro_Activity.user_id);
        if (cartAdapter==null){
            while (cursor.moveToNext()){
                cartStatistics.add(new CartStatistic(
                        cursor.getInt(0),
                        cursor.getInt(2),
                        productHandler.getProductNameById(cursor.getInt(2)),
                        cursor.getInt(3),
                        cursor.getFloat(4),
                        productHandler.getProducImagetById(cursor.getInt(2))));
            }
            cartAdapter = new CartAdapter(getContext(), R.layout.cart_item, cartStatistics);
            lv.setAdapter(cartAdapter);
        }else {
            cartAdapter.notifyDataSetChanged();
        }
}}
