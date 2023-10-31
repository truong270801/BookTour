package com.example.datn_tranvantruong.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Adapter.ItemCart_Adapter;
import com.example.datn_tranvantruong.Model.ItemCart_Model;
import com.example.datn_tranvantruong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView_Cart;
    private ItemCart_Adapter adapter;
    private List<ItemCart_Model> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView_Cart = view.findViewById(R.id.recyclerView_Cart);
        recyclerView_Cart.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemList = new ArrayList<>();
        adapter = new ItemCart_Adapter(getActivity(), itemList);
        recyclerView_Cart.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView_Cart.setLayoutManager(linearLayoutManager);

        // Xác định người dùng hiện tại
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // Lấy thông tin giỏ hàng từ Firebase Realtime Database
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("Carts")
                    .child(currentUser.getUid());

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    ItemCart_Model product = dataSnapshot.getValue(ItemCart_Model.class);
                    itemList.add(product);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    // Xử lý khi thông tin sản phẩm trong giỏ hàng bị thay đổi (nếu cần)
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    // Xử lý khi sản phẩm được di chuyển (nếu cần)
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi xảy ra lỗi (nếu cần)
                }
            });
        }

        return view;
    }
}
