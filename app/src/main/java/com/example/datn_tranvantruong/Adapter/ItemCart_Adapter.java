package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Activity.Payment_Activity;
import com.example.datn_tranvantruong.Model.ItemCart_Model;
import com.example.datn_tranvantruong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCart_Adapter extends RecyclerView.Adapter<ItemCart_Adapter.ViewHolder> {
    private List<ItemCart_Model> itemList;
    private Context context;
    int nextProductID = 1;

    public ItemCart_Adapter(Context context, List<ItemCart_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemCart_Model item = itemList.get(position);

        holder.nameTextView.setText(item.getTitleCart());
        holder.priceTextView.setText(item.getPriceCart());

        Picasso.get().load(item.getImageUrl()).into(holder.imageView);
holder.Pay_Button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), Payment_Activity.class);
        // Kích hoạt Activity
        v.getContext().startActivity(intent);
    }
});
        holder.remoeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser != null) {

                    // Lấy ID của mục bạn muốn xóa (có thể lấy từ itemList)
                    String productID = String.valueOf(item.getIdCart());

                    // Thực hiện xóa sản phẩm từ Firebase Realtime Database
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("Carts")
                            .child(currentUser.getUid())
                            .child(productID); // Sử dụng itemID làm tên nút con

                    databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Xóa thành công
                                itemList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Sản phẩm đã được xóa khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xóa thất bại
                                Toast.makeText(context, "Lỗi: Không thể xóa sản phẩm", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });}

        @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private Button remoeButton,Pay_Button;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_cart);
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            remoeButton = itemView.findViewById(R.id.remoeButton);
            Pay_Button = itemView.findViewById(R.id.Pay_Button);
        }
    }
}
