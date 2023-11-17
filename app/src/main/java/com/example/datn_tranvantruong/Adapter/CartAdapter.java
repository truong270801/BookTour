package com.example.datn_tranvantruong.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.CartHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Fragment.Order_Fragment;
import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.CartStatistic;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.R;

import java.util.Date;
import java.util.List;

public class CartAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<CartStatistic> cartList;
CartHandler cartHandler;
    public CartAdapter(Context context, int layout, List<CartStatistic> cartList) {
        this.context = context;
        this.layout = layout;
        this.cartList = cartList;
        cartHandler = new CartHandler(context);

    }
    @Override
    public int getCount() {
        return cartList.size();    }

    @Override
    public Object getItem(int i) {
        return cartList.get(i);
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

            holder = new CartAdapter.ViewHolder();
            holder.Cart_Image = view.findViewById(R.id.Cart_Image);
            holder.Cart_Name = view.findViewById(R.id.Cart_Name);
            holder.Cart_Price = view.findViewById(R.id.Cart_Price);
            holder.Cart_Quality = view.findViewById(R.id.Cart_Quality);
            holder.btn_Order = view.findViewById(R.id.btn_Order);
            holder.btn_Delete = view.findViewById(R.id.btn_Delete);

            view.setTag(holder);
        } else {
            holder = (CartAdapter.ViewHolder) view.getTag();
        }
        holder.btn_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                Date now = new Date();
                int product_id = cartList.get(i).getProduct_id();

                CartStatistic cart = cartList.get(i);
                int id = cart.getIdCart(); // Lấy ID của khách hàng
                int quatity = Integer.parseInt(String.valueOf(cart.getQuatity()));
                String price = holder.Cart_Price.getText().toString();
                String description = "Đã thanh toán";
                Bill bill = new Bill(Intro_Activity.user_id, product_id,quatity, price, description, now.toString());
                BillHandler billHandler = new BillHandler(context);
                billHandler.addBill(bill);

                // Clear the cart (assuming you have a method to clear it in your CartHandler)
                    cartHandler.deleteCart(id);
                    cartList.remove(i);


                // Show a toast indicating successful payment
                Toast.makeText(v.getContext(), "Thanh toán thành công!!", Toast.LENGTH_SHORT).show();

                // Navigate to another fragment (replace with your navigation logic)
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new Order_Fragment()); // Replace with the fragment you want to navigate to
                transaction.addToBackStack(null);
                transaction.commit();
                    }
                catch (Exception e) {
                    e.printStackTrace();

                    // Handle the exception, show an error message, or log it
                    Toast.makeText(v.getContext(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartStatistic cart = cartList.get(i);
                int id = cart.getIdCart(); // Lấy ID của khách hàng

                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                dialog.setMessage("Bạn thật sự muốn xóa ?");
                dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        cartHandler.deleteCart(id);
                        cartList.remove(i);
                        notifyDataSetChanged();

                        Toast.makeText(v.getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Hủy", null).show();
            }
        });
        CartStatistic cart = (CartStatistic) getItem(i);

        byte[] cartImage = cart.getProduct_image();
        if (cartImage != null && cartImage.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(cartImage, 0, cartImage.length);
            holder.Cart_Image.setImageBitmap(bitmap);
        } else {
            // Nếu hình ảnh là null, bạn có thể thiết lập một hình ảnh mặc định hoặc làm gì đó khác tùy thuộc vào yêu cầu của bạn.
            holder.Cart_Image.setImageResource(R.drawable.ic_avatar_default);
        }
        holder.Cart_Name.setText(cart.getProduct_name());
        holder.Cart_Quality.setText(String.valueOf(cart.getQuatity()));
        holder.Cart_Price.setText("" + cart.getTotal());

        return view;
    }
    private class ViewHolder {
        ImageView Cart_Image;
        TextView Cart_Name, Cart_Price,Cart_Quality;
        Button btn_Order, btn_Delete;
    }
}
