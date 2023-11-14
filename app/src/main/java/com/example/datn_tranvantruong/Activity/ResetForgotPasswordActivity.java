package com.example.datn_tranvantruong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.R;

public class ResetForgotPasswordActivity extends AppCompatActivity {
EditText passwordEditText,RepasswordEditText;
Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_forgot_password);
        passwordEditText = findViewById(R.id.passwordEditText);
        RepasswordEditText = findViewById(R.id.RepasswordEditText);
        done = findViewById(R.id.done);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String re_newPassword = RepasswordEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();

                if (re_newPassword.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(ResetForgotPasswordActivity.this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                } else if (newPassword.length() < 6){
                    Toast.makeText(ResetForgotPasswordActivity.this,"Mật khẩu chưa đử 6 kí tự",Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(re_newPassword)) {
                    Toast.makeText(ResetForgotPasswordActivity.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    // Kiểm tra mật khẩu cũ

                    CustomerHandler customerHandler = new CustomerHandler(ResetForgotPasswordActivity.this); // Khởi tạo đối tượng DBManager


                        // Cập nhật mật khẩu mới vào cơ sở dữ liệu
                        customerHandler.updateCustomerForgotPassword(email, newPassword);
                        Toast.makeText(ResetForgotPasswordActivity.this, "Mật khẩu đã được cập nhật", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetForgotPasswordActivity.this, Login_Activity.class));
                        finish();

                }
            }
        });
    }
}