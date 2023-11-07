package com.example.datn_tranvantruong.Fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.datn_tranvantruong.Activity.InforApp_Activity;
import com.example.datn_tranvantruong.Activity.Intro_Activity;
import com.example.datn_tranvantruong.Activity.Login_Activity;
import com.example.datn_tranvantruong.Activity.Profile_Activity;
import com.example.datn_tranvantruong.Activity.RePassword_Activity;
import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.Database.DBManager;
import com.example.datn_tranvantruong.R;


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

        DBManager dbManager = new DBManager(getContext());
        SQLiteDatabase db = dbManager.getWritableDatabase();

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


                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100041618133507&locale=vi_VN")); // Thay thế URL bằng liên kết Zalo của bạn
                    startActivity(webIntent);

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


                db.close();

                startActivity(new Intent(getActivity(), Login_Activity.class));
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
        return view;
    }
    private void  showUserInformation(){
        CustomerHandler customerHandler = new CustomerHandler(getContext());

                String email = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getEmail();
                String name = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getFullname();
                byte[] avatar = customerHandler.getCustomerInfo(String.valueOf(Intro_Activity.user_id)).getImage_avatar();


                // Gán giá trị "email" vào TextView
                user_email.setText(email);
                user_name.setText(name);
                if (avatar != null) {
                    Bitmap avatarBitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
                    img_avatar.setImageBitmap(avatarBitmap);

            }
        }
    }


