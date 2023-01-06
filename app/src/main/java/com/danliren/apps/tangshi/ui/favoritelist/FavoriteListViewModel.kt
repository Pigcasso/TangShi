package com.danliren.apps.tangshi.ui.favoritelist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danliren.apps.tangshi.data.Poetry
import com.danliren.apps.tangshi.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteListViewModel(
    application: Application
) : BaseViewModel(application) {

    private val _poetryList = MutableLiveData<List<Poetry>>()
    val poetryList: LiveData<List<Poetry>> = _poetryList

    private val _currentPoetry = MutableLiveData<Poetry>()
    val currentPoetry: LiveData<Poetry> = _currentPoetry

    fun loadFavoriteList() {
        viewModelScope.launch {
            val poetryList = repository.queryFavoriteList()
            _poetryList.postValue(poetryList)
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