package com.example.datn_tranvantruong.DBHandler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.datn_tranvantruong.Database.DBManager;
import com.example.datn_tranvantruong.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductHandler extends SQLiteOpenHelper {
    SQLiteDatabase db;

    private static final String DATABASE_NAME = "BOOK_TOURS";
    private static final int DATABASE_VERSION = 1;

    DBManager dbManager;
    private Context context;

    public ProductHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbManager = new DBManager(context);
        db = dbManager.getWritableDatabase();
    }

    public Cursor getAllProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products;", null);
        return cursor;
    }
    public void createProduct(int category_id, String name, String startdate, String enddate, String description, String location,
                              int price, byte[] image) {
        // Mở cơ sở dữ liệu để ghi
        SQLiteDatabase db = getWritableDatabase();
        // Bắt đầu một giao dịch để đảm bảo tính nhất quán của cơ sở dữ liệu
        db.beginTransaction();
        try {
            String sql = "INSERT INTO products (category_id, name, startdate, enddate, description, location, price, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            SQLiteStatement statement = db.compileStatement(sql);
            statement.clearBindings();

            // Gán giá trị cho các placeholder
            statement.bindLong(1, category_id);
            statement.bindString(2, name);
            statement.bindString(3, startdate);
            statement.bindString(4, enddate);
            statement.bindString(5, description);
            statement.bindString(6, location);
            statement.bindLong(7, price);
            statement.bindBlob(8, image);
            // Thực hiện câu lệnh chèn dữ liệu và lấy ra ID của dòng vừa chèn
            long rowId = statement.executeInsert();
            // Đánh dấu giao dịch thành công
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            // Xử lý ngoại lệ, ví dụ: Log lỗi hoặc thông báo cho người dùng
        } finally {
            // Kết thúc giao dịch và đảm bảo đóng cơ sở dữ liệu
            db.endTransaction();
            db.close();
        }
    }



    public void editProduct(int id, int category_id, String name, String startdate, String enddate, String description, String location,
                            int price, byte[] image) {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE products SET category_id = ?, name = ?, startdate = ?, enddate = ?, description = ?, location = ?, price = ?, image = ? WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1, category_id);
        statement.bindString(2, name);
        statement.bindString(3, startdate);  // Gán giá trị DATETIME cho startdate
        statement.bindString(4, enddate);    // Gán giá trị DATETIME cho enddate
        statement.bindString(5, description);
        statement.bindString(6, location);
        statement.bindLong(7, price);
        statement.bindBlob(8, image);
        statement.bindLong(9, id);
        statement.execute();
        db.close();
    }




    public void deleteProduct(int i) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete("products", "id" + " = " + i, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Product findById(int id) {
        String query = "SELECT * FROM products WHERE id = ?";
        Cursor c = dbManager.getReadableDatabase().rawQuery(query, new String[] {"" + id});

        if (c.moveToFirst()) {
            Product product = new Product();
            product.setId(id);
            product.setName(c.getString(2));
            product.setStartdate(c.getString(3));
            product.setEnddate(c.getString(4));
            product.setLocation(c.getString(6));
            product.setDescription(c.getString(5));
            product.setPrice(c.getInt(7));
            product.setImage(c.getBlob(8));

            return product;
        } else {
            // Xử lý trường hợp không tìm thấy dữ liệu với id đã cho.
            return null;
        }
    }


    public List<Product> getAllProductByCategoryId(int category_id) {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category_id = ?";
        Cursor c = dbManager.getReadableDatabase().rawQuery(query, new String[] {"" + category_id});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Product product = new Product();
            product.setId(c.getInt(0));
            product.setName(c.getString(2));
            product.setLocation(c.getString(6));
            product.setPrice((int) c.getFloat(7));
            product.setImage(c.getBlob(8));

            productList.add(product);
            c.moveToNext();
        }
        c.close();
        return productList;
    }
}
