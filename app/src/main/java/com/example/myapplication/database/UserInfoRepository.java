package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.myapplication.model.Product;
import com.example.myapplication.model.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserInfoRepository extends SQLiteOpenHelper {

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


    public UserInfoRepository(Context context) {
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

    public void register(UserInfo userInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(COLUMN_NAME, userInfo.getFullname());
        values.put(COLUMN_EMAIL, userInfo.getEmail());
        values.put(COlUMN_AVATAR, userInfo.getAvatar());
        values.put(COlUMN_ROLE, userInfo.getRole());
        values.put(COlUMN_GOOGLE_ID, userInfo.getGoogleId());


        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Toast.makeText(context, "Failed to register!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Welcome new member", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkExistIdGoogle(String googleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("zo ne");
        boolean checkExist = false;
//        String query = "SELECT "+COlUMN_GOOGLE_ID+" FROM " + TABLE_NAME;
        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COlUMN_GOOGLE_ID},
                COlUMN_GOOGLE_ID + "=?",
                new String[]{String.valueOf(googleId)},
                null,
                null,
                null,
                null);
        if (cursor.moveToNext()) {
            checkExist = true;
        }
        return checkExist;
    }

    public UserInfo getUserByIdGoogle(String googleId) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("zo ne");
        boolean checkExist = false;

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COlUMN_AVATAR, COlUMN_ROLE, COlUMN_GOOGLE_ID},
                COlUMN_GOOGLE_ID + "=?",
                new String[]{String.valueOf(googleId)},
                null,
                null,
                null,
                null);
        if (cursor.moveToNext()) {
            String userId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String fullname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));

            String avatar = cursor.getString(cursor.getColumnIndex(COlUMN_AVATAR));

            int role = cursor.getInt(cursor.getColumnIndex(COlUMN_ROLE));
//            String googleId = cursor.getString(cursor.getColumnIndex(COlUMN_GOOGLE_ID));
            UserInfo userInfo = new UserInfo(userId, fullname, email, avatar, role, googleId);
            return userInfo;

        }
        return null;
    }
    public boolean updateProfile(String googleId,String fullname) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("zo ne");
        boolean checkExist = false;

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,fullname);
        db.update(TABLE_NAME, cv, COlUMN_GOOGLE_ID+" = ?", new String[]{googleId});

        return true;
    }


    public UserInfo getUserByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("zo ne");
        boolean checkExist = false;

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_EMAIL, COlUMN_AVATAR, COlUMN_ROLE, COlUMN_GOOGLE_ID},
                COLUMN_NAME + "=?",
                new String[]{String.valueOf(name)},
                null,
                null,
                null,
                null);
        if (cursor.moveToNext()) {
            String userId = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String fullname = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));

            String avatar = cursor.getString(cursor.getColumnIndex(COlUMN_AVATAR));

            int role = cursor.getInt(cursor.getColumnIndex(COlUMN_ROLE));
            String googleId = cursor.getString(cursor.getColumnIndex(COlUMN_GOOGLE_ID));
            UserInfo userInfo = new UserInfo(userId, fullname, email, avatar, role, googleId);
            return userInfo;

        }
        return null;
    }


    public static void main(String[] args) {


    }
}
