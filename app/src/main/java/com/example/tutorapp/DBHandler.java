package com.example.tutorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public static final String dbName = "Register.db";
    public DBHandler(@Nullable Context context){
        super(context, "Register.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table allusers(email TEXT primary key, password TEXT, Usertype TEXT, name TEXT, rating FLOAT)");
        db.execSQL("create Table allclasses(id INTEGER primary key, subject TEXT, tutor TEXT, student TEXT, price INTEGER, time TEXT, date TEXT, details TEXT, address TEXT, " +
                "foreign key(tutor) references allusers(id)," +
                "foreign key(student) references allusers(id))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists allusers");
        db.execSQL("drop Table if exists allclasses");
    }

    public Boolean insertData(String name, String email, String password, String Usertype, Float rating ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("Usertype",Usertype);
        contentValues.put("rating",rating);
        long res = db.insert("allusers", null, contentValues);

        if (res == -1){
            return false;
        } else{
            return true;
        }
    }

    public boolean insertClass(String subject, String tutorEmail, String studentEmail, float price, String time, String date,
                               String details, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("subject", subject);
        contentValues.put("tutor", tutorEmail);
        contentValues.put("student", studentEmail);
        contentValues.put("price", price);
        contentValues.put("time", time);
        contentValues.put("date", date);
        contentValues.put("details", details);
        contentValues.put("address", address);

        long result = db.insert("allclasses", null, contentValues);

        if (result == -1){
            return false;
        } else{
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public Boolean checkEmailPassword(String email, String password ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from allusers where email = ? and password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close(); // Close cursor to free resources
        return exists;
    }

    public String checkUserType(String email, String password ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select Usertype from allusers where email = ? and password = ?", new String[]{email, password});
        String user="";
        if (cursor.moveToFirst()) {
            user = cursor.getString(0);
        }
        cursor.close(); // Close cursor to free resources
        return user;
    }

    public Cursor getDataForClasses() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT tutor, subject, price FROM allclasses ", null);
        return cursor;
    }

    public Cursor getData(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT subject, price, time, date, details, id FROM allclasses Where tutor = ?", new String[]{email});
        return cursor;
    }

    public Cursor getData1() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select email,name,rating from allusers Where Usertype = ?", new String[]{"Student"});
        return cursor;
    }

    public boolean updateRating(String email, float rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("rating", rating);

        Log.d("DBHandler", "Updating rating for email: " + email + " with rating: " + rating);

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM allusers WHERE email=?", new String[]{email});
            if (cursor.getCount() == 0) {
                Log.e("DBHandler", "No user found with email: " + email);
                cursor.close();
                return false;
            }

            cursor.close();

            int result = db.update("allusers", contentValues, "email=?", new String[]{email});
            Log.d("DBHandler", "Update result: " + result);
            return result > 0;
        } catch (Exception e) {
            Log.e("DBHandler", "Error updating rating: " + e.getMessage());
            return false;
        }
    }

    public Cursor getClassById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM allclasses WHERE id = ?", new String[]{String.valueOf(id)});
    }


}
