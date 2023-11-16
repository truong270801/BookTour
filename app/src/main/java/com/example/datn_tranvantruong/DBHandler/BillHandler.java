package com.example.datn_tranvantruong.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.datn_tranvantruong.Database.DBManager;
import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.Cart;

public class BillHandler extends SQLiteOpenHelper {
    DBManager dbManager;
    private static final String DATABASE_NAME = "BOOK_TOUR.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    public BillHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbManager = new DBManager(context); // tạo db
    }
    public void addBill(Bill bill) {
        ContentValues values = new ContentValues();
        values.put("user_id", bill.getUser_id());
        values.put("product_id", bill.getProduct_id());
        values.put("quatity",bill.getQuatity());
        values.put("total_price", bill.getPrice());
        values.put("description", bill.getDescription());
        values.put("date_created", bill.getDate_created());
        // Chèn dữ liệu vào bảng giỏ hàng
      dbManager.getWritableDatabase().insert("bills", null, values);
        dbManager.close();
    }
    public static Cursor getBillByUserID(Context context, int id) {
        BillHandler billHandler = new BillHandler(context);
        SQLiteDatabase db = billHandler.getReadableDatabase();

        String selectQuery = "SELECT * FROM bills WHERE user_id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);

        return cursor;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
