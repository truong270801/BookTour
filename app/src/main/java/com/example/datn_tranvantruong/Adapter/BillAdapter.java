package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.datn_tranvantruong.DBHandler.BillHandler;

import com.example.datn_tranvantruong.Model.BillStatistic;

import com.example.datn_tranvantruong.R;

import java.util.List;

public class BillAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<BillStatistic> billStatisticList;
    BillHandler billHandler;
    public BillAdapter(Context context, int layout, List<BillStatistic> billStatisticList) {
        this.context = context;
        this.layout = layout;
        this.billStatisticList = billStatisticList;
        billHandler = new BillHandler();

    }
    @Override
    public int getCount() {
        return billStatisticList.size();
    }

    @Override
    public Object getItem(int i) {
        return billStatisticList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.idbill = view.findViewById(R.id.idbill);
            holder.total = view.findViewById(R.id.total);
            holder.description = view.findViewById(R.id.description);
            holder.date = view.findViewById(R.id.date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BillStatistic bill = (BillStatistic) getItem(i);

        holder.idbill.setText("ID: #KTTRAVEL" + String.valueOf(bill.getBill_id()));
        holder.total.setText("Giá: "+ String.valueOf(bill.getPrice()) +"VND");
        holder.description.setText("Tình trạng: " + bill.getDescription());
        holder.date.setText("Ngày đặt Tour: " + bill.getDate());

        return view;
    }
    private class ViewHolder {
        TextView idbill, total,description,date;
    }
}
