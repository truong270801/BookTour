package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import static android.app.Activity.RESULT_OK;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

public class AddProduct_Fragment extends Fragment {
    private static final String TAG = AddProduct_Fragment.class.getSimpleName();

    private ProductHandler productHandler;
    private CategoryHandler categoryHandler;
    private Spinner spinnerCategory;
    private ImageView imgHinh;
    private Integer category;
    private Button btnSave;
    private EditText txtPrice, txtLocation, txtdescription;
    private TextView tvStartDate, tvEndDate;
    private EditText txtProductName;
    private int REQUEST_CODE_FOLDER = 352;

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

        tvStartDate.setOnClickListener(v -> showDatePickerDialog(tvStartDate));

        tvEndDate.setOnClickListener(v -> showDatePickerDialog(tvEndDate));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    category = categoryHandler.getCategoryIdByName(spinnerCategory.getSelectedItem().toString());
                    if (category != null) {
                        String name = txtProductName.getText().toString().trim();
                        String startdate = tvStartDate.getText().toString().trim();
                        String enddate = tvEndDate.getText().toString().trim();
                        String description = txtdescription.getText().toString().trim();
                        String location = txtLocation.getText().toString().trim();
                        int price = Integer.parseInt(txtPrice.getText().toString());
                        byte[] image = convertToArrayByte(imgHinh);

                        productHandler.createProduct(category, name, startdate, enddate, description, location, price, image);
                        Toast.makeText(requireContext(), "Đã thêm sản phẩm: " + name, Toast.LENGTH_SHORT).show();
                        ListProductFragment listProductFragment = new ListProductFragment();
                        // Thực hiện thay thế FragmentA bằng FragmentB
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container_admin, listProductFragment);
                        transaction.addToBackStack(null); // Nếu bạn muốn quản lý việc điều hướng ngược lại
                        transaction.commit();

                    } else {
                        Toast.makeText(requireContext(), "Loại Tour không tồn tại." , Toast.LENGTH_SHORT).show();
                        // Handle the case where category is null, show a message or take appropriate action.
                    }


                } catch (NumberFormatException e) {
                    Toast.makeText(requireContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });

                imgHinh.setOnClickListener(v -> {
                    Intent in = new Intent(Intent.ACTION_PICK);
                    in.setType("image/*");
                    startActivityForResult(in, REQUEST_CODE_FOLDER);
                });

        return view;
    }

    private void showDatePickerDialog(final TextView textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, year, monthOfYear, dayOfMonth) ->
                        textView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year),
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream ipstream = requireContext().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ipstream);
                imgHinh.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e(TAG, "Lỗi load ảnh qua URI", e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadSpinner() {
        List<String> categories = categoryHandler.getAllNameCategoryForCreateProduct();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerCategory.setAdapter(dataAdapter);
    }

    private byte[] convertToArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } else {
            Log.e(TAG, "Lỗi");
            return new byte[0];
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
