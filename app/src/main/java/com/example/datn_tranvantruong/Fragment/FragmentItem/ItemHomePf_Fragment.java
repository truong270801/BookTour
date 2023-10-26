package com.example.datn_tranvantruong.Fragment.FragmentItem;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemHomePf_Fragment extends Fragment {
    String imageUrl,name,location,  price;

    public ItemHomePf_Fragment(String imageUrl, String name, String location, String price){
        this.imageUrl = imageUrl;
        this.name = name;
        this.location = location;
        this.price = price;



    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_home_pf_, container, false);

        ImageView tourImageView = view.findViewById(R.id.tourImageView);
        TextView tourNametxtV = view.findViewById(R.id.tourNametxtV);
        TextView tourLocationtxtV = view.findViewById(R.id.tourLocationtxtV);
        TextView tourPricetxtV = view.findViewById(R.id.tourPricetxtV);

        Glide.with(tourImageView.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(tourImageView);

        tourNametxtV.setText(name);
        tourLocationtxtV.setText(location);
        tourPricetxtV.setText(price);

        //onBackPressed();
        return view;
    }
    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();


    }
}