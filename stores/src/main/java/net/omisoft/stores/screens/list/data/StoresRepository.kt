package net.omisoft.stores.screens.list.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.flowable
import io.reactivex.Completable
import io.reactivex.Flowable
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

    fun getStore(): Flowable<PagingData<Store>>
    fun refreshStores(): Completable
}

private class StoresRepositoryImpl(
    private val storeDao: StoreDao,
    private val api: StoresApi,
) : StoresRepository {

    companion object {
        private const val PAGE_SIZE = 15
    }

    override fun getStore(): Flowable<PagingData<Store>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { storeDao.getAll() }
        )
            .flowable
            .map { pagingData -> pagingData.map { it.toStore() } }
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