package com.example.datn_tranvantruong.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.datn_tranvantruong.Activity.Login_Activity;
import com.example.datn_tranvantruong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;


public class SettingFragment extends Fragment {
Button btnLogout;
ImageView img_avatar;
TextView user_name,user_email;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);

        img_avatar = view.findViewById(R.id.img_avatar);
        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);
        showUserInformation();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                mAuth.signOut();

                startActivity(new Intent(getActivity(), Login_Activity.class));
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
        return view;
    }
    private void  showUserInformation(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            return;
        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        if(name == null){
            user_name.setVisibility(View.GONE);
        }else {
            user_name.setVisibility(View.GONE);
            user_name.setText(name);

        }

        user_name.setText(name);
        user_email.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.ic_avatar_default).into(img_avatar);
    }
}