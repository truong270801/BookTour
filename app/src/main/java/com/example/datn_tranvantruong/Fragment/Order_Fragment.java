package com.example.datn_tranvantruong.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.Adapter.BillAdapter;
import com.example.datn_tranvantruong.Adapter.CartAdapter;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;


public class Order_Fragment extends Fragment {
    private ArrayList<BillStatistic> billStatistics = new ArrayList<>();
    private ListView lv;
    private BillAdapter billAdapter;
    private int userId;
    BillHandler billHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_, container, false);
        lv = view.findViewById(R.id.lv_Bill);
        userId = Intro_Activity.user_id;
        display();
        return  view;
    }

    private void display() {
        ProductHandler productHandler = new ProductHandler(getContext());
        Cursor cursor = billHandler.getBillByUserID(getContext(),Intro_Activity.user_id);
        if (billAdapter==null){
            while (cursor.moveToNext()){
                billStatistics.add(new BillStatistic(
                        cursor.getInt(0),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5)
                ));
            }
            billAdapter = new BillAdapter(getContext(), R.layout.order_item, billStatistics);
            lv.setAdapter(billAdapter);
        }else {
            billAdapter.notifyDataSetChanged();
        }
    }
}