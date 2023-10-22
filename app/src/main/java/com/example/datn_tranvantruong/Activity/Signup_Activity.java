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

import com.example.datn_tranvantruong.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup_Activity extends AppCompatActivity {
    Button bnt_signup;
    EditText signup_email, sigup_password, signup_phone,signup_name, signup_conpassword;
    TextView signupRedirectText;
    DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://databasetours-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_name = findViewById(R.id.signup_name);
        signup_email = findViewById(R.id.signup_email);
        sigup_password = findViewById(R.id.signup_password);
        signup_phone = findViewById(R.id.signup_phone);
        signup_conpassword = findViewById(R.id.signup_conpassword);

        signupRedirectText = findViewById(R.id.signupRedirectText);
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
                String conpassword = signup_conpassword.getText().toString();
                String email = signup_email.getText().toString();
                String phone = signup_phone.getText().toString();
                String password = sigup_password.getText().toString();
                String name = signup_name.getText().toString();

                if (email.isEmpty()||phone.isEmpty()||password.isEmpty()||conpassword.isEmpty()){
                    Toast.makeText(Signup_Activity.this,"Vui lòng nhập đầy đủ các trường",Toast.LENGTH_SHORT).show();
                } else if (!password.equals(conpassword)) {Toast.makeText(Signup_Activity.this,"Vui lòng nhập lại mật khẩu",Toast.LENGTH_SHORT).show();

                } else {
                    reference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(name)){
                                Toast.makeText(Signup_Activity.this,"Điện thoại đã được đăng ký",Toast.LENGTH_SHORT).show();

                            }else {
                                reference.child("users").child(name).child("email").setValue(email);
                                reference.child("users").child(name).child("phone").setValue(phone);
                                reference.child("users").child(name).child("password").setValue(password);

                                Toast.makeText(Signup_Activity.this,"Đăng kí thành công",Toast.LENGTH_SHORT).show();
                                finish();
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