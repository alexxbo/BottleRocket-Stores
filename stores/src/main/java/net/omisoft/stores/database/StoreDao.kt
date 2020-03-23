package net.omisoft.stores.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.omisoft.stores.database.entity.Store

@Dao
interface StoreDao {

    @Query("SELECT * FROM ${Store.ColumnName.TABLE_NAME}")
    fun getAll(): DataSource.Factory<Int, Store>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(storeList: List<Store>)
}