package com.example.datn_tranvantruong.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.datn_tranvantruong.Fragment.CartFragment;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.Fragment.Order_Fragment;
import com.example.datn_tranvantruong.Fragment.SettingFragment;


public class viewpagerAdapter extends FragmentStatePagerAdapter {

    public viewpagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CartFragment();
            case 2:
                return new Order_Fragment();
            case 3:
                return new SettingFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
