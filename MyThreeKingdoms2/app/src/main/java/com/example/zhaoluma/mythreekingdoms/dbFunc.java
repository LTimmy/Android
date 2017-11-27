package com.example.zhaoluma.mythreekingdoms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by zhaoluma on 2017/11/25.
 */

public class dbFunc {
    dbFunc(Context context) {
        //File dirFile = new File("/data/data/com.example.zhaoluma.mythreekingdoms/databases/main");
        //dirFile.delete();

        //SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase("main.db",null);
        //SQLiteDatabase db=SQLiteDatabase.openOrCreateDatabase(Environment.getExternalStorageDirectory().getPath()+"/main.db",null);
        SQLiteDatabase db = context.openOrCreateDatabase("main", 0, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS characters(id integer primary key autoincrement, name text, detail text, power text, image BLOB)");
        Log.e("lallaa", db.toString());
    }

    //图片一定是jpg格式的！！！！！
    private byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();
        return bitmapByte;
    }

    public void addPerson(Context context, Person person) {
        byte[] temp = bitmapToByte(person.getAvator());
        //Log.e("temp1", temp1.toString());

        //byte[] temp = new byte[2];
        //temp[0] = 1;
        //temp[1] = 2;

        //dbHelper helper = new dbHelper(context, "main");
        //SQLiteDatabase database = helper.getWritableDatabase();

        SQLiteDatabase database = context.openOrCreateDatabase("main", 0, null);

        ContentValues value = new ContentValues();
        value.put("name", person.getName());
        value.put("detail", person.getDetail());
        value.put("power", person.getPower());
        value.put("image", temp);
        database.insert("characters", null, value);
        database.close();
    }

    public void deletePerson(Context context, String name) {
        SQLiteDatabase database = context.openOrCreateDatabase("main", 0, null);
        String whereClause = "name=?";
        String[] whereArgs = {name};
        database.delete("characters",whereClause,whereArgs);
        database.close();
    }

    public Person QueryByName(Context context, String name) {
        SQLiteDatabase database = context.openOrCreateDatabase("main", 0, null);
        Cursor cursor = database.rawQuery("select * from characters where name=?",
                new String[]{name});
        Log.e("cur", cursor.toString());
        if (!cursor.moveToFirst()) return null;
        String detail = cursor.getString(cursor.getColumnIndex("detail"));
        String power = cursor.getString(cursor.getColumnIndex("power"));
        byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        Person person = new Person(bitmap,name, detail, power);
        cursor.close();
        database.close();
        return person;

    }

    public ArrayList<Person> getAllCharacters(Context context) {
        SQLiteDatabase database = context.openOrCreateDatabase("main", 0, null);
        Cursor cursor = database.rawQuery("select * from characters", null);
        ArrayList<Person> all = new ArrayList();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String detail = cursor.getString(cursor.getColumnIndex("detail"));
                    String power = cursor.getString(cursor.getColumnIndex("power"));
                    byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                    Person person = new Person(bitmap,name, detail, power);
                    all.add(person);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return all;
    }


    //for test
/*
    public void show(Context context) {
        SQLiteDatabase database = context.openOrCreateDatabase("main", 0, null);
        Cursor cursor = database.rawQuery("select * from characters", null);
        Log.e("rows", "" + cursor.getCount());

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    String nameString = cursor.getString(cursor.getColumnIndex("name"));
                    String phoneString = cursor.getString(cursor.getColumnIndex("detail"));
                    String emailString = cursor.getString(cursor.getColumnIndex("power"));
                    Log.e("contact", "id is " + id);
                    Log.e("contact", "name is " + nameString);
                    Log.e("contact", "detail is " + phoneString);
                    Log.e("contact", " is " + emailString);
                    Log.e("contact", "byte " + cursor.getBlob(cursor.getColumnIndex("image")));
                } while (cursor.moveToNext());
            }
        }
    }*/
}
