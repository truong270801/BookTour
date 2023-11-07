package com.example.datn_tranvantruong.DBHandler;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    public void createProduct(int category_id, String name,String description, String location,
                            int price, byte[] image) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Insert into products (category_id, name, quantity, price, image) values (?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1, category_id);
        statement.bindString(2, name);
        statement.bindString(3, description);
        statement.bindString(4, location);
        statement.bindLong(5, price);
        statement.bindBlob(6, image);
        statement.executeInsert();
        db.close();
    }

    public void editProduct(int id, int category_id, String name, int quantity,
                            int price, byte[] image) {

        SQLiteDatabase db = getWritableDatabase();
        String sql = "UPDATE products SET category_id = ? , name = ? , quantity = ?, price = ?, image = ? Where id = ? ";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1, category_id);
        statement.bindString(2, name);
        statement.bindLong(3, quantity);
        statement.bindLong(4, price);
        statement.bindBlob(5, image);
        statement.bindLong(6, id);
        statement.execute();
        db.close();
    }

    public void editQuantity(int product_id, int quantity) {
        String query = "UPDATE products SET quantity = quantity - ? WHERE id = ?";
        SQLiteStatement statement = db.compileStatement(query);
        statement.clearBindings();
        statement.bindLong(1, quantity);
        statement.bindLong(2, product_id);
        statement.execute();
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
        c.moveToFirst();

        Product product = new Product();
        product.setId(id);
        product.setName(c.getString(2));
        product.setDescription(c.getString(3));
        product.setLocation(c.getString(4));
        product.setPrice(c.getInt(5));
        product.setImage(c.getBlob(6));

        return product;
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
            product.setPrice((int) c.getFloat(5));
            product.setImage(c.getBlob(6));

            productList.add(product);
            c.moveToNext();
        }
        c.close();
        return productList;
    }
}
