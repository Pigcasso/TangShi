package cn.danliren.apps.tangshi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import cn.danliren.apps.tangshi.data.Footprint

@Database(
    entities = [
        Footprint::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class TangShiDatabase : RoomDatabase() {
    abstract fun footprintDao(): FootprintDao
}