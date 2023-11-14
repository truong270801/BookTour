package com.example.datn_tranvantruong.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_tranvantruong.Admin.MenuActivity;
import com.example.datn_tranvantruong.DBHandler.LoginHandler;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.R;


public class Login_Activity extends AppCompatActivity {
EditText login_email,login_password;
Button bnt_login;
ProgressDialog progressDialog;

TextView loginRedirectText,Forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        LoginHandler loginHandler = new LoginHandler(this);

        progressDialog = new ProgressDialog(this);
        Forgot_password = findViewById(R.id.Forgot_password);
        Forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, ForgotPassword_Activity.class));

            }
        });

        loginRedirectText = findViewById(R.id.signinRedirectText);
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Signup_Activity.class));
            }
        });

        bnt_login = findViewById(R.id.bnt_login);
bnt_login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(Login_Activity.this,"Không được để trống các trường",Toast.LENGTH_SHORT).show();
        }else {
        if (loginHandler.checkLogin(email, password) == "admin") {
            Intent i = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),
                    "Đăng nhập thành công", Toast.LENGTH_LONG).show();
            finish();
        } else if (loginHandler.checkLogin(email, password) == "customer"){
            Intro_Activity.user_id = loginHandler.getUserId(email);

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(),
                    "Đăng nhập thành công", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Tài khoản hoặc mật khẩu không đúng, hãy kiểm tra lại.",
                    Toast.LENGTH_LONG).show();
        }
    }
    }
});

    }

}