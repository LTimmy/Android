package com.example.zhaoluma.birthday_remind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by zhaoluma on 2017/12/7.
 */

public class myDB extends SQLiteOpenHelper{
    private static final String DB_NAME = "Contacts.db";
    private static final String TABLE_NAME = "Contacts";
    private static final int DB_VERSION = 1;
    public  myDB(Context context) {

        //第三个参数CursorFactory指定在执行查询时获得一个游标实例的工厂类,设置为null,代表使用系统默认的工厂类
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "create table " + TABLE_NAME
                + " (_id integer primary key autoincrement, "
                + "name text , "
                + "birthday text , "
                + "gift text);";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(member m) {
        if(!Query(m.getName())) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", m.getName());
            values.put("birthday", m.getBirthday());
            values.put("gift", m.getGift());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }
    }

    public void update(String n, String b, String g) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name = ?"; //主键列名，这里是根据名字修改
        String[] whereArgs = {n}; // 主键的值
        ContentValues values = new ContentValues();
        values.put("name", n);
        values.put("birthday", b);
        values.put("gift", g);
        db.update(TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    public void delete(String n) {
        SQLiteDatabase db = getReadableDatabase();
        String whereClause = "name = ?"; //主键列名，这里是根据名字删除
        String[] whereArgs = {n}; // 主键的值
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public boolean Query(String n) {   // 查询操作，用于判断姓名是否重复
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME + " where name=?",
                new String[]{n});
        if (!cursor.moveToFirst()) return false;
        else {
            cursor.close();
            db.close();
            return true;
        }
    }

    public ArrayList<member> getAllmember() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        ArrayList<member> all = new ArrayList();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String n = cursor.getString(cursor.getColumnIndex("name"));
                    String b = cursor.getString(cursor.getColumnIndex("birthday"));
                    String g = cursor.getString(cursor.getColumnIndex("gift"));
                    member m = new member(n, b, g);
                    all.add(m);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return all;
    }
}
