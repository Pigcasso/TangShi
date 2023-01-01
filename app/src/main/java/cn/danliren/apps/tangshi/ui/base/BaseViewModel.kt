package cn.danliren.apps.tangshi.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cn.danliren.apps.tangshi.data.Poetry
import cn.danliren.apps.tangshi.data.PoetryRepository
import com.github.promeg.pinyinhelper.Pinyin

abstract class BaseViewModel(
    application: Application
) : AndroidViewModel(application) {

    protected val repository: PoetryRepository = PoetryRepository.getInstance(application)

    protected val stringComparator = Comparator<String> { o1, o2 ->
        val c1 = Pinyin.toPinyin(o1[0])[0]
        val c2 = Pinyin.toPinyin(o2[0])[0]
        c1.compareTo(c2)
    }

    protected val poetryComparator = Comparator<Poetry> { o1, o2 ->
        o1.sort.compareTo(o2.sort)
    }
}