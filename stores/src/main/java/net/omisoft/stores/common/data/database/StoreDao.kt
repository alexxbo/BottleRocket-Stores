package net.omisoft.stores.common.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.omisoft.stores.common.data.database.entity.StoreEntity

@Dao
interface StoreDao {

    @Query("SELECT * FROM ${StoreEntity.ColumnName.TABLE_NAME}")
    fun getAll(): DataSource.Factory<Int, StoreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(storeList: List<StoreEntity>)
}