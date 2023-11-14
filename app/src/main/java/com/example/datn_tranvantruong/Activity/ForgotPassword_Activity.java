package com.example.datn_tranvantruong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datn_tranvantruong.Admin.MenuActivity;
import com.example.datn_tranvantruong.DBHandler.LoginHandler;
import com.example.datn_tranvantruong.R;


public class ForgotPassword_Activity extends AppCompatActivity {
    private EditText emailEditText,phoneEditText;
    private Button resetPasswordButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        phoneEditText = findViewById(R.id.phoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }

        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        LoginHandler loginHandler = new LoginHandler(this);
        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(phone)){
            Toast.makeText(ForgotPassword_Activity.this,"Không được để trống các trường",Toast.LENGTH_SHORT).show();
        }else {
            if (loginHandler.checkForgotPassword(email, phone) == "customers") {
                Intent i = new Intent(ForgotPassword_Activity.this, ResetForgotPasswordActivity.class);
                Toast.makeText(ForgotPassword_Activity.this,
                        "Thông tin chính xác!", Toast.LENGTH_LONG).show();
                // Truyền dữ liệu từ EditText sang Activity khác
                i.putExtra("email", email);
                startActivity(i);
                finish();

            } else {
                Toast.makeText(ForgotPassword_Activity.this,
                        "Tài khoản hoặc số điện thoại không đúng, hãy kiểm tra lại.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}

