package com.example.datn_tranvantruong;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//import com.example.datn_tranvantruong.Fragment.CartFragment;
//import com.example.datn_tranvantruong.Fragment.HomeFragment;
//import com.example.datn_tranvantruong.Fragment.Order_Fragment;
//import com.example.datn_tranvantruong.Fragment.SettingFragment;
import com.example.datn_tranvantruong.Fragment.CartFragment;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.Fragment.Order_Fragment;
import com.example.datn_tranvantruong.Fragment.SettingFragment;
import com.example.datn_tranvantruong.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    public static int user_id = 1;

ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.cart:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.order:
                    replaceFragment(new Order_Fragment());
                    break;
                case R.id.settings:
                    replaceFragment(new SettingFragment());
                    break;
            }
            return true;
        });
    }
        private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }


}


