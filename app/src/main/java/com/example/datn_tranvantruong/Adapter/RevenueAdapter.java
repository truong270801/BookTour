package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.BillRevenue;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.R;


import java.util.List;


public class RevenueAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BillRevenue> billRevenues;

    public RevenueAdapter(Context context, int layout, List<BillRevenue> billRevenues) {
        this.context = context;
        this.layout = layout;
        this.billRevenues = billRevenues;
    }

    @Override
    public int getCount() {
        return billRevenues.size();
    }

    @Override
    public Object getItem(int position) {
        return billRevenues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        TextView textViewBillId = (TextView) view.findViewById(R.id.textViewBillId);
        TextView textViewProductQuantity = (TextView) view.findViewById(R.id.textViewProductQuantity);
        TextView textViewProductTotalSales = (TextView) view.findViewById(R.id.textViewProductTotalSales);

        BillRevenue billSales = billRevenues.get(position);
        textViewBillId.setText("#KTTRAVEL" + Integer.toString(billSales.getBill_id()));
        textViewProductQuantity.setText("" + Integer.toString(billSales.getQuatity()));
        textViewProductTotalSales.setText("" + billSales.getPrice() + " VND");

        return view;
    }
}