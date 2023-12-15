package com.example.datn_tranvantruong.Fragment.FragmentItem;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.datn_tranvantruong.DBHandler.CartHandler;
import com.example.datn_tranvantruong.Adapter.EvaluateAdapter;
import com.example.datn_tranvantruong.Adapter.ProductUserAdapter;
import com.example.datn_tranvantruong.DBHandler.CartHandler;
import com.example.datn_tranvantruong.DBHandler.EvaluateHandler;
import com.example.datn_tranvantruong.DBHandler.ProductHandler;
import com.example.datn_tranvantruong.Fragment.HomeFragment;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.Cart;
import com.example.datn_tranvantruong.Model.CartStatistic;
import com.example.datn_tranvantruong.Model.Evaluate;
import com.example.datn_tranvantruong.Model.Product;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class DetailTour_Fragment extends Fragment {

    private int qualityValue = 1;
    TextView quality, tourNametxt, tourLocationtxt,contenttxt, tourPricetxt, startdaytxt, enddaytxt, categorytxt   ;
    RatingBar rating;
    ImageView tourImageView, add_quality, delete_quality, addcart;
    RecyclerView recyclerView;
    EvaluateAdapter evaluateAdapter;
    EvaluateHandler evaluateHandler;
    List<Evaluate> evaluateList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detai_home, container, false);

        tourImageView = view.findViewById(R.id.tourImageView);
         tourNametxt = view.findViewById(R.id.tourNametxtV);
         tourLocationtxt = view.findViewById(R.id.tourLocationtxtV);
         tourPricetxt = view.findViewById(R.id.tourPricetxtV);
         startdaytxt = view.findViewById(R.id.tv_startdate);
         enddaytxt = view.findViewById(R.id.tv_enddate);
         categorytxt = view.findViewById(R.id.tv_Category);
         contenttxt = view.findViewById(R.id.content_tour);
         add_quality = view.findViewById(R.id.add_quality);
         delete_quality = view.findViewById(R.id.delete_quality);
         quality = view.findViewById(R.id.quality);
        recyclerView = view.findViewById(R.id.evaluateTour);
         addcart = view.findViewById(R.id.btn_addcart);
        rating = view.findViewById(R.id.rating);

        toolbar(view);

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
        if (bundle != null) {
            int product_id = bundle.getInt("product_id");
            String category_name = bundle.getString("category_name");
            ProductHandler productHandler = new ProductHandler();
            Product product = productHandler.findById(product_id);


            tourNametxt.setText(product.getName());
            tourPricetxt.setText(String.valueOf(product.getPrice()));
            startdaytxt.setText(product.getStartdate());
            enddaytxt.setText(product.getEnddate());
            tourLocationtxt.setText(product.getLocation());
            contenttxt.setText(product.getDescription());
            categorytxt.setText(category_name);
            rating.setRating(productHandler.getRatingById(product_id));
            quality.setText(String.valueOf(qualityValue));
            byte[] productImage = product.getImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
            tourImageView.setImageBitmap(bitmap);

            evaluateHandler = new EvaluateHandler();
            evaluateList = evaluateHandler.getAllEvaluateByProductId(product_id);
            evaluateAdapter = new EvaluateAdapter(getContext(),evaluateList);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(evaluateAdapter);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int productId = getArguments().getInt("product_id");
                String name = tourNametxt.getText().toString() ;
                int price = Integer.parseInt(tourPricetxt.getText().toString().trim());
                int quatity = Integer.parseInt(quality.getText().toString().trim());
                int total = price * quatity;

                // Tạo đối tượng Cart
                Cart cart = new Cart(productId, MainActivity.user_id, quatity, total);

                //Thêm vào giỏ hàng
                CartHandler cartHandler = new CartHandler();
                cartHandler.addToCart(cart);
                Toast.makeText(getContext(), "Thêm sản phẩm:" + name + " vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void toolbar(View view) {
        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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