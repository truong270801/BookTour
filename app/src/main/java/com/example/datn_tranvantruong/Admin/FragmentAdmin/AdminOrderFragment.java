package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.Adapter.AdminBillAdapter;
import com.example.datn_tranvantruong.Adapter.BillAdapter;
import com.example.datn_tranvantruong.Adapter.CustomerAdapter;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Fragment.FragmentItem.BillDetailFragment;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class AdminOrderFragment extends Fragment {
    private List<BillStatistic> billStatistics = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdminBillAdapter adminBillAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_order, container, false);
        recyclerView = view.findViewById(R.id.rcv_Bill);

        display();


        return  view;
    }

    private void display() {
        BillHandler billHandler = new BillHandler();
        billStatistics = billHandler.getAllBill(); // Replace with your actual method to fetch bills

        // Set up the RecyclerView and its adapter
        adminBillAdapter = new AdminBillAdapter(getContext(), R.layout.order_item, billStatistics);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adminBillAdapter);
    }
    }


