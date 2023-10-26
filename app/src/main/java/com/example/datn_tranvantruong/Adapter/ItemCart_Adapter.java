package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.Model.ItemHome_Model;
import com.example.datn_tranvantruong.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCart_Adapter extends RecyclerView.Adapter<ItemCart_Adapter.ViewHolder> {
    private List<ItemHome_Model> itemList;
    private Context context;
    private OnItemClickListener listener;

    public void setData(List<ItemHome_Model> newData) {
        this.itemList = newData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(ItemHome_Model item);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public ItemCart_Adapter(Context context, List<ItemHome_Model> itemList) {
        this.context = context;
        this.itemList = itemList;
        notifyDataSetChanged();
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            ItemHome_Model item = itemList.get(position); // Đây là cách đúng

            // Set the data to views in your item layout
            holder.nameTextView.setText(item.getName());
            holder.locationTextView.setText(item.getLocation());
            holder.priceTextView.setText(item.getPrice());

            // Load the image using a library like Picasso or Glide.
            Picasso.get().load(item.getImageUrl()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public TextView locationTextView;
        public TextView priceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
        }

    }
}
