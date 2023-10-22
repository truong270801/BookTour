package com.example.datn_tranvantruong.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.R;

public class Intro_Activity extends AppCompatActivity {
Button bntIntro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        bntIntro = findViewById(R.id.btnIntro);
        bntIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro_Activity.this, Login_Activity.class);
                startActivity(intent);

            }
        });

    }
}