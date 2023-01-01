package cn.danliren.apps.tangshi.data.source

import cn.danliren.apps.tangshi.data.Poetry

interface PoetryDataSource {

    suspend fun queryPoetryList(): List<Poetry>

    suspend fun queryPoetryListByAuth(auth: String): List<Poetry>

    suspend fun queryPoetryListByCategory(category: String): List<Poetry>

    suspend fun queryAuthList(): List<String>

    suspend fun queryCategoryList(): List<String>

    suspend fun queryFavoriteList(): List<Poetry>

    suspend fun isFavorite(poetry: Poetry): Boolean

    /**
     * @return 添加收藏是否成功
     */
    suspend fun addFavorite(poetry: Poetry): Boolean

    /**
     * @return 移除收藏是否成功
     */
    suspend fun removeFavorite(poetry: Poetry): Boolean

    suspend fun queryHistoryList(): List<Poetry>

    suspend fun addHistory(poetry: Poetry)
}