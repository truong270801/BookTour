package com.example.datn_tranvantruong.Activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Profile_Activity extends AppCompatActivity {
    private View view;
    public static final int MY_REQUEST_CODE = 10;
    private ImageView edit_Avatar;
    private EditText edit_name,edit_phone,edit_address;
    private TextView user_email;
    ImageView add_avatar;
    private Button bnt_Update;
    int REQUEST_CODE_FOLDER = 352;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edit_Avatar = findViewById(R.id.edit_Avatar);
        edit_name = findViewById(R.id.edit_name);
        edit_phone = findViewById(R.id.edit_phone);
        bnt_Update = findViewById(R.id.bnt_Update);
        user_email = findViewById(R.id.user_email);
        edit_address = findViewById(R.id.edit_address);
        add_avatar = findViewById(R.id.add_avatar);
        bnt_Update = findViewById(R.id.bnt_Update);

        CustomerHandler customerHandler = new CustomerHandler(Profile_Activity.this);

                String email = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getEmail();
                String fullname = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getFullname();
                String address = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getAddress();
                String phone = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getPhone();
                byte[] avatar = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getImage_avatar();
                // Gán giá trị "email" vào TextView
                user_email.setText(email);
                edit_name.setText(fullname);
                edit_address.setText(address);
                edit_phone.setText(phone);
                if (avatar != null) {
                  Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
                  edit_Avatar.setImageBitmap(avatarBitmap);
                }

        bnt_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                int id = Intro_Activity.user_id;
                String fullname = edit_name.getText().toString().trim(); // Corrected .getText() method call
                String address = edit_address.getText().toString().trim();  // Corrected .getText() method call
                String phone = edit_phone.getText().toString().trim();  // Corrected .getText() method call
                byte[] avatarBytes = convertToArrayByte(edit_Avatar);

                customerHandler.updateCustomerInfo(String.valueOf(id),fullname, address, phone, avatarBytes);

                Intent intent = new Intent(getApplicationContext(), Profile_Activity.class);
                startActivity(intent);
                Toast.makeText(Profile_Activity.this, "Sửa hàng thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

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

        add_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent in = new Intent(Intent.ACTION_PICK);
                in.setType("image/*");
                startActivityForResult(in, REQUEST_CODE_FOLDER);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK & data != null) {
            Uri uri = data.getData();
            try {
                InputStream ipstream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(ipstream);
                edit_Avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] convertToArrayByte(ImageView img) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


}