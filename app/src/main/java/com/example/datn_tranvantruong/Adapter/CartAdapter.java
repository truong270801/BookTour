package com.example.datn_tranvantruong.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.CartHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.Fragment.Order_Fragment;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.CartStatistic;
import com.example.datn_tranvantruong.Model.CreateOrder;
import com.example.datn_tranvantruong.Model.Customer;
import com.example.datn_tranvantruong.Model.Pay;
import com.example.datn_tranvantruong.R;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CartAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<CartStatistic> cartList;
    CartHandler cartHandler;
    private boolean isZaloPayCompleted = false;
    Pay pay;
    public CartAdapter(Context context, int layout, List<CartStatistic> cartList) {
        this.context = context;
        this.layout = layout;
        this.cartList = cartList;
        cartHandler = new CartHandler();

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
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View dialogView = inflater.inflate(R.layout.pay_dialog, null);
                    dialogBuilder.setView(dialogView);

                    final EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
                    final EditText editTextUsername = dialogView.findViewById(R.id.editTextUsername);
                    final EditText editTextPhone = dialogView.findViewById(R.id.editTextPhone);
                    final EditText editTextAdrress = dialogView.findViewById(R.id.editTextAdrress);
                    final TextView pay_total  = dialogView.findViewById(R.id.pay_total);
                    final RadioGroup radioGroupPayment = dialogView.findViewById(R.id.radioGroupPayment);

                    int user_id = MainActivity.user_id;

                    CartStatistic cart = cartList.get(i);
                    int product_id = cart.getProduct_id();
                    int id = cart.getIdCart();
                    int quatity = Integer.parseInt(holder.Cart_Quality.getText().toString());
                    int price = Integer.parseInt(holder.Cart_Price.getText().toString());


                    CustomerHandler customerHandler = new CustomerHandler();
                    editTextEmail.setText(customerHandler.getCustomerInfo(user_id).getEmail());
                    editTextUsername.setText(customerHandler.getCustomerInfo(user_id).getFullname());
                    editTextAdrress.setText(customerHandler.getCustomerInfo(user_id).getAddress());
                    editTextPhone.setText(customerHandler.getCustomerInfo(user_id).getPhone());
                    pay_total.setText("SỐ TIỀN: " + price +" VND");


                    dialogBuilder.setPositiveButton("XÁC NHẬN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            if (TextUtils.isEmpty(editTextUsername.getText().toString().trim()) || TextUtils.isEmpty(editTextUsername.getText().toString().trim()) || TextUtils.isEmpty(editTextPhone.getText().toString().trim()) || TextUtils.isEmpty(editTextAdrress.getText().toString().trim())) {
                                Toast.makeText(context, "Không được để trống các trường", Toast.LENGTH_SHORT).show();
                            } else {
                                int selectedId = radioGroupPayment.getCheckedRadioButtonId();
                                if (selectedId == R.id.radioButtonDirectPayment) {
                                    // Xử lý thanh toán trực tiếp
                                    String description = "Thanh toán bằng tiền mặt";
                                    pay = new Pay(user_id, product_id, quatity, price, description, editTextEmail.getText().toString().trim(), editTextUsername.getText().toString().trim(), editTextPhone.getText().toString().trim(), editTextAdrress.getText().toString().trim());
                                    // Clear the cart (assuming you have a method to clear it in your CartHandler)
                                    BillHandler billHandler = new BillHandler();
                                    billHandler.CreateBill(pay);
                                    cartHandler.deleteCart(id);
                                    cartList.remove(i);
                                    // Show a toast indicating successful payment
                                    Toast.makeText(v.getContext(), "Đặt hàng thành công!!", Toast.LENGTH_SHORT).show();

                                    // Navigate to another fragment (replace with your navigation logic)
                                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.fragment_container, new Order_Fragment()); // Replace with the fragment you want to navigate to
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                } else if (selectedId == R.id.radioButtonZaloPay) {
                                    // Xử lý thanh toán qua ZaloPay
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                    StrictMode.setThreadPolicy(policy);
                                    ZaloPaySDK.init(2553, Environment.SANDBOX);

                                    requestZalo();
                                    if (isZaloPayCompleted = true) {
                                        // Nếu thanh toán ZaloPay đã hoàn thành, tiếp tục xử lý thanh toán
                                        continuePaymentProcessing();
                                    }



                                } else {
                                    // Không chọn hình thức thanh toán
                                    Toast.makeText(v.getContext(), "Vui lòng chọn hình thức thanh toán", Toast.LENGTH_SHORT).show();

                                }

                            }
                        }

                        private void continuePaymentProcessing() {
                            String description = "Thanh toán bằng Zalo Pay";
                            pay = new Pay(user_id, product_id,quatity,price,description,editTextEmail.getText().toString().trim(),editTextUsername.getText().toString().trim(),editTextPhone.getText().toString().trim(),editTextAdrress.getText().toString().trim());
                            // Clear the cart (assuming you have a method to clear it in your CartHandler)
                            BillHandler billHandler = new BillHandler();
                            billHandler.CreateBill(pay);
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

                        private void requestZalo() {
                            CreateOrder orderApi = new CreateOrder();

                            try {
                                JSONObject data = orderApi.createOrder(String.valueOf(price));
                                Log.d("Amount", String.valueOf(price));
                                String code = data.getString("return_code");

                                if (code.equals("1")) {
                                  String token = data.getString("zp_trans_token");
                                  ZaloPaySDK.getInstance().payOrder((Activity) context, token, "demozpdk://app", new PayOrderListener() {
                                      @Override
                                      public void onPaymentSucceeded(String s, String s1, String s2) {
                                          isZaloPayCompleted = true;
                                      }

                                      @Override
                                      public void onPaymentCanceled(String s, String s1) {
                                          isZaloPayCompleted = false;

                                      }

                                      @Override
                                      public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                          isZaloPayCompleted = false;
                                      }
                                  });}

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("Hủy", null).show();

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