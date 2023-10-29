package com.example.datn_tranvantruong.Activity;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.datn_tranvantruong.Fragment.SettingFragment;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.Users_Model;
import com.example.datn_tranvantruong.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class Profile_Activity extends AppCompatActivity {
    private View view;
    public static final int MY_REQUEST_CODE = 10;
    private ImageView edit_Avatar;
    private EditText edit_name,edit_phone;
    private TextView user_email;
    private Button bnt_Update, bnt_back;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edit_Avatar = findViewById(R.id.edit_Avatar);
        edit_name = findViewById(R.id.edit_name);
        edit_phone = findViewById(R.id.edit_phone);
        bnt_Update = findViewById(R.id.bnt_Update);
        user_email = findViewById(R.id.user_email);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        // Đặt nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Xử lý sự kiện khi nút back được nhấn
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(Profile_Activity.this, MainActivity.class));
            }
        });


        setUserInformation();
        initListener();
    }

    final private ActivityResultLauncher<Intent> mactivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK){
                        Intent intent = result.getData();
                        if (intent == null){
                            return;
                        }
                        Uri uri = intent.getData();
                        bnt_Update = findViewById(R.id.bnt_Update);
                        bnt_Update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String Fullname = edit_name.getText().toString().trim();

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user == null){
                                    return;
                                }
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(Fullname)
                                        .setPhotoUri(uri)
                                        .build();
                                user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Profile_Activity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }

                        });

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            setBitmapImageView(bitmap);
                        }catch (IOException e){
                            e.printStackTrace();
                        }

                    }

                }
            });


    private void initListener() {
        edit_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check version android
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    openGallery();
                    return;
                }
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    openGallery();
                }else {
                    //check cho phép vào ảnh ng dùng
                    String [] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permisstions,MY_REQUEST_CODE);
                }
            }
        });
    }


    private void setUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String email = user.getEmail();
        user_email.setText(email);
        edit_name.setText(user.getDisplayName());
        edit_phone.setText(user.getPhoneNumber());
        Uri photoUrl = user.getPhotoUrl();
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(edit_Avatar);
    }
    public void setBitmapImageView(Bitmap bitmapImageView){
        edit_Avatar.setImageBitmap(bitmapImageView);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }
    }
    public  void openGallery(){
        Intent intent  = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mactivityResultLauncher.launch(Intent.createChooser(intent,"Tải ảnh thành công"));
    }
}