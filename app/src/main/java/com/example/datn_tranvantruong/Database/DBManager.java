package com.example.datn_tranvantruong.Database;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




  import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DBManager extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu
    private static final String DATABASE_NAME = "BOOK_TOUR.db";
    // Phiên bản cơ sở dữ liệu
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    private static final String TABLE_CUSTOMER = "customers";
    private static final String TABLE_ADMIN = "admin";
    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CARTS = "carts";
    private static final String TABLE_BILLS = "bills";


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
    private static final String COLUMN_ADMIN_EMAIL = "email";
    private static final String COLUMN_ADMIN_PASSWORD = "password";

    // Các trường trong bảng Categories
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";
    private static final String COLUMN_CATEGORY_IMAGE = "image";


    // Các trường trong bảng Products
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_CATEGORY_ID = "category_id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_STARTDATE = "startdate";
    private static final String COLUMN_PRODUCT_ENDDATE = "enddate";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_LOCATION = "location";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE = "image";

    // Các trường trong bảng Carts
    private static final String COLUMN_CART_ID = "id";
    private static final String COLUMN_CART_USER_ID = "user_id";
    private static final String COLUMN_CART_PRODUCT_ID = "product_id";
    private static final String COLUMN_CART_QUANTITY = "quantity";
    private static final String COLUMN_CART_PRICE = "price";

    // Các trường trong bảng Bills
    private static final String COLUMN_BILL_ID = "id";
    private static final String COLUMN_BILL_USER_ID = "user_id";
    private static final String COLUMN_BILL_PRODUCT_ID = "product_id";
    private static final String COLUMN_BILL_PRODUCT_QUATITY = "quatity";
    private static final String COLUMN_BILL_TOTAL_PRICE = "total_price";
    private static final String COLUMN_BILL_DESCRIPTION = "description";
    private static final String COLUMN_BILL_DATE_CREATED = "date_created";

    Context context;
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
            + COLUMN_PRODUCT_STARTDATE + " TEXT,"
            + COLUMN_PRODUCT_ENDDATE + " TEXT,"
            + COLUMN_PRODUCT_DESCRIPTION +" TEXT,"
            + COLUMN_PRODUCT_LOCATION + " TEXT,"
            + COLUMN_PRODUCT_PRICE + " REAL,"
            + COLUMN_PRODUCT_IMAGE + " BLOB,"
            + "FOREIGN KEY(" + COLUMN_PRODUCT_CATEGORY_ID + ") REFERENCES " + TABLE_CATEGORIES + "(" + COLUMN_CATEGORY_ID + ")"
            + ")";

    // Câu lệnh SQL để tạo bảng cart
    private static final String CREATE_TABLE_CARTS = "CREATE TABLE " + TABLE_CARTS + "("
            + COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CART_USER_ID + " INTEGER,"
            + COLUMN_CART_PRODUCT_ID + " INTEGER,"
            + COLUMN_CART_QUANTITY + " INTEGER,"
            + COLUMN_CART_PRICE + " REAL,"
            + "FOREIGN KEY(" + COLUMN_CART_USER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + COLUMN_USER_ID + "), "
            + "FOREIGN KEY(" + COLUMN_CART_PRODUCT_ID + ") REFERENCES " + TABLE_PRODUCTS + "(" + COLUMN_PRODUCT_ID + ")"
            + ")";


    // Câu lệnh SQL để tạo bảng Bills
    private static final String CREATE_TABLE_BILLS = "CREATE TABLE " + TABLE_BILLS + "("
            + COLUMN_BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BILL_USER_ID + " INTEGER,"
            + COLUMN_BILL_PRODUCT_ID + " INTEGER,"
            + COLUMN_BILL_PRODUCT_QUATITY + " INTEGER,"
            + COLUMN_BILL_TOTAL_PRICE + " REAL,"
            + COLUMN_BILL_DESCRIPTION + " TEXT,"
            + COLUMN_BILL_DATE_CREATED + " DATETIME DEFAULT (datetime('now')),"
            + "FOREIGN KEY(" + COLUMN_BILL_USER_ID + ") REFERENCES " + TABLE_CUSTOMER + "(" + COLUMN_USER_ID + ")"
            + ")";


    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

        // Check if the database exists, if not, copy it from assets
        if (!checkDatabase()) {
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            // Database doesn't exist yet
        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    private void copyDatabaseFromAssets() throws IOException {
        InputStream myInput = null;
        OutputStream myOutput = null;

        try {
            // Open your local db as the input stream
            myInput = context.getAssets().open(DATABASE_NAME);

            // Path to the just created empty db
            String outFileName = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();

            // Open the empty db as the output stream
            myOutput = new FileOutputStream(outFileName);

            // Transfer bytes from the input file to the output file
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
        } finally {
            // Close the streams
            if (myOutput != null) {
                myOutput.flush();
                myOutput.close();
            }
            if (myInput != null) {
                myInput.close();
            }
        }
    }

    private static final String INSERT_ADMIN = "INSERT INTO admin (" + COLUMN_ADMIN_EMAIL + ", " + COLUMN_ADMIN_PASSWORD + ") VALUES ( 'admin', '270801')";




    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!checkDatabase()) {
            try {
                copyDatabaseFromAssets();
            } catch (IOException e) {
                e.printStackTrace();  // Handle the exception according to your application's requirements
            }
        }
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ADMIN);
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_BILLS);
        db.execSQL(CREATE_TABLE_CARTS);
        db.execSQL(INSERT_ADMIN);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTS);

        onCreate(db);
    }


}