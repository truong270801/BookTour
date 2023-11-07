package com.example.datn_tranvantruong.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.datn_tranvantruong.Model.Category;
import com.example.datn_tranvantruong.R;

import java.util.List;

public class ItemHome_Adapter extends BaseAdapter {
    Context context;
    List<Category> categoryList;
    int layout;

    public ItemHome_Adapter(Context context, int layout, List<Category> categoryList) {
        this.context = context;
        this.layout = layout;
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList == null ? 0 : categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categoryList.get(i).getIdCategory();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.txtCategoryName = view.findViewById(R.id.txtCategoryName);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Category category = (Category) getItem(i);
        holder.txtCategoryName.setText(category.getNameCategory());
        return view;
    }

    public static class ViewHolder {
        TextView txtCategoryName;
    }
}
