package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Activity.Login_Activity;
import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class EditProduct_Fragment extends Fragment {
    private Product product;
    private EditText txtProductName, txtLocation, txtdescription, txtPrice;
    private TextView txtStartDate,txtEndDate;
    private ImageView imgHinh;
    private ProductHandler productHandler;
    private CategoryHandler categoryHandler;
    private Spinner spinnerCategory;
    private int category;
    private ImageButton ibtnBack;
    private Button btnEdit, btnDelete;
    private int id;
    private static final int REQUEST_CODE_FOLDER = 352;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_product, container, false);

        txtProductName = view.findViewById(R.id.txtProductName);
        txtLocation = view.findViewById(R.id.txtLocation);
        txtStartDate = view.findViewById(R.id.txtStartDate);
        txtEndDate = view.findViewById(R.id.txtEndDate);
        txtdescription = view.findViewById(R.id.txtDescription);
        txtPrice = view.findViewById(R.id.txtPrice);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        btnEdit = view.findViewById(R.id.btnEdit);
        btnDelete = view.findViewById(R.id.btnDelete);
        ibtnBack = view.findViewById(R.id.ibtnBack);
        imgHinh = view.findViewById(R.id.imgHinh);

        productHandler = new ProductHandler(getContext());
        categoryHandler = new CategoryHandler(getContext());

        loadSpinner();
        getData();
        txtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txtStartDate);
            }
        });

        txtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txtEndDate);
            }


        });

        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                startActivity(new Intent(getActivity(), Login_Activity.class));
                if (getActivity() != null) {
                    getActivity().finish();
                }

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                try {
                    // Lấy giá trị từ các thành phần giao diện và kiểm tra tính hợp lệ.
                    String selectedCategory = spinnerCategory.getSelectedItem().toString().trim();
                    category = categoryHandler.getCategoryIdByName(selectedCategory);
                    String productName = txtProductName.getText().toString();
                    String productStartDate = txtStartDate.getText().toString();
                    String productEndDate = txtEndDate.getText().toString();

                    String productDescription = txtdescription.getText().toString();
                    String productLocation = txtLocation.getText().toString();
                    int productPrice = Integer.parseInt(txtPrice.getText().toString());

                    // Thực hiện sự kiện sửa hàng.
                    productHandler.editProduct(id, category, productName,productStartDate, productEndDate, productDescription, productLocation, productPrice, convertToArrayByte(imgHinh));

                    // Thông báo cho người dùng về thành công và cung cấp thông tin cụ thể.
                    Toast.makeText(getContext(), "Sửa hàng " + productName + " thành công!", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp không thể chuyển đổi giá trị giá thành một số nguyên.
                    Toast.makeText(getContext(), "Lỗi: Giá không hợp lệ.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Handle the delete button click here
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                dialog.setMessage("Bạn thật sự muốn xóa sản phẩm này?");
                dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        productHandler.deleteProduct(id);
                        Toast.makeText(getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton("Hủy", null).show();
            }
        });

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Handle image selection here
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

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK & data != null) {
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

    public void loadSpinner() {
        CategoryHandler categoryHandler = new CategoryHandler(getContext());
        List<String> categories = categoryHandler.getAllNameCategoryForCreateProduct();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spinnerCategory.setAdapter(dataAdapter);

        if (product != null) {
            int position = dataAdapter.getPosition(String.valueOf(product.getCategoryName()));
            spinnerCategory.setSelection(position);
        }
    }

    public byte[] convertToArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public void getData() {
        if (getArguments() != null) {
            product = (Product) getArguments().getSerializable("Edit");
            id = product.getId();
            byte[] hinhanh = product.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
            imgHinh.setImageBitmap(bitmap);
            txtProductName.setText(product.getName());
            txtStartDate.setText(product.getStartdate());
            txtEndDate.setText(product.getEnddate());
            txtdescription.setText(product.getDescription());
            txtLocation.setText(product.getLocation());
            txtPrice.setText(String.valueOf(product.getPrice()));
        }
    }
}
