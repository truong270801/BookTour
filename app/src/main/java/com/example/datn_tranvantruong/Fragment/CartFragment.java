package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Adapter.CartAdapter;
import com.example.datn_tranvantruong.DBHandler.CartHandler;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.CartStatistic;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private List<CartStatistic> cartList = new ArrayList<>();
    private ListView lv;
    private CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        lv = view.findViewById(R.id.lv_Cart);
        display();
        return view;
    }

    private void display() {
        CartHandler cartHandler = new CartHandler();

        cartList = cartHandler.getCartByUserID(MainActivity.user_id);
        cartAdapter = new CartAdapter(getContext(), R.layout.cart_item, cartList);
        lv.setAdapter(cartAdapter);

    }}
