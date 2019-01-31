package com.ninositsolution.celltower;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Parthasarathy D on 1/31/2019.
 * Ninos IT Solution Pvt Ltd
 * ben@ninositsolution.com
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "towers.db";
    public static final String TABLE_NAME = "towers";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "CL";
    public static final String COL_3 = "LAC";
    public static final String COL_4 = "RSSI";
    public static final String COL_5 = "PSC";
    public static final String COL_6 = "NETWORK_TYPE";
    public static final String COL_7 = "DBM";


    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CL TEXT, LAC TEXT, RSSI TEXT, PSC TEXT, NETWORK_TYPE TEXT, DBM TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String cl, String lac, String rssi, String psc, String network_type, String dbm)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, cl);
        contentValues.put(COL_4, rssi);
        contentValues.put(COL_3, lac);
        contentValues.put(COL_6, network_type);
        contentValues.put(COL_7, dbm);
        contentValues.put(COL_5, psc);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.rawQuery("select * from "+TABLE_NAME,null);
    }

    public void deleteAll()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from "+TABLE_NAME);
        sqLiteDatabase.close();
    }

    /*public boolean updateData(String id, String name, String username, String email, String password, String dob, String gender, String mobile)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, name);
        contentValues.put(COL_4, username);
        contentValues.put(COL_3, email);
        contentValues.put(COL_8, password);
        contentValues.put(COL_6, dob);
        contentValues.put(COL_7, gender);
        contentValues.put(COL_5, mobile);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id});

        return true;
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public boolean checkData(String value, String column)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[] {column},
                column + " LIKE ?", new String[] {"%" + value + "%"},
                null, null, null);

        return cursor.getCount() > 0;
    }

    public boolean checkPassword(String username, String password)
    {
        Cursor cursor = null;
        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            *//*cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "
                    +username+"=? AND "+password+"=?", null);*//*
            cursor = sqLiteDatabase.query(TABLE_NAME, null, "USERNAME=? and PASSWORD=?",
                    new String[] {username,password}, null, null, null);


        } catch (Exception e) {

        }

        return cursor.getCount() > 0;
    }*/
}
