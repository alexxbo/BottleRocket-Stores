package net.omisoft.stores.common.data.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.omisoft.mvptemplate.BuildConfig
import net.omisoft.stores.common.data.storage.database.entity.StoreEntity

@Database(
    entities = [StoreEntity::class],
    version = BuildConfig.DATABASE_VERSION, exportSchema = false
)
abstract class StoresDatabase : RoomDatabase() {

    abstract fun storeDao(): StoreDao
}