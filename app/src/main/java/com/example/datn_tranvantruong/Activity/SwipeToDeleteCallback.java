package com.example.datn_tranvantruong.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Adapter.BillAdapter;
import com.example.datn_tranvantruong.DBHandler.BillHandler;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final BillAdapter mAdapter; // Replace BillAdapter with the name of your Adapter
    private BillHandler billHandler;
    private Context mContext;

    public SwipeToDeleteCallback(BillAdapter adapter, Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        mContext = context;
        billHandler = new BillHandler(); // Initialize your BillHandler
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        String status1 = billHandler.getBillStatusById(mAdapter.getBillId(position)).toString().trim();

        if ("Chờ xác nhận".equals(status1) ) {


            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc muốn xóa đơn hàng này không?");

            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.deleteItem(position);
                }
            });

            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mAdapter.notifyItemChanged(position); // Restore item if the user cancels the delete operation
                }
            });

            builder.show();
        }else {
            mAdapter.notifyItemChanged(position);
            Toast.makeText(viewHolder.itemView.getContext(), "Không thể xóa do đơn hàng đã xác nhận vui lòng liên hệ để tư vấn!!", Toast.LENGTH_SHORT).show();        }
    }
}
