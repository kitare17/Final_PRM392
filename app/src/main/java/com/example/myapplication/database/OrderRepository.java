package com.example.myapplication.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.Order;
import com.example.myapplication.model.ProductCart;

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


    public List<Order> getOrdersForUser(int userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT o.order_id, o.create_date, o.address, o.phone " +
                "FROM ORDER_ o " +
                "WHERE o.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex("order_id"));
                @SuppressLint("Range") String createDate = cursor.getString(cursor.getColumnIndex("create_date"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

                Order order = new Order(orderId, userId, createDate, address, phone);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    public List<ProductCart> getOrderDetails(int orderId) {
        List<ProductCart> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT p.product_id, p.product_name, od.type, pi.image_url, od.amount, od.price_at_purchase " +
                "FROM ORDER_DETAIL od " +
                "JOIN PRODUCT p ON p.product_id = od.product_id " +
                "JOIN PRODUCT_IMAGE pi ON p.product_id = pi.product_id " +
                "WHERE od.order_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int productId = cursor.getInt(cursor.getColumnIndex("product_id"));
                @SuppressLint("Range") String productName = cursor.getString(cursor.getColumnIndex("product_name"));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex("type"));
                @SuppressLint("Range") String imageUrl = cursor.getString(cursor.getColumnIndex("image_url"));
                @SuppressLint("Range") int amount = cursor.getInt(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price_at_purchase"));

                ProductCart product = new ProductCart(productId, productName, type, price, amount, imageUrl);
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public Order getOrderById(int orderId) {
        SQLiteDatabase db = getReadableDatabase();
        Order order = null;

        String query = "SELECT o.order_id, o.user_id, o.create_date, o.address, o.phone " +
                "FROM ORDER_ o " +
                "WHERE o.order_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
            @SuppressLint("Range") String createDate = cursor.getString(cursor.getColumnIndex("create_date"));
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

            order = new Order(orderId, userId, createDate, address, phone);
        }

        cursor.close();
        return order;
    }
}
