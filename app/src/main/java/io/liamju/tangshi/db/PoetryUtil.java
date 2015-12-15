package io.liamju.tangshi.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.text.TextUtils;

import com.github.promeg.pinyinhelper.Pinyin;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.liamju.tangshi.entity.Footprint;
import io.liamju.tangshi.entity.Poetry;

/**
 * @author LiamJu
 * @version 1.0
 * @since 15/12/1
 */
public class PoetryUtil {

    private static PoetryUtil mInstance;
    private List<Poetry> mPoetryList;
    private Map<Character, Integer> mSections;
    private DbOpenHelper mDbOpenHelper;
    private static final Object object = new Object();

    private PoetryUtil(Context context) {
        initPoetryList(context);
        mDbOpenHelper = new DbOpenHelper(context);
    }

    public static PoetryUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PoetryUtil(context);
        }
        return mInstance;
    }

    private void initPoetryList(Context context) {
        if (mPoetryList != null) {
            return;
        }
        AssetManager assetManager = context.getApplicationContext().getAssets();
        try {
            InputStream is = assetManager.open("tangshi300.xml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(is, "UTF-8");
            int eventType = xpp.getEventType();

            Poetry poetry = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        mPoetryList = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        if (xpp.getName().equals("node")) {
                            poetry = new Poetry();
                        } else if (xpp.getName().equals("title")) {
                            xpp.next();
                            String title = xpp.getText();
                            poetry.setTitle(title);
                            poetry.setSort(Pinyin.toPinyin(title.charAt(0)).charAt(0));
                        } else if (xpp.getName().equals("auth")) {
                            xpp.next();
                            poetry.setAuth(xpp.getText());
                        } else if (xpp.getName().equals("type")) {
                            xpp.next();
                            poetry.setType(xpp.getText());
                        } else if (xpp.getName().equals("content")) {
                            xpp.next();
                            poetry.setContent(xpp.getText());
                        } else if (xpp.getName().equals("desc")) {
                            xpp.next();
                            poetry.setDesc(xpp.getText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xpp.getName().equals("node")) {
                            mPoetryList.add(poetry);
                            poetry = null;
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } finally {
            Collections.sort(mPoetryList, new PoetryComparator());
        }
    }


    public List<Poetry> getAllPoetry() {
        return mPoetryList;
    }

    public int getPosition(char sort) {
        if (mSections == null) {
            initSections();
        }
        return mSections.get(sort);
    }

    private void initSections() {
        mSections = new HashMap<>();
        final int length = mPoetryList.size();
        for (int i = 0; i < length; i++) {
            Character sort = mPoetryList.get(i).getSort();
            if (!mSections.containsKey(sort)) {
                mSections.put(sort, i);
            }
        }
    }

    public String[] getSections() {
        if (mSections == null) {
            initSections();
        }
        Object[] chars = mSections.keySet().toArray();
        List<String> sections = new ArrayList<>();
        for (Object obj : chars) {
            sections.add(obj.toString());
        }
        Collections.sort(sections, new StringComparator());
        String[] array = new String[sections.size()];
        System.arraycopy(sections.toArray(), 0, array, 0, sections.size());
        return array;
    }

    public String[] getAuthors() {
        HashSet<String> titles = new HashSet<>();
        for (Poetry poetry : mPoetryList) {
            titles.add(poetry.getAuth());
        }
        String[] array = new String[titles.size()];
        System.arraycopy(titles.toArray(), 0, array, 0, titles.size());
        Arrays.sort(array, new StringComparator());
        return array;
    }

    public String[] getTypes() {
        HashSet<String> titles = new HashSet<>();
        for (Poetry poetry : mPoetryList) {
            titles.add(poetry.getType());
        }
        String[] array = new String[titles.size()];
        System.arraycopy(titles.toArray(), 0, array, 0, titles.size());
        Arrays.sort(array, new StringComparator());
        return array;
    }

    public List<Poetry> getPoetryListOfAuth(String auth) {
        List<Poetry> poetryList = new ArrayList<>();
        for (Poetry poetry : mPoetryList) {
            if (poetry.getAuth().equals(auth)) {
                poetryList.add(poetry);
            }
        }
        return poetryList;
    }

    public List<Poetry> getPoetryListByType(String type) {
        List<Poetry> poetryList = new ArrayList<>();
        for (Poetry poetry : mPoetryList) {
            if (poetry.getType().equals(type)) {
                poetryList.add(poetry);
            }
        }
        return poetryList;
    }

    public Poetry getPoetryByTitle(String title) {
        for (Poetry poetry : mPoetryList) {
            if (poetry.getTitle().equals(title)) {
                return poetry;
            }
        }
        throw new RuntimeException("获取失败");
    }

    public void reset() {
        if (mDbOpenHelper != null) {
            mDbOpenHelper.close();
        }
    }

    public List<Poetry> searchPoetry(String newText) {
        String key = newText.trim();
        List<Poetry> result = new LinkedList<>();
        if (TextUtils.isEmpty(key)) {
            return result;
        }
        for (Poetry poetry : mPoetryList) {
            if (poetry.contains(key)) {
                result.add(poetry);
            }
        }
        return result;
    }

    private class PoetryComparator implements Comparator<Poetry> {

        @Override
        public int compare(Poetry lhs, Poetry rhs) {
            char lSort = lhs.getSort();
            char rSort = rhs.getSort();

            return lSort - rSort;
        }
    }

    private class StringComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            char lSort = Pinyin.toPinyin(lhs.charAt(0)).charAt(0);
            char rSort = Pinyin.toPinyin(rhs.charAt(0)).charAt(0);
            return lSort - rSort;
        }
    }

    /**
     * 收藏这首诗
     *
     * @param title       标题
     * @param createdTime 收藏时间点
     */
    public void collectPoetry(String title, long createdTime) {
        ContentValues values = new ContentValues();
        values.put(Footprint._TYPE, Footprint.VALUE_COLLECTION);
        values.put(Footprint._CREATED_TIME, createdTime);
        values.put(Footprint._TITLE, title);
        synchronized (this) {
            final long id = mDbOpenHelper.getWritableDatabase().insert(Footprint.TABLE_NAME, null, values);
            if (id == -1) {
                throw new IllegalStateException("收藏《" + title + "》失败");
            }
        }
    }

    /**
     * 取消收藏这首诗
     *
     * @param title 标题
     */
    public void cancelCollectPoetry(String title) {
        final String whereClause = "title=? and type=?";
        final String[] whereArgs = new String[]{title, "" + Footprint.VALUE_COLLECTION};
        synchronized (object) {
            int row = mDbOpenHelper.getWritableDatabase().delete(Footprint.TABLE_NAME, whereClause, whereArgs);
            if (row == 0) {
                throw new IllegalStateException("不应该没有删除掉这条被收藏的标题为" + "《" + title + "》的诗词");
            } else if (row > 1) {
                throw new IllegalStateException("被收藏的诗词在表中应该不会重复");
            }
        }
    }

    /**
     * 添加用户浏览诗词的历史记录
     *
     * @param title       标题
     * @param createdTime 阅读时间点
     */
    public void addHistoryRecord(String title, long createdTime) {
        ContentValues values = new ContentValues();
        values.put(Footprint._TITLE, title);
        values.put(Footprint._CREATED_TIME, createdTime);
        values.put(Footprint._TYPE, Footprint.VALUE_HISTORY);

        synchronized (object) {
            final long id = mDbOpenHelper.getWritableDatabase().insert(Footprint.TABLE_NAME, null, values);
            if (id == -1) {
                throw new IllegalStateException("添加《" + title + "》的历史记录失败");
            }
        }
    }

    public boolean isCollected(String title) {
        String selections = "title=?" + "and type=?";
        String[] selectionArgs = new String[]{title, "1"};
        synchronized (object) {
            Cursor cursor = null;
            try {
                cursor = mDbOpenHelper.getReadableDatabase().query(Footprint.TABLE_NAME,
                        new String[]{Footprint._ID},
                        selections, selectionArgs, null, null, null);
                final int count = cursor.getCount();
                if (count == 0) {
                    return false;
                } else if (count == 1) {
                    return true;
                } else {
                    throw new IllegalStateException("同一首诗词不应该被收藏多次");
                }
            } finally {
                if (cursor != null
                        && !cursor.isClosed()) {
                    cursor.close();
                }
            }
        }
    }

    private List<Footprint> getFootprintList(int footprintType) {
        String[] columns = new String[]{Footprint._ID, Footprint._CREATED_TIME, Footprint._TITLE, Footprint._TYPE};
        String selection = "type=?";
        String[] selectionArgs = new String[]{"" + footprintType};
        synchronized (object) {
            Cursor cursor = null;
            try {
                cursor = mDbOpenHelper.getReadableDatabase().query(Footprint.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
                Footprint footprint;
                List<Footprint> footprints = new ArrayList<>();
                while (cursor.moveToNext()) {
                    long id = cursor.getLong(cursor.getColumnIndex(Footprint._ID));
                    long createdTime = cursor.getLong(cursor.getColumnIndex(Footprint._CREATED_TIME));
                    String title = cursor.getString(cursor.getColumnIndex(Footprint._TITLE));
                    int type = cursor.getInt(cursor.getColumnIndex(Footprint._TYPE));
                    footprint = new Footprint(id, title, createdTime, type);
                    footprints.add(footprint);
                }
                return footprints;
            } finally {
                if (cursor != null
                        && !cursor.isClosed()) {
                    cursor.close();
                }
            }

        }
    }

    public List<Footprint> getAllHistoryList() {
        return getFootprintList(Footprint.VALUE_HISTORY);
    }

    public List<Footprint> getAllCollectionList() {
        return getFootprintList(Footprint.VALUE_COLLECTION);
    }
}
