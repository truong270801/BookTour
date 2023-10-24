package com.example.datn_tranvantruong.Fragment;

import static com.example.datn_tranvantruong.MainActivity.MY_REQUEST_CODE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Profile_Fragment extends Fragment {

private View view;
private ImageView edit_Avatar;
private EditText edit_name,edit_phone;
private TextView user_email;
private Button bnt_Update;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_, container, false);

        edit_Avatar = view.findViewById(R.id.edit_Avatar);
        edit_name = view.findViewById(R.id.edit_name);
        edit_phone = view.findViewById(R.id.edit_phone);
        bnt_Update = view.findViewById(R.id.bnt_Update);
        user_email = view.findViewById(R.id.user_email);
        setUserInformation();

        initListener();
        return view;
    }

    private void initListener() {
        edit_Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity == null){
            return;
}
            //check version android
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                    mainActivity.openGallery();
                    return;
                }
                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    mainActivity.openGallery();
                }else {
                    //check cho phép vào ảnh ng dùng
                    String [] permisstions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    getActivity().requestPermissions(permisstions,MY_REQUEST_CODE);
                }
            }
        });
    }


    private void setUserInformation(){
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user == null){
          return;
      }
      edit_name.setText(user.getDisplayName());
      edit_phone.setText(user.getPhoneNumber());
      user_email.setText(user.getEmail());
  }
  public void setBitmapImageView(Bitmap bitmapImageView){
      edit_Avatar.setImageBitmap(bitmapImageView);
  }
}