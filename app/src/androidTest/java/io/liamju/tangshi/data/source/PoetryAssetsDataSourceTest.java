package io.liamju.tangshi.data.source;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.liamju.tangshi.data.Poetry;
import io.liamju.tangshi.data.source.local.PoetryAssetsDataSource;

/**
 * @author Zhu Liang
 */
@RunWith(AndroidJUnit4.class)
public class PoetryAssetsDataSourceTest {

    private PoetryDataSource mPoetryDataSource;

    @Before
    public void setUp() throws Exception {
        mPoetryDataSource = PoetryAssetsDataSource.getInstance(
                InstrumentationRegistry.getTargetContext());
    }

    @Test
    public void getPoetryList() throws Exception {
        List<Poetry> poetryList = mPoetryDataSource.getPoetryList();
        Assert.assertNotNull(poetryList);
    }

}