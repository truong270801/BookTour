package com.example.datn_tranvantruong.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.datn_tranvantruong.Database.DBConnection;
import com.example.datn_tranvantruong.R;

import java.sql.Connection;

public class Intro_Activity extends AppCompatActivity {
Button bntIntro;
Connection connection = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        bntIntro = findViewById(R.id.btnIntro);
        try {
            DBConnection connect = new DBConnection();
            connection = connect.createConection();
            if (connection != null){
                Toast.makeText(this,"Kết nối database thành công",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this,"Kết nối thaast bai",Toast.LENGTH_LONG).show();

            }
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
        bntIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}