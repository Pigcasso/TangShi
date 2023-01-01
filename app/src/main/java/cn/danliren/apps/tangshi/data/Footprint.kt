package cn.danliren.apps.tangshi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * 历史记录和收藏记录的实体类
 */
@Entity(tableName = "footprint")
data class Footprint(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val poetryId: Int,
    @ColumnInfo(name = "created_time")
    val createdTime: Long,
    val type: Int
) {

    @Ignore
    constructor(poetryId: Int, type: Int) : this(
        null,
        poetryId = poetryId,
        createdTime = System.currentTimeMillis(),
        type = type
    )

    companion object {
        const val TYPE_FAVORITE = 0
        const val TYPE_HISTORY = 1
    }
}
