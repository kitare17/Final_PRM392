package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.Order;
import com.example.myapplication.model.OrderDetail;

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

    public List<Order> getOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase(); // Assuming getReadableDatabase() is defined to get a readable SQLiteDatabase instance

        String query = "SELECT * FROM ORDER_ WHERE user_id = " + userId;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {


                @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("order_id"));
                @SuppressLint("Range") String createDate = cursor.getString(cursor.getColumnIndex("create_date"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

                Order order = new Order(orderId, userId, address, phone, createDate);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        return orders;
    }

    public List<OrderDetail> getOrderDetail(int orderId) {
        List<OrderDetail> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase(); // Assuming getReadableDatabase() is defined to get a readable SQLiteDatabase instance

        String query = "SELECT * FROM ORDER_DETAIL d\n" +
                "JOIN PRODUCT p ON d.product_id=p.product_id\n" +
                "JOIN PRODUCT_IMAGE i ON i.product_id= p.product_id\n" +
                "WHERE d.order_id=" + orderId; // Adjust column names and table name as needed
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("product_name"));
                @SuppressLint("Range") int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex("image_url"));
                @SuppressLint("Range") double purchase = cursor.getDouble(cursor.getColumnIndex("price_at_purchase"));

                OrderDetail order = new OrderDetail(productName, amount, purchase, imageUrl);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        return orders;
    }
}
