package com.example.datn_tranvantruong.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Database.DBManager;
import com.example.datn_tranvantruong.R;


public class RePassword_Activity extends AppCompatActivity {
EditText newPasswordEditText,re_newPasswordEditText,currentPasswordEditText;
Button bnt_UpdatePassword;
ImageView img_avatar;
TextView user_name,user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_password);
        currentPasswordEditText = findViewById(R.id.currentPasswordEditText);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        re_newPasswordEditText = findViewById(R.id.re_newPasswordEditText);

        img_avatar = findViewById(R.id.img_avatar);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        // Đặt nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Xử lý sự kiện khi nút back được nhấn
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showUserInformation();

        bnt_UpdatePassword = findViewById(R.id.bnt_UpdatePassword);
        bnt_UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText().toString();
                String re_newPassword = re_newPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                if (currentPassword.isEmpty() || re_newPassword.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(RePassword_Activity.this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                }else if (newPassword.length() < 6){
                    Toast.makeText(RePassword_Activity.this,"Mật khẩu chưa đử 6 kí tự",Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(re_newPassword)) {
                    Toast.makeText(RePassword_Activity.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra mật khẩu cũ

                    CustomerHandler customerHandler = new CustomerHandler(RePassword_Activity.this); // Khởi tạo đối tượng DBManager
                    String currentPasswordFromDB = customerHandler.getPasswordFromDB(String.valueOf(Intro_Activity.user_id));

                    if (currentPassword.equals(currentPasswordFromDB)) {
                        // Cập nhật mật khẩu mới vào cơ sở dữ liệu
                        customerHandler.updateCustomerPassword(String.valueOf(Intro_Activity.user_id), newPassword);
                        Toast.makeText(RePassword_Activity.this, "Mật khẩu đã được cập nhật", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RePassword_Activity.this, Login_Activity.class));
                        finish();
                    } else {
                        Toast.makeText(RePassword_Activity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
    private void  showUserInformation(){
        DBManager dbManager = new DBManager(this);
        SQLiteDatabase db = dbManager.getWritableDatabase();
// Xây dựng câu lệnh truy vấn SQL
        String query = "SELECT email,fullname,image_avatar FROM customers";

// Thực hiện truy vấn SQL và lấy dữ liệu
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String name = cursor.getString(cursor.getColumnIndex("fullname"));
                byte[] avatar = cursor.getBlob(cursor.getColumnIndex("image_avatar"));


                // Gán giá trị "email" vào TextView
                user_email.setText(email);
                user_name.setText(name);
                if (avatar != null) {
                    Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
                    img_avatar.setImageBitmap(avatarBitmap);
                }
            }
            cursor.close();
        }
    }
}
