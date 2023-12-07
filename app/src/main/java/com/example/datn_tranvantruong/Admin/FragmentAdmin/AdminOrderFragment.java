package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.Adapter.BillAdapter;
import com.example.datn_tranvantruong.Adapter.CustomerAdapter;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Fragment.FragmentItem.BillDetailFragment;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;


public class AdminOrderFragment extends Fragment {
    private ArrayList<BillStatistic> billStatistics = new ArrayList<>();
    private ListView lv;
    private BillAdapter billAdapter;
    ArrayList<BillStatistic> billStatisticArrayList;
    BillHandler billHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_order, container, false);
        lv = view.findViewById(R.id.lv_Bill);
        billHandler = new BillHandler();

        display();
        lv.setAdapter(billAdapter);
        //xuwr lys
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BillDetailFragment billDetailFragment = new BillDetailFragment();

                Bundle args = new Bundle();
                BillStatistic billStatistic = (BillStatistic) billAdapter.getItem(i);
                args.putString("billprice", String.valueOf(billStatistic.getPrice()));
                args.putString("billid", String.valueOf(billStatistic.getBill_id()));
                args.putString("billdate", billStatistic.getDate());
                args.putInt("productId", billStatistic.getProduct_id());


                billDetailFragment.setArguments(args);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_admin, billDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return  view;
    }

    private void display() {
        billStatisticArrayList = (ArrayList<BillStatistic>) billHandler.getAllBill();
        billAdapter = new BillAdapter(getContext(), R.layout.order_item, billStatisticArrayList);
        lv.setAdapter(billAdapter);
        billAdapter.notifyDataSetChanged();
    }


}