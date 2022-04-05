package net.omisoft.stores.screens.list.data

import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import net.omisoft.stores.common.data.model.Store
import net.omisoft.stores.common.data.storage.StoresStorage
import net.omisoft.stores.common.data.storage.database.entity.StoreEntity
import net.omisoft.stores.common.data.storage.database.entity.toStore
import net.omisoft.stores.common.util.DispatcherProvider
import net.omisoft.stores.screens.list.api.StoresApi
import net.omisoft.stores.screens.list.api.model.toStoreEntity

interface StoresRepository {

    companion object {

        operator fun invoke(
            storage: StoresStorage,
            api: StoresApi,
            dispatchers: DispatcherProvider
        ): StoresRepository {
            return StoresRepositoryImpl(storage, api, dispatchers)
        }
    }

    fun getStore(): Flow<PagingData<Store>>
    suspend fun refreshStores(): Result<Unit>
}

private class StoresRepositoryImpl(
    private val storage: StoresStorage,
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
            pagingSourceFactory = { storage.getAllStores() }
        )
            .flow
            .map { pagingData -> pagingData.map { it.toStore() } }
            .flowOn(dispatchers.ioDispatcher)
    }

    override suspend fun refreshStores(): Result<Unit> = withContext(dispatchers.ioDispatcher) {
        return@withContext runCatching {
            val response = api.getStores()
            val storeEntities = response.stores.map { it.toStoreEntity() }
            insertAll(storeEntities)
        }
    }

    private fun insertAll(stores: List<StoreEntity>) {
        storage.insertAllStores(stores)
    }
}