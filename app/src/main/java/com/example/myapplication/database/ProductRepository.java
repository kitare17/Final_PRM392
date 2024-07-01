package com.example.myapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;


import com.example.myapplication.adapter.ProductTestAdapter;
import com.example.myapplication.model.Brand;
import com.example.myapplication.model.ProductTest;

import java.util.List;

public class ProductRepository extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "DeskDelights";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME_PRODUCT = "Product";
    public static final String TABLE_NAME_PRODUCT_IMAGE = "Product_Image";
    public static final String BRAND_TABLE_NAME = "Brand";

    // Columns for Product table
    public static final String COLUMN_ID = "product_id";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_DETAIL = "product_detail";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_BRAND_ID = "brand_id";

    // Columns for ProductImage table
    public static final String COLUMN_IMAGE_ID = "image_id";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String COLUMN_PRODUCT_ID = "product_id";

    // Columns for Brand table
    public static final String BRAND_COLUMN_ID = "brand_id";
    public static final String BRAND_COLUMN_IMAGE = "brand_img";
    public static final String BRAND_COLUMN_NAME = "brand_name";

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



    public List<ProductTest> getAllProduct() {
        List<ProductTest> listProduct = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT p." + COLUMN_ID + ", p." + COLUMN_NAME + ", p." + COLUMN_DETAIL +
                ", p." + COLUMN_PRICE + ", pi." + COLUMN_IMAGE_URL +
                " FROM " + TABLE_NAME_PRODUCT + " p" +
                " LEFT JOIN " + TABLE_NAME_PRODUCT_IMAGE + " pi" +
                " ON p." + COLUMN_ID + " = pi." + COLUMN_PRODUCT_ID,null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String detail = cursor.getString(2);
                double price = cursor.getDouble(3);
                String imageUrl = cursor.getString(4); // Fetch image URL

                ProductTest product = new ProductTest();
                product.setId(id);
                product.setName(name);
                product.setProductDetail(detail);
                product.setPrice(price);
                product.setImageUrl(imageUrl); // Set image URL

                listProduct.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }
    public List<ProductTest> searchProduct(String keyword) {
        List<ProductTest> listProduct = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT p." + COLUMN_ID + ", p." + COLUMN_NAME + ", p." + COLUMN_DETAIL +
                ", p." + COLUMN_PRICE + ", pi." + COLUMN_IMAGE_URL +
                " FROM " + TABLE_NAME_PRODUCT + " p" +
                " LEFT JOIN " + TABLE_NAME_PRODUCT_IMAGE + " pi" +
                " ON p." + COLUMN_ID + " = pi." + COLUMN_PRODUCT_ID+
                " WHERE p.product_name LIKE '%"+keyword+"%'",null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String detail = cursor.getString(2);
                double price = cursor.getDouble(3);
                String imageUrl = cursor.getString(4); // Fetch image URL

                ProductTest product = new ProductTest();
                product.setId(id);
                product.setName(name);
                product.setProductDetail(detail);
                product.setPrice(price);
                product.setImageUrl(imageUrl); // Set image URL

                listProduct.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listProduct;
    }

    public List<Brand> getAllBrands() {
        List<Brand> brandList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + BRAND_COLUMN_ID + ", " + BRAND_COLUMN_IMAGE + ", " + BRAND_COLUMN_NAME + " FROM " + BRAND_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String logoResId = cursor.getString(1);
                String name = cursor.getString(2);
                Brand brand = new Brand(id, logoResId, name);
                brandList.add(brand);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return brandList;
    }
    public ProductTest getProductById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT p." + COLUMN_ID + ", p." + COLUMN_NAME + ", p." + COLUMN_DETAIL +
                ", p." + COLUMN_PRICE + ", pi." + COLUMN_IMAGE_URL +
                " FROM " + TABLE_NAME_PRODUCT + " p" +
                " LEFT JOIN " + TABLE_NAME_PRODUCT_IMAGE + " pi" +
                " ON p." + COLUMN_ID + " = pi." + COLUMN_PRODUCT_ID +
                " WHERE p." + COLUMN_ID + " = ?", new String[]{id+""});
        ProductTest product=null;
        if (cursor.moveToFirst()) {
            do {

                String name = cursor.getString(1);
                String detail = cursor.getString(2);
                double price = cursor.getDouble(3);
                String imageUrl = cursor.getString(4); // Fetch image URL

                 product = new ProductTest();
                product.setId(id);
                product.setName(name);
                product.setProductDetail(detail);
                product.setPrice(price);
                product.setImageUrl(imageUrl); // Set image URL


            } while (cursor.moveToNext());
        }

        return product;
    }



    public static void main(String[] args) {


    }
}
