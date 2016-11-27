package io.liamju.tangshi.data.source.local;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.liamju.tangshi.data.Poetry;
import io.liamju.tangshi.data.PoetryList;
import io.liamju.tangshi.data.source.PoetryDataSource;

/**
 * @author Zhu Liang
 */

public class PoetryAssetsDataSource implements PoetryDataSource {

    private Context mContext;

    private static PoetryAssetsDataSource sInstance;

    public static PoetryAssetsDataSource getInstance(@NonNull Context context) {
        if (sInstance == null) {
            sInstance = new PoetryAssetsDataSource(context);
        }
        return sInstance;
    }

    private PoetryAssetsDataSource(Context context) {
        mContext = context;
    }

    @Override
    public List<Poetry> getPoetryList() {
        try {
            InputStream in = mContext.getAssets().open("tangshi300.xml", AssetManager.ACCESS_STREAMING);
            Serializer serializer = new Persister();
            PoetryList poetryList = serializer.read(PoetryList.class, in);
            return poetryList.getList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void savePoetryList(List<Poetry> poetryList) {
        throw new UnsupportedOperationException();
    }
}
