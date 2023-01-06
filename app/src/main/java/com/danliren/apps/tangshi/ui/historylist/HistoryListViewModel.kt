package com.danliren.apps.tangshi.ui.historylist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.danliren.apps.tangshi.data.Poetry
import com.danliren.apps.tangshi.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class HistoryListViewModel(
    application: Application
) : BaseViewModel(application) {

    private val _poetryList = MutableLiveData<List<Poetry>>()
    val poetryList: LiveData<List<Poetry>> = _poetryList

    fun loadHistoryList() {
        viewModelScope.launch {
            _poetryList.postValue(repository.queryHistoryList())
        }
    }
}