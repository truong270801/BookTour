package com.example.datn_tranvantruong.DBHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.datn_tranvantruong.Database.DBManager;


public class SignupHandler extends SQLiteOpenHelper {

    SQLiteDatabase db;

    private static final String DATABASE_NAME = "BOOK_TOUR";
    private static final int DATABASE_VERSION = 1;

    DBManager dbManager;
    private Context context;

    public SignupHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbManager = new DBManager(context);
        db = dbManager.getWritableDatabase();
    }

    public boolean register(String email, String fullname, String password, String phone) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into customers ( email, fullname, password,phone) values (?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, email);
        statement.bindString(2, fullname);
        statement.bindString(3, password);
        statement.bindString(4, phone);
        long rowId = statement.executeInsert();
        db.close();
        return (rowId != -1); // Trả về true nếu rowId khác -1 (tức là insert thành công), ngược lại trả về false
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
