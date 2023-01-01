package cn.danliren.apps.tangshi.ui.authlist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.danliren.apps.tangshi.data.Poetry
import cn.danliren.apps.tangshi.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthListViewModel(
    application: Application
) : BaseViewModel(application) {

    private val _authList = MutableLiveData<List<String>>()
    val authList: LiveData<List<String>> = _authList

    private val _poetryList = MutableLiveData<List<Poetry>>()
    val poetryList: LiveData<List<Poetry>> = _poetryList

    private val _poetrySections = MutableLiveData<Map<String, Int>>()
    val poetrySections: LiveData<Map<String, Int>> = _poetrySections

    private val _currentPoetry = MutableLiveData<Poetry>()
    val currentPoetry: LiveData<Poetry> = _currentPoetry

    fun loadAuthList() {
        viewModelScope.launch {
            val authList = repository.queryAuthList()
            _authList.postValue(authList)
        }
    }

    fun loadPoetryListByAuth(auth: String) {
        viewModelScope.launch {
            val poetryList = repository.queryPoetryListByAuth(auth).sortedWith(poetryComparator)
            val poetrySections = mutableMapOf<String, Int>()
            poetryList.forEachIndexed { index, poetry ->
                if (!poetrySections.contains(poetry.sort)) {
                    poetrySections[poetry.sort] = index
                }
            }
            _poetryList.postValue(poetryList)
            _poetrySections.postValue(poetrySections)
        }
    }

    fun isFavorite(poetry: Poetry) {
        viewModelScope.launch {
            poetry.isFavorite = repository.isFavorite(poetry)
            _currentPoetry.postValue(poetry)
        }
    }

    fun toggleFavorite(poetry: Poetry) {
        viewModelScope.launch {
            // 当前是收藏则尝试移除，当前没收藏则尝试收藏
            val success = if (poetry.isFavorite) {
                repository.removeFavorite(poetry)
            } else {
                repository.addFavorite(poetry)
            }
            if (success) {
                poetry.isFavorite = !poetry.isFavorite
                _currentPoetry.postValue(poetry)
            }
        }
    }
}