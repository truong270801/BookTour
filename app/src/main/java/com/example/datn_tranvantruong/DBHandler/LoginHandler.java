package com.example.datn_tranvantruong.DBHandler;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datn_tranvantruong.Database.DBManager;

public class LoginHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "BOOK_TOURS";
    private static final int DATABASE_VERSION = 1;

    DBManager dbManager;
    private Context context;

    public LoginHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbManager = new DBManager(context);
        db = dbManager.getWritableDatabase();
    }

    public String checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Kiểm tra tài khoản trong bảng admin
        String[] columns = { "email" };
        String selection = "email" + " = ?" + " AND " + "password" + " = ?";
        String[] selectionArgs = { email, password };
        Cursor cursor = db.query("admin", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return "admin";
        }

        // Kiểm tra tài khoản trong bảng customer
        columns = new String[]{ "email" };
        selection = "email" + " = ?" + " AND " + "password" + " = ?";
        selectionArgs = new String[]{ email, password };
        cursor = db.query("customers", columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return "customer";
        }

        cursor.close();
        return "false";
    }

    public int getUserId(String email) {
        String query = "SELECT id FROM customers WHERE email = ?";
        Cursor c = db.rawQuery(query, new String[] {email});
        c.moveToFirst();
        int user_id = -1;
        if (!c.isAfterLast()) {
            user_id = c.getInt(0);
        }
        c.close();
        return user_id;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
