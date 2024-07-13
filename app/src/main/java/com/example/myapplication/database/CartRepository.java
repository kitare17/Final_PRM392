package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductCart;
import com.example.myapplication.model.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CartRepository extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "DeskDelights";

    public static final String TABLE_NAME = "CART";
    public static final int DATABASE_VERSION = 1;


    public CartRepository(Context context) {
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


    public List<ProductCart> listAll(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("zo ne");
        boolean checkExist = false;
        String MY_QUERY = "SELECT * FROM CART c INNER JOIN USER_ u ON c.user_id=u.user_id INNER JOIN PRODUCT p ON p.product_id = c.product_id INNER JOIN PRODUCT_IMAGE i ON p.product_id = i.product_id WHERE c.user_id=?";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{String.valueOf(userId)});
        List<ProductCart> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("product_id"));
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));
            String productName = cursor.getString(cursor.getColumnIndex("product_name"));
            double price = cursor.getDouble(cursor.getColumnIndex("price"));
            String type = "";
            String imageUrl = cursor.getString(cursor.getColumnIndex("image_url"));

            ProductCart productCart = new ProductCart(id, productName, type, price, amount, imageUrl);
            list.add(productCart);
            System.out.println(productCart.toString());
        }
        return list;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM CART WHERE item_id = " + id);
    }

    public void updateAmount(int id, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE CART SET amount = " + amount + " WHERE item_id = " + id);
    }

    public boolean checkExistProduct(String productId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String MY_QUERY = "SELECT * FROM CART c  WHERE c.user_id=? AND c.product_id=?";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{String.valueOf(userId), productId});


        while (cursor.moveToNext()) {
            return true;
        }

        return false;
    }

    public void addToCart(String productId, int amount, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();

        System.out.println("zo ne cart=>"+productId);
        if (checkExistProduct(productId, userId)) {
            Toast.makeText(context, "This product already in cart", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put("product_id", productId);
            values.put("amount", amount);
            values.put("user_id", userId);
            long result = db.insert(TABLE_NAME, null, values);
            if (result == -1) {
                Toast.makeText(context, "Add fail to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Add to card successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteCart(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM CART WHERE user_id = " + userId);
    }

    public void makeOrder(int userId, String address, String phone, List<ProductCart> listItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Random random = new Random();
        int orderId = random.nextInt(999999999);
        values.put("order_id", orderId);
        values.put("user_id", userId);
        values.put("create_date", LocalDateTime.now().toString());
        values.put("address", address);
        values.put("phone", phone);

        makeOrderDetail(orderId, listItem);
        deleteCart(userId);


        long result = db.insert("ORDER_", null, values);
        if (result == -1) {
            Toast.makeText(context, "Create order fail", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Create order successfully", Toast.LENGTH_SHORT).show();
        }


    }

    private void makeOrderDetail(int orderId, List<ProductCart> listItem) {
        SQLiteDatabase db = this.getWritableDatabase();



        for (ProductCart data : listItem) {
            System.out.println("sp them =====>"+data.getName() +data.getId());
            ContentValues values = new ContentValues();
            values.put("order_id", orderId);
            values.put("product_id", data.getId());
            values.put("amount", data.getAmount());
            values.put("price_at_purchase", data.getPrice());
            db.insert("ORDER_DETAIL", null, values);

        }


    }

}
