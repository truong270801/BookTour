package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.datn_tranvantruong.Adapter.EvaluateAdapter;
import com.example.datn_tranvantruong.DBHandler.EvaluateHandler;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.Model.Evaluate;
import com.example.datn_tranvantruong.R;

import java.util.List;


public class EvaluateAdminFragment extends Fragment {

    RecyclerView rcv_evaluate;
    EvaluateAdapter evaluateAdapter;
    EvaluateHandler evaluateHandler;
    List<Evaluate> evaluateList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluate_admin, container, false);
        rcv_evaluate = view.findViewById(R.id.rcv_evaluate);


        Bundle bundle = getArguments();
        if (bundle != null) {
            int product_id = bundle.getInt("id");
            evaluateHandler = new EvaluateHandler();
            evaluateList = evaluateHandler.getAllEvaluateByProductId(product_id);
            evaluateAdapter = new EvaluateAdapter(getContext(), evaluateList);
        }
        toolbar(view);
        display();

        return view;
    }

    private void display() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv_evaluate.setLayoutManager(layoutManager);
        rcv_evaluate.setAdapter(evaluateAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcv_evaluate.getContext(), layoutManager.getOrientation());
        rcv_evaluate.addItemDecoration(dividerItemDecoration);
    }

    private void toolbar(View view) {
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar11);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_admin, new ListProductFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}