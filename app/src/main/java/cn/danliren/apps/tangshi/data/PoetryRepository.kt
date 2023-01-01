package cn.danliren.apps.tangshi.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import cn.danliren.apps.tangshi.data.local.FootprintDao
import cn.danliren.apps.tangshi.data.local.TangShiDatabase
import cn.danliren.apps.tangshi.data.source.PoetryDataSource
import com.github.promeg.pinyinhelper.Pinyin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class PoetryRepository(
    private val context: Context,
    private val footprintDao: FootprintDao
) : PoetryDataSource {

    private val poetryListInMemory = ArrayList<Poetry>()
    private val authListInMemory = ArrayList<String>()
    private val categoryListInMemory = ArrayList<String>()

    override suspend fun queryPoetryList(): List<Poetry> {
        if (poetryListInMemory.isNotEmpty()) {
            return poetryListInMemory.toList()
        }
        withContext(Dispatchers.IO) {
            var poetryList: ArrayList<Poetry>? = null
            val assetManager = context.assets
            try {
                val `is`: InputStream = assetManager.open("tangshi300.xml")
                val factory: XmlPullParserFactory = XmlPullParserFactory.newInstance()
                val xpp: XmlPullParser = factory.newPullParser()
                xpp.setInput(`is`, "UTF-8")
                var eventType: Int = xpp.eventType
                var poetry: Poetry? = null
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_DOCUMENT -> poetryList = ArrayList()
                        XmlPullParser.START_TAG -> if (xpp.name.equals("node")) {
                            poetry = Poetry()
                            poetry.id = poetryList!!.size
                            poetry.isFavorite = false
                        } else if (xpp.name.equals("title")) {
                            xpp.next()
                            val title: String = xpp.text
                            poetry!!.title = title
                            poetry.sort = Pinyin.toPinyin(title[0])[0].toString()
                        } else if (xpp.name.equals("auth")) {
                            xpp.next()
                            poetry!!.auth = xpp.text
                        } else if (xpp.name.equals("type")) {
                            xpp.next()
                            poetry!!.type = xpp.text
                        } else if (xpp.name.equals("content")) {
                            xpp.next()
                            poetry!!.content = xpp.text
                        } else if (xpp.name.equals("desc")) {
                            xpp.next()
                            poetry!!.desc = xpp.text
                        }
                        XmlPullParser.END_TAG -> if (xpp.name.equals("node")) {
                            poetryList?.add(poetry!!)
                            poetry = null
                        }
                    }
                    eventType = xpp.next()
                }
            } catch (e: Exception) {
                Log.e(TAG, "queryPoetryList: ", e)
            }
            poetryListInMemory.addAll(poetryList ?: emptyList())
        }
        return poetryListInMemory.toList()
    }

    override suspend fun queryPoetryListByAuth(auth: String): List<Poetry> {
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        val poetryList: List<Poetry> = withContext(Dispatchers.IO) {
            poetryListInMemory.filter { it.auth == auth }
        }
        return poetryList
    }

    override suspend fun queryPoetryListByCategory(category: String): List<Poetry> {
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        val poetryList: List<Poetry> = withContext(Dispatchers.IO) {
            poetryListInMemory.filter { it.type == category }
        }
        return poetryList
    }

    override suspend fun queryAuthList(): List<String> {
        if (authListInMemory.isNotEmpty()) {
            return authListInMemory.toList()
        }
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        withContext(Dispatchers.IO) {
            val authSet = mutableSetOf<String>()
            poetryListInMemory.forEach { authSet.add(it.auth) }
            authListInMemory.addAll(authSet)
        }
        return authListInMemory.toList()
    }

    override suspend fun queryCategoryList(): List<String> {
        if (categoryListInMemory.isNotEmpty()) {
            return categoryListInMemory.toList()
        }
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        withContext(Dispatchers.IO) {
            val categorySet = mutableSetOf<String>()
            poetryListInMemory.forEach { categorySet.add(it.type) }
            categoryListInMemory.addAll(categorySet)
        }
        return categoryListInMemory.toList()
    }

    override suspend fun queryFavoriteList(): List<Poetry> {
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        return withContext(Dispatchers.IO) {
            val footprints = footprintDao.loadAllByType(Footprint.TYPE_FAVORITE)
            footprints.map { footprint ->
                poetryListInMemory.first { poetry ->
                    footprint.poetryId == poetry.id
                }
            }
        }
    }

    override suspend fun isFavorite(poetry: Poetry): Boolean {
        return withContext(Dispatchers.IO) {
            footprintDao.findByPoetryIdAndType(poetry.id, Footprint.TYPE_FAVORITE) != null
        }
    }

    override suspend fun addFavorite(poetry: Poetry): Boolean {
        return withContext(Dispatchers.IO) {
            val footprint = footprintDao.findByPoetryIdAndType(poetry.id, Footprint.TYPE_FAVORITE)
            // 没收藏才能收藏
            if (footprint == null) {
                footprintDao.insertAll(Footprint(poetry.id, Footprint.TYPE_FAVORITE))
                true
            } else {
                false
            }
        }
    }

    override suspend fun removeFavorite(poetry: Poetry): Boolean {
        return withContext(Dispatchers.IO) {
            val footprint = footprintDao.findByPoetryIdAndType(poetry.id, Footprint.TYPE_FAVORITE)
            // 收藏才能移除
            if (footprint != null) {
                footprintDao.deleteByPoetryIdAndType(poetry.id, Footprint.TYPE_FAVORITE)
                true
            } else {
                false
            }
        }
    }

    override suspend fun queryHistoryList(): List<Poetry> {
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        return withContext(Dispatchers.IO) {
            val footprints = footprintDao.loadAllByType(Footprint.TYPE_HISTORY)
            footprints.map { footprint ->
                poetryListInMemory.first { poetry ->
                    footprint.poetryId == poetry.id
                }
            }
        }
    }

    override suspend fun addHistory(poetry: Poetry) {
        if (poetryListInMemory.isEmpty()) {
            queryPoetryList()
        }
        withContext(Dispatchers.IO) {
            footprintDao.insertAll(Footprint(poetry.id, Footprint.TYPE_HISTORY))
        }
    }

    companion object {

        private const val TAG = "PoetryRepository"

        private var INSTANCE: PoetryRepository? = null

        // TODO: 改用依赖注入框架
        fun getInstance(context: Context): PoetryRepository {
            return INSTANCE ?: synchronized(PoetryRepository::class) {
                INSTANCE ?: PoetryRepository(
                    context,
                    Room.databaseBuilder(
                        context,
                        TangShiDatabase::class.java,
                        "tang_shi_db"
                    ).build().footprintDao()
                )
            }.also { INSTANCE = it }
        }
    }
}