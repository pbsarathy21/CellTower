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
    public static final String COL_1 = "TYPE";
    public static final String COL_2 = "POWER";
    public static final String COL_3 = "DBM";
    public static final String COL_4 = "WATT";
    public static final String COL_5 = "CARRIER";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME,null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+" (TYPE TEXT, POWER TEXT, DBM TEXT, WATT TEXT, CARRIER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public boolean insertData(String type, String power, String dbm, String watt, String carrier)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, type);
        contentValues.put(COL_2, power);
        contentValues.put(COL_3, dbm);
        contentValues.put(COL_4, watt);
        contentValues.put(COL_5, carrier);

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

}
