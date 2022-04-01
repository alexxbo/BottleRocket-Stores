package net.omisoft.stores.screens.list.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import net.omisoft.stores.common.data.database.StoreDao
import net.omisoft.stores.common.data.database.entity.StoreEntity
import net.omisoft.stores.common.data.database.entity.toStore
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.api.model.toStoreEntity

interface StoresRepository {

    companion object {

        operator fun invoke(
            storeDao: StoreDao,
            api: StoresApi,
        ): StoresRepository {
            return StoresRepositoryImpl(storeDao, api)
        }
    }

    fun getStore(): LiveData<PagedList<Store>>
    fun refreshStores(): Completable
}

private class StoresRepositoryImpl(
    private val storeDao: StoreDao,
    private val api: StoresApi,
) : StoresRepository {

    companion object {
        private const val PAGE_SIZE = 30
    }

    override fun getStore(): LiveData<PagedList<Store>> {
        return LivePagedListBuilder(
            dataSourceFactory = storeDao.getAll().map { it.toStore() },
            pageSize = PAGE_SIZE
        ).build()
    }

    override fun refreshStores(): Completable {
        return api.getStores()
            .map { response ->
                response.stores.map { it.toStoreEntity() }
            }
            .flatMapCompletable { insertAll(it) }
    }

    private fun insertAll(stores: List<StoreEntity>): Completable {
        return Completable.fromAction { storeDao.insertAll(stores) }
    }
}