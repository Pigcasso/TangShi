package cn.danliren.apps.tangshi

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import cn.danliren.apps.tangshi.data.Poetry
import cn.danliren.apps.tangshi.data.local.FootprintDao
import cn.danliren.apps.tangshi.data.local.TangShiDatabase
import com.github.promeg.pinyinhelper.Pinyin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStream

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val stringComparator = Comparator<String> { o1, o2 ->
        val c1 = Pinyin.toPinyin(o1[0])[0]
        val c2 = Pinyin.toPinyin(o2[0])[0]
        c1.compareTo(c2)
    }

    private val poetryComparator = Comparator<Poetry> { o1, o2 ->
        o1.sort.compareTo(o2.sort)
    }

    private val _allPoetryList = MutableLiveData<List<Poetry>>()
    val allPoetryList: LiveData<List<Poetry>> = _allPoetryList

    private val _poetryList = MutableLiveData<List<Poetry>>()
    val poetryList: LiveData<List<Poetry>> = _poetryList

    private val _poetrySections = MutableLiveData<Map<String, Int>>()
    val poetrySections: LiveData<Map<String, Int>> = _poetrySections

    private val _authList = MutableLiveData<List<String>>()
    val authList: LiveData<List<String>> = _authList

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList

    private val _selectedPoetry = MutableLiveData<Pair<Int, Poetry>>()
    val selectedPoetry: LiveData<Pair<Int, Poetry>> = _selectedPoetry

    private val footprintDao: FootprintDao

    init {
        val db = Room.databaseBuilder(
            application,
            TangShiDatabase::class.java,
            "tang_shi_db"
        ).build()
        footprintDao = db.footprintDao()
    }

    fun loadPoetryList() {
        viewModelScope.launch {
            val poetryList = queryPoetryList()
            _allPoetryList.postValue(poetryList)
            // 章节
            withContext(Dispatchers.IO) {
                val poetrySections = mutableMapOf<String, Int>()
                poetryList.forEachIndexed { index, poetry ->
                    if (!poetrySections.contains(poetry.sort)) {
                        poetrySections[poetry.sort] = index
                    }
                }
                _poetrySections.postValue(poetrySections)
            }
            // 作者
            withContext(Dispatchers.IO) {
                val authSet = mutableSetOf<String>()
                poetryList.forEach {
                    authSet.add(it.auth)
                }
                _authList.postValue(authSet.sortedWith(stringComparator))
            }
            // 分类
            withContext(Dispatchers.IO) {
                val categorySet = mutableSetOf<String>()
                poetryList.forEach {
                    categorySet.add(it.type)
                }
                _categoryList.postValue(categorySet.sortedWith(stringComparator))
            }
        }
    }

    fun loadPoetryList(type: Int, position: Int) {

    }

    fun loadPoetryByPosition(position: Int) {

    }

    /**
     * 添加或移除收藏
     */
    fun toggleFavorite(position: Int, poetry: Poetry) {

    }

    /**
     * 添加到历史记录
     */
    fun addToHistory(poetry: Poetry) {

    }

    private suspend fun queryPoetryList(): List<Poetry> {
        var poetryList: ArrayList<Poetry>? = null
        withContext(Dispatchers.IO) {
            val context: Context = getApplication()
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
            } finally {
                poetryList?.sortWith(poetryComparator)
            }
        }
        return poetryList ?: emptyList()
    }
}

private const val TAG = "MainViewModel"