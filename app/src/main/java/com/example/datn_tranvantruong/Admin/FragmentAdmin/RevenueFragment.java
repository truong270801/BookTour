package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_tranvantruong.Adapter.RevenueAdapter;
import com.example.datn_tranvantruong.DBHandler.BillHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.BillRevenue;
import com.example.datn_tranvantruong.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RevenueFragment extends Fragment {

    Button buttonStartDate, buttonEndDate;
    Button buttonRevenue;
    TextView textViewStartDate, textViewEndDate;

    ListView lvRevenue;

    TextView textViewTotalBill;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);

        buttonStartDate = view.findViewById(R.id.buttonDateStart);
        buttonEndDate = view.findViewById(R.id.buttonEndDate);
        buttonRevenue = view.findViewById(R.id.buttonRevenue);
        textViewStartDate = view.findViewById(R.id.textViewStartDate);
        textViewEndDate = view.findViewById(R.id.textViewEndDate);
        lvRevenue = view.findViewById(R.id.lvRevenue);

        textViewTotalBill = view.findViewById(R.id.textViewTotalBill);

        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textViewStartDate);
            }
        });

        buttonEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(textViewEndDate);
            }
        });

        buttonRevenue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewEndDate.getText().toString().isEmpty() || textViewStartDate.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng đủ chọn ngày bắt đầu và ngày kết thúc", Toast.LENGTH_SHORT).show();
                } else {
                    String startDate = formatDateString(textViewStartDate.getText().toString() + " 00:00:00");
                    String endDate = formatDateString(textViewEndDate.getText().toString() + " 23:59:59");

                    BillHandler billHandler = new BillHandler(getContext());

                    int totalBill = billHandler.getTotalBill(startDate, endDate);
                    List<BillRevenue> productSales = billHandler.getAllBillRevenue();

                    if (totalBill == 0) {
                        textViewTotalBill.setText(String.format("Tổng doanh thu: %d VND", totalBill));
                        Toast.makeText(getContext(), "Không có sản phẩm nào được bán", Toast.LENGTH_SHORT).show();
                        productSales.clear();
                    } else {
                        textViewTotalBill.setText(String.format("Tổng doanh thu: %d VND", totalBill));
                    }

                    RevenueAdapter revenueAdapter = new RevenueAdapter(getContext(), R.layout.revenue_item, productSales);
                    lvRevenue.setAdapter(revenueAdapter);
                    revenueAdapter.notifyDataSetChanged(); // Đảm bảo cập nhật dữ liệu cho ListView
                }
            }
        });

        return view;
    }

    private void showDatePickerDialog(final TextView textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // Lấy năm hiện tại
        int mMonth = c.get(Calendar.MONTH); // Lấy tháng hiện tại
        int mDay = c.get(Calendar.DAY_OF_MONTH); // Lấy ngày hiện tại
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    private String formatDateString(String dateString) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateString = null;
        try {
            Date date = inputDateFormat.parse(dateString);
            formattedDateString = outputDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDateString;
    }

}