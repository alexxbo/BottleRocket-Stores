package net.omisoft.stores.common.data.storage

import androidx.paging.PagingSource
import net.omisoft.stores.common.data.storage.database.StoreDao
import net.omisoft.stores.common.data.storage.database.entity.StoreEntity

interface StoresStorage {

    companion object {

        operator fun invoke(
            storeDao: StoreDao
        ): StoresStorage {
            return StoresStorageImpl(storeDao)
        }
    }

    fun getAllStores(): PagingSource<Int, StoreEntity>
    fun insertAllStores(storeList: List<StoreEntity>)
}

private class StoresStorageImpl(
    private val storeDao: StoreDao,
) : StoresStorage {

    override fun getAllStores(): PagingSource<Int, StoreEntity> {
        return storeDao.getAll()
    }

    override fun insertAllStores(storeList: List<StoreEntity>) {
        storeDao.insertAll(storeList)
    }
}