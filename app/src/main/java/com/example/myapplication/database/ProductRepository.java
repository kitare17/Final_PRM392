package com.example.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;



import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "DeskDelights";

    public static final String TABLE_NAME = "Product";
    public static final int DATABASE_VERSION = 1;


    public static final String COLUMN_ID = "product_id";
    public static final String COLUMN_NAME = "product_name";

    public static final String COlUMN_DETAIL = "product_detail";
    public static final String COlUMN_PRICE = "price";
    public static final String COlUMN_CATEGORY_ID = "category_id";
    public static final String COlUMN_BRAND_ID = "brand_id";

    public ProductRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    public void deleteAll() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL("delete from " + TABLE_NAME);
//    }

//    public void insertShop(String name, int image) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, name);
//        values.put(COlUMN_IMAGE, image);
//        long result = db.insert(TABLE_NAME, null, values);
//        if (result == -1) {
//            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
//        }
//    }

    public List<Product> getAllProduct() {
        List<Product> listProduct = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("ok");
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COlUMN_DETAIL + ", " + COlUMN_PRICE + " FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String detail = cursor.getString(2);
                double price = cursor.getDouble(3);
                Product product = new Product();

                product.setId(id);
                product.setName(name);
                product.setProductDetail(detail);
                product.setPrice(price);
                System.out.println(product);
                listProduct.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }

    public static void main(String[] args) {


    }
}
