package com.example.datn_tranvantruong.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Activity extends AppCompatActivity {
EditText login_name,login_password;
Button bnt_login;
TextView loginRedirectText;
DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://databasetours-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_name = findViewById(R.id.login_name);
        login_password = findViewById(R.id.login_password);

        loginRedirectText = findViewById(R.id.loginRedirectText);
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
                final String txtName = login_name.getText().toString();
                final String txtPassword = login_password.getText().toString();

                if (txtName.isEmpty()||txtPassword.isEmpty()){
                    Toast.makeText(Login_Activity.this,"Vui lòng nhập đầy đủ các trường",Toast.LENGTH_SHORT).show();

                }else {
                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(txtName)){
                                String getPassword = snapshot.child(txtName).child("password").getValue(String.class);
                                if (getPassword.equals(txtPassword)){
                                    Toast.makeText(Login_Activity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login_Activity.this, MainActivity.class));
                                }else {
                                    Toast.makeText(Login_Activity.this,"Sai mật khẩu",Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(Login_Activity.this,"Sai mật khẩu",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

    }

}