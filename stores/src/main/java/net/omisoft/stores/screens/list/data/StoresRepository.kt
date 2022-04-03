package net.omisoft.stores.screens.list.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import net.omisoft.stores.common.coroutines.DispatcherProvider
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
            dispatchers: DispatcherProvider
        ): StoresRepository {
            return StoresRepositoryImpl(storeDao, api, dispatchers)
        }
    }

    fun getStore(): Flow<PagingData<Store>>
    suspend fun refreshStores(): Result<Unit>
}

private class StoresRepositoryImpl(
    private val storeDao: StoreDao,
    private val api: StoresApi,
    private val dispatchers: DispatcherProvider,
) : StoresRepository {

    companion object {
        private const val PAGE_SIZE = 15
    }

    override fun getStore(): Flow<PagingData<Store>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { storeDao.getAll() }
        )
            .flow
            .map { pagingData -> pagingData.map { it.toStore() } }
    }

    override suspend fun refreshStores(): Result<Unit> = withContext(dispatchers.ioDispatcher) {
        runCatching {
            val response = api.getStores()
            val storeEntities = response.stores.map { it.toStoreEntity() }
            insertAll(storeEntities)
        }
            .onSuccess { return@withContext Result.success(Unit) }
            .onFailure { return@withContext Result.failure(it) }
    }

    private fun insertAll(stores: List<StoreEntity>) {
        storeDao.insertAll(stores)
    }
}