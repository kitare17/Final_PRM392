package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.myapplication.model.ProductTest;
import com.example.myapplication.model.Rating;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReviewRepository extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "DeskDelights";
    public static final int DATABASE_VERSION = 1;


    public ReviewRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void createReview(int productId,int userId,int star, String detail) {
        SQLiteDatabase db = this.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("product_id", productId);
            values.put("user_id", userId);
            values.put("add_date", LocalDateTime.now().toString());
            values.put("star", star);
            values.put("detail", detail);

            long result = db.insert("RATING", null, values);
            if (result == -1) {
                Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Thanks you", Toast.LENGTH_SHORT).show();
            }

    }

    public List<Rating> getRatingList(int productId) {
        List<Rating> listRating = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RATING r\n" +
                "JOIN PRODUCT p on r.product_id=p.product_id\n" +
                "JOIN USER_ u on u.user_id=r.user_id\n" +
                "WHERE r.product_id=" + productId + "\n" +
                "ORDER BY r.add_date DESC", null);

        if (cursor.moveToFirst()) {
            do {
                String fullname = String.valueOf(cursor.getString(cursor.getColumnIndex("fullname")));
                Double rating = (double) cursor.getDouble(cursor.getColumnIndex("star"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                // Parse the string into a LocalDateTime object
                LocalDateTime ratingDate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex("add_date")));
                String detail = String.valueOf(cursor.getString(cursor.getColumnIndex("detail")));
                String image = String.valueOf(cursor.getString(cursor.getColumnIndex("avatar")));
                Rating newRating = new Rating(fullname, rating, ratingDate, detail, image);
                System.out.println("get list comment" + newRating);


                listRating.add(newRating);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listRating;
    }


    public Rating getFirstRating(int productId) {
        Rating ratingTop = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RATING r\n" +
                        "JOIN PRODUCT p on r.product_id=p.product_id\n" +
                        "JOIN USER_ u on u.user_id=r.user_id\n" +
                        "WHERE r.product_id=" + productId + "\n" +
                        "ORDER BY r.add_date DESC\n" +
                        "LIMIT 1"
                , null);

        if (cursor.moveToFirst()) {

            String fullname = String.valueOf(cursor.getString(cursor.getColumnIndex("fullname")));
            Double rating = (double) cursor.getDouble(cursor.getColumnIndex("star"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Parse the string into a LocalDateTime object
            LocalDateTime ratingDate = LocalDateTime.parse(cursor.getString(cursor.getColumnIndex("add_date")));
            String detail = String.valueOf(cursor.getString(cursor.getColumnIndex("detail")));
            String image = String.valueOf(cursor.getString(cursor.getColumnIndex("avatar")));
             ratingTop = new Rating(fullname, rating, ratingDate, detail, image);
            System.out.println("get list comment" + ratingTop);




        }
        cursor.close();
        return ratingTop;
    }
}
