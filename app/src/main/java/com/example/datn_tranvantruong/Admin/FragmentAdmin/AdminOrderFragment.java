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
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.Fragment.FragmentItem.BillDetailFragment;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;


public class AdminOrderFragment extends Fragment {
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
        Cursor cursor = billHandler.getBillByUserID(getContext(),Intro_Activity.user_id);
        if (billAdapter==null){
            while (cursor.moveToNext()){
                billStatistics.add(new BillStatistic(
                        cursor.getInt(0),
                        cursor.getInt(2),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6)
                ));
            }
            billAdapter = new BillAdapter(getContext(), R.layout.order_item, billStatistics);
            lv.setAdapter(billAdapter);
        }else {
            billAdapter.notifyDataSetChanged();
        }

        billAdapter = new BillAdapter(getContext(), R.layout.order_item, billStatistics);
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


}