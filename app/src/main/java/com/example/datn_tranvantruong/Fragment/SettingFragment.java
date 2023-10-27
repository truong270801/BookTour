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
import com.example.datn_tranvantruong.Activity.InforApp_Activity;
import com.example.datn_tranvantruong.Activity.Login_Activity;
import com.example.datn_tranvantruong.Activity.Profile_Activity;
import com.example.datn_tranvantruong.Activity.RePassword_Activity;
import com.example.datn_tranvantruong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingFragment extends Fragment {
Button btnLogout, btnEditProfile, bntHelp,btn_rePassword,btn_infor;
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

        btn_infor = view.findViewById(R.id.btn_infor);
        btn_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), InforApp_Activity.class));

            }
        });
        btn_rePassword = view.findViewById(R.id.btn_rePassword);
        btn_rePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RePassword_Activity.class));

            }
        });

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