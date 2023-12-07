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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    private TextView txtStartDate, txtEndDate;
    private ImageView imgHinh;
    private ProductHandler productHandler;
    private CategoryHandler categoryHandler;

    private Spinner spinnerCategory;
    private Integer category;
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
        imgHinh = view.findViewById(R.id.imgHinh);

        productHandler = new ProductHandler();
        categoryHandler = new CategoryHandler();

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loadSpinner();
        setListeners();
        getData();

        return view;
    }

    private void setListeners() {
        txtStartDate.setOnClickListener(v -> showDatePickerDialog(txtStartDate));

        txtEndDate.setOnClickListener(v -> showDatePickerDialog(txtEndDate));


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Get values from UI components and validate them.
                    category = categoryHandler.getCategoryIdByName(spinnerCategory.getSelectedItem().toString());
                    if (category != null) {
                    String productName = txtProductName.getText().toString();
                    String productStartDate = txtStartDate.getText().toString();
                    String productEndDate = txtEndDate.getText().toString();
                    String productDescription = txtdescription.getText().toString();
                    String productLocation = txtLocation.getText().toString();
                    int productPrice = Integer.parseInt(txtPrice.getText().toString());

                    // Perform edit product event.
                    productHandler.editProduct(id, category, productName, productStartDate, productEndDate, productDescription, productLocation, productPrice, convertToArrayByte(imgHinh));

                    // Notify the user about the success and provide specific information.
                    Toast.makeText(getContext(), "Sửa hàng " + productName + " thành công!", Toast.LENGTH_SHORT).show();
                        ListProductFragment listProductFragment = new ListProductFragment();
                        // Thực hiện thay thế FragmentA bằng FragmentB
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container_admin, listProductFragment);
                        transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                        transaction.commit();
                    } else {
                        Toast.makeText(requireContext(), "Loại Tour không tồn tại" , Toast.LENGTH_SHORT).show();
                        // Handle the case where category is null, show a message or take appropriate action.
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the price value cannot be converted to an integer.
                    Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }

            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Handle the delete button click here
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("XÓA SẢN PHẨM NÀY?");
                dialog.setMessage("Bạn thật sự muốn xóa sản phẩm này?");
                dialog.setPositiveButton("XÓA", (dialogInterface, which) -> {
                    productHandler.deleteProduct(id);
                    ListProductFragment listProductFragment = new ListProductFragment();
                    // Thực hiện thay thế FragmentA bằng FragmentB
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container_admin, listProductFragment);
                    transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                    transaction.commit();
                    Toast.makeText(getContext(), "Đã xóa!", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Hủy", null).show();


            }

        });

        imgHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Handle image selection here
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, REQUEST_CODE_FOLDER);
            }
        });
    }

    private void showDatePickerDialog(final TextView textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // Get the current year
        int mMonth = c.get(Calendar.MONTH); // Get the current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // Get the current day
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) ->
                        textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year),
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle the result of image selection
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK & data != null) {
            Uri uri = data.getData();
            try {
                InputStream ipstream = getContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ipstream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadSpinner() {
        List<String> categories = categoryHandler.getAllNameCategoryForCreateProduct();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerCategory.setAdapter(dataAdapter);

        if (product != null) {
            int position = dataAdapter.getPosition(String.valueOf(product.getCategoryName()));
            spinnerCategory.setSelection(position);
        }
    }

    private byte[] convertToArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void getData() {
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
    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_admin, new ListProductFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
