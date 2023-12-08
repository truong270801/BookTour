package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.datn_tranvantruong.Adapter.BillAdapter;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
//import com.example.datn_tranvantruong.Fragment.FragmentItem.BillDetailFragment;
import com.example.datn_tranvantruong.Fragment.FragmentItem.BillDetailFragment;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class Order_Fragment extends Fragment {
    private List<BillStatistic> billStatistics = new ArrayList<>();
    private ListView lv;
    private BillAdapter billAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_, container, false);
        lv = view.findViewById(R.id.lv_Bill);
       display();
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
                    transaction.replace(R.id.fragment_container, billDetailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

            }
        });

        return  view;
    }

    private void display() {
        BillHandler billHandler = new BillHandler();
        billStatistics = billHandler.getBillByUserID(MainActivity.user_id);
        billAdapter = new BillAdapter(getContext(), R.layout.order_item, billStatistics);
        lv.setAdapter(billAdapter);
        billAdapter.notifyDataSetChanged();
    }

}