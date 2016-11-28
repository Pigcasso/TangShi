package io.liamju.tangshi.data;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.liamju.tangshi.data.source.PoetryDataSource;

/**
 * @author Zhu Liang
 */
public class PoetryRepository implements PoetryDataSource {

    private final List<Poetry> mCachedPoetryList = new ArrayList<>();

    private PoetryDataSource mAssertsPoetryDataSource;
    private PoetryDataSource mLocalPoetryDataSource;

    private static PoetryRepository sInstance;

    public static PoetryRepository getInstance(@NonNull PoetryDataSource assetsPoetryDataSource,
                                               @NonNull PoetryDataSource localPoetryDataSource) {
        if (sInstance == null) {
            synchronized (PoetryRepository.class) {
                if (sInstance == null) {
                    sInstance = new PoetryRepository(assetsPoetryDataSource, localPoetryDataSource);
                }
            }
        }
        return sInstance;
    }

    private PoetryRepository(@NonNull PoetryDataSource assetsPoetryDataSource,
                             @NonNull PoetryDataSource localPoetryDataSource) {
        mAssertsPoetryDataSource = assetsPoetryDataSource;
        mLocalPoetryDataSource = localPoetryDataSource;
    }

    @Override
    public List<Poetry> getPoetryList() {
        // 从内存中获取数据
        if (!mCachedPoetryList.isEmpty()) {
            return mCachedPoetryList;
        }

        // 从数据库中获取数据
        List<Poetry> poetryList = mLocalPoetryDataSource.getPoetryList();
        if (poetryList != null && !poetryList.isEmpty()) {
            mCachedPoetryList.addAll(new ArrayList<>(poetryList));
            return poetryList;
        }

        poetryList = mAssertsPoetryDataSource.getPoetryList();
        // 保存到数据库
        savePoetryList(poetryList);
        mCachedPoetryList.addAll(new ArrayList<>(poetryList));
        return poetryList;
    }

    @Override
    public void savePoetryList(List<Poetry> poetryList) {
        mLocalPoetryDataSource.savePoetryList(poetryList);
    }
}
