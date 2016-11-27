package io.liamju.tangshi.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import io.liamju.tangshi.data.Poetry;
import io.liamju.tangshi.data.source.PoetryDataSource;

/**
 * @author Zhu Liang
 */

public class PoetryLocalDataSource implements PoetryDataSource {

    private static PoetryLocalDataSource sInstance;


    public static PoetryLocalDataSource getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new PoetryLocalDataSource();
        }
        return sInstance;
    }

    private PoetryLocalDataSource() {

    }

    @Override
    public List<Poetry> getPoetryList() {
        return null;
    }

    @Override
    public void savePoetryList(List<Poetry> poetryList) {

    }
}
