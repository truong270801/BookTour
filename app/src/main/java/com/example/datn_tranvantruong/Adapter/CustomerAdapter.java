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

import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Customer> customersList;
    CustomerHandler customerHandler;

    public CustomerAdapter(Context context, int layout, List<Customer> customersList) {
        this.context = context;
        this.layout = layout;
        this.customersList = customersList;
        customerHandler = new CustomerHandler();
    }

    @Override
    public int getCount() {
        return customersList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        TextView customer_name = view.findViewById(R.id.customer_name);
        TextView customer_address = view.findViewById(R.id.customer_address);
        TextView customer_phone = view.findViewById(R.id.customer_phone);
        TextView customer_email = view.findViewById(R.id.customer_email);

        Customer customer = customersList.get(i);
        customer_email.setText(customer.getEmail());
        customer_name.setText(customer.getFullname());
        customer_address.setText(customer.getAddress());
        customer_phone.setText(customer.getPhone());
        byte[] imageByteArray = customer.getImage_avatar();
        ImageView customerImg = null;
        if (imageByteArray != null) {
            // Chuyển đổi mảng byte thành đối tượng Bitmap
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);

            // Tìm ImageView trong layout của bạn
            customerImg = view.findViewById(R.id.customer_img);

            // Đặt hình ảnh vào ImageView
            customerImg.setImageBitmap(imageBitmap);
        } else {

        }


        Button btn_deleteCustomer = (Button) view.findViewById(R.id.btn_deleteCustomer);
        btn_deleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy khách hàng được chọn
                Customer customer = customersList.get(i);
                int id = customer.getId(); // Lấy ID của khách hàng

                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
                dialog.setTitle("XÓA KHÁCH HÀNG NÀY?");
                dialog.setMessage("Bạn thật sự muốn xóa khách hàng này?");
                dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        customerHandler.deleteCartCustomer(id);
                        customerHandler.deleteBillCustomer(id);
                        customerHandler.deleteEvaluateCustomer(id);
                        customerHandler.deleteCustomer(id); // Xóa khách hàng bằng ID
                        customersList.remove(i); // Xóa khách hàng khỏi danh sách
                        notifyDataSetChanged(); // Cập nhật Adapter

                        Toast.makeText(view.getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Hủy", null).show();
            }
        });



        return view;
    }

}
