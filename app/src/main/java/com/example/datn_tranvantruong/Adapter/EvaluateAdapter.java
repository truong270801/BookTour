package com.example.datn_tranvantruong.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_tranvantruong.DBHandler.CustomerHandler;
import com.example.datn_tranvantruong.MainActivity;
import com.example.datn_tranvantruong.Model.Evaluate;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.EvaluateViewHolder>  {
    private List<Evaluate> evaluateList;
    private Context context;
    public EvaluateAdapter(Context context,List<Evaluate> evaluateList) {
        this.context = context;
        this.evaluateList = evaluateList;
        }

    @NonNull
    @Override
    public EvaluateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluate,parent,false);

        return new EvaluateViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull EvaluateViewHolder holder, int position) {
        Evaluate evaluate = evaluateList.get(position);
        if (evaluate == null){
            return;
        }
        CustomerHandler customerHandler = new CustomerHandler();
        String email = customerHandler.getCustomerInfo(evaluateList.get(position).getUser_id()).getEmail();
        byte[] avatar = customerHandler.getCustomerInfo(evaluateList.get(position).getUser_id()).getImage_avatar();
        holder.user_email.setText(email);
        holder.ratingBar.setRating(evaluate.getRating());
        holder.txtComment.setText(evaluate.getComment());
        Bitmap bitmap = BitmapFactory.decodeByteArray(avatar, 0, avatar.length);
        holder.image_avatar.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        if (evaluateList != null){
            return  evaluateList.size();
        }
        return 0;
    }

    public class EvaluateViewHolder extends RecyclerView.ViewHolder{
        ImageView image_avatar;
        RatingBar ratingBar;
        TextView user_email,txtComment;
        public EvaluateViewHolder(@NonNull View itemView) {
            super(itemView);
            image_avatar = itemView.findViewById(R.id.image_avatar);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            user_email = itemView.findViewById(R.id.user_email);
            txtComment = itemView.findViewById(R.id.txtComment);
        }
    }

}
