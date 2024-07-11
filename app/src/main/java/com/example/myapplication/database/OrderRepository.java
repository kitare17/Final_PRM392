package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "DeskDelights";
    public static final int DATABASE_VERSION = 1;

    public OrderRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase(); // Assuming getReadableDatabase() is defined to get a readable SQLiteDatabase instance

        String query = "SELECT p.product_name" +
                        ", pi.image_url" +
                        ", od.amount" +
                        ", od.price_at_purchase " +
                        ", o.create_date FROM ORDER_DETAIL od" +
                        " JOIN PRODUCT p on p.product_id = od.product_id" +
                        " JOIN PRODUCT_IMAGE pi on p.product_id = pi.product_id" +
                        " JOIN ORDER_ o on o.order_id = od.order_id"; // Adjust column names and table name as needed
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("product_name"));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex("image_url"));
                @SuppressLint("Range") int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") double purchase = cursor.getDouble(cursor.getColumnIndex("price_at_purchase"));
                @SuppressLint("Range") String createDate = cursor.getString(cursor.getColumnIndex("create_date"));

                Order order = new Order(productName, imageUrl, amount, purchase, createDate);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        return orders;
    }

}
