package net.omisoft.stores.screens.list.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import net.omisoft.stores.database.StoreDao
import net.omisoft.stores.database.StoresDatabase
import net.omisoft.stores.database.entity.Store

class StoresLocalRepository(database: StoresDatabase) : StoresRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    private val storeDao: StoreDao = database.storeDao()

    override fun getStore(): LiveData<PagedList<Store>> {
        return LivePagedListBuilder(storeDao.getAll(), PAGE_SIZE).build()
    }

    override fun insertAll(stores: List<Store>): Completable {
        return Completable.fromAction { storeDao.insertAll(stores) }
    }
}