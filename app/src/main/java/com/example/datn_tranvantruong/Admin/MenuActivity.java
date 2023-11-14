package com.example.datn_tranvantruong.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.Activity.Login_Activity;
import com.example.datn_tranvantruong.Admin.FragmentAdmin.CategoryFragment;
import com.example.datn_tranvantruong.Admin.FragmentAdmin.CustomerListFragment;
import com.example.datn_tranvantruong.Admin.FragmentAdmin.ListProductFragment;
import com.example.datn_tranvantruong.R;
import com.example.datn_tranvantruong.databinding.ActivityMenuAdminBinding;


public class MenuActivity extends AppCompatActivity {
    ActivityMenuAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        binding = ActivityMenuAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CategoryFragment());
        ImageView logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.bottomNavigationViewAdmin.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.category:
                    replaceFragment(new CategoryFragment());
                    break;
                case R.id.product:
                    replaceFragment(new ListProductFragment());
                    break;
                case R.id.customer:
                    replaceFragment(new CustomerListFragment());
                    break;

            }
            return true;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_admin,fragment);
        fragmentTransaction.commit();
    }


}