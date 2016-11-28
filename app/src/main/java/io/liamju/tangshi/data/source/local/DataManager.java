package io.liamju.tangshi.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Zhu Liang
 */
public class DataManager {
    private static final String DATABASE_NAME = "Poetry.db";

    public static final int READABLE = 0;
    public static final int WRITABLE = 1;

    private static DataManager sInstance;
    private DaoMaster.DevOpenHelper openHelper;

    public static DataManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DataManager.class) {
                if (sInstance == null) {
                    sInstance = new DataManager(context);
                }
            }
        }
        return sInstance;
    }

    private DataManager(Context context) {
        openHelper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME);
    }

    public PoetryDao getPoetryDao(int sqlType) {
        DaoMaster daoMaster = new DaoMaster(getDatabase(sqlType));
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getPoetryDao();
    }

    private SQLiteDatabase getDatabase(int sqlType) {
        if (sqlType == READABLE) {
            return getReadableDatabase();
        } else if (sqlType == WRITABLE) {
            return getWritableDatabase();
        } else {
            throw new IllegalStateException();
        }
    }

    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        return openHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        return openHelper.getWritableDatabase();
    }

}
