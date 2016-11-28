package io.liamju.tangshi.data.source.local;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.liamju.tangshi.data.Poetry;

/**
 * @author Zhu Liang
 */
@RunWith(AndroidJUnit4.class)
public class PoetryLocalDataSourceTest {
    private PoetryLocalDataSource mPoetryLocalDataSource;

    @Before
    public void setup() throws Exception {
        Context context = InstrumentationRegistry.getTargetContext();
        mPoetryLocalDataSource = PoetryLocalDataSource.getInstance(context);
    }

    @Test
    public void getPoetryList() throws Exception {
        List<Poetry> poetryList = mPoetryLocalDataSource.getPoetryList();
        Assert.assertNotNull(poetryList);
    }

    @Test
    public void savePoetryList() throws Exception {

        List<Poetry> poetryList = new ArrayList<>();
        poetryList.add(new Poetry("杂诗·君自故乡来", "王维", "五言绝句", "君自故乡来，应知故乡事。", "注释"));
        poetryList.add(new Poetry("杂诗·君自故乡来", "王维", "五言绝句", "君自故乡来，应知故乡事。", "注释"));
        poetryList.add(new Poetry("杂诗·君自故乡来", "王维", "五言绝句", "君自故乡来，应知故乡事。", "注释"));
        poetryList.add(new Poetry("杂诗·君自故乡来", "王维", "五言绝句", "君自故乡来，应知故乡事。", "注释"));

        mPoetryLocalDataSource.savePoetryList(poetryList);
    }

}