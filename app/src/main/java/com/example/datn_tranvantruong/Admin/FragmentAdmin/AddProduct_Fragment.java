package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class AddProduct_Fragment extends Fragment {
    ProductHandler productHandler;
    CategoryHandler categoryHandler;
    Spinner spinnerCategory;
    ImageView imgHinh;
    Integer category;
    Button btnSave;
    EditText txtPrice, txtLocation, txtdescription;
    TextView tvStartDate,tvEndDate;
    EditText txtProductName;
    int REQUEST_CODE_FOLDER = 352;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_product, container, false);
        imgHinh = view.findViewById(R.id.imgHinh);
        btnSave = view.findViewById(R.id.btnSave);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);

        txtPrice = view.findViewById(R.id.txtPrice);
        txtProductName = view.findViewById(R.id.txtProductName);
        tvStartDate = view.findViewById(R.id.tvStartDate);
        tvEndDate = view.findViewById(R.id.tvEndDate);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtdescription = view.findViewById(R.id.txtDescription);
        productHandler = new ProductHandler(getContext());
        categoryHandler = new CategoryHandler(getContext());

        loadSpinner();

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvStartDate);
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tvEndDate);
            }


        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    // Lấy dữ liệu từ các thành phần giao diện và kiểm tra tính hợp lệ.
                    category = categoryHandler.getCategoryIdByName(spinnerCategory.getSelectedItem().toString().trim());
                    String name = txtProductName.getText().toString().trim();
                    String startdate = tvStartDate.getText().toString().trim();
                    String enddate = tvEndDate.getText().toString().trim();
                    String description = txtdescription.getText().toString().trim();
                    String location = txtLocation.getText().toString().trim();
                    int price = Integer.parseInt(txtPrice.getText().toString());
                    byte[] image = convertToArrayByte(imgHinh);

                    // Thực hiện sự kiện thêm sản phẩm.
                    productHandler.createProduct(category, name, startdate, enddate, description, location, price, image);

                    // Thông báo cho người dùng về thành công và cung cấp thông tin cụ thể.
                    Toast.makeText(getContext(), "Đã thêm sản phẩm: " + name, Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp không thể chuyển đổi giá trị giá thành số nguyên.
                    Toast.makeText(getContext(), "Lỗi: Giá không hợp lệ.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        imgHinh.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, REQUEST_CODE_FOLDER);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode==REQUEST_CODE_FOLDER&&resultCode==RESULT_OK & data!=null){
            Uri uri = data.getData();
            try {
                InputStream ipstream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ipstream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void loadSpinner(){

        CategoryHandler categoryHandler = new CategoryHandler(getContext());
        List<String> category  = categoryHandler.getAllNameCategoryForCreateProduct();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinnerCategory.setAdapter(dataAdapter);
    }
    public byte[] convertToArrayByte(ImageView img){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

}
