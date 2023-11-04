package com.example.datn_tranvantruong.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_tranvantruong.Model.User;
import com.example.datn_tranvantruong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Activity extends AppCompatActivity {
    Button bnt_signup;
    EditText signup_email, signup_password,signup_name,signup_phone, signup_conpassword;
    TextView signupRedirectText;
    ProgressDialog progressDialog;
    FirebaseDatabase database;



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

        database = FirebaseDatabase.getInstance();

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
        String name = signup_name.getText().toString().trim();
        String email = signup_email.getText().toString();
        String password = signup_password.getText().toString();
        String conpassword = signup_conpassword.getText().toString();
        String phone = signup_phone.getText().toString();

        if (TextUtils.isEmpty(phone)||TextUtils.isEmpty(name)||TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
            Toast.makeText(this,"Không được để trống các trường",Toast.LENGTH_SHORT).show();
        }
        else if (password.length() < 6){
            Toast.makeText(this,"Mật khẩu chưa đử 6 kí tự",Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(conpassword)) {
            Toast.makeText(Signup_Activity.this,"Vui lòng nhập lại mật khẩu",Toast.LENGTH_SHORT).show();

        }
        else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Signup_Activity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        String idUser = task.getResult().getUser().getUid();
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        User usersModel = new User(idUser,name,email,password,phone);

                        database.getReference().child("Users").child(idUser).setValue(usersModel);
                        if (user == null){
                            return;
                        }
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build();
                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                        }
                                    }
                                });
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(Signup_Activity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Signup_Activity.this, Login_Activity.class));
                        finishAffinity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(Signup_Activity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }



    }
}