package com.example.datn_tranvantruong.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
        holder.description.setText("Tình trạng: " + bill.getDescription());
        holder.date.setText("Ngày đặt Tour: " + bill.getDate());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
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
    }
});
        holder.rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int product_id = billStatisticList.get(position).getProduct_id();
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.rate_dialog, null);
                dialogBuilder.setView(dialogView);

                final RatingBar dialogRatingBar = dialogView.findViewById(R.id.dialogRatingBar);
                final EditText dialogComment = dialogView.findViewById(R.id.dialogComment);

                dialogBuilder.setPositiveButton("Gửi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        float rating = dialogRatingBar.getRating();
                        String commentText = dialogComment.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDateTimeString = sdf.format(new Date(System.currentTimeMillis()));

                        Evaluate evaluate = new Evaluate(MainActivity.user_id, product_id, rating, commentText, currentDateTimeString);
                        //Thêm vào giỏ hàng
                        EvaluateHandler evaluateHandler = new EvaluateHandler();
                        evaluateHandler.rating(evaluate);
                        ProductHandler productHandler = new ProductHandler();

                        productHandler.updateAverageRating(product_id);
                        Toast.makeText(context, "Cảm ơn bạn đã đánh giá !", Toast.LENGTH_SHORT).show();
                    }
                });

                dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return billStatisticList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView idbill, total, description, date;
        Button rateButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idbill = itemView.findViewById(R.id.idbill);
            total = itemView.findViewById(R.id.total);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            rateButton = itemView.findViewById(R.id.rateButton);
        }
    }
}
