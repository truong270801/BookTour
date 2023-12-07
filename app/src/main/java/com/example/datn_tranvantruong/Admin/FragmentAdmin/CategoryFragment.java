package com.example.datn_tranvantruong.Admin.FragmentAdmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.DBHandler.CategoryHandler;
import com.example.datn_tranvantruong.Model.Category;
import com.example.datn_tranvantruong.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {
    EditText txtNameCategory,txtIDCategory;
    ListView lv;
    Button btnCreate, btnEdit, btnDelete;
    ArrayAdapter<String> arrayAdapter;
    List<String> list = new ArrayList<String>();
    Context content;
    CategoryHandler handleCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category, container, false);

        txtNameCategory = (EditText) view.findViewById(R.id.txtNameCategory);
        txtIDCategory = (EditText) view.findViewById(R.id.txtId);
        lv = (ListView) view.findViewById(R.id.lvCategory);
        btnCreate = view.findViewById(R.id.add);
        btnEdit = view.findViewById(R.id.edit);
        btnDelete = view.findViewById(R.id.delete);
        //Khởi tạo biến
        content = getContext();
        //Hiển thị khi chạy chương trình
        display();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy ra giá trị của item được chọn
                String selectedItem = (String) parent.getItemAtPosition(position);
                String[] arr = selectedItem.split(" - ");
                txtIDCategory.setText(arr[0]);
                txtNameCategory.setText(arr[1]);
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = txtNameCategory.getText().toString();
                if(!inputString.isEmpty()){
                    Category category = new Category();
                    category.setNameCategory(txtNameCategory.getText().toString());
                    int kq = handleCategory.insertCategory(category);
                    if(kq == -1){
                        Toast.makeText(content,"Insert Thất Bại",Toast.LENGTH_LONG).show();
                    }else {
                        String text = null;
                        txtNameCategory.setText(text);
                        txtIDCategory.setText(text);
                        display();
                        Toast.makeText(content,"Insert Thành Công",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(content,"Điền đầy đủ thông tin trước khi tạo",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog =	new AlertDialog.Builder(content);
                dialog.setTitle("XÓA DANH MỤC NÀY?");
                dialog.setMessage("Bạn thật sự muốn xóa danh mục này?");
                dialog.setPositiveButton("XÓA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        String inputString = txtNameCategory.getText().toString();
                        String idString = txtIDCategory.getText().toString();
                        if(!inputString.isEmpty() && !idString.isEmpty()){
                            String _id = txtIDCategory.getText().toString();
                            int kq = handleCategory.deleteCategory(_id);
                            if(kq == -1){
                                Toast.makeText(content,"Delete Thất Bại",Toast.LENGTH_LONG).show();
                            }else {
                                String text = null;
                                txtNameCategory.setText(text);
                                txtIDCategory.setText(text);
                                display();
                                Toast.makeText(content,"Delete Thành Công",Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(content,"Chọn danh mục muốn xóa",Toast.LENGTH_LONG).show();
                        }
                    }
                }).setNegativeButton("Hủy", null).show();

            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = txtNameCategory.getText().toString();
                String idString = txtIDCategory.getText().toString();
                if(!inputString.isEmpty() && !idString.isEmpty()){
                    Category category = new Category();
                    category.setNameCategory(txtNameCategory.getText().toString());
                    category.setIdCategory(Integer.parseInt(txtIDCategory.getText().toString()));

                    int kq = handleCategory.updateCategory(category);
                    if(kq == -1){
                        Toast.makeText(content,"Update Thất Bại",Toast.LENGTH_LONG).show();
                    }else {
                        String text = null;
                        txtIDCategory.setText(text);
                        txtNameCategory.setText(text);
                        display();
                        Toast.makeText(content,"Update Thành Công",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(content,"Chọn danh mục muốn sửa",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }
    protected void display(){
        list.clear();
        handleCategory = new CategoryHandler();
        list = handleCategory.getAllCategory();
        arrayAdapter = new ArrayAdapter<>(content, android.R.layout.simple_list_item_1,list);
        lv.setAdapter(arrayAdapter);
    }
}