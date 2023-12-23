package com.example.datn_tranvantruong.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.EvaluateHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Fragment.FragmentItem.BillDetailFragment;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Evaluate;
import com.example.datn_tranvantruong.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminBillAdapter extends RecyclerView.Adapter<AdminBillAdapter.ViewHolder> {
    private Context context;
    private int layout;
    private List<BillStatistic> billStatisticList;
    private BillHandler billHandler;

    public AdminBillAdapter(Context context, int layout, List<BillStatistic> billStatisticList) {
        this.context = context;
        this.layout = layout;
        this.billStatisticList = billStatisticList;
        billHandler = new BillHandler();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillStatistic bill = billStatisticList.get(position);

        holder.idbill.setText("ID: #KTTRAVEL" + String.valueOf(bill.getBill_id()));
        holder.total.setText("Giá: " + String.valueOf(bill.getPrice()) + "VND");
        holder.description.setText(bill.getDescription());
        holder.date.setText("Ngày đặt Tour: " + bill.getDate());
        holder.status.setText("Tình trạng: " + bill.getStatus());
        holder.edtstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.status_dialog, null);
                dialogBuilder.setView(dialogView);

                final RadioGroup radioGroupStatus = dialogView.findViewById(R.id.radioGroupStatus);
                dialogBuilder.setPositiveButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedId = radioGroupStatus.getCheckedRadioButtonId();
                        if (selectedId == R.id.radioXN) {
                            String status = "Đã xác nhận";
                            billHandler.UpdateBillStatus(bill.getBill_id(),status);

                        } else if(selectedId == R.id.radioTH){
                            String status = "Chuyến tour đang thực hiện";
                            billHandler.UpdateBillStatus(bill.getBill_id(),status);
                        } else if(selectedId == R.id.radioKT){
                            String status = "Đã kết thúc";
                            billHandler.UpdateBillStatus(bill.getBill_id(),status);

                        }else {
                            String status = "Hủy tour";
                            billHandler.UpdateBillStatus(bill.getBill_id(),status);

                        }
                        notifyDataSetChanged();
                    }
                } ).setNegativeButton("Hủy", null).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status1 = billHandler.getBillStatusById(bill.getBill_id()).toString().trim();

                if ("Đã xác nhận".equals(status1) || "Chuyến tour đang thực hiện".equals(status1) || "Đã kết thúc".equals(status1)) {

                    BillDetailFragment billDetailFragment = new BillDetailFragment();

                Bundle args = new Bundle();
                BillStatistic billStatistic = billStatisticList.get(position);
                args.putString("billprice", String.valueOf(billStatistic.getPrice()));
                args.putString("billid", String.valueOf(billStatistic.getBill_id()));
                args.putString("billdate", billStatistic.getDate());
                args.putInt("productId", billStatistic.getProduct_id());

                billDetailFragment.setArguments(args);
                FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_admin, billDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                }else {
                    Toast.makeText(v.getContext(), "Đơn hàng đã bị hủy!!", Toast.LENGTH_SHORT).show();                }
            }
        });

    }

    private void onclick(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return billStatisticList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idbill, total, description, date,status;
        Button edtstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                    idbill = itemView.findViewById(R.id.idbill);
            total = itemView.findViewById(R.id.total);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            status = itemView.findViewById(R.id.status);
            edtstatus = itemView.findViewById(R.id.edtstatus);
        }
    }
}
