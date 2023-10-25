package com.example.datn_tranvantruong.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.datn_tranvantruong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        showUserInformation();

        bnt_UpdatePassword = findViewById(R.id.bnt_UpdatePassword);
        bnt_UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    String currentPassword = currentPasswordEditText.getText().toString();
                    String re_newPassword = re_newPasswordEditText.getText().toString();
                    String newPassword = newPasswordEditText.getText().toString();

                    if (currentPassword.isEmpty() || re_newPassword.isEmpty() || newPassword.isEmpty()) {
                        Toast.makeText(RePassword_Activity.this, "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
                    } else if (!newPassword.equals(re_newPassword)) {
                        Toast.makeText(RePassword_Activity.this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                    } else {
                        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
                        // Kiểm tra mật khẩu cũ
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Mật khẩu cũ chính xác, tiến hành thay đổi mật khẩu mới
                                            user.updatePassword(newPassword)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                                                                mAuth.signOut();
                                                                startActivity(new Intent(RePassword_Activity.this, Login_Activity.class));
                                                                finish();
                                                                // Đổi mật khẩu thành công
                                                                Toast.makeText(getApplicationContext(), "Mật khẩu đã được thay đổi", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                // Đổi mật khẩu thất bại
                                                                Toast.makeText(getApplicationContext(), "Lỗi khi thay đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                                            }


                                                            }

                                                    });
                                        } else {
                                            // Mật khẩu cũ không chính xác
                                            Toast.makeText(getApplicationContext(), "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }

            }
        });


    }
    private void  showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if(user_name == null){
            user_name.setVisibility(View.GONE);
        }else {
            user_name.setText(user.getDisplayName());
        }
        user_email.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(img_avatar);
    }
}
