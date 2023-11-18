package com.example.datn_tranvantruong.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datn_tranvantruong.Database.DBManager;
import com.example.datn_tranvantruong.Model.Bill;
import com.example.datn_tranvantruong.Model.BillRevenue;
import com.example.datn_tranvantruong.Model.BillStatistic;
import com.example.datn_tranvantruong.Model.Customer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTimeString = sdf.format(new Date(System.currentTimeMillis()));

        ContentValues values = new ContentValues();
        values.put("user_id", bill.getUser_id());
        values.put("product_id", bill.getProduct_id());
        values.put("quatity",bill.getQuatity());
        values.put("total_price", bill.getPrice());
        values.put("description", bill.getDescription());
        values.put("date_created", currentDateTimeString);
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
    public String getBillQuatityById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT quatity FROM bills WHERE id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        String billQuatity = null;
        if (cursor.moveToFirst()) {
            billQuatity = cursor.getString(0);
        }
        cursor.close();
        return billQuatity;
    }

    public String getUserIdById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT user_id FROM bills WHERE id = ?";
        String[] selectionArgs = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(selectQuery, selectionArgs);
        String user_id = null;
        if (cursor.moveToFirst()) {
            user_id = cursor.getString(0);
        }
        cursor.close();
        return user_id;
    }
    public List<BillStatistic> getAllBill() {
        List<BillStatistic> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("bills", null, null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            BillStatistic bill = new BillStatistic();
            bill.setBill_id(c.getInt(0));
bill.setProduct_id(c.getInt(2));
            // Sử dụng Double.parseDouble() thay vì Integer.parseInt()
            bill.setPrice((int) Double.parseDouble(c.getString(4)));

            bill.setDescription(c.getString(5));
            bill.setDate(c.getString(6));

            ls.add(bill);
            c.moveToNext();
        }

        c.close();
        return ls;
    }
    public List<BillRevenue> getAllBillRevenue() {
        List<BillRevenue> ls = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query("bills", null, null, null, null, null, null);

        c.moveToFirst();
        while (!c.isAfterLast()) {
            BillRevenue BillRevenue = new BillRevenue();

            BillRevenue.setBill_id(c.getInt(0));
            BillRevenue.setQuatity(Integer.parseInt(c.getString(3)));

            // Sử dụng Double.parseDouble() thay vì Integer.parseInt()
            BillRevenue.setPrice(c.getString(4));



            ls.add(BillRevenue);
            c.moveToNext();
        }

        c.close();
        return ls;
    }
    public int getTotalBill(String start_date, String end_date) {
        int total = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(total_price) FROM  bills  WHERE date_created BETWEEN ? AND ? ";
        Cursor cursor = db.rawQuery(query, new String[]{start_date, end_date});
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return total;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
