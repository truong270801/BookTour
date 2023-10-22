package com.example.datn_tranvantruong.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datn_tranvantruong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup_Activity extends AppCompatActivity {
    Button bnt_signup;
    EditText signup_email, signup_password,signup_name, signup_conpassword;
    TextView signupRedirectText;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressDialog = new ProgressDialog(this);

        //signup_name = findViewById(R.id.signup_name);
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
                String email = signup_email.getText().toString().trim();
                String password = signup_password.getText().toString().trim();
                String conpassword = signup_conpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Signup_Activity.this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();

                }
                else if (!password.equals(conpassword)) {Toast.makeText(Signup_Activity.this,"Vui lòng nhập lại mật khẩu",Toast.LENGTH_SHORT).show();}
                else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Signup_Activity.this, new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
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
        });

    }
}