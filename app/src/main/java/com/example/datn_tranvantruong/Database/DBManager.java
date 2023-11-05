package com.example.datn_tranvantruong.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "BOOK_TOUR";
    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    private static final String TABLE_CUSTOMER = "customers";
    private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_BILLS = "bills";
    private static final String TABLE_BILL_DETAIL = "bill_detail";

    // Các trường trong bảng Users
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_FULLNAME = "fullname";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ADDRESS = "address";
    private static final String COLUMN_USER_AVATAR = "image_avatar";
    private static final String COLUMN_USER_PHONE = "phone";

    // Các trường trong bảng Admin
    private static final String COLUMN_ADMIN_ID = "id";
    private static final String COLUMN_ADMIN_NAME = "name";
    private static final String COLUMN_ADMIN_EMAIL = "email";
    private static final String COLUMN_ADMIN_PASSWORD = "password";

    // Các trường trong bảng Categories
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    // Các trường trong bảng Products
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_LOCATION = "location";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE = "image_tour";

    // Các trường trong bảng Bills
    private static final String COLUMN_BILL_ID = "id";
    private static final String COLUMN_BILL_USER_ID = "user_id";
    private static final String COLUMN_BILL_TOTAL_PRICE = "total_price";
    private static final String COLUMN_BILL_DESCRIPTION = "description";
    private static final String COLUMN_BILL_DATE_CREATED = "date_created";

    // Các trường trong bảng Bill Detail
    private static final String COLUMN_BILL_DETAIL_ID = "id";
    private static final String COLUMN_BILL_DETAIL_BILL_ID = "bill_id";
    private static final String COLUMN_BILL_DETAIL_PRODUCT_NAME = "product_name";
    private static final String COLUMN_BILL_DETAIL_QUANTITY = "quantity";
    private static final String COLUMN_BILL_DETAIL_PRICE = "price";

    // Câu lệnh SQL để tạo bảng Users
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_CUSTOMER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_EMAIL + " TEXT UNIQUE,"
            + COLUMN_USER_FULLNAME + " TEXT UNIQUE,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_ADDRESS + " TEXT,"
            + COLUMN_USER_AVATAR + " BLOB,"
            + COLUMN_USER_PHONE + " TEXT"
            + ")";
    // Câu lệnh SQL để tạo bảng Admin
    private static final String CREATE_TABLE_ADMIN = "CREATE TABLE " + TABLE_ADMIN + "("
            + COLUMN_ADMIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ADMIN_NAME + " TEXT,"
            + COLUMN_ADMIN_EMAIL + " TEXT,"
            + COLUMN_ADMIN_PASSWORD + " TEXT"
            + ")";

    // Câu lệnh SQL để tạo bảng Categories
    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CATEGORY_NAME + " TEXT UNIQUE"
            + ")";

    // Câu lệnh SQL để tạo bảng Products
    private static final String CREATE_TABLE_PRODUCTS = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PRODUCT_CATEGORY_ID + " INTEGER,"
            + COLUMN_PRODUCT_NAME + " TEXT,"
            + COLUMN_PRODUCT_DESCRIPTION +"TEXT,"
            + COLUMN_PRODUCT_LOCATION + "TEXT,"
            + COLUMN_PRODUCT_PRICE + " REAL,"
            + COLUMN_PRODUCT_IMAGE + " BLOB,"
            + "FOREIGN KEY(" + COLUMN_PRODUCT_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + ")"
            + ")";

    // Câu lệnh SQL để tạo bảng Bills
    private static final String CREATE_TABLE_BILLS = "CREATE TABLE " + TABLE_BILLS + "("
            + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BILL_USER_ID + " INTEGER,"
            + COLUMN_BILL_TOTAL_PRICE + " REAL,"
            + COLUMN_BILL_DESCRIPTION + " TEXT,"
            + COLUMN_BILL_DATE_CREATED + " DATETIME DEFAULT (datetime('now')),"
            + "FOREIGN KEY(" + COLUMN_BILL_USER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + COLUMN_USER_ID + ")"
            + ")";


    // Câu lệnh SQL để tạo bảng Bill Detail
    private static final String CREATE_TABLE_BILL_DETAIL = "CREATE TABLE " + TABLE_BILL_DETAIL + "("
            + COLUMN_BILL_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BILL_DETAIL_BILL_ID + " INTEGER,"
            + COLUMN_BILL_DETAIL_PRODUCT_NAME + " TEXT,"
            + COLUMN_BILL_DETAIL_QUANTITY + " INTEGER,"
            + COLUMN_BILL_DETAIL_PRICE + " REAL,"
            + "FOREIGN KEY(" + COLUMN_BILL_DETAIL_BILL_ID + ") REFERENCES " + TABLE_BILLS + "(" + COLUMN_BILL_ID + ")"
            + ")";

    private static final String INSERT_ADMIN = "INSERT INTO admin (" + COLUMN_ADMIN_NAME + ", " + COLUMN_ADMIN_EMAIL + ", " + COLUMN_ADMIN_PASSWORD + ") VALUES ('admin', 'admin', 'password')";

    // Constructor của DatabaseHelper
    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo các bảng dữ liệu trong cơ sở dữ liệu
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_BILLS);
        db.execSQL(CREATE_TABLE_BILL_DETAIL);
        db.execSQL(INSERT_ADMIN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng dữ liệu cũ nếu có và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL);
        onCreate(db);
    }
}