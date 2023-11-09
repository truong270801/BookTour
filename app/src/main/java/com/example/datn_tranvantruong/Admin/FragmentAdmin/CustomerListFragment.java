package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Adapter.CustomerAdapter;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;


public class CustomerListFragment extends Fragment {
    ArrayList<Customer> customerArrayList;
    CustomerAdapter customerAdapter;
    ListView lv;
    CustomerHandler customerHandler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);
        lv = view.findViewById(R.id.lv_Customer);
        customerHandler = new CustomerHandler(getContext());
        customerArrayList = new ArrayList<>();

        display();
        return view;
    }
    public void display(){
        customerArrayList = (ArrayList<Customer>) customerHandler.getAllCustomer();
        customerAdapter = new CustomerAdapter(getContext(), R.layout.customer_item, customerArrayList);
        lv.setAdapter(customerAdapter);
        customerAdapter.notifyDataSetChanged();
    }
}