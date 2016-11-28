package io.liamju.tangshi.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.liamju.tangshi.data.Poetry;
import io.liamju.tangshi.data.source.PoetryDataSource;

/**
 * @author Zhu Liang
 */

public class PoetryLocalDataSource implements PoetryDataSource {

    private static PoetryLocalDataSource sInstance;
    private DataManager mDataManager;

    public static PoetryLocalDataSource getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new PoetryLocalDataSource(context);
        }
        return sInstance;
    }

    private PoetryLocalDataSource(Context context) {
        mDataManager = DataManager.getInstance(context);
    }

    @Override
    public List<Poetry> getPoetryList() {
        PoetryDao poetryDao = mDataManager.getPoetryDao(DataManager.READABLE);
        QueryBuilder<Poetry> qb = poetryDao.queryBuilder();
        return qb.list();
    }

    @Override
    public void savePoetryList(List<Poetry> poetryList) {
        PoetryDao poetryDao = mDataManager.getPoetryDao(DataManager.WRITABLE);
        poetryDao.insertInTx(poetryList);
    }
}
