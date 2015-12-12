package io.liamju.tangshi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import io.liamju.tangshi.entity.Footprint;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/5
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "poetry.db";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE = "create table if not exists footprint_tb(" +
            "id integer primary key autoincrement," +
            "title string not null," +
            "created_time long not null," +
            "type integer default 0);";


    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
