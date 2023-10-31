package com.example.datn_tranvantruong.Fragment.FragmentItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.Model.ItemCart_Model;
import com.example.datn_tranvantruong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailTour_Fragment extends Fragment {
    String imageUrl, name, location, price, content, startday;
    FirebaseUser currentUser; // Khai báo biến ở mức lớn hơn

    public DetailTour_Fragment(String imageUrl, String name, String location, String price, String content, String startday) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.location = location;
        this.price = price;
        this.content = content;
        this.startday = startday;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detai_home, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        ImageView tourImageView = view.findViewById(R.id.tourImageView);
        TextView tourNametxtV = view.findViewById(R.id.tourNametxtV);
        TextView tourLocationtxtV = view.findViewById(R.id.tourLocationtxtV);
        TextView tourPricetxtV = view.findViewById(R.id.tourPricetxtV);
        TextView startday1 = view.findViewById(R.id.startday);
        TextView content1 = view.findViewById(R.id.content_tour);

        ImageView addcart = view.findViewById(R.id.btn_addcart);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference cartsRef = FirebaseDatabase.getInstance().getReference()
                        .child("Carts")
                        .child(currentUser.getUid());

                cartsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long itemCount = dataSnapshot.getChildrenCount(); // Đếm số lượng mục trong giỏ hàng

                        String productID = String.valueOf(itemCount);  // Tạo ID dựa trên số thứ tự
                        String productName = name;
                        String productPrice = price;
                        String productImageUrl = imageUrl;

                        // Lưu thông tin sản phẩm vào Firebase Realtime Database với ID mới
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                .child("Carts")
                                .child(currentUser.getUid())
                                .child(productID); // Sử dụng productID làm tên nút con

                        ItemCart_Model product = new ItemCart_Model(productID, productName, productPrice, productImageUrl);
                        databaseReference.setValue(product);

                        Toast.makeText(getContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý lỗi nếu cần
                    }
                });
            }
        });

        Glide.with(tourImageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(tourImageView);

        tourNametxtV.setText(name);
        tourLocationtxtV.setText(location);
        tourPricetxtV.setText(price);
        startday1.setText(startday);

        // Xử lý dữ liệu xuống dòng cho nội dung tour
        String formattedContent = content.replace("\\n", "\n");
        content1.setText(formattedContent);

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
