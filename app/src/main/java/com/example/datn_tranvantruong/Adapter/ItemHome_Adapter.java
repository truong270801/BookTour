package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Model.ItemHome_Model;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class ItemHome_Adapter extends RecyclerView.Adapter<ItemHome_Adapter.ViewHolder> {
    private List<ItemHome_Model> itemList;

    public ItemHome_Adapter(List<ItemHome_Model> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemHome_Model item = itemList.get(position);
        holder.titleTour.setText(item.getTitle());
        holder.locationTour.setText(item.getLocation());
        holder.priceTour.setText(item.getPrice());
        Bitmap bitmap = BitmapFactory.decodeByteArray(ItemHome_Model.getImgtour(), 0, ItemHome_Model.getImgtour().length);
        holder.imgTour.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTour, locationTour, priceTour;
        ImageView imgTour;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTour = itemView.findViewById(R.id.titleTour);
            locationTour = itemView.findViewById(R.id.locationTour);
            priceTour = itemView.findViewById(R.id.priceTour);
            imgTour = itemView.findViewById(R.id.imgTour);

        }
    }
}
