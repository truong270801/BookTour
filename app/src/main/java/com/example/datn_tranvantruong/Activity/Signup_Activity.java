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

//import com.example.datn_tranvantruong.DBHandler.SignupHandler;
import com.example.datn_tranvantruong.DBHandler.SignupHandler;
import com.example.datn_tranvantruong.R;

public class Signup_Activity extends AppCompatActivity {
    Button bnt_signup;
    EditText signup_email, signup_password,signup_name,signup_phone, signup_conpassword;
    TextView signupRedirectText;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressDialog = new ProgressDialog(this);
        signup_phone = findViewById(R.id.signup_phone);
        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        signup_password = findViewById(R.id.signup_password);
        signup_conpassword = findViewById(R.id.signup_conpassword);

        signupRedirectText = findViewById(R.id.loginRedirectText);
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup_Activity.this, Login_Activity.class));

            }
        });

        bnt_signup = findViewById(R.id.bnt_signup);
        bnt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                creatUser();
            }
        });

    }

    private void creatUser() {
        String fullname = signup_name.getText().toString().trim();
        String email = signup_email.getText().toString().trim();
        String password = signup_password.getText().toString().trim();
        String conpassword = signup_conpassword.getText().toString().trim();
        String phone = signup_phone.getText().toString().trim();
        SignupHandler signupHandler = new SignupHandler();


        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(fullname)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(this,"Không được để trống các trường",Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6){
            Toast.makeText(this,"Mật khẩu chưa đử 6 kí tự",Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(conpassword)) {
            Toast.makeText(Signup_Activity.this,"Vui lòng nhập lại mật khẩu",Toast.LENGTH_SHORT).show();

        }
        else {
            if (signupHandler.register(email, fullname, password, phone)) {
                Intent i = new Intent(this, Login_Activity.class);
                startActivity(i);
                Toast.makeText(this,
                        "Đăng ký tài khoản thành công.",
                        Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Đăng ký tài khoản không thành công.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}