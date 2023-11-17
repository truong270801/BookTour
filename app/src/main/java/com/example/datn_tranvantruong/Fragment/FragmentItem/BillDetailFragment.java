package com.example.datn_tranvantruong.Fragment.FragmentItem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.text.NumberFormat;

public class BillDetailFragment extends Fragment {
    TextView bill_id;
    TextView users_name;
    TextView users_address;
    TextView users_phone;
    TextView users_email;
    TextView product_id;
    TextView product_name;
    TextView quatity;
    TextView price;
    TextView bill_date;
    TextView bill_price;
    TextView customer_name;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_detail, container, false);

        bill_id = view.findViewById(R.id.bill_id);
        users_name = view.findViewById(R.id.users_name);
        users_address = view.findViewById(R.id.users_address);
        users_phone = view.findViewById(R.id.users_phone);
        users_email = view.findViewById(R.id.users_email);
        product_id = view.findViewById(R.id.product_id);
        product_name = view.findViewById(R.id.product_name);
        quatity = view.findViewById(R.id.product_quatity);
        price = view.findViewById(R.id.product_price);
        bill_date = view.findViewById(R.id.bill_date);
        bill_price = view.findViewById(R.id.bill_price);
        customer_name = view.findViewById(R.id.customer_name);
        ProductHandler productHandler = new ProductHandler(getContext());
        CustomerHandler customerHandler = new CustomerHandler(getContext());
        BillHandler billHandler = new BillHandler(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String billdate = bundle.getString("billdate");
            String billid = bundle.getString("billid");
            String billPrice = bundle.getString("billprice");
            int productId = bundle.getInt("productId");

            // Lấy thông tin khách hàng


            int user_id = Integer.parseInt(billHandler.getUserIdById(Integer.parseInt(billid)));
                    users_email.setText(customerHandler.getCustomerInfo(String.valueOf(user_id)).getEmail());
            users_name.setText(customerHandler.getCustomerInfo(String.valueOf(user_id)).getFullname());
            users_address.setText(customerHandler.getCustomerInfo(String.valueOf(user_id)).getAddress());
            users_phone.setText(customerHandler.getCustomerInfo(String.valueOf(user_id)).getPhone());
            customer_name.setText(users_name.getText().toString());

            // Lấy thông tin hóa đơn
            bill_id.setText("Mã số: #KTTRAVEL" + billid);
            price.setText(billPrice + "VND");
            bill_date.setText(billdate);
            product_id.setText(String.valueOf(productId));


            product_name.setText(productHandler.getProductNameById(productId));

            quatity.setText(billHandler.getBillQuatityById(Integer.parseInt(billid)));

            // Chuyển đổi giá từ dạng số sang dạng chữ và hiển thị trong TextView bill_price
            String priceInWords = convertNumberToWords(Integer.parseInt(billPrice));
            bill_price.setText(priceInWords + " việt nam đồng.");
        }
        return view;
    }

    private String convertNumberToWords(int number) {
        // Sử dụng đoạn mã chuyển đổi số thành chữ ở đây
        String[] units = {"", " nghìn ", " triệu ", " tỷ "};
        String result = "";

        int unitIndex = 0;

        do {
            int num = number % 1000;
            if (num > 0) {
                String str = convertLessThanOneThousand(num);
                result = str + units[unitIndex] + " " + result;
            }
            unitIndex++;
            number /= 1000;
        } while (number > 0);

        return result.trim();
    }

    private String convertLessThanOneThousand(int number) {
        String[] tensNames = {"", "mười", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
        String[] numNames = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười", "mười một", "mười hai", "mười ba", "mười bốn", "mười lăm", "mười sáu", "mười bảy", "mười tám", "mười chín"};

        String result;

        if (number % 100 < 20) {
            result = numNames[number % 100];
            number /= 100;
        } else {
            result = numNames[number % 10];
            number /= 10;
            result = tensNames[number % 10] + " " + result;
            number /= 10;
        }

        if (number > 0) {
            result = numNames[number] + " trăm " + result;
        }

        return result;
    }
}
