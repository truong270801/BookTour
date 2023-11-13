package com.example.datn_tranvantruong.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.datn_tranvantruong.Database.DBManager;
import com.example.datn_tranvantruong.Model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartHandler extends SQLiteOpenHelper {
    DBManager dbManager;
    private static final String DATABASE_NAME = "BOOK_TOUR.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    public CartHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbManager = new DBManager(context); // tạo db
    }
    // Trong CartHandler hoặc lớp xử lý giỏ hàng tương tự
    // Trong CartHandler hoặc lớp xử lý giỏ hàng tương tự
    public void addToCart(Cart cartItem) {


        ContentValues values = new ContentValues();
        values.put("product_id", cartItem.getProduct_id());
        values.put("user_id", cartItem.getUser_id());
        values.put("quantity", cartItem.getQuality());
        values.put("price", cartItem.getPrice());

        // Chèn dữ liệu vào bảng giỏ hàng
        long result = dbManager.getWritableDatabase().insert("carts", null, values);

        dbManager.close();

        if (result == -1) {
            // Xử lý khi có lỗi thêm vào CSDL
            Log.e("AddToCart", "Error inserting data into cart table");
        } else {
            // Xử lý khi thêm vào CSDL thành công
            Log.i("AddToCart", "Successfully inserted data into cart table");
        }
    }

    public static Cursor getCartByUserID(Context context, int id) {
        CartHandler cartHandler = new CartHandler(context);
        SQLiteDatabase db = cartHandler.getReadableDatabase();

        String selectQuery = "SELECT * FROM carts WHERE user_id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        return cursor;
    }
    public void deleteCart(int i) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("carts", "id" + " = " + i, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
