package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductCart;
import com.example.myapplication.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartRepository extends SQLiteOpenHelper {

    private Context context;

    public static final String DATABASE_NAME = "DeskDelights";

    public static final String TABLE_NAME = "USER_";
    public static final int DATABASE_VERSION = 1;


    public static final String COLUMN_ID = "user_id";
    public static final String COLUMN_NAME = "fullname";
    public static final String COLUMN_EMAIL = "email";

    public static final String COlUMN_AVATAR = "avatar";

    public static final String COlUMN_ROLE = "role";
    public static final String COlUMN_GOOGLE_ID = "google_id";


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
            int id = cursor.getInt(cursor.getColumnIndex("item_id"));
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
        db.execSQL("DELETE FROM CART WHERE item_id = "+id);
    }
    public void updateAmount(int id, int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE CART SET amount = "+amount+" WHERE item_id = "+id);
    }

    public static void main(String[] args) {


    }
}
