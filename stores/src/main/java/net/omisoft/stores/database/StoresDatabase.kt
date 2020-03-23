package net.omisoft.stores.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.omisoft.mvptemplate.BuildConfig
import net.omisoft.stores.database.entity.Store

@Database(entities = [Store::class],
        version = BuildConfig.DATABASE_VERSION, exportSchema = false)
abstract class StoresDatabase : RoomDatabase() {

    abstract fun storeDao(): StoreDao
}


private lateinit var INSTANCE: StoresDatabase

fun getDatabase(context: Context): StoresDatabase {
    synchronized(StoresDatabase::class) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                            StoresDatabase::class.java,
                            BuildConfig.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }
    return INSTANCE
}