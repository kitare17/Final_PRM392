package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.myapplication.model.Address;
import com.example.myapplication.model.Product;
import com.example.myapplication.model.ProductCart;
import com.example.myapplication.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddressRepository extends SQLiteOpenHelper {

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


    public AddressRepository(Context context) {
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


    public List<Address> listAllAddress(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("zo ne");
        String MY_QUERY = "select * from ADDRESS_ a WHERE user_id = ?";

        Cursor cursor = db.rawQuery(MY_QUERY, new String[]{String.valueOf(userId)});
        List<Address> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("address_id"));
            int userIdSelected = cursor.getInt(cursor.getColumnIndex("user_id"));
            String addressSelected = cursor.getString(cursor.getColumnIndex("address"));
            String phone = cursor.getString(cursor.getColumnIndex("phone"));

            Address address = new Address(id, userIdSelected, addressSelected, phone);
            list.add(address);
            System.out.println(address.toString());
        }
        return list;
    }

    public void addAddress(Address address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put("user_id", address.getUserId());
        values.put("address", address.getAddress());
        values.put("phone", address.getPhone());


        long result = db.insert("ADDRESS_", null, values);
        if (result == -1) {
            Toast.makeText(context, "Add failed!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add successfully!!!", Toast.LENGTH_SHORT).show();
        }
    }

    public static void main(String[] args) {


    }
}
