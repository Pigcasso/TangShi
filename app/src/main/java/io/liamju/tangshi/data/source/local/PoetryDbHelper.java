package io.liamju.tangshi.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Zhu Liang
 */

public class PoetryDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Poetry.db";
    private static final int DATABASE_VERSION = 1;

    public PoetryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
