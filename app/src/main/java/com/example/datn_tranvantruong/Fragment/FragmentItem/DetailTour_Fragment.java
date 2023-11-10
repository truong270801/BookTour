package com.example.datn_tranvantruong.Fragment.FragmentItem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

public class DetailTour_Fragment extends Fragment {

    private int qualityValue = 1;
    TextView quality;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detai_home, container, false);

        ImageView tourImageView = view.findViewById(R.id.tourImageView);
        TextView tourNametxt = view.findViewById(R.id.tourNametxtV);
        TextView tourLocationtxt = view.findViewById(R.id.tourLocationtxtV);
        TextView tourPricetxt = view.findViewById(R.id.tourPricetxtV);
        TextView startdaytxt = view.findViewById(R.id.tv_startdate);
        TextView enddaytxt = view.findViewById(R.id.tv_enddate);
        TextView categorytxt = view.findViewById(R.id.tv_Category);
        TextView contenttxt = view.findViewById(R.id.content_tour);
        ImageView add_quality = view.findViewById(R.id.add_quality);
        ImageView delete_quality = view.findViewById(R.id.delete_quality);
        quality = view.findViewById(R.id.quality);
        quality.setText(String.valueOf(qualityValue));

        ImageView addcart = view.findViewById(R.id.btn_addcart);
//
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        add_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuality();
            }
        });

        // Thêm sự kiện cho nút delete_quality
        delete_quality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuality();
            }
        });

        Bundle bundle = getArguments();
        int product_id = bundle.getInt("product_id");
        String category_name = bundle.getString("category_name");
        ProductHandler productHandler = new ProductHandler(getContext());
        Product product = productHandler.findById(product_id);



        tourNametxt.setText(product.getName());
        tourPricetxt.setText(product.getPrice() + "");
        startdaytxt.setText(product.getStartdate());
        enddaytxt.setText(product.getEnddate());
        tourLocationtxt.setText(product.getLocation());
        contenttxt.setText(product.getDescription());
        categorytxt.setText(category_name);
        byte[] productImage = product.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
        tourImageView.setImageBitmap(bitmap);

        return view;
    }

    public void onBackPressed() {
        AppCompatActivity activity = (AppCompatActivity) getContext();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    // Phương thức tăng giá trị chất lượng
    private void increaseQuality() {
        qualityValue++;
        quality.setText(String.valueOf(qualityValue));
    }

    // Phương thức giảm giá trị chất lượng
    private void decreaseQuality() {
        if (qualityValue > 1) {
            qualityValue--;
            quality.setText(String.valueOf(qualityValue));
        }
    }

}