package com.example.datn_tranvantruong.DBHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.datn_tranvantruong.Model.User;



public class CustomerHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BOOK_TOURS";
    private static final int DATABASE_VERSION = 1;
    public CustomerHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    public User getCustomerInfo(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();

        String query = "SELECT fullname,email, address, phone,image_avatar FROM customers WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor.moveToFirst()) {
            user.setFullname(cursor.getString(0));
            user.setEmail(cursor.getString(1));
            user.setAddress(cursor.getString(2));
            user.setPhone(cursor.getString(3));
            user.setImage_avatar(cursor.getBlob(4));
        }
        return user;
    }
    public void updateCustomerPassword(String id, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        db.update("customers", values, "id = ?", new String[]{id});
        db.close();
    }
    public String getPasswordFromDB(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String password = null;

        Cursor cursor = db.query("customers", new String[]{"password"}, "id = ?", new String[]{id}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
        }

        return password;
    }
    public void updateCustomerInfo(String id, String fullname, String address, String phone, byte[] avatar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fullname", fullname);
        values.put("address", address);
        values.put("phone", phone);
        values.put("image_avatar", avatar);

        db.update("customers", values, "id = ?", new String[]{id});
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}

