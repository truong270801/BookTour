package com.example.datn_tranvantruong.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datn_tranvantruong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword_Activity extends AppCompatActivity {
    private EditText emailEditText;
    private Button resetPasswordButton;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        resetPasswordButton = findViewById(R.id.resetPasswordButton);

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
                startActivity(new Intent(ForgotPassword_Activity.this,Login_Activity.class));
                finish();
            }

        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Email đặt lại mật khẩu đã được gửi thành công
                            Toast.makeText(getApplicationContext(), "Hãy kiểm tra email của bạn để đặt lại mật khẩu.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Lỗi khi gửi email đặt lại mật khẩu
                            Toast.makeText(getApplicationContext(), "Lỗi khi gửi email đặt lại mật khẩu.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

