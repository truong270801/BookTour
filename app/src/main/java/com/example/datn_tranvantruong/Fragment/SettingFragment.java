package com.example.datn_tranvantruong.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.datn_tranvantruong.Activity.Login_Activity;
import com.example.datn_tranvantruong.Activity.Profile_Activity;
import com.example.datn_tranvantruong.Activity.Signup_Activity;
import com.example.datn_tranvantruong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;


public class SettingFragment extends Fragment {
Button btnLogout, btnEditProfile, bntHelp;
ImageView img_avatar;
TextView user_name,user_email;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        img_avatar = view.findViewById(R.id.img_avatar);
        user_name = view.findViewById(R.id.user_name);
        user_email = view.findViewById(R.id.user_email);

        bntHelp = view.findViewById(R.id.btnHelp);
        bntHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở ứng dụng Zalo nếu đã cài đặt
                Intent intent = getActivity().getPackageManager().getLaunchIntentForPackage("com.facebook.katana");

                if (intent != null) {
                    startActivity(intent);
                } else {
                    // Nếu không có ứng dụng Zalo, mở trình duyệt để trang web Zalo
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100041618133507&locale=vi_VN")); // Thay thế URL bằng liên kết Zalo của bạn
                    startActivity(webIntent);
                }
            }
        });

        showUserInformation();
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Profile_Activity.class));

            }
        });
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